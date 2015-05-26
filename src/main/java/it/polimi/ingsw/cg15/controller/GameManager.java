package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.SessionTokenGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author matteo
 *
 */
/**
 * @author matteo
 *
 */
public class GameManager {

    private GameInstance gameInstance;
    private static GameManager singletonInstance = new GameManager();


    private Map<String,GameBox> gameBoxList = new HashMap<String,GameBox>();

    //ExecutorService executor = Executors.newCachedThreadPool();





    private GameManager(){
        gameInstance = GameInstance.getInstance();
    }

    public static GameManager getInstance(){
        return singletonInstance;
    }

    /**
     * Dispatch the event to the requested game instance
     * @param e received event
     */
    public Event dispatchMessage(Event e){
        String gameToken = e.getToken().getGameToken();
        if(!gameBoxList.containsKey(gameToken)){
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, "Client" + e.getToken().getPlayerToken() + " tried to access an invalid game" );

            return new Event(e, "error game not avaible");
        }

        try {
            gameBoxList.get(gameToken).getQueue().put(e);
        } catch (InterruptedException e1) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE,"InterruptedException on BlockingQueue",e1 );
        }
        GameController controller = new GameController(gameBoxList.get(gameToken));
        return controller.eventHandler(e);
        //executor.execute(controller);
    }

    /**
     * 
     * @param e received event
     * @return a new eventh with the requested list
     */
    public Event getGameList(Event e){

        Map<String,String> result = new HashMap<String, String>();
        for (Entry<String,GameBox> game : gameBoxList.entrySet()) {
            GameBox g = game.getValue();
            result.put(g.getGameState().getName(), g.getGameToken());
        }

        Event event=new Event(e,result);
        return event;
    }



    public Event eventHandler(Event e) {
        Event response=null;

        if(e.getToken().getPlayerToken()!=null){
            String command = e.getCommand();
            switch(command){

                case "creategame": {
    
                    response =createGame(e);
                    break;
                }
                case "listgame": {
                    response=getGameList(e);
                    break;
                }
                case "joingame":{
                    response =joinGame(e);
                    break;
                }
                case "startgame":{
                    response =startGame(e);
                    break;
                }
    
                default:{ 
                    response=dispatchMessage(e);
                    break;
                }
            }
        }

        else{
            response = getClientToken();

        }
        return response;

    }

    private Event startGame(Event e) {
        ClientToken token = e.getToken();
        String gameToken = token.getGameToken();

        if(!gameBoxList.containsKey(gameToken)){
            return new Event(e,"error","invalid_game");
        }
        if(gameBoxList.get(gameToken).getPlayers().size() < 2){
            Logger.getLogger(GameManager.class.getName()).log(Level.INFO,"player size=" + gameBoxList.get(gameToken).getPlayers().size());
            return new Event(e,"error","wait for more players");
        }
        GameBox gameBox = gameBoxList.get(gameToken);
        gameBox.getGameState().setStarted();
        return new Event(e,"game_started");
    }

    private Event getClientToken() {
        ClientToken ctoken = new ClientToken(SessionTokenGenerator.nextSessionId(), null);
        return new Event(ctoken,ctoken.getPlayerToken());

    }

    private Event joinGame(Event e) {
        ClientToken token = e.getToken();
        String gameToken = token.getGameToken();

        if(!gameBoxList.containsKey(gameToken)){

            return new Event(e,"error","invalid_game");
        }
        if(gameBoxList.get(gameToken).getPlayers().size() >= 8){


            return new Event(e,"error","game_full");
        }

        
        GameBox gameBox = gameBoxList.get(gameToken);
        if(gameBox.getGameState().isStarted()){
            return new Event(e,"error","game_already_started");

        }
        gameBox.getPlayers().put(token.getPlayerToken(), new Player());


        return new Event(e,"joined");
    }

    /**
     * Create a new instance of a game with his game token
     * @param e received event
     * @return a new event with the game token
     */
    public Event createGame(Event e){
        BlockingQueue<Event> queue = new ArrayBlockingQueue<Event>(10,true);
        String token = SessionTokenGenerator.nextSessionId();

        
        GameState gameState = GameInstance.getInstance().addGameInstance();
        gameState.setName(e.getArgs().get("gamename"));
        String mapName = e.getArgs().get("mapname");
        gameState.setMapName(mapName);
        
        Map<String,Player> players = new HashMap<String, Player>();
        GameBox gameBox = new GameBox(gameState,queue,token,players);   

        gameBoxList.put(token, gameBox);
        Map<String,String> result = new HashMap<String, String>();
        result.put("gameToken", token);
        Event event = new Event(e,result);
        return event;
    }








}
