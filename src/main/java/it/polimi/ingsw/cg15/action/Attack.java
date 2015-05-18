package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.List;

public class Attack extends Action {

    public Attack(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }

    
    //TODO: Evolution
    @Override
    public boolean execute() {
        GameController gc = getGameController();
        PlayerController pc = getCurrentPlayerController();
        
        if(pc.hasAttacked()){
            return false;
        }
        
        List<Player> playersInSector = getGameController().getFieldController().getPlayersInSector(pc.getPlayerPosition());
        
        for (Player player : playersInSector) {
            Action defend = new Defend(gc, player);
            if(defend.execute()==false){
                getCurrentPlayerController().killPlayer(player);
            }
        }
        pc.setHasAttacked();
        
        
        return false;
    }

}
