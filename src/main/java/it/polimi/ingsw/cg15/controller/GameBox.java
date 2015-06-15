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
     * The unique token for the match.
     */
    private String gameToken;
    
    /**
     * A map with the players.
     * The key is the playerToken and the value is the actual representation of the player in the model
     */
    private Map<String,Player> players;
    
    /**
     * Boolean that says that if the game is over.
     */
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
     * A method that return a map of the current client in the game.
     * The key is the playerToken and the value is the actual representation of the player in the model
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
    
    /**
     * @return if the game is to remove from the list or not.
     */
    public boolean toRemove(){
        return ended;
    }
    
    /**
     * Sets that the game is over.
     */
    public void setToRemove(){
        this.ended=true;
    }

}
