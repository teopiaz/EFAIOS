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
 * This action represents the state "adrenaline" of the human player, during this state a player may, for a turn, move two steps in a turn only once used the card is discarded.
 */
public class Sedatives extends Action {

    /**
     * The event.
     */
    Event e;
    
    /**
     * This class is the action of the item card sedatives. It takes the instance of the corresponding Game controller.
     * @param gc The game controller.
     */
    public Sedatives(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * The action of the item card sedatives. 
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

        
        Event checkCardEvent = checkCardUse(e,retValues);
        if(checkCardEvent!=null){
            return checkCardEvent;
        }
        
        if(pc.hasCard(ItemCard.ITEM_SEDATIVES)){
            pc.removeCard(ItemCard.ITEM_SEDATIVES);
            pc.setUnderSedatives();
            pc.setItemUsed();
            
            
            String currentPlayerNumber = Integer.toString( getGameController().getCurrentPlayer().getPlayerNumber() );
            Map<String,String> retPub = new HashMap<String, String>();
            retPub.put("player", currentPlayerNumber);
            retPub.put("card","sedatives");
            Event toPublish = new Event(new ClientToken("", getGameController().getGameToken()),"log",null, retPub);
            Broker.publish(getGameController().getGameToken(), NetworkProxy.eventToJSON(toPublish));
            
            
            
            retValues.put(Event.RETURN, Event.TRUE);
            retValues.put("state", "sedatives");
            return new Event(e, retValues);
        }
        retValues.put(Event.RETURN, Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);
    }

}
