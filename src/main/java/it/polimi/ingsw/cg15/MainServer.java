package it.polimi.ingsw.cg15;


import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;
import it.polimi.ingsw.cg15.networking.Server;
import it.polimi.ingsw.cg15.networking.ServerRMI;
import it.polimi.ingsw.cg15.networking.ServerSock;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

public class MainServer {




    public static void main(String[] args) throws IOException, AlreadyBoundException {
        
        ServerGUI serverGUI = new ServerGUI();
        ServerLogger logger = new ServerLogger(serverGUI);
        Server serverSocket = new ServerSock();
        Server serverRMI = new ServerRMI();
        Broker broker = Broker.getInstance();
        
        Thread serverSocketThread = new Thread(serverSocket);

        serverGUI.setServer(serverSocket,serverRMI,broker);
        

        serverSocketThread.start();
        try {
            SwingUtilities.invokeAndWait(serverGUI);
        } catch (InvocationTargetException | InterruptedException e) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, "Invocation Target Exception", e);
            e.printStackTrace();
        }

      
    }
    
}
