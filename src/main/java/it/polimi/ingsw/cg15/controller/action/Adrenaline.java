package it.polimi.ingsw.cg15.controller.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

public class Adrenaline extends Action {

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
