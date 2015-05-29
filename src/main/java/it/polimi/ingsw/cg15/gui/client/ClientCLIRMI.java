package it.polimi.ingsw.cg15.gui.client;
import it.polimi.ingsw.cg15.gui.ViewClientInterfaceCLI;
import it.polimi.ingsw.cg15.networking.ClientRMI;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class ClientCLIRMI implements ViewClientInterfaceCLI {


    private ClientToken ctoken = null;
    private GameManagerRemote gmRemote=null;
    private Map<String,String> args;
    private Map<String,String> retValues;
    private Map<String,String> gameList = new HashMap<String, String>();


    public  ClientCLIRMI() throws RemoteException, AlreadyBoundException,MalformedURLException, NotBoundException{
        ClientRMI clientRMI = new ClientRMI();
        gmRemote = clientRMI.connect();
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




    @Override
    public void stampa(String messaggio) {
        System.out.println(messaggio);
    }

    @Override
    public void requestClientToken() {
        Event request = new Event(new ClientToken(null, null), "");
        Event result;
        try {
            result = gmRemote.getClientToken();
            this.ctoken = result.getToken();

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 

    }

    @Override
    public Map<String, String> getGamesList() {
        args=new HashMap<String, String>();

        Event e = new Event(ctoken,"listgame",args);
        Event result = null;
        try {
            result = gmRemote.getGameList(e);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        return result.getRetValues();

    }

    @Override
    public void createGame(String gameName, String mapName) {
        if(ctoken==null){
            requestClientToken();
        }

        args=new HashMap<String, String>();
        args.put("gamename", gameName);
        args.put("mapname", mapName);
        Event e = new Event(ctoken,"creategame",args);
        try {
            Event result = gmRemote.createGame(e);


            System.out.println();
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

    @Override
    public void startGame() {
        if(ctoken==null){
            requestClientToken();
        }
        if(ctoken.getGameToken()==null){
            stampa("Join a game First");
        }
        Event e = new Event(ctoken,"startgame",args);
        Event response;
        try {
            response = gmRemote.startGame(e);

            if(response.getRetValues().get("error")!=null){
                System.out.println("ERRORE: " +response.getRetValues().get("error"));

                
            }
            else{
                if(response.getRetValues().get("return").equals("game_started")){
                    gameMenu();
                }       
            }

        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
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
        try {
            result = gmRemote.getGameInfo(e);
        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return result.getRetValues();
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

    @Override
    public void joinGame(String gameToken) {
        if(ctoken==null){
            requestClientToken();
        }

        ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);

        Event e = new Event(ctoken,"joingame",args);
        Event result;

        try {
            result = gmRemote.joinGame(e);
            stampa("JOIN: "+result.getRetValues().get("return"));

        } catch (RemoteException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

    }

}
