package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.*;

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
     * @return
     * 
     */
    // TODO: testare se può essere mai chiamato (per design NON deve essere mai
    // chiamato)
    public boolean moveIsPossible(Coordinate dest) {
        // TODO implement here
        System.out.println("playerController");
        return true;

    }

    public void movePlayer(Coordinate dest) {
        Cell destination = gameState.getField().getCell(dest);
        gameState.getTurnState().getCurrentPlayer().setPosition(destination);

    }

}