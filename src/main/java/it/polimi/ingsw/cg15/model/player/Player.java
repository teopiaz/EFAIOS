package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the instance of the model "Player", contains player information such as location, the list of cards that object and the type of player (human, alien). 
 * There is also the status that indicates whether the player is still alive or has been killed by an alien, in which case the variable status is false.
 * @author MMP - LMR
 */
public class Player {

    /**
     * Maximum number of objects that can be held simultaneously by a player is 3
     */
    public static final int MAX_ITEMCARD = 3;

    /**
     * The position of the player.
     */
    protected Cell position;

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
    private boolean status = true;


    /**
     * The constructor.
     * @param origin  the cell from where the players
     * @param type the type of player that is human or alien
     */
    public Player(Cell origin, PlayerType type) {
        this.position = origin;
        this.type = type;
        cards = new ArrayList<ItemCard>(3);
    }

    //TODO: verificare se questo serve!
    public Player(){
    }

    /**
     * This method returns the item cards held by the player via their identification.
     * @param id
     * @return
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
     * @return a boolean that says if the player is still alive or not
     */
    public boolean isAlive() {
        return status;
    }

    /**
     * @return says if the deck of cards the player's personal are present or not
     */
    public boolean isPersonalDeckEmpty(){
        return cards.isEmpty();
    }

    /**
     * Function to kill the player that makes zero parameter isAlive.
     * @return
     */
    public boolean killPlayer() {
        if(status==true){
            this.status = false;
            return true;
        }
        return false;
    }

    /**
     * Removing object card in the player's possession.
     * @param card
     * @return
     */
    public boolean removeCard(ItemCard card) {
        if(cards.remove(card)){
            return true;
        }
        return false;
    }

    /**
     * He adds the current player a card object available. The card must be passed to the function.
     * @param card
     * @return
     */
    public boolean addCard(ItemCard card){
        return cards.add(card);
    }

    /**
     * This function takes as input a target cell and move there the current player.
     * @param dest
     */
    public void setPosition(Cell dest) {
        this.position = dest;
    }
}