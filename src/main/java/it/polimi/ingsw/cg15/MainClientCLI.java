package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.cli.client.ClientLobbyCLI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * @author MMP - LMR
 * The main Command Line Interface Client.
 */
public class MainClientCLI {

    /**
     * Variable for the join state.
     */
    private static boolean joined = false;

    /**
     * Set joined as true.
     */
    public static void join(){
        joined = true;
    }

    /**
     * The main of CLI.
     * @param args
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws AlreadyBoundException
     * @throws NotBoundException
     */
    public static void main(String[] args) throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException { 
        ClientLobbyCLI client=null;
        NetworkHelper netHelper =null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("1)Socket\n2)RMI");
        boolean isRunning = true;
        String choice = scanner.nextLine();
        switch(choice){
        case "1":
            netHelper = NetworkHelper.getClientSocket("localhost", 1337);
            break;
        case "2":
            netHelper = NetworkHelper.getClientRMI();
            break;
        default:
           System.exit(0);
           break;
       }
        client = new ClientLobbyCLI(netHelper);
        while(isRunning){
            if(!joined)
                client.menu();
        }
        scanner.close();
    }

}
