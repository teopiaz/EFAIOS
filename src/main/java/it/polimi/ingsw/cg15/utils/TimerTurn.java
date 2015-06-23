package it.polimi.ingsw.cg15.utils;

import it.polimi.ingsw.cg15.controller.GameController;

import java.util.TimerTask;

public class TimerTurn extends TimerTask {
    
    private boolean interrupted=false;
    
    private GameController gc;
    
    public TimerTurn(GameController gc){
        this.gc = gc;
    }
    
    public void interruptTimer(){
        this.interrupted=true;
    }
    
    @Override
    public void run() {
        if(!interrupted)
        gc.nextTurn();
    }

}



