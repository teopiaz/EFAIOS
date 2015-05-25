package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

/**
 * @author MMP - LMR
 * This class specifies the logical card type object teleport. 
 * It allows you to move at once the human player who uses it in the initial position of the corresponding type of player.
 */
public class Teleport extends Action {

    /**
     * @param gc the game controller
     */
    public Teleport(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if(pc.itemCardUsed()){
            return false;
        }
        if(pc.hasCard(ItemCard.ITEM_TELEPORT)){
            pc.removeCard(ItemCard.ITEM_TELEPORT);
            pc.movePlayer(getGameController().getFieldController().getHumanStartingPosition());
            return true;
        }
        return false;

    }
}
