package it.polimi.ingsw.cg15;


import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.networking.Server;

import java.io.IOException;

public class MainServer {




    public static void main(String[] args) throws IOException {
        
        ServerGUI serverGUI = new ServerGUI();
        Thread serverGUIThread = new Thread(serverGUI);
        
        Server server = new Server(serverGUI);
        Thread serverThread = new Thread(server);
        
        serverGUI.setServer(server);
        
        serverThread.start();
        
      
        serverGUIThread.start();
        
       
        
    }
}
