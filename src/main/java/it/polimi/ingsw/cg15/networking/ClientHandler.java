package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.controller.GameManager;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable{

    private Socket socket;
    private boolean socketAlive = true;

    public ClientHandler(Socket s){
        this.socket=s;
    }

    public void run() {
        // TODO Auto-generated method stub

        try {


            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            
            InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
            
            
           
            while (socketAlive){
                char[] buffer = new char[1024];

                String message = "";
             
                int num = inReader.read(buffer);
                message = new String(buffer);
                message = message.substring(0,num);
                

              Event request = NetworkProxy.JSONToEvent(message);
              Event response = GameManager.getInstance().createGame(request);

              
                
              socketOut.println("request "+request);
              socketOut.println("response "+response);
                    socketOut.flush();
                
            }
            inReader.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
