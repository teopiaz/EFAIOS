package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;
import it.polimi.ingsw.cg15.networking.pubsub.SubscriberThread;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ClientGameCLI {

    private static SocketCommunicator server;
    private static GameManagerRemote gmRemote;
    private static ClientToken ctoken;
    private static boolean isStarted = false;
    private String ip="127.0.0.1";
    private int port = 1337;
    
    private static int currentPlayerId;

    String playerType;
    String currentPosition;
    int playerNumber;
    int cardNumber;
    private boolean myTurn;
    private boolean hasMove=false;
    Scanner scanner = new Scanner(System.in);


    public ClientGameCLI(ClientToken ctoken, SocketCommunicator server, GameManagerRemote gmRemote) {
        this.ctoken = ctoken;
        this.server = server;
        this.gmRemote = gmRemote;



    }

    public static void notifyStart(){
        isStarted=true;
    }


    public void start(){
        Scanner scanner = new Scanner(System.in);
        boolean test = true;
        while(true){

            if(isStarted){
                if(test){
                getMap();
                getPlayerInfo();
                getTurnInfo();
                System.out.println("Game Started");
                System.out.println(myTurn());
                test=false;
                }
                if(myTurn()){
                    System.out.println("E' il tuo turno");
                    getPlayerInfo();
                    debugPrintPlayerInfo();
                    
                    if(!hasMove){
                    move();
                   
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    endTurn();
                    debugPrintPlayerInfo();
                    }

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
    
    private void debugPrintPlayerInfo(){
        System.out.println("player number: "+playerNumber+"\n"+
                "player type: "+playerType+"\n"+
                "num cards: "+cardNumber+"\n"+
                "current position: "+currentPosition+"\n");

    }
    
    public static void debugPrint(String s){
        System.out.println(s);
    }

    private void endTurn() {
        System.out.println("ENDTURN");
        Event e = new Event(ctoken,"endturn",null);
        Event result;
        result = send(e);  
        
        if(result.actionResult()){
            System.out.println("FINTE TURNO");
            hasMove = false;
        }
        
    }

    private void getTurnInfo() {
        Event e = new Event(ctoken,"getturninfo",null);
        Event result;
        result = send(e);
        this.currentPlayerId =Integer.parseInt( result.getRetValues().get("currentplayer"));

    }

    private boolean myTurn() {
        return currentPlayerId == playerNumber;
    }

    private void getPlayerInfo() {        

        Event e = new Event(ctoken,"getplayerinfo",null);
        Event result;
        result = send(e);
        this.playerNumber =Integer.parseInt( result.getRetValues().get("playernumber"));
        this.currentPosition = result.getRetValues().get("currentposition");
        this.cardNumber = Integer.parseInt( result.getRetValues().get("cardnumber"));
        this.playerType = result.getRetValues().get("playertype");
         
        System.out.println(result);

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
        //System.out.println(NetworkProxy.eventToJSON(result));
    }

    private void move() {
        System.out.println("DESTINATION:");
        String destination="";
        Scanner scanner = new Scanner(System.in);
       
        if(scanner.hasNextLine()){
        destination = scanner.nextLine();
        System.out.println("end scanner");
        }else{
            System.out.println("no");
        }
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event e = new Event(ctoken,"move",args);
        Event result;

        result = send(e);
        if(result.actionResult()){
            System.out.println(result);
            currentPosition=result.getRetValues().get("destination");
            System.out.println("DEST: "+currentPosition);
            hasMove=true;

        }else{
            System.out.println(NetworkProxy.eventToJSON(result));
            hasMove=false;
        }
       scanner.close();
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

    public static void setCurrentPlayer(int currentPlayer) {
        currentPlayerId = currentPlayer;
    }

}
