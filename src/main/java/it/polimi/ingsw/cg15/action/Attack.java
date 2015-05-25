package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.List;

/**
 * @author MMP - LMR
 * This class contains the logic of attack. 
 * It can be done only by a player-type alien or superalien. 
 * Once a player has declared attack dates back to its current location on the map and check if there are players (both human and alien) in the corresponding cell. 
 * If there are they are eliminated then the variable isAlive the model is set to false. 
 * TODO You can attack even before moving or just after?
 */
public class Attack extends Action {

    /**
     * @param gc the game controller
     */
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
