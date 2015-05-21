package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.concurrent.ConcurrentHashMap;

public class GameManager {
    
    private GameInstance gameInstance;
    private static GameManager singletonInstance = new GameManager();
    
    ConcurrentHashMap<String, GameController> gamesList = new ConcurrentHashMap<String, GameController>();
    ConcurrentHashMap<String, Player> clientList = new ConcurrentHashMap<String, Player>();

    
    private GameManager(){
        gameInstance = GameInstance.getInstance();
    }

    public static GameManager getInstance(){
        return singletonInstance;
    }
    
    public Event getGameList(Event e){
        
        
        
        Event event=new Event();
        
        return event;
    }
    
    
    
    
    
    
    

}
