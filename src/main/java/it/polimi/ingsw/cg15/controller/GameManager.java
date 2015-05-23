package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.SessionTokenGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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

    ExecutorService executor = Executors.newCachedThreadPool();





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
    public void dispatchMessage(Event e){

        String gameToken = e.getToken().getGameToken();
        try {
            gameBoxList.get(gameToken).getQueue().put(e);
        } catch (InterruptedException e1) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE,"InterruptedException on BlockingQueue",e1 );
        }
        GameController controller = new GameController(gameBoxList.get(gameToken));
        executor.execute(controller);
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

        GameBox gameBox = new GameBox(gameState,queue,token);        
        gameBoxList.put(token, gameBox);
        Event event = new Event(new ClientToken(e.getToken().getPlayerToken(), token),e.getCommand(),e.getArgs(),e.getRetValues());
        return event;
    }








}
