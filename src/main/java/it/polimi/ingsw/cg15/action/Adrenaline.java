package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

/**
 * @author MMP - LMR
 * This action represents the state "adrenaline" of the human player, during this state a player may, for a turn, move two steps in a turn only once used the card is discarded.
 */
public class Adrenaline extends Action {

    /**
     * This class is the action of the paper object adrenaline. It takes the instance of the corresponding Gamecontroller.
     * @param gc the game controller
     */
    public Adrenaline(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if(pc.hasCard(ItemCard.ITEM_ADRENALINE)){
            pc.removeCard(ItemCard.ITEM_ADRENALINE);
            pc.setOnAdrenaline();
            return true;
        }
        return false;

    }

}
