package it.polimi.ingsw.cg15.model.field;


import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

/**
 * 
 */
public class Field {

	/**
	 * 
	 */
	private Table<Integer, Integer, Cell> map = TreeBasedTable.create();
	private int r;

	private int q;

	public Table<Integer, Integer, Cell> getMap() {
		return map;
	}

	public int getR() {
		return r;
	}
	public int getQ() {
		return q;
	}

	/**
	 * 
	 */
	public Field(int r,int q){
		this.r=r;
		this.q=q;
		for(int i=1;i<r+1;i++){
			for(int j=1;j<q+1;j++){
				map.put(i, j, new Cell(i,j,this,Sector.WHITE));
			}
		}
	}

	public Cell getCell(int r, int q){
		if(r>map.rowKeySet().size()){
			System.out.println("r= "+r+ " max = " + map.rowKeySet().size());
			return null;
		}
		else{
		return map.get(r, q);
		}
	}

	public void printMap(){
		int i,j;
		for(j=1;j<map.columnKeySet().size()+1;j++){
			System.out.println("");
				for (i=1;i<map.rowKeySet().size()+1;i++) {
				System.out.print(map.get(i, j).getLabel()+" ");
			}
		}
	}
}