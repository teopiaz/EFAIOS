package it.polimi.ingsw.cg15.model.field;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 */
public class Field {

	/**
	 * 
	 */
	private Map<Coordinate,Cell> map = new ConcurrentHashMap<Coordinate, Cell>();
	private int r;
	private int c;
	
	private Cell humanStartingPosition;

	public Field(int row,int col){
		this.r=row;
		this.c=col;
		for(int i=1;i<r+1;i++){
			for(int j=1;j<c+1;j++){
				Coordinate coord = new Coordinate(i,j);
				addCell(coord, Sector.WHITE);
			}
		}
	}
	


	public Cell getHumanStartingPosition() {
        return humanStartingPosition;
    }



    public void setHumanStartingPosition(Cell humanStartingPosition) {
        this.humanStartingPosition = humanStartingPosition;
    }



    public void addCell(Coordinate coord, Sector sector ){
		map.put(coord, new Cell(coord, this, sector));
	}


	public int getR() {
		return r;
	}
	public int getC() {
		return c;
	}


	public Map<Coordinate,Cell> getField(){
		return map;
	}


	public Cell getCell(Coordinate coords){
		return map.get(coords);
	}

	public void printMap(){

		for(Entry<Coordinate, Cell> entry : map.entrySet()) {
			System.out.println("R: "+entry.getKey().getRow()+"C: "+entry.getKey().getCol()+" value: "+entry.getValue().getLabel());
		}
	}


	public boolean isReachable(Cell src,Cell dest,int distance){
		int k;

		Coordinate start = src.getCoordinate();


		//creo l'array con la lista di nodi vuoto
		List<List<Coordinate>> fringes = new ArrayList<List<Coordinate>>();

		List<Coordinate> visited = new LinkedList<Coordinate>();
		//creo la prima lista a distanza 0 e ci metto il nodo di partenza
		fringes.add(0,new LinkedList<Coordinate>());
		fringes.get(0).add(start);


		for(k=1; k<=distance ;k++){
			fringes.add(k,new LinkedList<Coordinate>());

			//prendo la lista dei nodi a k-1
			List<Coordinate> nodikmenouno =  fringes.get(k-1);


			for (Coordinate nodo : nodikmenouno) {

				//	prendo la lista dei suoi adiacenti non ancora visitati 

				for (Coordinate coordinate : nodo.getNeighborsList()) {

					if(map.containsKey(coordinate) && !visited.contains(coordinate)){
						if(coordinate.equals(dest.getCoordinate())){
							return true;
						}
						fringes.get(k).add(coordinate);
						visited.add(nodo);

					}
				}

			}

		}

		return false;


	}

//ritorna una lista di celle reaggiungibili da una sorgente data una distanza
	public List<Cell> getReachableCellsList(Cell src,int distance){
		int k;

		Coordinate start = src.getCoordinate();
		List<Cell> reachable = new ArrayList<Cell>();

		//creo l'array con la lista di nodi vuoto
		List<List<Coordinate>> fringes = new ArrayList<List<Coordinate>>();

		List<Coordinate> visited = new LinkedList<Coordinate>();
		//creo la prima lista a distanza 0 e ci metto il nodo di partenza
		fringes.add(0,new LinkedList<Coordinate>());
		fringes.get(0).add(start);


		for(k=1; k<=distance ;k++){
			fringes.add(k,new LinkedList<Coordinate>());

			//prendo la lista dei nodi a k-1
			List<Coordinate> nodikmenouno =  fringes.get(k-1);


			for (Coordinate nodo : nodikmenouno) {
				for (Coordinate coordinate : nodo.getNeighborsList()) {

					if(map.containsKey(coordinate) && !visited.contains(coordinate)){
						
						fringes.get(k).add(coordinate);
						reachable.add(map.get(coordinate));
						
						visited.add(nodo);

					}
				}

			}

		}
		
		return reachable;


	}



}