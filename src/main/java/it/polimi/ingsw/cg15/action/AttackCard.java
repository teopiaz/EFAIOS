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
 * This class contains the effect of the sector card attack. 
 * It checks to see if the player in question is human and then checks to see if other players are present in the cell of the player who calls this card. 
 * If they are discarded and lost.
 */
public class AttackCard extends Action {

    /**
     * The event.
     */
    Event e;
    
    /**
     * @param gc The game controller.
     */
    public AttackCard(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * Logic of the attack card that allow a human player to attack an alien.
     * @return a message with the list of return values.
     * @see it.polimi.ingsw.cg15.action.Action#execute()
     */
    @Override
    public Event execute() {
        Map<String,String> retValues = e.getRetValues();
        PlayerController pc = getCurrentPlayerController();
        if(!pc.canUseCard()){
            retValues.put(Event.RETURN, Event.FALSE);
            retValues.put(Event.ERROR,"solo gli umani possono usare le carte oggetto");
            return new Event(e, retValues);
        }
        if(pc.itemCardUsed()){
            retValues.put(Event.RETURN, Event.FALSE);
            retValues.put(Event.ERROR,"carta già usata in questo turno");
            return new Event(e, retValues);
        }
        if(pc.hasCard(ItemCard.ITEM_ATTACK)){
            pc.removeCard(ItemCard.ITEM_ATTACK);
            pc.setItemUsed();
            Action attack = new Attack(getGameController(),e);
            Event attackEvent = attack.execute();
            
            String currentPlayerNumber = Integer.toString( getGameController().getCurrentPlayer().getPlayerNumber() );
            Map<String,String> retPub = new HashMap<String, String>();
            retPub.put("player", currentPlayerNumber);
            retPub.put("card","spotlight");
            Event toPublish = new Event(new ClientToken("", getGameController().getGameToken()),"log",null, retPub);
            Broker.publish(getGameController().getGameToken(), NetworkProxy.eventToJSON(toPublish));

            
            return attackEvent;
        }
        retValues.put(Event.RETURN, Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);
        }

}
