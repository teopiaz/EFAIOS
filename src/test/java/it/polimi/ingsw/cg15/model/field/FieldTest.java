package it.polimi.ingsw.cg15.model.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FieldTest {

    Field map;

    @Before
    public void setUp() throws Exception {
        map = new Field(10, 10);
        // map.printMap();
    }

    @Test
    public void testIsReachable() {

        Cell cell1 = map.getCell(new Coordinate(1, 1));
        System.out.println(cell1.getCoordinate());
        Cell cell2 = map.getCell(new Coordinate(3, 3));

        boolean res = map.isReachable(cell1, cell2, 3);
        System.out.println("res= " + res);
        assertTrue(res);
    }

}
