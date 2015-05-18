package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;

/**
 * @author LMR - MMP
 */
public class AlienPlayerController extends PlayerController {

    /**
     * 
     */
    private GameState gameState;


    public AlienPlayerController(GameState gameState) {
        super(gameState);
        this.gameState = gameState;
        System.out.println(gameState.getTurnState().getCurrentPlayer()
                .toString()
                + " costruttore chiamato");
    }

    @Override
    public boolean moveIsPossible(Coordinate coord) {
        if(gameState.getTurnState().hasMoved()){
            return false;
        }
        Field field = gameState.getField();
        Cell currentPosition = gameState.getTurnState().getCurrentPlayer()
                .getPosition();
        Cell destination = field.getCell(coord);
        return field.isReachable(currentPosition, destination, 2);
    }


    
    


    
    



}
