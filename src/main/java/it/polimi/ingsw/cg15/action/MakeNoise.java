package it.polimi.ingsw.cg15.action;

import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * This class contains the logic of "noise".
 */
public class MakeNoise extends Action {

    Event e;
    /**
     * @param gc the game controller
     */
    public MakeNoise(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }


    @Override
    public Event execute() {

        //TODO NOTIFICARE COL PUBLISHER IL RUMORE

        Map<String,String> retValues = e.getRetValues();
        retValues.put("noise", "true");
        e = new Event(e, retValues);
        return e;
    }

}
