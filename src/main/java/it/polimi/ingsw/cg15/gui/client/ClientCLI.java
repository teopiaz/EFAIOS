package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ClientCLI implements ViewClientInterface {

    SocketCommunicator server;
    ClientToken ctoken=null;
    Map<String,String> args;
    Map<String,String> retValues;
    Map<String,String> gameList = new HashMap<String, String>();



    private String ip;
    private int port;

    public ClientCLI(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }



    public static void main(String[] args) { 
        ClientCLI client = new ClientCLI("127.0.0.1", 1337);
        while(true){
            client.menu();
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
        System.out.println("Connection established");

        String toSend = NetworkProxy.eventToJSON(e);
        System.out.println(toSend);
        server.send(toSend);
        System.out.println("aaa");

        String response = server.receive();
        server.close();

        return NetworkProxy.JSONToEvent(response);
    }

    @Override
    public void stampa(String messaggio) {
        System.out.println(messaggio);
    }

    @Override
    public void createGame(String gameName, String mapName) {
        //{"playerToken": "g84b1onftafm21m9okc5u1t089",  "command": "creategame",  "args": {   "gamename":"NOME_PARTITA"    }  ,  "retValues": {}}

        if(ctoken==null){
            requestClientToken();
        }

        args=new HashMap<String, String>();
        args.put("gamename", gameName);
        args.put("mapname", mapName);
        Event e = new Event(ctoken,"creategame",args);
        Event response = send(e);
    }

    private Map<String, String> getGamesList() {


        if(ctoken==null){
            requestClientToken();
        }

        args=new HashMap<String, String>();

        Event e = new Event(ctoken,"listgame",args);
        Event response = send(e);

        return response.getRetValues();
    }


    @Override
    public void requestClientToken() {
        //{  "command": "gettoken",  "args": {      }  ,  "retValues": {}}

        args=new HashMap<String, String>();
        Event e = new Event(null, "gettoken",args,args);
        System.out.println(e);
        Event response = send(e);
        this.ctoken= response.getToken();
    }

    public void menu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "1)Create game"+"\n"
                        + "2)List Game"+"\n"
                        + "3)Join Game"+"\n"
                        + "4)Start Game"+"\n"
                        + "5)Exit");

        String action=null;
        while(scanner.hasNextLine())
        {
            action  = scanner.nextLine();

            switch(action){
            case "1" :{
                System.out.println("insert game name");
                String gameName = scanner.nextLine();

                System.out.println("insert map name");
                String mapName = scanner.nextLine();
                createGame(gameName, mapName);
                break;
            }
            case "2" :{
                gameList = getGamesList();
                for (Entry<String, String> game : gameList.entrySet()) {
                    System.out.println(game.getKey());
                }
                break;
            }

            }

        }
        scanner.close();
    }





}
