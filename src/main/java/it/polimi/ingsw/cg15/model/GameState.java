package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.player.*;
import it.polimi.ingsw.cg15.turncontroller.State;

public class GameState {

	DeckContainer deckContainer = new DeckContainer();
	State currentState;
	Player currentPlayer;

	public DeckContainer getDeckContainer() {
		return deckContainer;
	}

	public void setCurrentState(State state) {
		this.currentState = state;
	}
	public State getCurrentState() {
		return this.currentState;
	}
	
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}
	
}
