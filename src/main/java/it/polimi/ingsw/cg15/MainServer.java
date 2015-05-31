package it.polimi.ingsw.cg15;


import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;
import it.polimi.ingsw.cg15.networking.Server;
import it.polimi.ingsw.cg15.networking.ServerRMI;
import it.polimi.ingsw.cg15.networking.ServerSock;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;

import javax.swing.SwingUtilities;

public class MainServer {




    public static void main(String[] args) throws IOException, AlreadyBoundException {
        
        ServerGUI serverGUI = new ServerGUI();
        ServerLogger logger = new ServerLogger(serverGUI);
       Server serverSocket = new ServerSock();
        Server serverRMI = new ServerRMI();
        
        Thread serverSocketThread = new Thread(serverSocket);
        
        serverGUI.setServer(serverSocket,serverRMI);
        

        serverSocketThread.start();
        
        try {
            SwingUtilities.invokeAndWait(serverGUI);
        } catch (InvocationTargetException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

       // serverGUIThread.start();
        
       
        
    }
}
