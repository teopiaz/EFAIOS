package it.polimi.ingsw.cg15.model.player;
import static org.junit.Assert.*;

import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;

import org.junit.Before;
import org.junit.Test;

public class PlayerTypeTest {
    
    private Player playerTest;
    private Field fieldTest;
    private Cell cell;

    @Before
    public void setUp() throws Exception {
        fieldTest = new Field(3,3);
        playerTest = new Player(cell, PlayerType.ALIEN);
        cell = new Cell(new Coordinate(1,1),fieldTest, Sector.ALIEN );
    }

    @Test
    public void testToClassName() {
        assertEquals("Alien", playerTest.getPlayerType().toClassName() );
    }

}
