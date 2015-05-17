package it.polimi.ingsw.cg15.model.field;

import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Cell {


    private final Coordinate coords;
    private final Sector sectorType;
    private final Field map;
    private List<Player> players = new ArrayList<Player>();



    public Cell(Coordinate coords,Field map,Sector sectorType) {
        this.coords = coords;
        this.map = map;
        this.sectorType = sectorType;

    }


    public void addPlayer(Player player){
        this.players.add(player);
    }

    public Coordinate getCoordinate(){
        return this.coords;
    }

    public String getLabel(){
        return coords.toString();
    }


    public Field getMap() {
        return map;
    }


    public List<Player> getPlayers() {
        return players;
    }


    public Sector getSectorType() {
        return sectorType;
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
