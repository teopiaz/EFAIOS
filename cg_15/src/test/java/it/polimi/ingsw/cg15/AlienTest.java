package it.polimi.ingsw.cg15;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.Alien;
import it.polimi.ingsw.cg15.exception.InvalidAction;

import org.junit.Test;

public class AlienTest {

	@Test
	public void testMove() {
		
		Alien a = new Alien();
		Field map = new Field(5, 5);
		Cell cell = new Cell(1, 1, map);
		a.position = cell;
		Cell dest = new Cell(1,2,map);
		a.move(dest);
		assertEquals(a.position, dest);
		
		Cell dest2 = new Cell(5,5,map);
		a.move(dest);
		assertEquals(a.position, dest);
	
	}
	
	@Test(expected=InvalidAction.class)
	public void testMoveNull() {
		Alien a = new Alien();
		Field map = new Field(5, 5);
		Cell cell = new Cell(1, 1, map);
		a.position = cell;
		a.move(null);
		assertEquals(a.position, cell);

	}

	

}
