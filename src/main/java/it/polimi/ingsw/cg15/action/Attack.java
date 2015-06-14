package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.ActionEnum;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.util.ArrayList;
import java.util.HashMap;
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
        Map<String,String> retValues;
        
        if(e.getRetValues()==null){
            retValues = new HashMap<String, String>();
        }else{
        retValues = e.getRetValues();
        }

        if(pc.hasAttacked()){
            retValues.put("return", Event.FALSE);
            retValues.put(Event.ERROR, "attacco già effettuato");
            System.err.println("ERRORE"+retValues.toString());
            return new Event(e, retValues);
        }
        
        pc.setHasAttacked();
        getGameController().removeAction(ActionEnum.ATTACK);

        Player currentPlayer = getGameController().getCurrentPlayer();
        String gameToken = getGameController().getGameToken();
        Map<String,String> pubRet = new HashMap<String, String>();
        
        List<Player> playersInSector = getGameController().getFieldController().getPlayersInSector(pc.getPlayerPosition());
        List<Player> playersToIterate = new ArrayList<Player>();
        
         playersToIterate.addAll(playersInSector);
        
        
        int killcount=0;
        for (Player player : playersToIterate) {
            if(player != currentPlayer){
                Action defend = new Defend(gc, player,e);
                Event defenseEvent = defend.execute();

                if(defenseEvent.getRetValues().containsKey("defense") ){
                    if(defenseEvent.getRetValues().get("defense").equals(Event.FALSE) ){
                        getCurrentPlayerController().killPlayer(player);
                        killcount++;
                        pubRet.put(Integer.toString(player.getPlayerNumber()),"killed");

                    }
                    else{
                    pubRet.put(Integer.toString(player.getPlayerNumber()),"defense");
                    }
                }
                pc.setHasAttacked();
                if(killcount>0){
                    pc.evolve();
                }
            }
        }
  
        pubRet.put("player", Integer.toString(currentPlayer.getPlayerNumber()));
        pubRet.put("attack", currentPlayer.getPosition().getLabel());
        

        Event toPublish = new Event(new ClientToken("", gameToken),"log",null, pubRet);
        Broker.publish(gameToken,NetworkProxy.eventToJSON(toPublish));

        retValues.put("return",Event.TRUE);
        retValues.put("killcount", Integer.toString(killcount));
        return new Event(e, retValues);
    }

}
