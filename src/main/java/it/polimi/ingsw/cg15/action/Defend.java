package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.player.Player;

public class Defend extends Action {
    PlayerController pc;

    
    public Defend(GameController gc,Player player) {
        super(gc);
        this.pc= getGameController().getPlayerInstance(player);
    
    }

    @Override
    public boolean execute() {
        if(pc.hasCard(ItemCard.ITEM_DEFENSE)){
            pc.removeCard(ItemCard.ITEM_DEFENSE);
            return true;
        }
        return false;
    }


}


