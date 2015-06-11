package it.polimi.ingsw.cg15.action;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This action represents the state "adrenaline" of the human player, during this state a player may, for a turn, move two steps in a turn only once used the card is discarded.
 */
public class Adrenaline extends Action {

    Event e;
    /**
     * This class is the action of the paper object adrenaline. It takes the instance of the corresponding Gamecontroller.
     * @param gc the game controller
     */
    public Adrenaline(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    @Override
    public Event execute() {

       Map<String,String> retValues;
        
        if(e.getRetValues()==null){
            retValues = new HashMap<String, String>();
        }else{
        retValues = e.getRetValues();
        }

        
        PlayerController pc = getCurrentPlayerController();
        if(!pc.canUseCard()){
            retValues.put(Event.ERROR, Event.FALSE);
            retValues.put("error","solo gli umani possono usare le carte oggetto");
            return new Event(e, retValues);
        }
        if(pc.itemCardUsed()){
            retValues.put("return", Event.FALSE);
            retValues.put(Event.ERROR,"carta già usata in questo turno");
            return new Event(e, retValues);
        }
        
        if(pc.hasCard(ItemCard.ITEM_ADRENALINE)){
            pc.removeCard(ItemCard.ITEM_ADRENALINE);
            pc.setOnAdrenaline();
            retValues.put("return", Event.TRUE);
            retValues.put("state", "adrenaline");
            return new Event(e, retValues);
        }
        retValues.put("return", Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);
    }

}
