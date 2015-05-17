package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.controller.player.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * @author LMR - MMP
 */
public class GameController {


	private GameState gameState;
	private FieldController fieldController;


	public GameController(GameState gs) {
		this.gameState = gs;
		fieldController = new FieldController(gs);
	}



	public FieldController getFieldController() {
		return this.fieldController;
	}



    // return a new specification instance of PlayerController from an
    // Enumeration Value
    public PlayerController getCurrentPlayerInstance() {
        Player currentPlayer = gameState.getTurnState().getCurrentPlayer();
        String className = ((new PlayerController(gameState)).getClass()
                .getPackage()
                + "."
                + currentPlayer.getPlayerType().toClassName() + "PlayerController")
                .substring("package ".length());

        Class<?> classe;

        Object object = null;
        try {
            System.out.println(className);
            classe = Class.forName(className);

            Constructor<?> costruttore = classe.getConstructor(GameState.class);
            object = costruttore.newInstance(gameState);
            System.out.println(object.toString() + " object");

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return objectToPlayerController(object);

    }

    private PlayerController objectToPlayerController(Object myObject) {
        return (PlayerController) myObject;
    }


}
