package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.field.Cell;

/**
 * 
 */
public class Human extends Player {

    /**
     * 
     */
    public Human(Cell origin) {
    	super(origin);
    }

    public void drawSectorCard() {
    	//super().drawSectorCard();
    	
    }



	@Override
    public void move(Cell dest) {
		try{
    	if(this.position.distance(dest)<=1){
    		this.position=dest;
    	}
		}catch(IllegalArgumentException e){
			throw new InvalidAction();
		}
    }

        
    
}