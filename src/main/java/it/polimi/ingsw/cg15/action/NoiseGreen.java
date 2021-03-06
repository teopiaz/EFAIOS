package it.polimi.ingsw.cg15.action;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * When a player lands in a dangerous game and draw a card sector of green type is called this class and 
 * run the execute method that allows the player to select a cell of the game in which pretend to disclose its position.
 */
public class NoiseGreen extends Action {

    /**
     * The event.
     */
    Event e;


    /**
     * @param gc The game controller.
     * @param e The event.
     */
    public NoiseGreen(GameController gc, Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * Execute method that allows the player to select a cell of the game in which pretend to disclose its position.
     * @return a message with the list of return values.
     * @see it.polimi.ingsw.cg15.action.Action#execute()
     */
    @Override
    public Event execute() {
        Map<String,String> retValues;

        if(e.getRetValues()==null){
            retValues = new HashMap<String, String>();
        }else{
            retValues = e.getRetValues();
        }
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
