package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.cards.DeckContainer;
import it.polimi.ingsw.cg15.cards.ItemCard;
import it.polimi.ingsw.cg15.cards.SectorCard;
import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 */
public class Human extends Player {

    /**
     * 
     */
	
	private List<ItemCard> cards = new ArrayList<ItemCard>();
	
    public Human(Cell origin) {
    	super(origin);
    }


	@Override
    public void move(Cell dest) {
		try{
    	if(this.position.distance(dest)<=1){
    		this.position=dest;
    	}
		}catch(IllegalArgumentException e){
			throw new InvalidAction();
		}
    }
	
	
    @Override
    public SectorCard drawSectorCard() {
    	SectorCard card = super.drawSectorCard();
    	if(card.hasItemIcon() && cards.size()<3){
    		cards.add(DeckContainer.getItemCard());
    	}
    	return card;
    	
    }

    public void useItemCard(ItemCard card){
    	card.action();
    }


        
    
}