package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.List;
import java.util.Map;

/**
 * @author MMP - LMR
 * This class contains the logic of attack. 
 * It can be done only by a player-type alien or superalien. 
 * Once a player has declared attack dates back to its current location on the map and check if there are players (both human and alien) in the corresponding cell. 
 * If there are they are eliminated then the variable isAlive the model is set to false. 
 * TODO You can attack even before moving or just after?
 */
public class Attack extends Action {

    Event e;
    
    /**
     * @param gc the game controller
     */
    public Attack(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }


    //TODO: Evolution
    @Override
    public Event execute() {
        GameController gc = getGameController();
        PlayerController pc = getCurrentPlayerController();
        Map<String,String> retValues = e.getRetValues();
        if(pc.hasAttacked()){
            retValues.put("return", "false");
            retValues.put("error", "attacco già effettuato");

            return new Event(e, retValues);
        }

        List<Player> playersInSector = getGameController().getFieldController().getPlayersInSector(pc.getPlayerPosition());

        for (Player player : playersInSector) {
            Action defend = new Defend(gc, player,e);
            Event defenseEvent = defend.execute();

            if(defenseEvent.getRetValues().containsKey("defense") ){
                if(defenseEvent.getRetValues().get("defense").equals("false") ){
                    getCurrentPlayerController().killPlayer(player);
                }
            }
            pc.setHasAttacked();
        }
        retValues.put("return", "true");
        return new Event(e, retValues);
        }

    }
