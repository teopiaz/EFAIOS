package it.polimi.ingsw.cg15.controller.player;


import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.*;
import it.polimi.ingsw.cg15.model.player.*;

import java.util.*;

/**
 * @author LMR - MMP
 */
public class PlayerController {

	
	private GameState gameState;

    /**
     * 
     */
    public PlayerController(GameState state) {
    	this.gameState = state;
    }


    /**
     * @param player2 
     * 
     */
    public static void movePlayer(Player player2, Cell dest) {
        // TODO implement here
    /*	Player player = gameState.getCurrentPlayer();
    	
    	if(player instanceof Alien){
    		AlienPlayerController.movePlayer( player, dest);
    		

    	}*/
    }

    /**
     * 
     */
    public void moveIsPossible() {
        // TODO implement here
    }

}