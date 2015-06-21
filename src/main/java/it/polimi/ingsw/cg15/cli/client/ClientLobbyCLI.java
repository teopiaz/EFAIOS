package it.polimi.ingsw.cg15.cli.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * @author MMP - LMR
 * Class that creates the lobby of players waiting to start new games or add to existing ones.
 */
public class ClientLobbyCLI {

    /**
     * The server socket communicator.
     */
    private SocketCommunicator server;
    
    /**
     * The remote game manager.
     */
    private GameManagerRemote gmRemote=null;
    
    /**
     * The client token.
     */
    private ClientToken ctoken=null;

    /**
     * A list with the various games.
     */
    private Map<String,String> gameList = new HashMap<String, String>();

    /**
     * The network helper for the communications.
     */
    private NetworkHelper networkHelper;
    
    /**
     * A scanner for IO.
     */
    Scanner scanner;

    /**
     * The constructor.
     * @param netHelper The network helper.
     */
    public ClientLobbyCLI(NetworkHelper netHelper){
        this.networkHelper=netHelper;
    }

    /**
     * Print the list of games.
     * @param gameList The game list.
     */
    public void printGameList(Map<String, String> gameList){
        if(gameList==null || gameList.isEmpty()){
        	printToScreen("No games available. Create a new game!");
        }else{
            for (Entry<String,String> game : gameList.entrySet()) {
                Map<String, String> retValues = networkHelper.getGameInfo(game.getValue());
                printToScreen(
                        "GameName: "+retValues.get("name")+
                        "\tMap: "+retValues.get("mapName") +
                        "\t"+retValues.get("playercount")+"/8"
                        );
            }
        }
    }

    /**
     * The menu with the various possibilities for the user. 
     * He can create a new game, show available games or join to an existing one.
     */
    public void menu(){
        scanner = new Scanner(System.in);
        String action=null;
        boolean exit = false;
        printToScreen(
                "1)Create game"+"\n"
                        + "2)List Game"+"\n"
                        + "3)Join Game"+"\n"
                        + "4)Exit");
        while(scanner.hasNextLine() && !exit){
        	printToScreen(
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

    /**
     * The method that allows you to add yourself to an existing match.
     */
    private void actionJoinGame() {
        gameList = networkHelper.getGamesList();
        printToScreen("Insert game name:");
        String gameName = scanner.nextLine();
        String gameToken = gameList.get(gameName);
        ctoken = new ClientToken(networkHelper.getPlayerToken(), gameToken);
        networkHelper.setToken(ctoken);
        networkHelper.joinGame(gameToken);
        ClientGameCLI gameCLI = new ClientGameCLI(ctoken,networkHelper);
        gameCLI.start();
    }

    /**
     * The method that allows you to create a new game.
     */
    private void actionCreateGame() {
    	printToScreen("Insert game name:");
        String gameName = scanner.nextLine();
        printToScreen("Insert map name:");
        String mapName = scanner.nextLine();
        networkHelper.createGame(gameName,mapName.toLowerCase());
    }

    /**
     * @param messaggio A message to print.
     */
    public void printToScreen(String messaggio) {
        System.out.println(messaggio);
    }

}
