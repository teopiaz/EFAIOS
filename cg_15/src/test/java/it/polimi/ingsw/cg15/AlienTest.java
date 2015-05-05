package it.polimi.ingsw.cg15;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.exception.InvalidAction;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.CellColor;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Alien;

import org.junit.Test;

public class AlienTest {

	@Test
	public void testMove() {
		Field map = new Field(5, 5);
		Cell cell = new Cell(1, 1, map,CellColor.WHITE);
		Alien a = new Alien(cell);
		Cell dest = new Cell(1,2,map,CellColor.WHITE);
		a.move(dest);
		assertEquals(a.getPosition(), dest);
		Cell dest2 = new Cell(5,2,map,CellColor.WHITE);
		a.move(dest2);
		assertEquals(a.getPosition(), dest);

	}

	@Test(expected=InvalidAction.class)
	public void testMoveNull() {
		
		Field map = new Field(5, 5);
		Cell cell = new Cell(1, 1, map,CellColor.WHITE);
		Alien a = new Alien(cell);
		a.move(null);
		assertEquals(a.getPosition(), cell);

	}



}
