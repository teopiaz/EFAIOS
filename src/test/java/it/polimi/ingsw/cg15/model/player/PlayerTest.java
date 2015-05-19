package it.polimi.ingsw.cg15.model.player;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    private Player playerTest;
    private Field fieldTest;
    private Cell cell;

    @Before
    public void setUp() throws Exception {
        fieldTest = new Field();
        playerTest = new Player(cell, PlayerType.ALIEN);
        cell = new Cell(new Coordinate(1, 1), fieldTest, Sector.ALIEN);
    }

    @Test
    public void testGetPosition() {
        Cell cell = new Cell(new Coordinate(1, 1), fieldTest, Sector.ALIEN);
        playerTest.setPosition(cell);
        assertEquals(cell, playerTest.getPosition());
    }

    @Test
    public void testGetPlayerType() {
        assertEquals(PlayerType.ALIEN, playerTest.getPlayerType());
    }

    @Test
    public void testSetPosition() {
        Cell cell2 = new Cell(new Coordinate(2, 2), fieldTest, Sector.GREY);
        playerTest.setPosition(cell2);
        assertEquals(cell2, playerTest.getPosition());
    }

    @Test
    public void testIsAlive() {
        assertTrue(playerTest.isAlive());
        playerTest.killPlayer();
        assertFalse(playerTest.isAlive());

    }

}
