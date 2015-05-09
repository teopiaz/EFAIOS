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
    public void setPosition(Cell dest) {
		this.position=dest;
    }




}