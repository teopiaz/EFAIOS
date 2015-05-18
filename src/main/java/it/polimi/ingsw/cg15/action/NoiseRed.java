package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;

public class NoiseRed extends Action {
    
    boolean hasItem=false;

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
