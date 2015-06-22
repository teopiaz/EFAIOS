package it.polimi.ingsw.cg15.networking.pubsub;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author MMP - LMR
 * The broker RMI interface.
 */
public interface BrokerRMIInterface extends Remote {

    /**
     * Subscribe to a topic.
     * @param topic The topic.
     * @param r The RMI subscriber interface.
     * @throws RemoteException
     */
    public void subscribe(String topic, SubscriberRMIInterface r) throws RemoteException;
}
