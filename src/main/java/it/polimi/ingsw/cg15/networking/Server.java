package it.polimi.ingsw.cg15.networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    private static final int PORT = 1337;

    static ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {

        Logger.getLogger(Server.class.getName()).log(Level.INFO, "Starting Server on port"+ PORT);

        ServerSocket serverSocket = new ServerSocket(PORT);

        while (true) {
            try {

                Socket socket = serverSocket.accept();
                // Submits a Runnable task for execution
                executor.submit(new ClientHandler(socket));
            } catch (IOException e) {
                break;
            }
        }
        // shutdown the executor
        executor.shutdown();
        // closes the ServerSocket
        serverSocket.close();


    }
}
