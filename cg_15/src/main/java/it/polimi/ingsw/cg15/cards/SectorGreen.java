package it.polimi.ingsw.cg15.cards;

public class SectorGreen extends SectorCard {

	
	
	public SectorGreen(boolean item){
		this.itemIcon=item;
	}
	
	@Override
	public void action() {
	System.out.println("pescata carta settore Verde oggetto: "+this.itemIcon);
	}

}
