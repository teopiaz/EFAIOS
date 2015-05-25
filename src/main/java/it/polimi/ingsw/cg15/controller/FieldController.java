package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.utils.MapLoader;

import java.util.List;

/**
 * @author MMP - LMR
 * This is the class of the game controller that takes care of managing the field.
 */
public class FieldController {
	
	Field field;
			
			
	public FieldController(GameState gs){
		this.field = gs.getField();
	}
	
	public boolean loadMap(String mapName){
	    return MapLoader.loadMap(field, mapName);
	}

	public Coordinate getHumanStartingPosition() {
	    
	    return field.getHumanStartingPosition().getCoordinate();
	}
	
	public void setHumanStartingPosition(Coordinate coord){
	    Cell startingPosition = field.getCell(coord);
	    if(startingPosition!=null){
	        field.setHumanStartingPosition(startingPosition);
	    }
	}
	
	
	public boolean isDangerousSector(Coordinate coord){
	    
	   if(field.getCell(coord).getSectorType() == Sector.GREY){
	       return true;
	   }
	    
	    return false;
	}
	
	public List<Player> getPlayersInSector(Coordinate coord){
	    return  field.getCell(coord).getPlayers();
	}
	
	

}
