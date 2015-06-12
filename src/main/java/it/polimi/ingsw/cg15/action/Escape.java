package it.polimi.ingsw.cg15.action;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;


public class Escape extends Action {

    Event e;

    /**
     * @param gc the game controller
     * @param e 
     */
    public Escape(GameController gc, Event e) {
        super(gc);
        this.e=e;
    }

    @Override
    public Event execute() {


        Map<String,String> retValues = e.getRetValues();
        GameController gc = getGameController();
        FieldController fc = gc.getFieldController();
        Player currentPlayer = getGameController().getCurrentPlayer();
        PlayerController pc= gc.getPlayerInstance(currentPlayer);
        Coordinate sector = pc.getPlayerPosition();
        
        Map<String,String>pubRet = new HashMap<String, String>();

        
        if(gc.getCurrentPlayer().getPlayerType()!=PlayerType.HUMAN){
            return e;
        }

        if(!fc.isHatchSector(sector)){
            return e;
        }

        if(fc.isHatchBlocked(sector)){
            pubRet.put("hatch", Event.FALSE);
            pubRet.put("message", "il giocatore "+currentPlayer.getPlayerNumber()+" prova a scappare ma il settore "+sector.toString()+" è bloccato");
            retValues.put("hatch", Event.FALSE);
            pubRet.put(Event.ERROR, "il settore "+ sector.toString() +" è bloccato");

            
        }else{
            pubRet.put("hatch", Event.TRUE);
            retValues.put("hatch", Event.TRUE);

            HatchCard card = pc.drawHatchCard();
            if(card == HatchCard.HATCH_RED){
                retValues.put("hatchcard", "red");
                pubRet.put("hatchcard", "red");

            }
            if(card == HatchCard.HATCH_GREEN){
                pc.escape();
             
                retValues.put("hatchcard", "green");
                pubRet.put("hatchcard", "green");
                }
            
            
            //blocco il settore
            fc.blockHatchSector(sector);
            
            }


        pubRet.put("player", Integer.toString(currentPlayer.getPlayerNumber()));
        Event toPublish = new Event(new ClientToken("", e.getToken().getGameToken()),"log",null, pubRet);
        Broker.publish(e.getToken().getGameToken(), NetworkProxy.eventToJSON(toPublish));
        e = new Event(e,retValues);


        return e;
    }

}
