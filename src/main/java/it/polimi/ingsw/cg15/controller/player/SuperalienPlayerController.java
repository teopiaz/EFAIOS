package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;

/**
 * @author MMP - LMR
 * The controller of the player.
 */
public class SuperalienPlayerController extends PlayerController {

    /**
     *  The state of the game.
     */
    private GameState gameState;

    /**
     * The constructor.
     * @param gameState The current state of the game.
     */
    public SuperalienPlayerController(GameState gameState) {
        super(gameState);
        this.gameState = gameState;
    }

    /**
     * Check if a move is possible for the super alien type of player.
     * @see it.polimi.ingsw.cg15.controller.player.PlayerController#moveIsPossible(it.polimi.ingsw.cg15.model.field.Coordinate)
     */
    @Override
    public boolean moveIsPossible(Coordinate coord) {
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
    
    /**
     * Check if I can use a item card.
     * @see it.polimi.ingsw.cg15.controller.player.PlayerController#canUseCard()
     */
    @Override
    public boolean canUseCard(){
        return false;
    }
    
}
