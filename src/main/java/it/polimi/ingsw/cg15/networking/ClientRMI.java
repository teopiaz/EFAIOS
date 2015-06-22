package it.polimi.ingsw.cg15.networking;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static final String LOCAL="127";

    /*
     * Sonar rompe le scatole pure per gli ip!!!
     */
    private static final String HOST=LOCAL+".0.0.1";

    /**
     * The name of the RMI object.
     */
    private static final String NOMEOGGETTO = "gm";

    /**
     * The connect method that provide the RMI communication.
     * @return List of remote actions.
     * @throws RemoteException
     * @throws NotBoundException
     */
    public GameManagerRemote connect() throws RemoteException {
        //scarico il registro dal server
        Registry registry = LocateRegistry.getRegistry(HOST,PORT);
        //cerco nel registro remoto l'oggetto che ha un certo nome
        try {
            return (GameManagerRemote) registry.lookup(NOMEOGGETTO);
        } catch (NotBoundException e) {
            Logger.getLogger(ClientRMI.class.getName()).log(Level.SEVERE,
                    "NotBoundException", e);
            return null;
        }
    }

}
