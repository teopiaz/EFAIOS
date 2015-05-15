package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;

public class FieldController {
	
	Field field;
			
			
	public FieldController(GameState gs){
		this.field = gs.getField();
	}

	public Coordinate getHumanStartingPosition() {
	    
	    return field.getHumanStartingPosition().getCoordinate();
	}
	
	
	public boolean isDangerousSector(Coordinate coord){
	    
	   if(field.getCell(coord).getSectorType() == Sector.GREY){
	       return true;
	   }
	    
	    return false;
	}

}
