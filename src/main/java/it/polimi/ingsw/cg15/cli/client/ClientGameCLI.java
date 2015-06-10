package it.polimi.ingsw.cg15.cli.client;

import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private boolean init = true;
    private Scanner scanner = new Scanner(System.in);
    private List<String> actionList=new ArrayList<String>();
    private boolean hasAttacked;
    private List<String> cardList=new ArrayList<String>();
    private int cardsSize;



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
                if(init){
                    getMap();
                    getPlayerInfo();
                    getTurnInfo();
                    System.out.println("Game Started");
                    System.out.println("E' il mio turno? "+myTurn());
                    init=false;
                }
                if(myTurn()){
                    System.out.println("E' il tuo turno");
                    getPlayerInfo();
                    debugPrintPlayerInfo();
                    getAvailableActionsList();
                    debugPrintActionList();
                    getAvailableCardList();
                    debugPrintCardList();
                    System.out.println("SELEZIONA UN AZIONE");
                    String choice = scanner.nextLine();

                    switch(choice){

                    case "m":
                        move();
                        break;

                    case "a":
                        attack();
                        break;

                    case "e":
                        endTurn();
                        break;

                    case "c":
                        useCardMenu();
                        break;
                    case "ask":
                        askSector();
                        break;

                    default:
                        System.out.println("Azione Non Valida");
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

    private void askSector() {
        boolean validSector=false;
        while(!validSector){
            System.out.println("In quale settore vuoi fare rumore?");
            String position = scanner.nextLine();
            Map<String,String> args = new HashMap<String,String>();
            args.put("position", position);
            Event e = new Event(ctoken,"asksector",args);
            Event result;
            result = send(e);     
            System.out.println(result);
            if(result.actionResult()){
                validSector=true;
            }else{
                System.out.println(result.getRetValues().get("error"));
            }
        }
    }

    private void useCardMenu() {
        getAvailableCardList();
        debugPrintCardList();
        String choice = scanner.nextLine();

        switch(choice){

        case "teleport":
            useCard("teleport");
            break;
        case "adrenaline":
            useCard("adrenaline");
            break;
        case "sedatives":
            useCard("sedatives");
            break;
        case "attack":
            useCard("attack");
            break;
        case "spotlight":
            spotlight();

        }






    }

    private void spotlight() {
        if(cardList.contains("spotlight")){
            System.out.println("Inserisci un settore da illuminare");
            String target = scanner.nextLine();
            Map<String,String> args = new HashMap<String, String>();
            args.put("itemcard", "spotlight");
            args.put("target", target); 
            Event e = new Event(ctoken,"useitem",args);
            Event result;
            System.out.println(e);
            result = send(e);
            System.out.println(result);
        }
        else{
            System.out.println("Non possiedi questa carta");
        }

    }

    private void useCard(String card) {
        if(cardList.contains(card)){
            Map<String,String> args = new HashMap<String, String>();
            args.put("itemcard", card);
            Event e = new Event(ctoken,"useitem",args);
            Event result;
            System.out.println(e);
            result = send(e);
            System.out.println(result);
        }
        else{
            System.out.println("Non possiedi questa carta");
        }

    }

    private void debugPrintCardList() {
        System.out.println("CARTE DISPONIBILI "+cardNumber);
        for (String string : cardList) {
            System.out.println(string);
        }

    }

    private void attack() {
        if(!hasAttacked){


            Event e = new Event(ctoken,"attack",null);
            Event result;

            result = send(e);
            System.out.println(result);
            if(result.actionResult()){
                int killedPlayer =Integer.parseInt(result.getRetValues().get("killcount"));
                if(killedPlayer==0){
                    System.out.println("Nessuna Vittima");
                }
                else{
                    System.out.println("Hai ucciso "+killedPlayer+" giocatori");
                }
                hasAttacked=true;

            }else{
                System.out.println("ERRORE: "+result.getRetValues().get("error"));
                hasAttacked=false;
            }
        }
    }

    private void debugPrintActionList() {
        System.out.println("AZIONI DISPONIBILI");
        for (String string : actionList) {
            System.out.println(string);
        }

    }

    private void getAvailableActionsList() {
        actionList = new ArrayList<String>();
        Event e = new Event(ctoken,"getactionlist",null);
        Event result;
        result = send(e);
        if(result.actionResult()){

            for (String action : result.getRetValues().keySet()) {
                if(!action.equals("return")){
                    actionList.add(action);  
                }


            }
        }

    }

    private void getAvailableCardList() {
        cardList = new ArrayList<String>();
        Event e = new Event(ctoken,"getcardlist",null);
        Event result;
        result = send(e);
        if(result.actionResult()){

            String size =result.getRetValues().get("cardssize");
            cardsSize = Integer.parseInt(size);

            for (String action : result.getRetValues().keySet()) {
                if((!action.equals("return")) && (!action.equals("cardssize")) ){
                    cardList.add(action);  
                }


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
            hasAttacked=false;
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
    }

    private void move() {
        if(!hasMove){
            System.out.println("CURRENT POSITION: "+currentPosition);
            System.out.println("DESTINATION:");
            String destination="";

            destination = scanner.nextLine();


            Map<String,String> args = new HashMap<String,String>();
            args.put("destination", destination);
            Event e = new Event(ctoken,"move",args);
            Event result;

            result = send(e);
            if(result.actionResult()){
                currentPosition=result.getRetValues().get("destination");
                System.out.println("DEST: "+currentPosition);
                hasMove=true;

                if(result.getRetValues().containsKey("asksector")){
                    askSector();
                }
                if(result.getRetValues().containsKey("item")){
                    if(result.getRetValues().get("item").equals("true")){
                        System.out.println("hai pescato la carta "+(result.getRetValues().get("card")));
                    }
                }

            }else{
                System.out.println("ERRORE: "+result.getRetValues().get("error"));
                hasMove=false;
            }
        }
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
