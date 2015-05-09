package it.polimi.ingsw.cg15.controller.cards.strategy;

import it.polimi.ingsw.cg15.controller.cards.Card;

public abstract class SectorCardStrategy implements Card {

	protected boolean itemIcon;

	public boolean hasItemIcon(){
		return this.itemIcon;
	}

	public void action() {
System.out.println("sono la classe SectorCard");		
	}

}
