package it.polimi.ingsw.cg15.turncontroller;

import it.polimi.ingsw.cg15.model.field.Cell;


/**
 * 
 */
public interface State {

public void attack();
public void useItem();
public void move(Cell cell);
public void endTurn();
public void startTurn();


	

}