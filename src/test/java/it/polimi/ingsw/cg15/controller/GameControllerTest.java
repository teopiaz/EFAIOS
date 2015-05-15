package it.polimi.ingsw.cg15.controller;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.player.AlienPlayerController;
import it.polimi.ingsw.cg15.controller.player.HumanPlayerController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class GameControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	
	
	@Test
	public void testGetCurrentPlayerInstance() {
		Field field = new Field(1,1);

		List<Player> players = new ArrayList<Player>();
		players.add(
				new Player( 
						new Cell( new Coordinate(1, 1) ,field,Sector.WHITE), 
						PlayerType.ALIEN  )
				);
		GameState gs = new GameState(field,new DeckContainer(),players);
		gs.getTurnState().setCurrentPlayer(players.get(0));

		GameController  gc = new GameController(gs);
		assertTrue(gc.getCurrentPlayerInstance().getClass().equals(new AlienPlayerController(gs).getClass()));
		PlayerController pc2 =	gc.getCurrentPlayerInstance();
	
		pc2.moveIsPossible(new Coordinate(1, 2));
	
		
	}
	public static PlayerController objectToPlayerController(Object myObject) {
		// This will only work when the myObject currently holding value is string.
		return (PlayerController)myObject;
	}

}
