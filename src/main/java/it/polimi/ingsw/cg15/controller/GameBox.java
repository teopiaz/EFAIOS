package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class GameBox {


    @Override
    public String toString() {
        return "GameBox [gameState=" + gameState + ", gameToken=" + gameToken + ", queue=" + queue
                + ", players=" + players + "]";
    }


    private GameState gameState;
    private String gameToken;
    private BlockingQueue<Event> queue;
    private Map<String,Player> players;
    private boolean ended = false;


    public GameBox(GameState gs,  String token, Map<String,Player> players) {
        this.gameState=gs;
        this.gameToken=token;
        this.players=players;
    }


    public Map<String, Player> getPlayers() {
        return players;
    }



    public GameState getGameState() {
        return gameState;
    }


    public String getGameToken() {
        return gameToken;
    }
    
    public boolean toRemove(){
        return ended;
    }
    
    public void setToRemove(){
        this.ended=true;
    }



}
