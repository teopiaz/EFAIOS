package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 *  * @author MMP - LMR
 * This is the instance of the model "Player", contains player information such as location, the list of cards that object and the type of player (human, alien). 
 * There is also the status that indicates whether the player is still alive or has been killed by an alien, in which case the variable status is false.
 */
public class Player {

    /**
     * Maximum number of objects that can be held simultaneously by a player is 3.
     */
    public static final int MAX_ITEMCARD = 3;

    /**
     * Variable to determine whether or not a player is part of a game.
     */
    public static final int INGAME = 0;

    /**
     * Variable that determines whether a player has won.
     */
    public static final int WIN = 1;

    /**
     * Variable that determines whether a player has killed.
     */
    public static final int KILLED = 2;

    /**
     * The position of the player.
     */
    protected Cell position;

    /**
     * The number of the player.
     */
    private int playerNumber;

    /**
     * List item cards the player owns. They are cards.
     */
    private List<ItemCard> cards;

    /**
     * list item cards the player owns. Are type paper
     */
    private PlayerType type;

    /**
     * The status of a player indicates if it is still active (in the race) in the current game, or if he was killed and then deleted. Default is active.
     */
    private int status = INGAME;

    /**
     * Variable that determines if a player was able to reach an hatch sector and escape.
     */
    private boolean escaped = false;

    /**
     * The constructor.
     * @param origin  the cell from where the players
     * @param type the type of player that is human or alien
     */
    public Player(Cell origin, PlayerType type) {
        this.position = origin;
        this.type = type;
        cards = new ArrayList<ItemCard>(MAX_ITEMCARD);
    }

    /**
     * A constructor.
     */
    public Player(){
        cards = new ArrayList<ItemCard>(MAX_ITEMCARD);
    }

    /**
     * This method returns the item cards held by the player via their identification.
     * @param id The identifier of the card.
     * @return The card requested by a id.
     */
    public ItemCard getCardById(int id){
        return cards.get(id);
    }

    /**
     * Method to get the number of the cards.
     * @return the number of object cards the player owns.
     */
    public int getCardListSize(){
        return cards.size();
    }

    /**
     * @return the type of player.
     */
    public PlayerType getPlayerType() {
        return this.type;
    }

    /**
     * @return the current position of the player
     */
    public Cell getPosition() {
        return this.position;
    }

    /**
     * @return a boolean that says if the player is still alive or not.
     */
    public boolean isAlive() {
        if(status==INGAME){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return says if the deck of the player has cards or not.
     */
    public boolean isPersonalDeckEmpty(){
        return cards.isEmpty();
    }

    /**
     * Function to kill the player and set isAlive to false.
     * @return
     */
    public boolean killPlayer() {
        if(status==INGAME){
            this.status = KILLED;
            return true;
        }
        return false;
    }

    /**
     * Removing object card in the player's possession.
     * @param card The card to eliminate.
     * @return a boolean for the success of the operation.
     */
    public boolean removeCard(ItemCard card) {
        if(cards.remove(card)){
            return true;
        }
        return false;
    }

    /**
     * He adds the current player a card object available. The card must be passed to the function.
     * @param card The card to add.
     * @return The card added.
     */
    public boolean addCard(ItemCard card){
        return cards.add(card);
    }

    /**
     * This function takes as input a target cell and move there the current player.
     * @param dest The destination.
     */
    public void setPosition(Cell dest) {
        this.position = dest;
    }

    /**
     * Set the type of the player.
     * @param type The type to be set.
     */
    public void setPlayerType(PlayerType type) {
        this.type=type;
    }

    /**
     * @return the number of the player.
     */
    public int getPlayerNumber() {
        return playerNumber;
    }

    /**
     * @param playerNumber the player's number to be set.
     */
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    /**
     * @return The card list of a player.
     */
    public List<ItemCard> getCardList() {
        return cards;
    }

    /**
     * Method that checks whether a player has run away from the game. That is won.
     * @return A boolean indicating whether or not the win.
     */
    public boolean isEscaped() {
        if(status==WIN){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @return The status of the player.Win, Killed, InGame.
     */
    public int getStatus() {
        return status;
    }

    /**
     * Method that sets a player escaped.
     */
    public void setEscaped() {
        setWin();
    }

    /**
     * Method that puts the player in the state won.
     */
    public void setWin() {
        this.status = WIN;
    }

}
