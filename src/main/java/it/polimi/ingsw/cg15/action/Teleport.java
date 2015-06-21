package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MMP - LMR
 * This class specifies the logical card type object teleport. 
 * It allows you to move at once the human player who uses it in the initial position of the corresponding type of player.
 */
public class Teleport extends Action {

    /**
     * The event.
     */
    Event e;
    
    /**
     * @param gc The game controller.
     */
    public Teleport(GameController gc, Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * The action that perform the ability of the Teleport item card.
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

        if(pc.hasCard(ItemCard.ITEM_TELEPORT)){
            pc.removeCard(ItemCard.ITEM_TELEPORT);
            pc.setItemUsed();
            pc.movePlayer(getGameController().getFieldController().getHumanStartingPosition());
            
            String currentPlayerNumber = Integer.toString( getGameController().getCurrentPlayer().getPlayerNumber() );
            Map<String,String> retPub = new HashMap<String, String>();
            retPub.put("player", currentPlayerNumber);
            retPub.put("card","teleport");
            Event toPublish = new Event(new ClientToken("", getGameController().getGameToken()),"log",null, retPub);
            Broker.publish(getGameController().getGameToken(), NetworkProxy.eventToJSON(toPublish));

            
            
            
            retValues.put(Event.RETURN, Event.TRUE);
            return new Event(e, retValues);
        }
        retValues.put(Event.RETURN, Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);

    }
}
