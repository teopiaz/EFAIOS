package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.exception.*;

/**
 * 
 */
public class Alien extends Player {

    /**
     * 
     */
    public Alien() {
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

    /**
     * @return
     */
    private void pesca() {
    }



}