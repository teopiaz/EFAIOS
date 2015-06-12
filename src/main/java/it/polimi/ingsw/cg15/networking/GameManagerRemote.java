package it.polimi.ingsw.cg15.networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameManagerRemote  extends Remote{

    public Event dispatchMessage(Event e)  throws RemoteException;
    public Event getGameList(Event e)  throws RemoteException;
    public Event getGameInfo(Event e) throws RemoteException;
    public Event eventHandler(Event e) throws RemoteException; 
    public Event startGame(Event e) throws RemoteException;
    public Event getClientToken() throws RemoteException;
    public Event joinGame(Event e) throws RemoteException; 
    public Event createGame(Event e) throws RemoteException;
    
    
}
