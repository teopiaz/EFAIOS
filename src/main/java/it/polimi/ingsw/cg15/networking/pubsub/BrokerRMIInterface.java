package it.polimi.ingsw.cg15.networking.pubsub;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface BrokerRMIInterface extends Remote {

    public void subscribe(String topic, SubscriberRMIInterface r) throws RemoteException;
}
