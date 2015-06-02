package it.polimi.ingsw.cg15.action;

import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * When a player lands in a dangerous game and draw a card sector of green type is called this class and run the execute method that allows the player to select a cell of the game in which pretend to disclose its position.
 */
public class NoiseGreen extends Action {

    Event e;
    /**
     * The icon of the item present in the sector card.
     */

    /**
     * @param gc the game controller
     * @param e 
     * @param item the icon of the item present in the sector card
     */
    public NoiseGreen(GameController gc, Event e) {
        // TODO Auto-generated constructor stub
        super(gc);
        this.e=e;

    }

    //TODO: SISTEMARE switchare le azioni possibili permettendo solo la selezione del settore
    @Override
    public Event execute() {

 
        
        
        return e;
    }

}
