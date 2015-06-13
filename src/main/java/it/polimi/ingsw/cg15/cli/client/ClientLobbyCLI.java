package it.polimi.ingsw.cg15.cli.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ClientLobbyCLI {

    private SocketCommunicator server;
    private GameManagerRemote gmRemote=null;
    private ClientToken ctoken=null;

    private Map<String,String> gameList = new HashMap<String, String>();

    private NetworkHelper networkHelper;
    Scanner scanner;


    public ClientLobbyCLI(NetworkHelper netHelper){
        this.networkHelper=netHelper;

    }




    public void printGameList(Map<String, String> gameList){
        if(gameList==null || gameList.isEmpty()){
            System.out.println("Nessuna Partita disponibile");
        }else{
            for (Entry<String,String> game : gameList.entrySet()) {
                Map<String, String> retValues = networkHelper.getGameInfo(game.getValue());
                System.out.println(
                        "GameName: "+retValues.get("name")+
                        "\tMap: "+retValues.get("mapName") +
                        "\t"+retValues.get("playercount")+"/8"
                        );


            }
        }
    }



    public void menu(){
        scanner = new Scanner(System.in);
        String action=null;
        boolean exit = false;
        System.out.println(
                "1)Create game"+"\n"
                        + "2)List Game"+"\n"
                        + "3)Join Game"+"\n"
                        + "4)Exit");

        while(scanner.hasNextLine() && !exit){

            System.out.println(
                    "1)Create game"+"\n"
                            + "2)List Game"+"\n"
                            + "3)Join Game"+"\n"
                            + "4)Exit");

            action  = scanner.nextLine();

            switch(action){
            case "1" :{
               actionCreateGame();
                break;
            }
            case "2" :{
             
                printGameList(networkHelper.getGamesList());
                break;
            }
            case "3": {
                actionJoinGame();
                break;
            }

            case "4" :{


                break;
            }


            }

        }
        scanner.close();
    }





    private void actionJoinGame() {
        gameList = networkHelper.getGamesList();
        System.out.println("insert game name");
        String gameName = scanner.nextLine();
        String gameToken = gameList.get(gameName);
        ctoken = new ClientToken(networkHelper.getPlayerToken(), gameToken);
        networkHelper.setToken(ctoken);
        networkHelper.joinGame(gameToken);
        ClientGameCLI gameCLI = new ClientGameCLI(ctoken,networkHelper,server,gmRemote);
        gameCLI.start();
        
    }




    private void actionCreateGame() {
        System.out.println("insert game name");
        String gameName = scanner.nextLine();

        System.out.println("insert map name");
        String mapName = scanner.nextLine();
        networkHelper.createGame(gameName,mapName.toLowerCase());
        
    }



    public void stampa(String messaggio) {
        System.out.println(messaggio);
    }




}
