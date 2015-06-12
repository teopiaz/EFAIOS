package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.cli.client.ClientGameCLI;
import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.ClientRMI;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;
import it.polimi.ingsw.cg15.networking.pubsub.SubscriberThread;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkHelper implements ViewClientInterface {


    private SocketCommunicator server;
    private GameManagerRemote gmRemote=null;
    private ClientToken ctoken=null;
    private Map<String,String> args;
    private Map<String,String> retValues;
    private Map<String,String> gameList = new HashMap<String, String>();

    private SubscriberThread subThread;

    private String ip;
    private int port;
    private final int SOCKET = 1;
    private final int RMI = 2;
    private int type;
    private static NetworkHelper instance = null;


    //costruttore sock
    private NetworkHelper(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.type=SOCKET;

    }



    private NetworkHelper() throws RemoteException, AlreadyBoundException,MalformedURLException, NotBoundException{
        ClientRMI clientRMI = new ClientRMI();
        gmRemote = clientRMI.connect();
        this.type=RMI;

    }

    public static NetworkHelper getInstance(){
        //RMI di default se non costruito precedentemente
        if(instance==null){
            try {
                instance = new NetworkHelper();
            } catch (RemoteException | MalformedURLException | AlreadyBoundException
                    | NotBoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return instance;
    }



    public static NetworkHelper getClientSocket(String ip, int port) {

        return new NetworkHelper(ip,port);
    }

    public static NetworkHelper getClientRMI() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException{

        return new NetworkHelper();
    }


    @Override
    public void requestClientToken() {
        Event e = new Event(new ClientToken(null, null), "requesttoken");
        Event result = null;

        if(type==SOCKET){
            result = send(e);
            this.ctoken= result.getToken();
        }
        if(type==RMI){
            try {
                result = gmRemote.getClientToken();
                this.ctoken = result.getToken();

            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } 
        }
        stampa("TOKEN: "+result.getToken().getPlayerToken());


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


    @Override
    public void createGame(String gameName, String mapName) {
        //TODO: gestione errori
        if(ctoken==null){
            requestClientToken();
        }
        args=new HashMap<String, String>();
        args.put("gamename", gameName);
        args.put("mapname", mapName);
        Event e = new Event(ctoken,"creategame",args);
        Event result;

        if(type==SOCKET){
            result = send(e);
        }
        if(type==RMI){
            try {
                result = gmRemote.createGame(e);


                System.out.println();
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        System.out.println("Game Created: "+ gameName);
    }

    @Override
    public Map<String, String> getGamesList() {

        if(ctoken==null){
            requestClientToken();
        }

        args=new HashMap<String, String>();
        Event e = new Event(ctoken,"listgame",args);
        Event result = null;

        if(type==SOCKET){
            result = send(e);
        }
        if(type==RMI){
            try {
                result = gmRemote.getGameList(e);
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

        return result.getRetValues();

    }


    @Override
    public void joinGame(String gameToken) {
        if(ctoken==null){
            requestClientToken();
        }

        ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);

        Event e = new Event(ctoken,"joingame",args);
        Event result;

        if(type==SOCKET){


            result = send(e);

            if(result.getRetValues().get("error")!=null){
                System.out.println("ERRORE: " +result.getRetValues().get("error"));
            }
            else{
                subThread =  new SubscriberThread(gameToken);
                subThread.start();

                System.out.println(result.getRetValues().get("return"));

            }
        }
        if(type==RMI){
            try {
                result = gmRemote.joinGame(e);
                if(result.getRetValues().get("error")!=null){
                    System.out.println("ERRORE: " +result.getRetValues().get("error"));
                }
                else{
                    //TODO SUBSCRIBER RMI
                    subThread =  new SubscriberThread(gameToken);
                    subThread.start();
                    System.out.println(result.getRetValues().get("return"));
                }

            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

    }




    @Override
    public Map<String, String> getGameInfo(String gameToken) {

        if(ctoken==null){
            requestClientToken();
        }
        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "gameinfo", null);
        Event result=null;   

        if(type==SOCKET){
            result = send(e);

        }
        if(type==RMI){
            try {
                result = gmRemote.getGameInfo(e);
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }

        return result.getRetValues();

    }  

    public String getMap() {

        if(ctoken==null){
            requestClientToken();
        }
        String gameToken = getGameToken();

        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "getmap", null);
        Event result = eventHandler(e);  

        return result.getRetValues().get("map");
    }



    public Event move(String destination) {

        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event e = new Event(ctoken,"move",args);
        return eventHandler(e);  

    }



    public Event askSector(String position) {

        Map<String,String> args = new HashMap<String,String>();
        args.put("position", position);
        Event e = new Event(ctoken,"asksector",args);
        return eventHandler(e);  

    }


    public Event useCard(String card) {

        Map<String,String> args = new HashMap<String, String>();
        args.put("itemcard", card);
        Event e = new Event(ctoken,"useitem",args);

        return eventHandler(e);  


    }



    public void spotlight(String target) {
       
            Map<String,String> args = new HashMap<String, String>();
            args.put("itemcard", "spotlight");
            args.put("target", target); 
            Event e = new Event(ctoken,"useitem",args);
            eventHandler(e);

    }



    public Event  attack() {

        Event e = new Event(ctoken,"attack",null);
        return eventHandler(e);
    }



    public Event endTurn() {
        Event e = new Event(ctoken,"endturn",null);

        return eventHandler(e);

    }



    public Event getPlayerInfo() {        


        Event e = new Event(ctoken,"getplayerinfo",null);

        return eventHandler(e);  


    }


    public int getTurnInfo() {
        Event e = new Event(ctoken,"getturninfo",null);

        Event result = eventHandler(e);  

        return Integer.parseInt( result.getRetValues().get("currentplayer"));

    }


    public List<String> getAvailableCardsList() {
        List<String> cardList = new ArrayList<String>();
        Event e = new Event(ctoken,"getcardlist",null);

        Event result = eventHandler(e);  

        if(result.actionResult()){

            String size =result.getRetValues().get("cardssize");

            for (String action : result.getRetValues().keySet()) {
                if((!action.equals("return")) && (!action.equals("cardssize")) ){
                    cardList.add(action);  
                }


            }
        }
        return cardList;

    }



    public List<String> getAvailableActionsList() {
        List<String> actionList = new ArrayList<String>();
        Event e = new Event(ctoken,"getactionlist",null);


        Event result = eventHandler(e);  

        if(result.actionResult()){

            for (String action : result.getRetValues().keySet()) {
                if(!action.equals("return")){
                    actionList.add(action);  
                }


            }

        }
        return actionList;
    }




    public void setGameToken(String gameToken){
        this.ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);
    }

    public String getGameToken(){
        return this.ctoken.getGameToken();
    }


    @Override
    public void stampa(String messaggio) {
        System.out.println(messaggio);
    }



    public String getPlayerToken() {
        if(ctoken==null){
            requestClientToken();
        }

        return ctoken.getPlayerToken();
    }



    public void setToken(ClientToken ctoken) {
        this.ctoken=ctoken;
    }


    private Event eventHandler(Event e){
        Event result =null;
        if(type==SOCKET){
            result = send(e);

        }
        if(type==RMI){
            try {
                result = gmRemote.eventHandler(e);
            } catch (RemoteException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

        }
        return result;
    }






}
