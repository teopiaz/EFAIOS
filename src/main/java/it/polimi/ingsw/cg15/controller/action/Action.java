package it.polimi.ingsw.cg15.controller.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;

public abstract class Action<T> {

    private GameController gameController;


    public Action(GameController gc) {
        this.gameController = gc;
    }

    public GameController getGameController() {
        return this.gameController;
    }

    public PlayerController getCurrentPlayerController() {
        return gameController.getCurrentPlayerInstance();
    }

    public abstract T execute();

}
