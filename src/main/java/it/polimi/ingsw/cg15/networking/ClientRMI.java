package it.polimi.ingsw.cg15.networking;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class ClientRMI {
    
    private final int PORT=1099;
    private final String HOST="127.0.0.1";
    private final String NOMEOGGETTO = "gm";
    
    public GameManagerRemote connect() throws RemoteException, NotBoundException {        
        System.out.println("CLIENT: mi connetto al registro:");
        //scarico il registro dal server
        Registry registry = LocateRegistry.getRegistry(HOST,PORT);
        //cerco nel registro remoto l'oggetto che ha un certo nome
        GameManagerRemote serverStubRemoto = (GameManagerRemote) registry.lookup(NOMEOGGETTO);
        //ora posso chiamare su serverStubRemoto i metodi 
        return serverStubRemoto;
    }
    
    

}
