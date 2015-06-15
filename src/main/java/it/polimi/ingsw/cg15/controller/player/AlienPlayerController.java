package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;

/**
 * @author LMR - MMP
 * The controller of the Alien player.
 */
public class AlienPlayerController extends PlayerController {

    /**
     *  The state of the game.
     */
    private GameState gameState;

    /**
     * The constructor.
     * @param gameState
     */
    public AlienPlayerController(GameState gameState) {
        super(gameState);
        this.gameState = gameState;
    }

    /**
     * A method that verify if a move is possible in that particular sector based on the type of the player.
     * @param coord Coordinate where you would verify if the move is possible.
     */
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
        Cell currentPosition = gameState.getTurnState().getCurrentPlayer().getPosition();
        Cell destination = field.getCell(coord);
        return field.isReachable(currentPosition, destination, 2);
    }
    
    /**
     * A method that can evolve a player from alien to super alien.
     */
    @Override
    public boolean evolve(){
        Player cp = gameState.getTurnState().getCurrentPlayer();
        if(cp.getPlayerType()==PlayerType.ALIEN){
            cp.setPlayerType(PlayerType.SUPERALIEN);
            return true;
        }
        else
            return false;
    }
    @Override
    public boolean canUseCard(){
        return false;
    }

}
