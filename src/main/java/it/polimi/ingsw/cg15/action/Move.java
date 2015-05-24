package it.polimi.ingsw.cg15.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.utils.MapLoader;

/**
 * @author MMP - LMR
 * This class extends the class action and is therefore one of the possible actions to be taken during a turn. 
 * In particular, it is the action that allows the player to move to a cell in the map. 
 * Also, if the selected cell is a dangerous sector it is concerned to call the functionality that allows you to draw a card sector.
 */
public class Move extends Action {

    /**
     * It is the cell that will be the destination for the player.
     */
    private Coordinate dest;

    /**
     * @param gc, it is the Game Controller
     * @param dest, It is the cell that will be the destination for the player. 
     */
    public Move(GameController gc, Coordinate dest) {
        super(gc);
        this.dest = dest;
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if (pc.moveIsPossible(dest)) {
            System.out.println();
            pc.movePlayer(dest);
            Action draw = new DrawSectorCard(getGameController());
            draw.execute();
            return true;
        }
        Logger.getLogger(Move.class.getName()).log(Level.INFO, "Action Move:  impossible to move");

        return false;
    }

}
