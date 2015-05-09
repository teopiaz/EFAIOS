package it.polimi.ingsw.cg15.model.player;


import it.polimi.ingsw.cg15.controller.cards.Card;
import it.polimi.ingsw.cg15.controller.cards.strategy.CardStrategy;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
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
    
    
    /**
     * @return
     */
    public abstract void move(Cell dest);



}