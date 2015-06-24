package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The client handler.
 */
public class ClientHandler implements Runnable{

    /**
     * The socket.
     */
    private Socket socket;

    /**
     * The constructor.
     * @param s The socket.
     */
    public ClientHandler(Socket s){
        this.socket=s;
    }

    /**
     * Run a new Client Handler that handle the received message.
     */
    @Override
    public void run() {
        try {
            PrintWriter socketOut = new PrintWriter(socket.getOutputStream());
            InputStreamReader inReader = new InputStreamReader(socket.getInputStream());
                char[] buffer = new char[8162];
                String message = "";
                int num = inReader.read(buffer);
                message = new String(buffer);
                message = message.substring(0,num);
              Event request = NetworkProxy.jsonToEvent(message);
              ServerLogger.log("RICHIESTA: "+request.toString()+"\n");
              Event req1 = new Event(request, "");
              Event response = GameManager.getInstance().eventHandler(req1);
              String json = NetworkProxy.eventToJSON(response);
              socketOut.println(json);
              ServerLogger.log("RISPOSTA: "+response.toString()+"\n");
                    socketOut.flush();
            inReader.close();
            socketOut.close();
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IOException in ClientHandler", e);
            ServerLogger.log("EXCEPTION: "+e.getMessage()+"\n");

        }
    }
    
}
