package it.polimi.ingsw.cg15.turncontroller;

import it.polimi.ingsw.cg15.model.field.Cell;


/**
 * 
 */
public class ItemState implements State {

	TurnController turnController;
    /**
     * @param turnController 
     * 
     */
    public ItemState(TurnController turnController) {
    	this.turnController= turnController;
    }



	public void attack() {
		// TODO Auto-generated method stub
		
		//NON HO ANCORA ATTACCATO
		 turnController.changeState(turnController.attackState);
		 turnController.getState().attack();
		//if has attack non non mi sposto in attack state
		//altrimenti mi sposto in attack state ed attacco
	}







	public void useItem() {
		// TODO Auto-generated method stub
		//setto la variabile item già usato = true
		//implemento il metodo
	}



	public void endTurn() {
		// TODO Auto-generated method stub
		
	}



	public void startTurn() {
		// TODO Auto-generated method stub
		
	}



	public void move(Cell cell) {
		// TODO Auto-generated method stub
		
	}


	


}