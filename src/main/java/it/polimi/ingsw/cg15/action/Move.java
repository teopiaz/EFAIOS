package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.field.Coordinate;

public class Move<T> extends Action {

    private Coordinate dest;

    public Move(GameController gc, Coordinate dest) {
        super(gc);
        this.dest = dest;
    }

    @Override
    public boolean execute() {
        PlayerController pc = getCurrentPlayerController();
        if (pc.moveIsPossible(dest)) {
            pc.movePlayer(dest);
            Action draw = new DrawSectorCard(getGameController());
            draw.execute();
            return true;
        }

        return false;
    }

}
