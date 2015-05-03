package it.polimi.ingsw.cg15;

/**
 * 
 */
public class Cell {


	private String identifier;

	private Colore color;
	private Field map;

	private final int x,y,z;
	private final String label;

	public Cell(int x, int y, int z,Field map) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.map = map;

		String tmp;
		tmp = new String(""+((char)(x+64)));
		if((z+1)<10)
			tmp = new String(tmp+"0");
		label = new String(tmp+(z));
	}

	public Cell(int r, int q, Field map){
		this(q,-q-r,r,map);
	}

	//TODO: handle null parameter
	public int distance(Cell b){
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




}