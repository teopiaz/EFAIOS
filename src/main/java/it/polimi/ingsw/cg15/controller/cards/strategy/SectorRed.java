package it.polimi.ingsw.cg15.controller.cards.strategy;

public class SectorRed extends SectorCardStrategy {

	
	public SectorRed(boolean item){
		this.itemIcon=item;
	}
	
	@Override
	public void action() {
		System.out.println("pescata carta settore Rossa oggetto: "+this.itemIcon);

	}

}
