package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;
import it.polimi.ingsw.cg15.networking.pubsub.BrokerRMI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The RMI server class. It implements the connection through RMI.
 */
public class ServerRMI implements Server {

    /**
     * The port for communication.
     */
    private final int PORT=1099;
    
    /**
     * A registry.
     */
    private Registry registry;
    
    /**
     * A page of the register.
     */
    private final String REG = "gm";
    
    /**
     * The RMI server. Here it is created the register.
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public ServerRMI() throws RemoteException, AlreadyBoundException{
        registry = LocateRegistry.createRegistry(PORT);
        System.out.println("CREO IL REGISTRO");
       
        
    }
    
    /**
     * I create a new server RMI.
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public void startServerRMI() throws RemoteException, AlreadyBoundException{
        //creo un istanza dell'oggetto concreta
        GameManager gm = GameManager.getInstance();
        //Creo un oggetto remoto che è soltanto l'interfaccia dell'oggetto di prima
        //(ServerStubRemote) UnicastRemoteObject.exportObject(NOME_OGGETTO_CONCRETO, 0)
        GameManagerRemote gameManagerRemote = (GameManagerRemote) UnicastRemoteObject.exportObject(gm, 0);
        //Associo l'oggetto ad una "pagina" del registro
        registry.bind(REG, gameManagerRemote);
        ServerLogger.log("bind remote registry");
    }

    /**
     * Run the RMI server.
     */
    @Override
    public void run() {
        

    	
    	
    	
    }

    /**
     * Start the RMI server.
     */
    @Override
    public void startServer() {
        try {
            startServerRMI();
            ServerLogger.log("RMI Server Started");
        } catch (RemoteException | AlreadyBoundException e) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, "RemoteException | AlreadyBoundException", e);
        }
    }

    /**
     * Stop the RMI server.
     */
    @Override
    public void stopServer() {
        // TODO Auto-generated method stub
        ServerLogger.log("RMI Server Stopped");
    }
    
}
