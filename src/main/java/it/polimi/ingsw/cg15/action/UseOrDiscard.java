package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This class specifies the situation of the game in which a player who already has three Item cards and draws another, he is given the chance to discard one of four Item cards, or play one.
 */
public class UseOrDiscard extends Action {
    Event e;
    /**
     * @param gc the game controller
     */
    public UseOrDiscard(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    @Override
    public Event execute() {
        // TODO Auto-generated method stub
        //ask player
        return e;
    }

}
