package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.ArrayList;
import java.util.List;

/*
 * Questa è l'istanza del model "Player", contiene le informazioni sul giocatore 
 * come: posizione, la lista delle carte oggetto che possiede e il tipo del giocatore 
 * (umano, alieno). Inoltre è presente status che indica se il giocatore è ancora vivo 
 * oppure è stato eliminato da un alieno, in quel caso la variabile status è false.
 */

public class Player {

    
    /**
     * Maximum number of objects that can be held simultaneously by a player is 3
     */
    public static final int MAX_ITEMCARD = 3;
    
    /**
     * Maximum number of objects that can be held simultaneously by a player is 3
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
     * Funzione che uccide il giocatore cioè mette a zero il parametro isAlive.
     * @return
     */
    public boolean killPlayer() {
        if(status==true){
            this.status = false;
            return true;
        }
        return false;
    }

    public boolean removeCard(ItemCard card) {
        if(cards.remove(card)){
            return true;
        }
        return false;
    }
    
    public boolean addCard(ItemCard card){
        return cards.add(card);
    }

    /**
     * Questo metodo assegna al giocatore una nuova posizione.
     */
    public void setPosition(Cell dest) {
        this.position = dest;
    }
}