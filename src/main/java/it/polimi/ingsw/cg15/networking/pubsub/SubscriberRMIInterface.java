package it.polimi.ingsw.cg15.networking.pubsub;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author MMP - LMR
 * The subscriber RMI interface.
 */
public interface SubscriberRMIInterface extends Remote {

    /**
     * @param msg The message to dispatch.
     * @throws RemoteException
     */
    public void dispatchMessage(String msg) throws RemoteException;

}
