package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MMP - LMR
 * The class defense continental logic card object defense holding a human-type player.
 * If you run an attack by an alien or human player and the player has hit this card in your deck it is able to survive. Once used the card is discarded. 
 * This card is activated automatically can not be used by the player.
 */
public class Defend extends Action {
    PlayerController pc;
    Event e;
    
    /**
     * @param gc the game controller
     * @param player the current player in use
     */
    public Defend(GameController gc,Player player,Event e) {
        super(gc);
        this.pc= getGameController().getPlayerInstance(player);
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

        if(pc.hasCard(ItemCard.ITEM_DEFENSE)){
            pc.removeCard(ItemCard.ITEM_DEFENSE);
            retValues.put("defense", Event.TRUE);
            
            return new Event(e, retValues);
        }
        retValues.put("defense", Event.FALSE);

        return new Event(e, retValues);
    }


}


