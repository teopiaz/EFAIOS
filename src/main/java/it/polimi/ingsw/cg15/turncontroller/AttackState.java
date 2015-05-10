package it.polimi.ingsw.cg15.turncontroller;

import it.polimi.ingsw.cg15.model.field.Cell;



/**
 * 
 */
public class AttackState implements State {

	TurnController turnController;
    /**
     * @param turnController 
     * 
     */
    public AttackState(TurnController turnController) {
    	this.turnController = turnController;
    }




	public void attack() {
			// TODO Auto-generated method stub
		
		//implemento tutta l'azione dell'attacco
		
	}



	public void move(Cell cell) {
		// TODO Auto-generated method stub
	//errore non puoi attaccare
		
	}

	public void endTurn() {
		// TODO Auto-generated method stub
		turnController.changeState(turnController.endState);
		turnController.getState().endTurn();
		
		
	}




	public void useItem() {
		// TODO Auto-generated method stub
		
	}




	public void startTurn() {
		// TODO Auto-generated method stub
		
	}





}