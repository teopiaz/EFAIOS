package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MMP - LMR
 * This class specifies the logical card type object teleport. 
 * It allows you to move at once the human player who uses it in the initial position of the corresponding type of player.
 */
public class Teleport extends Action {

    Event e;
    /**
     * @param gc the game controller
     */
    public Teleport(GameController gc,Event e) {
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
            retValues.put("return",Event.FALSE);
            retValues.put(Event.ERROR,"solo gli umani possono usare le carte oggetto");
            return new Event(e, retValues);
        }
        if(pc.itemCardUsed()){
            retValues.put("return", Event.FALSE);
            retValues.put(Event.ERROR,"carta già usata in questo turno");
            return new Event(e, retValues);
        }
        if(pc.hasCard(ItemCard.ITEM_TELEPORT)){
            pc.removeCard(ItemCard.ITEM_TELEPORT);
            pc.movePlayer(getGameController().getFieldController().getHumanStartingPosition());
            retValues.put("return", Event.TRUE);
            return new Event(e, retValues);
        }
        retValues.put("return", Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);

    }
}
