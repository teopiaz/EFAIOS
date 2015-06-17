package it.polimi.ingsw.cg15.networking.pubsub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SubscriberRMIInterface extends Remote {
	
	public void dispatchMessage(String msg) throws RemoteException;

}
