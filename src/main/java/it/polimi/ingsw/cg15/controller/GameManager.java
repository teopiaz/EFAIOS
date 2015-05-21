package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.SessionTokenGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {
    
    private GameInstance gameInstance;
    private static GameManager singletonInstance = new GameManager();
    

    private List<GameBox> gameBoxList = new ArrayList<GameBox>();
    ExecutorService executor = Executors.newFixedThreadPool(5);
    
    
    
    
    
    private GameManager(){
        gameInstance = GameInstance.getInstance();
    }

    public static GameManager getInstance(){
        return singletonInstance;
    }
    
    public Event getGameList(Event e){
        

        Event event=new Event(e,null);
        
        return event;
    }
    
    public Event createGame(Event e){
        BlockingQueue<Event> queue = new ArrayBlockingQueue<Event>(10,true);
        String token = SessionTokenGenerator.nextSessionId();
        GameState gameState = GameInstance.getInstance().addGameInstance();
        GameBox gameBox = new GameBox(gameState,queue,token);        
        
        Event event = new Event(e,null);
        
        //TODO: impacchettare un evento
    return null;
    }
    
    
    
    
    
    

}
