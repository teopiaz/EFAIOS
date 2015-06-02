package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GameState {


    private DeckContainer deckContainer;
    private Field field;
    private TurnState turnState;
    private List<Player> players;
    private String name;
    private String mapName;
    public final int MAX_PLAYERS = 8;
    
    private boolean isStarted=false;
    private boolean isEnded=false;
    private boolean isInit=false;


    public GameState(Field field, DeckContainer deckContainer) {
        this.field = field;
        this.deckContainer = deckContainer;
        this.players = new ArrayList<Player>();;
        turnState = new TurnState();
    }

    public DeckContainer getDeckContainer() {
        return deckContainer;
    }
    
    public Player addPlayer(Player player){
        if(this.players.size()<MAX_PLAYERS){
            this.players.add(player);
            return player;
        }
        return null;
    }
    
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }


    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    public List<Player> getPlayerList() {
        return this.players;

    }

    public Field getField() {
        return field;
    }

    public TurnState getTurnState() {
        return turnState;
    }



    public void newTurnState(Player currentPlayer) {
        this.turnState = new TurnState();
        turnState.setCurrentPlayer(currentPlayer);
    }
    

    public void setStarted(){
       this.isStarted=true;
    }
    public void setEnded(){
        this.isEnded=true;
    }
    
    public void setInit(){
        this.isInit=true;
    }
    
    public boolean isStarted(){
       return isStarted;
    }
    

    
    public boolean isEnded(){
        return isEnded; 
    }

    public boolean isInit() {
        return isInit;
    }
    



}
