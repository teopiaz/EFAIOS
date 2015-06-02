package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.Map;

/**
 * @author MMP - LMR
 * This is the class that contains the logic of the paper industry of red color. 
 * It retrieves the current position of the player and says "noise in that cell."
 */
public class NoiseRed extends Action {
    
    Event e;
    
    /**
     * @param gc the game controller
     * @param e 
     * @param item the icon of the item present in the sector card
     */
    public NoiseRed(GameController gc, Event e) {
        super(gc);
        this.e=e;
    }

    @Override
    public Event execute() {

        //faccio rumore sulla cella di destinazione
        Action noise = new MakeNoise(getGameController(),e);
        e = noise.execute();
        
        return e;
    }

}
