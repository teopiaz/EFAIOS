package it.polimi.ingsw.cg15.turncontroller;

import it.polimi.ingsw.cg15.model.field.Cell;


/**
 * 
 */
public class StartingState implements State {

     TurnController turnController;

	/**
     * @param turnController 
     * 
     */
    public StartingState(TurnController turnController) {
    	this.turnController = turnController;
    }


	public void move(Cell cell) {
		//POSSO MUOVERMI? cioè mi sono già mosso o no?
		//SE POSSO MUOVERMI
		turnController.changeState(turnController.moveState);
		 turnController.getState().move(cell);
		// TODO Auto-generated method stub
		
	}


	public void attack() {
		//NON HO ANCORA ATTACCATO oppure SONO UN ALIENO
		 turnController.changeState(turnController.attackState);
		 turnController.getState().attack();
		//if has attack non non mi sposto in attack state
		//altrimenti mi sposto in attack state ed attacco
		
	}

	public void endTurn() {
		// TODO Auto-generated method stub
		
	}

	public void useItem() {
		// TODO Auto-generated method stub
		
	}

	public void startTurn() {
		// TODO Auto-generated method stub
		
	}
		


}