package it.polimi.ingsw.cg15.action;

import java.util.HashMap;
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
     */
    public NoiseGreen(GameController gc, Event e) {
        // TODO Auto-generated constructor stub
        super(gc);
        this.e=e;

    }

    //TODO: SISTEMARE switchare le azioni possibili permettendo solo la selezione del settore
    @Override
    public Event execute() {
        Map<String,String> retValues = new HashMap<String, String>();

       if( getGameController().askForSector()){
           retValues.put("asksector",Event.TRUE);
           e = new Event(e, retValues);
           return e;
       }      
       
       
       retValues.put("asksector",Event.FALSE);
       retValues.put(Event.ERROR,"non puoi eseguire questa azione");
       e = new Event(e, retValues);
        return e;
    }

}
