package it.polimi.ingsw.cg15.turncontroller;


import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.*;

/**
 * @author LMR - MMP
 */
public class MoveState implements State {

     TurnController turnController;

	/**
     * @param turnController 
     * 
     */
    public MoveState(TurnController turnController) {
    	this.turnController = turnController;

    }



	public void attack() {
		// TODO Auto-generated method stub
		
6		 turnController.changeState(turnController.attackState);
		 turnController.getState().attack();
		//if has attack non non mi sposto in attack state
		//altrimenti mi sposto in attack state ed attacco
	}

	public void move(Cell cell) {
		turnController.setHasMoved();

		//PlayerController.movePlayer(cell);
		
		//Settare la variabile "mi sono mosso" a true;
		//implementare il movimento vero e proprio del giocatore
		// TODO Auto-generated method stub
		
	}

	public void endTurn() {
		// TODO Auto-generated method stub
		
	}



	public void useItem() {
		
		// TODO Auto-generated method stub
		
		//controlla se ho già usato l'oggetto
		//SE GIà USATO ERRORE
		//ALTRIMENTI CAMBIA LO STATO E RILANCIA L'AZIONE
		 turnController.changeState(turnController.itemState);
		 turnController.getState().useItem();
	}



	public void startTurn() {
		// TODO Auto-generated method stub
		
	}


}