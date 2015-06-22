package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;

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
    private static final int port=1099;
    
    /**
     * A registry.
     */
    private Registry registry;
    
    /**
     * A page of the register.
     */
    private static final String registerName = "gm";
    
    /**
     * The RMI server. Here it is created the register.
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public ServerRMI() throws RemoteException{
        registry = LocateRegistry.createRegistry(port);
    }
    
    /**
     * I create a new server RMI.
     * @throws RemoteException
     * @throws AlreadyBoundException
     */
    public void startServerRMI() throws RemoteException{
        //creo un istanza dell'oggetto concreta
        GameManager gm = GameManager.getInstance();
        //Creo un oggetto remoto che è soltanto l'interfaccia dell'oggetto di prima
        //(ServerStubRemote) UnicastRemoteObject.exportObject(NOME_OGGETTO_CONCRETO, 0)
        GameManagerRemote gameManagerRemote = (GameManagerRemote) UnicastRemoteObject.exportObject(gm, 0);
        //Associo l'oggetto ad una "pagina" del registro
        try {
            registry.bind(registerName, gameManagerRemote);
        } catch (AlreadyBoundException e) {
            ServerLogger.log("EXCEPTION"+e.getMessage());
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "AlreadyBoundException in ServerRMI", e);

        }
        ServerLogger.log("bind remote registry");
    }

    /**
     * Run the RMI server.
     */
    @Override
    public void run() {
        ServerLogger.log("RMI Server is Running");
    }

    /**
     * Start the RMI server.
     */
    @Override
    public void startServer() {
        try {
            startServerRMI();
            ServerLogger.log("RMI Server Started");
        } catch (RemoteException e) {
            Logger.getLogger(ServerRMI.class.getName()).log(Level.SEVERE, "RemoteException | AlreadyBoundException", e);
        }
    }

    /**
     * Stop the RMI server.
     */
    @Override
    public void stopServer() {
        ServerLogger.log("RMI Server Stopped");
    }
    
}
