package it.polimi.ingsw.cg15.networking;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author MMP - LMR
 * The Remote Game Manager.
 */
public interface GameManagerRemote  extends Remote{

    /**
     * Dispatch the various messages.
     * @param e The event.
     * @return a new event with return values.
     * @throws RemoteException
     */
    public Event dispatchMessage(Event e)  throws RemoteException;

    /**
     * @param e The event.
     * @return The game list.
     * @throws RemoteException
     */
    public Event getGameList(Event e)  throws RemoteException;

    /**
     * @param e The event.
     * @return The game info.
     * @throws RemoteException
     */
    public Event getGameInfo(Event e) throws RemoteException;

    /**
     * @param e The event.
     * @return a new event with return values.
     * @throws RemoteException
     */
    public Event eventHandler(Event e) throws RemoteException; 

    /**
     * @param e The event.
     * @return a new event with return values.
     * @throws RemoteException
     */
    public Event startGame(Event e) throws RemoteException;

    /**
     * @return the client token.
     * @throws RemoteException
     */
    public Event getClientToken() throws RemoteException;

    /**
     * @param e The event.
     * @return a new event with return values.
     * @throws RemoteException
     */
    public Event joinGame(Event e) throws RemoteException; 

    /**
     * @param e The event.
     * @return a new event with return values.
     * @throws RemoteException
     */
    public Event createGame(Event e) throws RemoteException;

}
