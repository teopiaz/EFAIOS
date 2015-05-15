package it.polimi.ingsw.cg15.controller;


import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.controller.player.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * @author LMR - MMP
 */
public class GameController implements Observer {

	private GameState gameState;
	private FieldController fieldController;

	public GameController(GameState gs) {
		this.gameState = gs;
		fieldController = new FieldController(gs);
	}


	/**
	 * @return
	 */
	public void handleMessage() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void StartGame() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void NextPlayer() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void SaveGameState() {
		// TODO implement here
	}

	/**
	 * 
	 */
	public void LoadGameState() {
		// TODO implement here
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub

	}

	//return a new specification instance of PlayerController from an Enumeration Value
	public PlayerController getCurrentPlayerInstance(){
		Player currentPlayer = gameState.getTurnState().getCurrentPlayer();
		String className = ((new PlayerController(gameState)).getClass().getPackage()+"."+currentPlayer.getType().toClassName()+"PlayerController").substring("package ".length());

		Class<?> classe;

		Object object = null;
		try {
			System.out.println(className);
			classe = Class.forName(className);

			Constructor<?> costruttore = classe.getConstructor(GameState.class);
			object = costruttore.newInstance(gameState);
			System.out.println(object.toString()+" object");

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
		return (PlayerController)myObject;
	}




	public FieldController getFieldController() {
		return this.fieldController;
	}

}