package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This is the class that contains the logic of the of the noise red. 
 * It retrieves the current position of the player and says "noise in that cell".
 */
public class NoiseRed extends Action {
    
    /**
     * The event.
     */
    Event e;
    
    /**
     * @param gc The game controller.
     * @param e The event.
     */
    public NoiseRed(GameController gc, Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * The logic of the of the noise red.
     * @return a message with the list of return values.
     * @see it.polimi.ingsw.cg15.action.Action#execute()
     */
    @Override
    public Event execute() {
        //faccio rumore sulla cella di destinazione
        Action noise = new MakeNoise(getGameController(),e);
        e = noise.execute();
        return e;
    }

}
