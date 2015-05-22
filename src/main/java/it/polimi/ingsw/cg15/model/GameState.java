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
    private GameStatus gameStatus = GameStatus.INITIALIZATION;
    private final int MAX_PLAYERS = 8;


    public GameState(Field field, DeckContainer deckContainer) {
        this.field = field;
        this.deckContainer = deckContainer;
        this.players = new ArrayList<Player>();;
        turnState = new TurnState();
    }

    public DeckContainer getDeckContainer() {
        return deckContainer;
    }
    
    public boolean addPlayer(Player player){
        if(this.players.size()<MAX_PLAYERS){
            this.players.add(player);
            return true;
        }
        return false;
    }
    
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }


    public List<Player> getPlayerList() {
        return this.players;

    }

    public Field getField() {
        return field;
    }
    public Player getPlayerById(int id){
        return this.players.get(id);
    }




    public TurnState getTurnState() {
        return turnState;
    }



    public void setTurnState(TurnState state) {
        this.turnState = state;
    }

private enum GameStatus{
    INITIALIZATION,CREATED,STARTED,ENDED;
}


}
