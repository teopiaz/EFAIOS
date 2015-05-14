package it.polimi.ingsw.cg15.model.player;


import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public abstract class Player {
	

			
    protected Cell position;

    private List<ItemCard> cards;
    
    private PlayerType type;

    private boolean status = true;
    

    /**
     * 
     */
    public Player(Cell origin,PlayerType type) {
    	this.position = origin;
    	this.type = type;
    	cards = new ArrayList<ItemCard>(3);
    }
    
    public Cell getPosition(){
    	return this.position;
    }
    
   
    public void setPosition(Cell dest){
    	this.position = dest;
    }
    
    public boolean isAlive(){
    	return status;
    }
    
    public void killPlayer(){
    	this.status=false;
    }



}