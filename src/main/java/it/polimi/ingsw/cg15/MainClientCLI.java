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
  
    
    public static void main(String[] args) { 
        ClientLobbyCLI client=null;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("1)Socket\n2)RMI");
        
        
        String choice = scanner.nextLine();
        if(choice.equals("1")){
             client = ClientLobbyCLI.getClientSocket("127.0.0.1", 1337);
        }
        else{
             try {
                client = ClientLobbyCLI.getClientRMI();
            } catch (RemoteException | MalformedURLException | AlreadyBoundException
                    | NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
        while(true){
           if(joined == false)
            client.menu();
            System.out.println("lol");
        }
    }

}
