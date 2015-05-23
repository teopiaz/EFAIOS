package it.polimi.ingsw.cg15.action;

import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.utils.MapLoader;

public class Move extends Action {

    private Coordinate dest;

    public Move(GameController gc, Coordinate dest) {
        super(gc);
        this.dest = dest;
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if (pc.moveIsPossible(dest)) {
            System.out.println();
            pc.movePlayer(dest);
            Action draw = new DrawSectorCard(getGameController());
            draw.execute();
            return true;
        }
        Logger.getLogger(Move.class.getName()).log(Level.INFO, "Action Move:  impossible to move");

        return false;
    }

}
