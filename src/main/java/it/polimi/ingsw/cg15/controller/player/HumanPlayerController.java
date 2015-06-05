package it.polimi.ingsw.cg15.controller.player;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;

/**
 * @author LMR - MMP
 */
public class HumanPlayerController extends PlayerController {

    /**
     * 
     */
    private GameState gameState;


    public HumanPlayerController(GameState gameState) {
        super(gameState);
        this.gameState = gameState;

        System.out.println(gameState.getTurnState().getCurrentPlayer()
                .toString()
                + " costruttore chiamato");
    }


    @Override
    public boolean moveIsPossible(Coordinate coord) {
        Logger.getLogger(Field.class.getName()).log(Level.INFO,"Human move is possible");
        if(gameState.getTurnState().hasMoved()){
            System.err.println("ciao");
            //return false;
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
    
    @Override
    public void setOnAdrenaline(){
        gameState.getTurnState().setUnderAdrenaline();
    }
    




}
