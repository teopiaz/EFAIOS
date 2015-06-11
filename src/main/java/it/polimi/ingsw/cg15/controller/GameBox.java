package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.Map;

/**
 * @author MMP - LMR
 * The Game Box, a container which It contains the information required to the game controller.
 */
public class GameBox {

    /** 
     * A method that transforms to a string the game box information.
     */
    @Override
    public String toString() {
        return "GameBox [gameState=" + gameState + ", gameToken=" + gameToken + ", players=" + players + "]";
    }

    /**
     * The Game State.
     */
    private GameState gameState;
    
    /**
     * A token for the match.
     */
    private String gameToken;
    
    /**
     * A map with the players.
     */
    private Map<String,Player> players;
    private boolean ended = false;

    /**
     * The constructor.
     * @param gs The Game State.
     * @param token The Token for the match.
     * @param players The references for the players.
     */
    public GameBox(GameState gs,  String token, Map<String,Player> players) {
        this.gameState=gs;
        this.gameToken=token;
        this.players=players;
    }

    /**
     * A method that return the players.
     * @return the players 
     */
    public Map<String, Player> getPlayers() {
        return players;
    }

    /**
     * @return The Game State.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * @return The Game Token.
     */
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
