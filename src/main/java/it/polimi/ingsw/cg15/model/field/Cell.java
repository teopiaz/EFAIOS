package it.polimi.ingsw.cg15.model.field;

import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Cell {

    private Sector sectorType;
	private Field map;
	private List<Player> players = new ArrayList<Player>();

	private final int x,y,z;
	private final String label;

	public Cell(int x, int y, int z,Field map,Sector sectorType) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.map = map;
		this.sectorType = sectorType;

		String tmp;
		tmp = new String(""+((char)(x+64)));
		if((z+1)<10)
			tmp = new String(tmp+"0");
		label = new String(tmp+(z));
	}

	public Cell(int r, int q, Field map,Sector sectorType){
		this(q,-q-r,r,map,sectorType);
	}

	//TODO: handle null parameter
	public int getDistance(Cell b){
		if(b==null){
			throw new IllegalArgumentException("destination parameter cannot be null");
		}
		return (Math.abs(this.x - b.getX()) + Math.abs(this.y - b.getY()) + Math.abs(this.z - b.getZ())) / 2;

	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
	public String getLabel(){
		return new String(this.label);
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



}