package it.polimi.ingsw.cg15.model.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;



/**
 * This is the instance of the model of the playing field.
 * @author MMP - LMR
 */
public class Field {


    /**
     *  The map.
     */
    private Map<Coordinate, Cell> map = new ConcurrentHashMap<Coordinate, Cell>();
    
    /**
     * The row
     */
    private int r;
    
    /**
     * The column.
     */
    private int c;

    /**
     * The location of the field from where the human players.
     */
    private Cell humanStartingPosition;
    
    /**
     * Alien players starting position in the current field
     */
    private Cell alienStartingPosition;

    private Map<Coordinate,Boolean> hatchSectorsList = new HashMap<Coordinate, Boolean>();
    

    public Field() {
    }

    /**
     * @param coordinate
     * @param sector
     */
    public void addCell(Coordinate coord, Sector sector) {
        map.put(coord, new Cell(coord, this, sector));
    }

    /**
     * @return the column
     */
    public int getC() {
        return c;
    }

    /**
     * @return the row.
     */
    public int getR() {
        return r;
    }

    public Cell getCell(Coordinate coords) {
        return map.get(coords);
    }

    /**
     * @return the map of the game.
     */
    public Map<Coordinate, Cell> getField() {
        return map;
    }

    /**
     * @return  the location of the field from where the human players.
     */
    public Cell getHumanStartingPosition() {
        return humanStartingPosition;
    }
    
    
    public void addHatchToList(Coordinate coord){
        hatchSectorsList.put(coord, true);
    }
    
    public void setHatchBroken(Coordinate coord){
        
        if (hatchSectorsList.containsKey(coord) && Objects.equals(hatchSectorsList.get(coord), true)) {
            hatchSectorsList.put(coord,false);
        }
 
    }
    
    public boolean getHatchSectorStatus(Coordinate coord){
        if(hatchSectorsList.containsKey(coord)){
            return hatchSectorsList.get(coord);
        }
        return false;
     }
    
    public Map<Coordinate, Boolean> getHatchSectorsList() {
        return hatchSectorsList;
    }

    /**
     * @param human starting position
     */
    public void setHumanStartingPosition(Cell humanStartingPosition) {
        this.humanStartingPosition = humanStartingPosition;
    }

    public Cell getAlienStartingPosition() {
        return alienStartingPosition;

    }

    public void setAlienStartingPosition(Cell alienStartingPosition) {
       this.alienStartingPosition = alienStartingPosition;
        
    }
    

    /**
     * @param source
     * @param distance
     * @return list of cells from a source reaggiungibili date a distance
     */
    public List<Cell> getReachableCellsList(Cell src, int distance) {
        int k;
        Coordinate start = src.getCoordinate();
        List<Cell> reachable = new ArrayList<Cell>();

        // creo l'array con la lista di nodi vuoto
        List<List<Coordinate>> fringes = new ArrayList<List<Coordinate>>();

        List<Coordinate> visited = new LinkedList<Coordinate>();
        // creo la prima lista a distanza 0 e ci metto il nodo di partenza
        fringes.add(0, new LinkedList<Coordinate>());
        fringes.get(0).add(start);

        for (k = 1; k <= distance; k++) {
            fringes.add(k, new LinkedList<Coordinate>());

            // prendo la lista dei nodi a k-1
            List<Coordinate> nodikmenouno = fringes.get(k - 1);

            for (Coordinate nodo : nodikmenouno) {
                for (Coordinate coordinate : nodo.getNeighborsList()) {

                    if (map.containsKey(coordinate) && !visited.contains(coordinate)) {

                        fringes.get(k).add(coordinate);
                        reachable.add(map.get(coordinate));

                        visited.add(nodo);

                    }
                }

            }

        }

        return reachable;

    }

    /**
     * @param src
     * @param dest
     * @param distance
     * @return 
     */
    public boolean isReachable(Cell src, Cell dest, int distance) {
        int k;

        Coordinate start = src.getCoordinate();

        // creo l'array con la lista di nodi vuoto
        List<List<Coordinate>> fringes = new ArrayList<List<Coordinate>>();
        
        List<Coordinate> visited = new LinkedList<Coordinate>();
        // creo la prima lista a distanza 0 e ci metto il nodo di partenza
        fringes.add(0, new LinkedList<Coordinate>());
        fringes.get(0).add(start);

        for (k = 1; k <= distance; k++) {
            fringes.add(k, new LinkedList<Coordinate>());

            // prendo la lista dei nodi a k-1
            List<Coordinate> nodikmenouno = fringes.get(k - 1);

            for (Coordinate nodo : nodikmenouno) {

                // prendo la lista dei suoi adiacenti non ancora visitati

                for (Coordinate coordinate : nodo.getNeighborsList()) {

                    if (map.containsKey(coordinate) && !visited.contains(coordinate)) {
                        if (coordinate.equals(dest.getCoordinate())) {
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


    
    
    public String getPrintableMap(){
        
        StringBuilder stringBuilder = new StringBuilder();

      String line;
        for (int r = 1; r < 15; r++) {
            for (int c = 1; c < 24; c++) {
                Cell cell = map.get(new Coordinate(r, c));
                if (cell != null) {
                     line = r+","+c+","+cell.getSectorType().getValue()+"\n";
                    }
                else{
                     line = r+","+c+","+"0"+"\n";
                }
                
                stringBuilder.append(line);

            }
        }
        String result = stringBuilder.toString();

        return result;
    }


  

}