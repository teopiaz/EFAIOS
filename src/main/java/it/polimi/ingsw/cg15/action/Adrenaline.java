package it.polimi.ingsw.cg15.action;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

/**
 * @author MMP - LMR
 * This action represents the state "adrenaline" of the human player, during this state 
 * a player may, for a turn, move two steps in a turn only once used the card is discarded.
 */
public class Adrenaline extends Action {

    /**
     * The event.
     */
    Event e;
    
    /**
     * This class is the action of the paper object adrenaline. It takes the instance of 
     * the corresponding Game Controller.
     * @param gc the game controller
     */
    public Adrenaline(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * The logic of the adrenaline item card that allow a human player to move by to cells instead of one.
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
            pc.setItemUsed();
            
            
            String currentPlayerNumber = Integer.toString( getGameController().getCurrentPlayer().getPlayerNumber() );
            Map<String,String> retPub = new HashMap<String, String>();
            retPub.put("player", currentPlayerNumber);
            retPub.put("card","adrenaline");
            Event toPublish = new Event(new ClientToken("", getGameController().getGameToken()),"log",null, retPub);
            Broker.publish(getGameController().getGameToken(), NetworkProxy.eventToJSON(toPublish));

            
            
            retValues.put("return", Event.TRUE);
            retValues.put("state", "adrenaline");
            return new Event(e, retValues);
        }
        retValues.put("return", Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);
    }

}
