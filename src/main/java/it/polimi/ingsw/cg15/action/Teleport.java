package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

public class Teleport extends Action {

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
