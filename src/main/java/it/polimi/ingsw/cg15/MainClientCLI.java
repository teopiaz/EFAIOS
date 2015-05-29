package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.gui.ViewClientInterfaceCLI;
import it.polimi.ingsw.cg15.gui.client.ClientCLIRMI;
import it.polimi.ingsw.cg15.gui.client.ClientCLISocket;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainClientCLI {

  
    
    public static void main(String[] args) { 
        ViewClientInterfaceCLI client=null;
        
        Scanner scanner = new Scanner(System.in);
        System.out.println("1)Socket\n2)RMI");
        String choice = scanner.nextLine();
        if(choice.equals("1")){
             client = new ClientCLISocket("127.0.0.1", 1337);
        }
        else{
             try {
                client = new ClientCLIRMI();
            } catch (RemoteException | MalformedURLException | AlreadyBoundException
                    | NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        
        while(true){
            
            client.menu();
        }
    }

}
