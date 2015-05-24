package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

/**
 * @author MMP - LMR
 * This class contains the effect of the paper object attacks. 
 * It checks to see if the player in question is human and then checks to see if other players are present in the cell of the player who calls this card. 
 * If they are discarded and lost.
 */
public class AttackCard extends Action {

    public AttackCard(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if(pc.itemCardUsed()){
            return false;
        }
        if(pc.hasCard(ItemCard.ITEM_ATTACK)){
            pc.removeCard(ItemCard.ITEM_ATTACK);
            Action attack = new Attack(getGameController());
            attack.execute();
            return true;
        }
        return false;
    }

}
