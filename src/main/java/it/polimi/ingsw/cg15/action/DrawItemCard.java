package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;

public class DrawItemCard extends Action {

    public DrawItemCard(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        
        if(pc.canDrawItemCard()){
            pc.drawItemCard();
        }
        else{
            Action useOrDiscard = new UseOrDiscard(getGameController());
            useOrDiscard.execute();
        }
        
        return true;
    }

}
