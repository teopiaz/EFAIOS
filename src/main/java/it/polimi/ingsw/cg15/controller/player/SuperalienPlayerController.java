package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;

/**
 * @author LMR - MMP
 * The controller of the player.
 */
public class SuperalienPlayerController extends PlayerController {

    /**
     *  The state of the game.
     */
    private GameState gameState;


    /**
     * @param gameState
     */
    public SuperalienPlayerController(GameState gameState) {
        super(gameState);
        this.gameState = gameState;
    }

    @Override
    public boolean moveIsPossible(Coordinate coord) {
        System.out.println("alien moveIsPossible");
        if(gameState.getTurnState().hasMoved()){
            return false;
        }
       
        Field field = gameState.getField();
        if(!field.getField().containsKey(coord)){
            return false;
        }
        Cell currentPosition = gameState.getTurnState().getCurrentPlayer()
                .getPosition();
        Cell destination = field.getCell(coord);
        return field.isReachable(currentPosition, destination, 3);
    }
    @Override
    public boolean canUseCard(){
        return false;
    }
    


}
