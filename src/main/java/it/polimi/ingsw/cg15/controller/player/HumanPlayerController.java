package it.polimi.ingsw.cg15.controller.player;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;

/**
 * @author MMP - LMR 
 * The controller of the Human player.
 */
public class HumanPlayerController extends PlayerController {

    /**
     *  The state of the game.
     */
    private GameState gameState;

    /**
     * The constructor.
     * @param gameState
     */
    public HumanPlayerController(GameState gameState) {
        super(gameState);
        this.gameState = gameState;
    }

    /**
     * A method that verify if a move is possible in that particular sector based on the type of the player.
     * @param coord Coordinate where you would verify if the move is possible.
     */
    @Override
    public boolean moveIsPossible(Coordinate coord) {
        Logger.getLogger(Field.class.getName()).log(Level.INFO,"Human move is possible");
        if(gameState.getTurnState().hasMoved()){
            return false;
        }
        Field field = gameState.getField();
        if(!field.getField().containsKey(coord)){
            return false;
        }
        int distance = 1;
        if(gameState.getTurnState().isUnderAdrenaline()){
            distance = 2;
        }
        Cell currentPosition = gameState.getTurnState().getCurrentPlayer().getPosition();
        Cell destination = field.getCell(coord);
        return field.isReachable(currentPosition, destination, distance);
    }
    
    /**
     * A method that set the adrenaline state for the human player. In this state he can move of two cells in 1 turn.
     */
    @Override
    public void setOnAdrenaline(){
        gameState.getTurnState().setUnderAdrenaline();
    }
    
}
