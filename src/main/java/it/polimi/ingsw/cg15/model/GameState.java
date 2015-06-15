package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MMP - LMR
 * The class that contains information about the state of the game, the players in a game and the current map used.
 */
public class GameState {

    /**
     * The deck container.
     */
    private DeckContainer deckContainer;
    
    /**
     * The field.
     */
    private Field field;
    
    /**
     * The turn state
     */
    private TurnState turnState;
    
    /**
     * The list of player in the current game state.
     */
    private List<Player> players;
    
    /**
     * The name of the match.
     */
    private String name;
    
    /**
     * The name of the map in which play.
     */
    private String mapName;
    
    /**
     * The max number of players in a current game.
     */
    public final int MAX_PLAYERS = 8;
    
    /**
     * The turn number.
     */
    private int turnNumber = 1;
    
    /**
     * Say if the game is started or no.
     */
    private boolean isStarted=false;
    
    /**
     * Say if the game is ended or no.
     */
    private boolean isEnded=false;
    
    /**
     * Say if the game is initialized.
     */
    private boolean isInit=false;

    /**
     * The constructor for the game.
     * @param field The field in which play.
     * @param deckContainer The decks.
     */
    public GameState(Field field, DeckContainer deckContainer) {
        this.field = field;
        this.deckContainer = deckContainer;
        this.players = new ArrayList<Player>();;
        turnState = new TurnState();
    }

    /**
     * @return The deck container.
     */
    public DeckContainer getDeckContainer() {
        return deckContainer;
    }
    
    /**
     * @param player The player to add in the current list of players.
     * @return the player added.
     */
    public Player addPlayer(Player player){
        if(this.players.size()<MAX_PLAYERS){
            this.players.add(player);
            return player;
        }
        return null;
    }
    
    /**
     * @return the number of the turn.
     */
    public int getTurnNumber(){
        return turnNumber;
    }
    
    /**
     * @param n The number to set as the current turn number.
     */
    public void setTurnNumber(int n){
        this.turnNumber=n;
    }
    
    /**
     * @param name The name to set as a game's name.
     */
    public void setName(String name){
        this.name=name;
    }
    
    /**
     * @return The name of the match.
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return The name of the map.
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * @param mapName the name to set as the map name.
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * @return The list of players.
     */
    public List<Player> getPlayerList() {
        return this.players;
    }

    /**
     * @return The current game field.
     */
    public Field getField() {
        return field;
    }

    /**
     * @return The turn state.
     */
    public TurnState getTurnState() {
        return turnState;
    }

    /**
     * @param currentPlayer The current player.
     * @return the current turn state.
     */
    public TurnState newTurnState(Player currentPlayer) {
        this.turnState = new TurnState();
        turnState.setCurrentPlayer(currentPlayer);
        return turnState;
    }
    
    /**
     * Set the game as started.
     */
    public void setStarted(){
       this.isStarted=true;
    }
    
    /**
     * Set the game as ended.
     */
    public void setEnded(){
        this.isEnded=true;
    }
    
    /**
     * Set the game as initialized.
     */
    public void setInit(){
        this.isInit=true;
    }
    
    /**
     * @return if the game is started.
     */
    public boolean isStarted(){
       return isStarted;
    }
    
    /**
     * @return if the game is ended.
     */
    public boolean isEnded(){
        return isEnded; 
    }

    /**
     * @return if the game is initialized.
     */
    public boolean isInit() {
        return isInit;
    }
    
}
