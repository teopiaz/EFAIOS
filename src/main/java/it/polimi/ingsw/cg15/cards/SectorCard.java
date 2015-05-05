package it.polimi.ingsw.cg15.cards;

public abstract class SectorCard implements Card {

	protected boolean itemIcon;

	public boolean hasItemIcon(){
		return this.itemIcon;
	}

	public void action() {
System.out.println("sono la classe SectorCard");		
	}

}
