package it.polimi.ingsw.cg15.utils;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

/**
 * @author MMP - LMR
 * The class for timer of the turn.
 */
public class TimerTurn extends TimerTask {
    
    /**
     * Interrupted state.
     */
    private boolean interrupted=false;
    
    /**
     * The game controller.
     */
    private GameController gc;
    
    /**
     * Timer turn.
     * @param gc The game controller.
     */
    public TimerTurn(GameController gc){
        this.gc = gc;
    }
    
    /**
     * Interrupted timer is true.
     */
    public void interruptTimer(){
        this.interrupted=true;
    }
    
    /**
     * Run the timer.
     * @see java.util.TimerTask#run()
     */
    @Override
    public void run() {
        if(!interrupted){
            Map<String,String> retPub = new HashMap<String,String>();
            retPub.put("message","Timeout Turn");
            Event toPublish = new Event(new ClientToken("", gc.getGameToken()),"log",null, retPub);
            Broker.publish(gc.getGameToken(), NetworkProxy.eventToJSON(toPublish));
        gc.nextTurn();
        }
    }

}



