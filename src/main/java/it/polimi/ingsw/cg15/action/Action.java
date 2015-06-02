package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * The class action is generic within the game, all other types of action inherit from this superclass.
 * @author MMP - LMR
 */
public abstract class Action {

    private GameController gameController;


    /**
     * The constructor
     * @param gc is the Game Controller
     */
    public Action(GameController gc) {
        this.gameController = gc;
    }

    /**
     * @return the game controller.
     */
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

    /**
     * This is the method that will be override.
     * @return
     */
    public abstract Event execute();


}
