package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;

public class GameState {

	DeckContainer deckContainer = new DeckContainer();

	public DeckContainer getDeckContainer() {
		return deckContainer;
	}
	
}
