package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MMP - LMR
 * This class refers to a specific situation of the game in which a player, after having drawn one card green segment has the possibility of lying on its real position declaring noise in an area of the game that is not really the one in which it is located. 
 * The choice of the cell where the state noise is at the discretion of the player.
 */
public class AskSector extends Action {
Event e;
    /**
     * @param gc the game controller
     */
    public AskSector(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    @Override
    public Event execute() {
        Map<String,String> retValues = new HashMap<String,String>();

        String strTarget = e.getArgs().get("position").toUpperCase();
        Coordinate target = Coordinate.getByLabel(strTarget);
        if(getGameController().getFieldController().existInMap(target)){
            getGameController().restoreActionList();
            Action noise = new MakeNoise(getGameController(), e);
            e = noise.execute();
            
            if(e.getRetValues()==null){
                retValues = new HashMap<String, String>();
            }else{
            retValues = e.getRetValues();
            }
            retValues.put("return", Event.TRUE);    
            e = new Event(e, retValues);
             return e;
        }
        retValues.put("return", Event.FALSE);
        retValues.put(Event.ERROR,"settore non valido");
        e = new Event(e, retValues);
        return e;


    }

}
