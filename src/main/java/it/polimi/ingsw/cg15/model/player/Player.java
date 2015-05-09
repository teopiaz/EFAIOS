package it.polimi.ingsw.cg15.model.player;


import it.polimi.ingsw.cg15.controller.cards.strategy.CardStrategy;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
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
    public ItemCard[] cards;


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
    public abstract void setPosition(Cell dest);



}