package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.gui.client.ClientGameGUI;
import it.polimi.ingsw.cg15.gui.client.ClientLobbyGUI;
import it.polimi.ingsw.cg15.gui.client.TVeffect;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * @author MMP - LMR
 * The main Graphical User Interface Client.
 */
public class MainClientGUI {
    
    private MainClientGUI() {
    }

    /**
     * The main of GUI.
     * @param args
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws AlreadyBoundException
     * @throws NotBoundException
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException,  AlreadyBoundException, NotBoundException {
        boolean intro = false;
        NetworkHelper netHelper = NetworkHelper.getClientSocket("localhost", 1337);
        Runnable clientTaskGUI = new ClientGameGUI(netHelper);
        Runnable taskLobby = new ClientLobbyGUI(netHelper, clientTaskGUI);
        EventQueue.invokeLater(clientTaskGUI);
        EventQueue.invokeLater(taskLobby);
        if (intro) {
            TVeffect tvEffect = new TVeffect(taskLobby);
            tvEffect.hashCode();
        } else {
            ((ClientLobbyGUI) taskLobby).showGUI();
        }
    }

}
