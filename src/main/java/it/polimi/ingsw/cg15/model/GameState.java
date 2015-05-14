package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.List;


public class GameState {

	
private	DeckContainer deckContainer;
private	Field field;
private	TurnState turnState;
private	List<Player> players;



public GameState(Field field, DeckContainer deckContainer,List<Player> players){
	this.field=field;
	this.deckContainer = deckContainer;
	this.players = players;
	turnState = new TurnState();
}

	public DeckContainer getDeckContainer() {
		return deckContainer;
	}
	
	public List<Player> getPlayerList(){
		return this.players;
	}
	public Player getPlayerById(int id){
		return this.players.get(id);
	}
	
	public Field getField() {
		return field;
	}


	public TurnState getTurnState() {
		return turnState;
	}
	
	public void setTurnState(TurnState state){
		this.turnState = state;
	}


	
	
}
