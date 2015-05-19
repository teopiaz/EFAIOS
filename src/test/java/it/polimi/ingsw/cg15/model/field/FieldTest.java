package it.polimi.ingsw.cg15.model.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class FieldTest {

    Field map;

    @Before
    public void setUp() throws Exception {
        map = new Field();
    }

    @Test
    public void testIsReachable() {
        map.addCell(new Coordinate(1, 1), Sector.ALIEN);
        map.addCell(new Coordinate(1, 2), Sector.ALIEN);
        map.addCell(new Coordinate(1, 3), Sector.ALIEN);
        map.addCell(new Coordinate(2, 3), Sector.ALIEN);

        Cell cell1 = map.getCell(new Coordinate(1, 1));
        System.out.println(cell1.getCoordinate());
        Cell cell2 = map.getCell(new Coordinate(2, 3));

        assertFalse(map.isReachable(cell1, cell2, 2));
        assertTrue(map.isReachable(cell1, cell2, 3));
    }

}
