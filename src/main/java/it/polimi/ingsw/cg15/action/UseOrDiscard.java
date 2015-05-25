package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;

/**
 * @author MMP - LMR
 * This class specifies the situation of the game in which a player who already has three Item cards and draws another, he is given the chance to discard one of four Item cards, or play one.
 */
public class UseOrDiscard extends Action {

    /**
     * @param gc the game controller
     */
    public UseOrDiscard(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute() {
        // TODO Auto-generated method stub
        //ask player
        return false;
    }

}
