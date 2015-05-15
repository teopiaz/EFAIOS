package it.polimi.ingsw.cg15.controller.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

public class Telport extends Action<Boolean> {

    public Telport(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if(pc.hasCard(ItemCard.ITEM_TELEPORT)){
            pc.removeCard(ItemCard.ITEM_TELEPORT);
            pc.movePlayer(getGameController().getFieldController().getHumanStartingPosition());
            return true;
        }
        return false;

    }
}
