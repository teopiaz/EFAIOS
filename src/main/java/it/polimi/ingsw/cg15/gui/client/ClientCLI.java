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

        String toSend = NetworkProxy.eventToJSON(e);
        server.send(toSend);

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

    public Map<String, String> getGamesList() {


        if(ctoken==null){
            requestClientToken();
        }

        args=new HashMap<String, String>();

        Event e = new Event(ctoken,"listgame",args);
        Event response = send(e);

        return response.getRetValues();
    }


    public void printGameList(Map<String, String> gameList){
        
        for (Entry<String,String> game : gameList.entrySet()) {
            Map<String, String> retValues = getGameInfo(game.getValue());
            System.out.println(
                    "GameName: "+retValues.get("name")+
                    "\tMap: "+retValues.get("mapName") +
                    "\t"+retValues.get("playercount")+"/8"
                    );
            
        }
        
        
    }
    
    public Map<String, String> getGameInfo(String gameToken){
        
        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "gameinfo", null);
        Event response = send(e);
        return response.getRetValues();
    }
    

    public void joinGame(String gameToken) {

        //{"playerToken": "dkdsulq5e864auhhenl3n7q5g4", "gameToken":"kb4unik0b8735q4ka3179m0vvc"  "command": "joingame",  "args": {    }  ,  "retValues": {}}

        if(ctoken==null){
            requestClientToken();
        }
        
        ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);
        
        Event e = new Event(ctoken,"joingame",args);
        Event response = send(e);
        stampa("JOIN: "+response.getRetValues().get("return"));
        
    }
    
    
    
    
    public void startGame(){
    //{"playerToken": "do85madnn97nq9vn29rccb1c5e", "gameToken":"kb4unik0b8735q4ka3179m0vvc"  "command": "startgame",  "args": {    }  ,  "retValues": {}}
        if(ctoken==null){
            requestClientToken();
        }
        if(ctoken.getGameToken()==null){
            stampa("Join a game First");
        }
        Event e = new Event(ctoken,"startgame",args);
        Event response = send(e);
        for (Entry<String,String> ret : response.getRetValues().entrySet()) {
            stampa("A"+ret.getKey()+ " "+ret.getValue());
        }
        if(response.getRetValues().get("return").equals("game_started")){
            gameMenu();
        }
        
        
    }

    
    
    

    @Override
    public void requestClientToken() {
        //{  "command": "gettoken",  "args": {      }  ,  "retValues": {}}

        args=new HashMap<String, String>();
        Event e = new Event(null, "gettoken",args,args);
        Event response = send(e);
        this.ctoken= response.getToken();
        stampa("TOKEN: "+response.getToken().getPlayerToken());
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
               /* gameList = getGamesList();
                for (Entry<String, String> game : gameList.entrySet()) {
                    System.out.println(game.getKey());
                }*/
                printGameList(getGamesList());
                break;
            }
            case "3": {
                gameList = getGamesList();
                System.out.println("insert game name");
                String gameName = scanner.nextLine();
                String gameToken = gameList.get(gameName);
                joinGame(gameToken);
                break;
            }
            case "4": {
                startGame();
                break;
            }
            

            }

        }
        scanner.close();
    }



    public void gameMenu(){
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "1)Move"+"\n"
                        + "2)UseCard"+"\n"
                        + "");

        String action=null;
        while(scanner.hasNextLine())
        {
            action  = scanner.nextLine();

            switch(action){
                case "1" :{
                    System.out.println("action");
                }
            }
        }
        scanner.close();

    }

}
