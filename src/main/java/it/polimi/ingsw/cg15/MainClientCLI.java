package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.cli.client.ClientLobbyCLI;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClientCLI {

    private static boolean joined = false;

    public static void join(){
        joined = true;
    }


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
            if(joined == false)
                client.menu();
        }
        scanner.close();
    }

}
