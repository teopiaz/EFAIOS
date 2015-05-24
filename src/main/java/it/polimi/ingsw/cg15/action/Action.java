package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;

/**
 * The class action is generic within the game, all other types of action inherit from this superclass.
 * @author MMP - LMR
 */
public abstract class Action {

    private GameController gameController;


    public Action(GameController gc) {
        this.gameController = gc;
    }

    public GameController getGameController() {
        return this.gameController;
    }

    /**
     * @return the current instance of the player.
     */
    public PlayerController getCurrentPlayerController() {
        Player currentPlayer = getGameController().getCurrentPlayer();        
        return gameController.getPlayerInstance(currentPlayer);
    }

    public abstract boolean execute();


}
