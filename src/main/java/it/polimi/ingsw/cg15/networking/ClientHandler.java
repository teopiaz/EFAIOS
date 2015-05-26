package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.gui.server.ServerGUI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientHandler implements Runnable{

    private Socket socket;
    private boolean socketAlive = true;
    private ServerGUI gui;

    public ClientHandler(Socket s,ServerGUI gui){
        this.socket=s;
        this.gui=gui;
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
              Event response = GameManager.getInstance().eventHandler(request);

              
                
              socketOut.println("request "+request+"\n");
              socketOut.println("response "+response+"\n");
              gui.stampa(socket.getRemoteSocketAddress()+ " "+request.toString()+"\n");
              gui.stampa(response.toString()+"\n");
              System.out.println(response.toString());
              
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
