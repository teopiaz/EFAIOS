package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * This class extends the class action and is therefore one of the possible actions to be taken during a turn. 
 * In particular, it is the action that allows the player to move to a cell in the map. 
 * Also, if the selected cell is a dangerous sector it is concerned to call the functionality that allows you to draw a card sector.
 */
public class Move extends Action {

    /**
     * It is the cell that will be the destination for the player.
     */
    private Coordinate dest;
    Event e;

    /**
     * @param gc the Game Controller
     * @param dest It is the cell that will be the destination for the player. 
     */
    public Move(GameController gc, Event request) {
        super(gc);
        this.e=request;
        String destinationString = e.getArgs().get("destination");
        this.dest = Coordinate.getByLabel(destinationString);
    }

    @Override
    public Event execute() {
        PlayerController pc = getCurrentPlayerController();
        if (pc.moveIsPossible(dest)) {
            System.out.println();
            pc.movePlayer(dest);
            Action draw = new DrawSectorCard(getGameController(),e);
            Event response = draw.execute();
            
            Map<String,String> retValues = response.getRetValues();
            retValues.put("return", "true");
            retValues.put("destination", dest.toString());

            return new Event(e, retValues);
        }
        Logger.getLogger(Move.class.getName()).log(Level.INFO, "Action Move:  impossible to move");

        Map<String,String> retValues = e.getRetValues();
        retValues.put("error", "impossibile eseguire lo spostamento");
        retValues.put("return", "false");

        return new Event(e, retValues);
    }

}
