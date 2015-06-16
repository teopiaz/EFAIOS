package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.MainServer;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The server socket class.
 */
public class ServerSock implements Server{

    /**
     * The port for communication.
     */
    private final int PORT = 1337;
    
    /**
     * A variable that says if the server is started or not.
     */
    private boolean isStarted = false;
    
    /**
     * TODO non so che cosa faccia.
     */
    Object lock = new Object();

    /**
     * A new server socket.
     */
    private ServerSocket serverSocket;
    
    /**
     * A new thread pool of executors.
     */
    static ExecutorService executor = Executors.newCachedThreadPool();
    
    /**
     * The constructor.
     */
    public ServerSock(){
    }

    /**
     * Run a new Server Socket.
     */
    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (isStarted==true) {
                    Logger.getLogger(MainServer.class.getName()).log(Level.INFO, "start server==" + isStarted);
                    try {
                        Socket socket = serverSocket.accept();
                        System.out.println(socket);
                       // Submits a Runnable task for execution
                        executor.submit(new ClientHandler(socket));
                    } catch (IOException e) {
                        break;
                    }
                }
            }
        }
    }

    /**
     * Start a server socket.
     */
    @Override
    public void startServer() {
        synchronized (lock) {
            if (!isStarted) {
                ServerLogger.log("Starting Socket Server on port" + PORT);
                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (IOException e) {
                    Logger.getLogger(ServerSock.class.getName()).log(Level.SEVERE, "Io Exception", e);
                }
                isStarted = true;
                ServerLogger.log("start server==" + isStarted);
            }
            else{
                ServerLogger.log("Socket Server already up");
            }
        }
    }

    /**
     * Stop a server socket.
     */
    @Override
    public void stopServer() {
            if (isStarted) {
                ServerLogger.log("Stopping Socket Server");
                try {
                    serverSocket.close();
                    isStarted = false;
                } catch (IOException e) {
                    Logger.getLogger(ServerSock.class.getName()).log(Level.SEVERE, "IO Exception", e);
                }
            }
            else{
                ServerLogger.log("Nothing to stop");
            }
        }
    
}
