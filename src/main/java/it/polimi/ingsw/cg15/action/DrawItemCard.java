package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This class is the action that allows you to draw a card object. 
 * At the beginning of the game the card object may be used by anyone but only the human player can use it in all respects. 
 * In reality the choice to draw a card object is subject to a charter fishing sector that has the symbol object. 
 * It is not an action that can be selected directly from the player.
 */
public class DrawItemCard extends Action {

    Event e;
    /**
     * @param gc the game controller
     */
    public DrawItemCard(GameController gc,Event e) {
        super(gc);
        this.e=e;
        }

    @Override
    public Event execute() {
        PlayerController pc = getCurrentPlayerController();
        
        if(pc.canDrawItemCard()){
            pc.drawItemCard();
        }
        else{
            Action useOrDiscard = new UseOrDiscard(getGameController(),e);
            e= useOrDiscard.execute();
        }
        
        return e;
    }

}
