package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{

    private Socket socket;

    public ClientHandler(Socket s){
        this.socket=s;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        try {


            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            
            InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
            
            
           
           // while (socketAlive){
                System.out.println("socketAlive");
                char[] buffer = new char[1024];

                String message = "";
             
                int num = inReader.read(buffer);
                message = new String(buffer);
                message = message.substring(0,num);
                System.out.println("\nRAW MESSAGE:\n"+message+"\n\n");
               
                if(message.contains("move")){
                    System.out.println("move");
                }

              Event request = NetworkProxy.JSONToEvent(message);
              ServerLogger.log("RICHIESTA: "+request.toString()+"\n");

              Event req1 = new Event(request, "");
              Event response = GameManager.getInstance().eventHandler(req1);


                
              
              String json = NetworkProxy.eventToJSON(response);
              socketOut.println(json);
              ServerLogger.log("RISPOSTA: "+response.toString()+"\n");
              System.out.println(response.toString());
              
                    socketOut.flush();
                
            //}
            inReader.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
