package it.polimi.ingsw.cg15.networking;

import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.gui.server.ServerGUI;
import it.polimi.ingsw.cg15.gui.server.ServerLogger;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class ServerRMI implements Server {

    private final int PORT=1099;
    private Registry registry;
    private final String REG = "gm";
    
    
    public ServerRMI() throws RemoteException, AlreadyBoundException{
        
        //creo il registro
        registry = LocateRegistry.createRegistry(PORT);
        System.out.println("CREO IL REGISTRO");
    }
    
    
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


    @Override
    public void run() {
        // TODO Auto-generated method stub
        
    }


    @Override
    public void startServer() {
        try {
            startServerRMI();
            ServerLogger.log("RMI Server Started");
        } catch (RemoteException | AlreadyBoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }


    @Override
    public void stopServer() {
        // TODO Auto-generated method stub
        ServerLogger.log("RMI Server Stopped");
        
    }
}
