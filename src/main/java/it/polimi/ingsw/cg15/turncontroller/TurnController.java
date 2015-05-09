package it.polimi.ingsw.cg15.turncontroller;


/**
 * 
 */
public class TurnController {

	/**
	 * 
	 */
	public TurnController() {
	}

	/**
	 * 
	 */
	public boolean hasMoved;

	/**
	 * 
	 */
	public boolean hasAttacked;

	/**
	 * 
	 */
	public boolean usedItemCard;


	public State currentState;

	/**
	 * @param State
	 */
	public void setState(State state) {
		this.currentState = state;
	}
	

}