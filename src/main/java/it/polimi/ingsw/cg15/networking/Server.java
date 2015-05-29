package it.polimi.ingsw.cg15.networking;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public interface Server extends Runnable{
    public void startServer();
    public void stopServer();
    
}
