package it.polimi.ingsw.cg15.networking;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private Socket socket;
    private boolean socketAlive = true;

    public ClientHandler(Socket s){
        this.socket=s;
    }

    public void run() {
        // TODO Auto-generated method stub


        try {

            Scanner socketIn = new Scanner(socket.getInputStream());

            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            
            InputStreamReader inReader = new InputStreamReader(socket.getInputStream());

        //    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            
            
           
            while (socketAlive){
                
                char[] buffer = new char[1024];
                // reads a new Line from the Socket
              //  String line = socketIn.nextLine();
                // writes the line in the screen
              //  System.out.println(line);
                // if the line is equal to quit
                String message = "";
             
                inReader.read(buffer);
                message = new String(buffer);
                message = message.substring(0,message.length()-10);

              
                 // the Server sends to the client the following string
             String   s = "{\"playerToken\": \"d23d2ed223faf3f3a3\",\"gameToken\": \"91ca29jd933d2ed2f3f33\",\"command\": \"move\",\"args\": {\"destination\": \"A02\", \"asdasd\": \"B99\"},\"retValues\": { \"result\": \"true\",\"sectorCard\":\"cardRed\", \"itemCard\":\"itemTeleport\" }}";

                    socketOut.println("ricevuto "+ message+"\n event "+NetworkProxy.JSONToEvent(NetworkProxy.eventToJSON( NetworkProxy.JSONToEvent(s)    )));   //+line.toUpperCase());
                    socketOut.flush();
                
            }
            socketIn.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
