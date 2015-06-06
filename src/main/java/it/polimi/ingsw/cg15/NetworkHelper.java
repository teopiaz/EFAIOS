package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.MainClientCLI;
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
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

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




    @Override
    public void stampa(String messaggio) {
        System.out.println(messaggio);
    }







}
