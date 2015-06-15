package it.polimi.ingsw.cg15.action;

import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This class is the action that allows you to draw a card object. 
 * At the beginning of the game the card object may be used by anyone but only the human player can use it in all respects. 
 * In reality the choice to draw a card object is subject to a charter fishing sector that has the symbol object. 
 * It is not an action that can be selected directly from the player.
 */
public class DrawItemCard extends Action {

    /**
     * The event.
     */
    Event e;
    
    /**
     * @param gc The game controller.
     */
    public DrawItemCard(GameController gc,Event e) {
        super(gc);
        this.e=e;
        }

    /**
     * The logic of drawing a item card. It has various possibility based on the type of card drawn.
     * @return a message with the list of return values.
     * @see it.polimi.ingsw.cg15.action.Action#execute()
     */
    @Override
    public Event execute() {
        PlayerController pc = getCurrentPlayerController();
        Map<String, String> retValues = e.getRetValues();
        if(pc.canDrawItemCard()){
            ItemCard card = pc.drawItemCard();
            retValues.put("card", card.toString());
            e = new Event(e, retValues);
        }
        else{
            Action useOrDiscard = new UseOrDiscard(getGameController(),e);
            e= useOrDiscard.execute();
        }
        return e;
    }

}
