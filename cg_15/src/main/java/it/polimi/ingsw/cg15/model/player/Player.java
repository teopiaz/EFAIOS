package it.polimi.ingsw.cg15.model.player;


import it.polimi.ingsw.cg15.cards.Card;
import it.polimi.ingsw.cg15.cards.DeckContainer;
import it.polimi.ingsw.cg15.cards.SectorCard;
import it.polimi.ingsw.cg15.model.field.Cell;

/**
 * 
 */
public abstract class Player {


    /**
     * 
     */
    protected Cell position;

    /**
     * 
     */
    public Card[] cards;


    /**
     * 
     */
    public Player(Cell origin) {
    	this.position = origin;
    }
    
    public Cell getPosition(){
    	return this.position;
    }
    
    public void drawSectorCard(){
    	SectorCard card = DeckContainer.getSectorCard();
    	card.action();
    }
    
    /**
     * @return
     */
    public abstract void move(Cell dest);



}