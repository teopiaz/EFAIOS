package it.polimi.ingsw.cg15.model.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author MMP - LMR
 * This is the instance of the model of the playing field.
 */
public class Field {

    /**
     *  The map, a correspondence between a coordinate and a cell.
     */
    private Map<Coordinate, Cell> map = new ConcurrentHashMap<Coordinate, Cell>();

    /**
     * The row.
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

    /**
     * A list with the hatch sector.
     */
    private Map<Coordinate,Boolean> hatchSectorsList = new HashMap<Coordinate, Boolean>();

    /**
     * The constructor.
     */
    public Field() {
    }

    /**
     * A method that add a cell.
     * @param coordinate, The coordinate of the sector.
     * @param sector, The sector.
     */
    public void addCell(Coordinate coord, Sector sector) {
        map.put(coord, new Cell(coord, this, sector));
    }

    /**
     * @return the column.
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

    /**
     * Get a coordinate.
     * @param coords The coordinate of the sector.
     * @return The coordinate
     */
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
     * @return the location of the field from where the human players.
     */
    public Cell getHumanStartingPosition() {
        return humanStartingPosition;
    }

    /**
     * @param coord The coordinate to add as a hatch sector.
     */
    public void addHatchToList(Coordinate coord){
        hatchSectorsList.put(coord, true);
    }

    /**
     * @param coord The coordinate of the hatch sector to set as broken.
     */
    public void setHatchBroken(Coordinate coord){
        if (hatchSectorsList.containsKey(coord) && Objects.equals(hatchSectorsList.get(coord), true)) {
            hatchSectorsList.put(coord,false);
        }
    }

    /**
     * @param coord The coordinate to verify.
     * @return if the hatch sector is broken or not.
     */
    public boolean getHatchSectorStatus(Coordinate coord){
        if(hatchSectorsList.containsKey(coord)){
            return hatchSectorsList.get(coord);
        }
        return false;
    }

    /**
     * @return The list of hatch sectors.
     */
    public Map<Coordinate, Boolean> getHatchSectorsList() {
        return hatchSectorsList;
    }

    /**
     * @param The human starting position.
     */
    public void setHumanStartingPosition(Cell humanStartingPosition) {
        this.humanStartingPosition = humanStartingPosition;
    }

    /**
     * @return The alien starting position.
     */
    public Cell getAlienStartingPosition() {
        return alienStartingPosition;
    }

    /**
     * @param alienStartingPosition The alien starting position.
     */
    public void setAlienStartingPosition(Cell alienStartingPosition) {
        this.alienStartingPosition = alienStartingPosition;
    }

    /**
     * Method that returns a list of cells within a given cell of origin and a certain distance.
     * @param src A source cell.
     * @param distance the distance where verify the reachability.
     * @return list of cells from a source reachable given a distance.
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
     * Method that determines whether a cell is accessible by the player.
     * @param src The source cell.
     * @param dest The destination cell.
     * @param distance The max distance.
     * @return a boolean whether a cell is accessible or not.
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

    /**
     * Method that returns a printable map.
     * @return the printable map.
     */
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