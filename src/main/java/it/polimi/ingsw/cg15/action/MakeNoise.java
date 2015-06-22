package it.polimi.ingsw.cg15.action;

import java.util.HashMap;
import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

/**
 * @author MMP - LMR
 * This class contains the logic of "noise" in a particular sector..
 */
public class MakeNoise extends Action {

    /**
     * The event.
     */
    Event e;
    
    public static final String POSITION = "position";

    
    /**
     * @param gc The game controller.
     */
    public MakeNoise(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

/**
 * Perform the action of make noise in a particular sector.
 * @return a message with the list of return values.
 * @see it.polimi.ingsw.cg15.action.Action#execute()
 */
    @Override
    public Event execute() {
        Coordinate position;
        if(e.getArgs().containsKey(POSITION)){
            position = Coordinate.getByLabel(e.getArgs().get(POSITION));
        }else{
         position = getCurrentPlayerController().getPlayerPosition();
        }
        Map<String,String>retValues;
        if(e.getRetValues()==null){
            retValues = new HashMap<String, String>();
        }else{
        retValues = e.getRetValues();
        }
        retValues.put("noise", Event.TRUE);
        retValues.put(POSITION, position.toString());
        e = new Event(e, retValues);
        String currentPlayerNumber = Integer.toString( getGameController().getCurrentPlayer().getPlayerNumber() );
        Map<String,String> retPub = new HashMap<String, String>();
        retPub.put("player", currentPlayerNumber);
        retPub.put("noise", Event.TRUE);
        retPub.put(POSITION, position.toString());
        Event toPublish = new Event(new ClientToken("", getGameController().getGameToken()),"log",null, retPub);
        Broker.publish(getGameController().getGameToken(), NetworkProxy.eventToJSON(toPublish));
        return e;
    }

}
