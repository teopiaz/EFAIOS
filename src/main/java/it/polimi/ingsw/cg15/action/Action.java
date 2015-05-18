package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;

public abstract class Action {

    private GameController gameController;


    public Action(GameController gc) {
        this.gameController = gc;
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public PlayerController getCurrentPlayerController() {
        Player currentPlayer = getGameController().getCurrentPlayer();
        return gameController.getPlayerInstance(currentPlayer);
    }

    public abstract boolean execute();


}
