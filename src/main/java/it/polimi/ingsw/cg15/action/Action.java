package it.polimi.ingsw.cg15.action;

import java.util.Map;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

/**
 * The class action is generic within the game, all other types of action inherit from this superclass.
 * @author MMP - LMR
 */
public abstract class Action {

    /**
     * The game controller.
     */
    private GameController gameController;

    /**
     * The constructor.
     * @param gc Is the Game Controller.
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
     * This is the method that will be override by other classes to implements the Command pattern per le azioni.
     * @return Event
     */
    public abstract Event execute();
    
    public Event checkCardUse(Event e, Map<String, String> retValues){
        
        PlayerController pc = getCurrentPlayerController();
        if(!pc.canUseCard()){
            retValues.put(Event.RETURN, Event.FALSE);
            retValues.put(Event.ERROR,"solo gli umani possono usare le carte oggetto");
            return new Event(e, retValues);
        }
        if(pc.itemCardUsed()){
            retValues.put(Event.RETURN, Event.FALSE);
            retValues.put(Event.ERROR,"carta già usata in questo turno");
            return new Event(e, retValues);
        }
        return null;
    }

}
