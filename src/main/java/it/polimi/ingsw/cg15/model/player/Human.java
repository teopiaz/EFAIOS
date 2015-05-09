package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.model.field.Cell;

/**
 * 
 */
public class Human extends Player {

	public Human(Cell origin) {
		super(origin);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void setPosition(Cell dest) {
		// TODO Auto-generated method stub
		
	}

    /**
     * 
     
	
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

*/
        
    
}