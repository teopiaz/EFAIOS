package it.polimi.ingsw.cg15.turncontroller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;


/**
 * TODO: https://sourcemaking.com/design_patterns/state/java/6
 */
public class TurnController {
	
	public State attackState;
	public State endState;
	public State startingState;
	public State itemState;
	public State moveState;
	public boolean hasAttacked = false;
	public boolean itemUsed = false;
	public boolean  hasMoved = false;

	private GameState gameState;
	
	/**
	 * 
	 */
	public TurnController(GameState gameState) {
		this.gameState = gameState;
		
		attackState = new AttackState(this);
		endState = new EndState(this);
		startingState = new StartingState(this);
		itemState = new ItemState(this);
		moveState = new MoveState(this);
		
		
	}
	
	//AZIONI
	
	public void attack(){
		gameState.getCurrentState().attack();

	}
	
	public void useItem(){
		gameState.getCurrentState().useItem();

	}

	public void move(Cell cell){
		gameState.getCurrentState().move(cell);
	}
	

	public void endTurn() {
		// TODO Auto-generated method stub
		
	}
	
	
	//metodi di transizione
	
	public void changeState(State state){
		gameState.setCurrentState(state);
	}
	public State getState(){
		return gameState.getCurrentState(); 
	}
	
	
	public void nextPlayer(){
		//changeState(idleState);
	}

	///////////////////



	
	
	//getter e setter
	
	
	public boolean HasAttacked() {
		return hasAttacked;
	}

	public void setHasAttacked() {
		this.hasAttacked = true;
	}

	public boolean ItemUsed() {
		return itemUsed;
	}

	public void setItemUsed() {
		this.itemUsed = true;
	}


	public boolean HasMoved() {
		return hasMoved;
	}

	public void setHasMoved() {
		this.hasMoved = true;
	}
	
	public GameState getModel(){
		return this.gameState;
	}
	

}