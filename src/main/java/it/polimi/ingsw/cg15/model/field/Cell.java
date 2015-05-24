package it.polimi.ingsw.cg15.model.field;

import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that contains cell information of the game that are part of the map.
 * @author MMP - LMR
 */
public class Cell {

    /**
     * It contains the reference to the coordinates of the cell.
     */
    private final Coordinate coords;
    
    /**
     * It contains the information on the type of sector of the cell. It may be dangerous or not, departure or escape.
     */
    private final Sector sectorType;
    
    /**
     * The game board, within the field in which they move the players.
     */
    private final Field map;
    
    /**
     * The list of players.
     */
    private List<Player> players = new ArrayList<Player>();

    /**
     * The constructor of the cells.
     * @param coords coordinate.
     * @param the map
     * @param the type of the sector.
     */
    public Cell(Coordinate coords, Field map, Sector sectorType) {
        this.coords = coords;
        this.map = map;
        this.sectorType = sectorType;

    }

    /**
     * He adds the player that is passed as a parameter to the list of active players.
     * @param player
     */
    public void addPlayer(Player player){
        this.players.add(player);
    }

    /**
     * @return the coordinate.
     */
    public Coordinate getCoordinate(){
        return this.coords;
    }

    /**
     * It converts the coordinates in a string
     * @return the string
     */
    public String getLabel(){
        return coords.toString();
    }

    /**
     * @return the map
     */
    public Field getMap() {
        return map;
    }

    /**
     * @return he list of the players
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * @return the sector type of the cell.
     */
    public Sector getSectorType() {
        return sectorType;
    }

    /**
     * Removes the player passed as a parameter from the list of the players present.
     * @param player
     */
    public void removePlayer(Player player){
        if(players.contains(player)){
            players.remove(player);
        }
        else{
            throw new InvalidAction("player non presente nella cella");
        }
    }

}
