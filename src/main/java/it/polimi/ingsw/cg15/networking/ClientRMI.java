package it.polimi.ingsw.cg15.networking;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author MMP - LMR
 * The RMI Client class.
 */
public class ClientRMI {

    /**
     * The port for communication.
     */
    private static final int PORT = 1099;

    /**
     * The IP address.
     */
    private static final String HOST="127.0.0.1";

    /**
     * The name of the RMI object.
     */
    private final String NOMEOGGETTO = "gm";

    /**
     * The connect method that provide the RMI communication.
     * @return List of remote actions.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public GameManagerRemote connect() throws RemoteException, NotBoundException {
        //scarico il registro dal server
        Registry registry = LocateRegistry.getRegistry(HOST,PORT);
        //cerco nel registro remoto l'oggetto che ha un certo nome
        GameManagerRemote serverStubRemoto = (GameManagerRemote) registry.lookup(NOMEOGGETTO);
        //ora posso chiamare su serverStubRemoto i metodi 
        return serverStubRemoto;
    }

}
