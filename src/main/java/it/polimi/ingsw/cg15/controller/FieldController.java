package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.utils.MapLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MMP - LMR
 * This is the class of the game controller that takes care of managing the field.
 */
public class FieldController {

    /**
     * The field for the match.
     */
    Field field;

    /**
     * The constructor.
     * @param gs The Game State.
     */
    public FieldController(GameState gs){
        this.field = gs.getField();
    }

    /**
     * A method that return a field specified by the name's map.
     * @param mapName The name of the map.
     * @return the field requested.
     */
    public boolean loadMap(String mapName){
        return MapLoader.loadMap(field, mapName);
    }

    /**
     * A method that return the Human starting position sector.
     * @return the human starting position.
     */
    public Coordinate getHumanStartingPosition() {
        return field.getHumanStartingPosition().getCoordinate();
    }

    /**
     * A method that assign a starting position to a human player.
     * @param coord The coordinate of the sector for assign the starting position.
     */
    public void setHumanStartingPosition(Coordinate coord){
        Cell startingPosition = field.getCell(coord);
        if(startingPosition!=null){
            field.setHumanStartingPosition(startingPosition);
        }
    }

    /**
     * A method that verify if a sector is dangerous or not.
     * @param coord The coordinate of the sector to analyze.
     * @return yes or no if the sector is dangerous.
     */
    public boolean isDangerousSector(Coordinate coord){
        if(field.getCell(coord).getSectorType() == Sector.GREY){
            return true;
        }
        return false;
    }

    /**
     * A method that verify if a sector is an hatch or not.
     * @param coord The coordinate of the sector to analyze.
     * @return yes or no if the sector is hatch.
     */
    public boolean isHatchSector(Coordinate coord){
        if(field.getCell(coord).getSectorType() == Sector.HATCH){
            return true;
        }
        return false; 
    }

    /**
     * A method that verify if in a sector there is any player alive.
     * @param coord The coordinate of the sector to analyze.
     * @return the list of the player in that particular sector.
     */
    public List<Player> getPlayersInSector(Coordinate coord){
        if(existInMap(coord)){
            return  field.getCell(coord).getPlayers();
        }
        else{
            return new ArrayList<Player>();
        }
    }

    /**
     * A method that verify if a sector exist in the map or no.
     * @param coord The coordinate of the sector to analyze.
     * @return yes or not if the sector are in the map.
     */
    public boolean existInMap(Coordinate coord) {
        if(field.getCell(coord)!= null){
            return true;
        }
        return false;
    }

    /**
     * A method that verify if a hatch sector is already been used by a player. In this situation it will be blocked.
     * @param coord The coordinate of the sector to analyze.
     * @return true or false if the sector is blocked.
     */
    public boolean isHatchBlocked(Coordinate coord) {
        return (!field.getHatchSectorStatus(coord));
    }

    /**
     * A method that can block a sector if a player escape or he draw a red hatch card.
     * @param sector The coordinate of the sector to analyze.
     */
    public void blockHatchSector(Coordinate sector) {
        field.setHatchBroken(sector);
    }

    /**
     * @return true if all hatch sector are blocked.
     */
    public boolean allHatchBlocked() {
        for (Coordinate hatchSector : field.getHatchSectorsList().keySet()) {
            if(field.getHatchSectorStatus(hatchSector)){
                return false;
            }
        }
        return true;
    }

}
