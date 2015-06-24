package it.polimi.ingsw.cg15.utils;

import it.polimi.ingsw.cg15.controller.GameController;

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
        gc.nextTurn();
        System.out.println("TIMER FINITO per il giocatore: "+gc.getCurrentPlayer().getPlayerNumber());
        }else{
        System.out.println("TIMER INTERROTTO per il giocatore: "+gc.getCurrentPlayer().getPlayerNumber());
        }
    }

}



