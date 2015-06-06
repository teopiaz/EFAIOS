package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.MainServer;
import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerSock implements Server{

    private final int PORT = 1337;
    private boolean isStarted = false;
    Object lock = new Object();

    private ServerSocket serverSocket;
    static ExecutorService executor = Executors.newCachedThreadPool();
    
    public ServerSock(){
    }


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


    public void startServer() {
        synchronized (lock) {


            if (!isStarted) {
                ServerLogger.log("Starting Socket Server on port" + PORT);

                try {
                    serverSocket = new ServerSocket(PORT);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                isStarted = true;
                ServerLogger.log("start server==" + isStarted);

            }
            else{
                ServerLogger.log("Socket Server already up");

            }
        }
    }

    public void stopServer() {
   

            if (isStarted) {

                ServerLogger.log("Stopping Socket Server");

                try {
                    serverSocket.close();
                    isStarted = false;

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            else{
                ServerLogger.log("Nothing to stop");


            }
        }
    

}