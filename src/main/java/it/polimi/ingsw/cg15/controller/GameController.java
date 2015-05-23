package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.controller.player.AlienPlayerController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.utils.MapLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author LMR - MMP
 */
public class GameController implements Runnable {

    private GameState gameState;
    private FieldController fieldController;
    private BlockingQueue<Event> queue;

    public GameController(GameBox gameBox) {
        this.gameState = gameBox.getGameState();
        this.queue = gameBox.getQueue();
        // TODO: modificare la sequenza di creazione della partita

        this.fieldController = new FieldController(gameState);

    }

    public void run() {
        while (!queue.isEmpty()) {
            eventHandler(queue.poll());
        }
    }

    public void eventHandler(Event e) {

        System.out.println(this.toString() + " eventHandler -  evento: " + e.getCommand());
    }

    public FieldController getFieldController() {
        return this.fieldController;
    }

    // return a new specification instance of PlayerController from an
    // Enumeration Value
    public PlayerController getPlayerInstance(Player player) {
        String className = ((new PlayerController(gameState)).getClass().getPackage() + "."
                + player.getPlayerType().toClassName() + "PlayerController").substring("package "
                        .length());

        Class<?> classe;

        Object object = null;
        try {
            System.out.println(className);
            classe = Class.forName(className);
            Constructor<?> costruttore = classe.getConstructor(GameState.class);
            object = costruttore.newInstance(gameState);

        } catch (ClassNotFoundException e) {

            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "ClassNotFoundException", e);

        } catch (NoSuchMethodException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "NoSuchMethodException", e);

        } catch (SecurityException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "SecurityException", e);
        } catch (InstantiationException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "InstantiationException", e);

        } catch (IllegalAccessException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "IllegalAccessException", e);

        } catch (IllegalArgumentException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE,
                    "IllegalArgumentException", e);

        } catch (InvocationTargetException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE,
                    "InvocationTargetException", e);

        }
        return objectToPlayerController(object);
    }

    private PlayerController objectToPlayerController(Object myObject) {
        return (PlayerController) myObject;
    }

    public Player getCurrentPlayer() {
        return gameState.getTurnState().getCurrentPlayer();
    }

}
