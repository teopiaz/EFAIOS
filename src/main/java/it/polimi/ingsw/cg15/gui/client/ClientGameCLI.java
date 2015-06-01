package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;

import java.io.IOException;
import java.net.Socket;

public class ClientGameCLI {

    private static SocketCommunicator server;
    private static GameManagerRemote gmRemote;
    private static ClientToken ctoken;
    private static boolean isStarted = false;
    private String ip="127.0.0.1";
    private int port = 1337;
    
    String playerType;
    String currentPosition;
    int playerNumber;
    int cardNumber;


    public ClientGameCLI(ClientToken ctoken, SocketCommunicator server, GameManagerRemote gmRemote) {
        this.ctoken = ctoken;
        this.server = server;
        this.gmRemote = gmRemote;


    }

    public static void notifyStart(){
        isStarted=true;
    }


    public void start(){
        while(true){
                        
            if(isStarted){

                System.out.println("sono dentro al gioco ");
                
                //getMap();
                getPlayerInfo();
                
                System.out.println("player number: "+playerNumber+"\n"+
                                   "player type: "+playerType+"\n"+
                                   "num cards: "+cardNumber+"\n"+
                                   "current position: "+currentPosition+"\n");
                        
                        
                
                
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
   

            }
            
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void getPlayerInfo() {        
        
        Event e = new Event(ctoken,"getplayerinfo",null);
        Event result;
        result = send(e);
        
        this.playerNumber =Integer.parseInt( result.getRetValues().get("playernumber"));
        this.currentPosition = result.getRetValues().get("currentposition");
        this.cardNumber = Integer.parseInt( result.getRetValues().get("cardnumber"));
        this.playerType = result.getRetValues().get("playertype");
        
        /*
         * numero 1
         * tipo Alien/Human/Superalien
         * currentposition A03
         * numcard = 2
         */
        
    }

    private void getMap() {
       
        Event e = new Event(ctoken,"getmap",null);
        Event result;

        result = send(e);
        System.out.println(NetworkProxy.eventToJSON(result));
    }
    
    
    public Event send(Event e){

        Socket socket = null;
        try {
            socket = new Socket(ip, port);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        server = new SocketCommunicator(socket);

        String toSend = NetworkProxy.eventToJSON(e);
        server.send(toSend);

        String response = server.receive();
        server.close();

        return NetworkProxy.JSONToEvent(response);
    }

}
