package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This class refers to a specific situation of the game in which a player, after having drawn one card green segment has the possibility of lying on its real position declaring noise in an area of the game that is not really the one in which it is located. 
 * The choice of the cell where the state noise is at the discretion of the player.
 */
public class AskSector extends Action {
Event e;
    /**
     * @param gc the game controller
     */
    public AskSector(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    @Override
    public Event execute() {
        // TODO Auto-generated method stub
        return e;
    }

}
