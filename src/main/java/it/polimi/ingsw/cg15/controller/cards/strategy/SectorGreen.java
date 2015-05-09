package it.polimi.ingsw.cg15.controller.cards.strategy;

public class SectorGreen extends SectorCardStrategy {

	
	
	public SectorGreen(boolean item){
		this.itemIcon=item;
	}
	
	@Override
	public void action() {
	System.out.println("pescata carta settore Verde oggetto: "+this.itemIcon);
	}

}


