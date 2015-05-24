package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;

/**
 * @author MMP - LMR
 * This class refers to a specific situation of the game in which a player, after having drawn one card green segment has the possibility of lying on its real position declaring noise in an area of the game that is not really the one in which it is located. 
 * The choice of the cell where the state noise is at the discretion of the player.
 */
public class AskSector extends Action {

    public AskSector(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        return false;
    }

}
