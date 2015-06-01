package it.polimi.ingsw.cg15;


import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.Server;
import it.polimi.ingsw.cg15.networking.ServerRMI;
import it.polimi.ingsw.cg15.networking.ServerSock;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.AlreadyBoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

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

       // serverGUIThread.start();
        
        Scanner stdin = new Scanner(System.in);
        try {
            while (true) {
                String topic = stdin.nextLine(); //gameToken
                
                String inputLine = stdin.nextLine();//il messaggio da pubblicare
                

            }
        }catch(NoSuchElementException e) {}

    }
}
