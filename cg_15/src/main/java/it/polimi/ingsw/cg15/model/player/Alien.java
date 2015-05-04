package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.exception.*;
import it.polimi.ingsw.cg15.model.field.Cell;

/**
 * 
 */
public class Alien extends Player {

    /**
     * 
     */
    public Alien(Cell origin) {
    	super(origin);
    }

    /**
     * @return
     */
	@Override
    public void move(Cell dest) {
		try{
    	if(this.position.distance(dest)<=2){
    		this.position=dest;
    	}
		}catch(IllegalArgumentException e){
			throw new InvalidAction();
		}
    }




}