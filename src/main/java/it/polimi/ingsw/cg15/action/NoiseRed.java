package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;

/**
 * @author MMP - LMR
 * This is the class that contains the logic of the paper industry of red color. 
 * It retrieves the current position of the player and says "noise in that cell."
 */
public class NoiseRed extends Action {
    
    boolean hasItem=false;

    /**
     * @param gc the game controller
     * @param item the icon of the item present in the sector card
     */
    public NoiseRed(GameController gc, boolean item) {
        super(gc);
        this.hasItem=item;

    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub

        //TODO ask player 
        return false;
    }

}
