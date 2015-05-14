package it.polimi.ingsw.cg15.model.field;

import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.field.Direction;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Cell {

    private Sector sectorType;
	private Field map;
	private List<Player> players = new ArrayList<Player>();

	private final Coordinate coords;
	

	public Cell(Coordinate coords,Field map,Sector sectorType) {
		this.coords = coords;
		this.map = map;
		this.sectorType = sectorType;
	}

	
	public String getLabel(){
		return coords.toString();
	}
	
	public void addPlayer(Player player){
		this.players.add(player);
	}

	public void removePlayer(Player player){
		if(players.contains(player)){
			players.remove(player);
		}
		else{
			throw new InvalidAction("player non presente nella cella");
		}
	}
	
	
	public Coordinate getCoordinate(){
		return this.coords;
	}
	




}