package it.polimi.ingsw.cg15.networking;

/**
 * @author MMP - LMR
 * The main server class.
 */
public interface Server extends Runnable{
    
    /**
     * Start the server.
     */
    public void startServer();
    
    /**
     * Stop the server.
     */
    public void stopServer();
    
}
