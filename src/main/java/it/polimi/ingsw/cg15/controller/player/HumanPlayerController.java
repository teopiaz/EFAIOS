package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR 
 * The controller of the Human Player.
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
     * A method that set the adrenaline state for the human player. In this state he can move of 2 cells in 1 turn.
     */
    @Override
    public void setOnAdrenaline(){
        gameState.getTurnState().setUnderAdrenaline();
    }

    /** 
     * The escape method that only a human player can do.
     * @see it.polimi.ingsw.cg15.controller.player.PlayerController#escape()
     */
    @Override
    public boolean escape() {
        Player currentPlayer = gameState.getTurnState().getCurrentPlayer();
        if(currentPlayer.isAlive()){
            currentPlayer.setWin();
            return true;
        }
        return false;
    }
    
    /**
     * The possibility to use a card is true in the human player.
     */
    @Override
    public boolean canUseCard(){
        return true;
    }

}
