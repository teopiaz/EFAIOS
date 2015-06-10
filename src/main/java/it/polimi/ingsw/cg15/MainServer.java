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
import javax.swing.SwingUtilities;

public class MainServer {




    public static void main(String[] args) throws IOException, AlreadyBoundException {
        
        ServerGUI serverGUI = new ServerGUI();
        ServerLogger logger = new ServerLogger(serverGUI);
        Server serverSocket = new ServerSock();
        Server serverRMI = new ServerRMI();
        Server broker = Broker.getInstance();
        
        Thread serverSocketThread = new Thread(serverSocket);
        Thread brokerThread = new Thread(broker);

        serverGUI.setServer(serverSocket,serverRMI,broker);
        

        serverSocketThread.start();
        brokerThread.start();
        try {
            SwingUtilities.invokeAndWait(serverGUI);
        } catch (InvocationTargetException | InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

      
    }
    
}
