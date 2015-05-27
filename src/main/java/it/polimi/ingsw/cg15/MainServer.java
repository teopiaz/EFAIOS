package it.polimi.ingsw.cg15;


import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.networking.Server;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

public class MainServer {




    public static void main(String[] args) throws IOException {
        
        ServerGUI serverGUI = new ServerGUI();
      //  Thread serverGUIThread = new Thread(serverGUI);
        
        Server server = new Server(serverGUI);
        Thread serverThread = new Thread(server);
        
        serverGUI.setServer(server);
        

        serverThread.start();
        
        try {
            SwingUtilities.invokeAndWait(serverGUI);
        } catch (InvocationTargetException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       // serverGUIThread.start();
        
       
        
    }
}
