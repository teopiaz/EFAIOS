package it.polimi.ingsw.cg15;


import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.networking.Server;

import java.io.IOException;

public class MainServer {




    public static void main(String[] args) throws IOException {

        Server server = new Server();
        Thread serverThread = new Thread(server);
        serverThread.start();
        
        ServerGUI serverGUI = new ServerGUI(server);
        Thread serverGUIThread = new Thread(serverGUI);
        serverGUIThread.start();
        
       
        
    }
}
