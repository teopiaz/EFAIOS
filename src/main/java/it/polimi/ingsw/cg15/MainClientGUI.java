package it.polimi.ingsw.cg15;
import it.polimi.ingsw.cg15.gui.client.ClientGameGUI;
import it.polimi.ingsw.cg15.gui.client.ClienLobbyGUI;
import it.polimi.ingsw.cg15.gui.client.TVeffect;

import java.awt.EventQueue;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;


public class MainClientGUI {
	private MainClientGUI(){
		
	}

	public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException {
		// TODO Auto-generated method stub
		
	    boolean intro = false;
	    NetworkHelper netHelper = NetworkHelper.getClientSocket("localhost", 1337);
	    
	   // NetworkHelper netHelper = NetworkHelper.getClientRMI();
	    
        Runnable clientTaskGUI = new ClientGameGUI(netHelper);
	    
	    Runnable taskLobby = new ClienLobbyGUI(netHelper,clientTaskGUI);
        EventQueue.invokeLater(clientTaskGUI);
        EventQueue.invokeLater(taskLobby);
        if(intro){
        new TVeffect(taskLobby);
        }else{
            ((ClienLobbyGUI) taskLobby).showGUI();
        }


        
        


	}


}
