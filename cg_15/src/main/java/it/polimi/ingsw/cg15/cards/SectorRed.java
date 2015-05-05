package it.polimi.ingsw.cg15.cards;

public class SectorRed extends SectorCard {

	
	public SectorRed(boolean item){
		this.itemIcon=item;
	}
	
	@Override
	public void action() {
		System.out.println("pescata carta settore Rossa oggetto: "+this.itemIcon);

	}

}
