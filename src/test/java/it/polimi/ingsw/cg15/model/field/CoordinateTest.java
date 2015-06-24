package it.polimi.ingsw.cg15.model.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CoordinateTest {


    Coordinate coord;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        coord = new Coordinate(1, 2);
    }

    @Test
    public final void testHashCode() {
        assertEquals(coord.hashCode(),31651);
    }

    @Test
    public final void testCoordinateIntIntInt() {
        assertEquals( coord.getX()+coord.getY()+coord.getZ(),0);
    }

    @Test
    public final void testCoordinateIntInt() {
        Coordinate coord2 = new Coordinate(1,2,-3);
        assertEquals( coord2.getX()+coord2.getY()+coord2.getZ(),0);
    }

    @Test
    public final void testGetByLabel() {
        Coordinate bylabel = Coordinate.getByLabel("B1");
        assertEquals(bylabel.toString(), "B01");
        Coordinate bylabel2 = Coordinate.getByLabel("F18");
        assertEquals(bylabel2.toString(), "F18");
        Coordinate bylabel3 = Coordinate.getByLabel("L9");
        assertEquals( "L09",bylabel3.toString());


    }


    @Test
    public final void testGetCol() {
        assertEquals(coord.getCol(), 2);
        assertEquals(coord.getRow(), 1);

    }



    @Test
    public final void testToString() {

    }

    @Test
    public final void testEqualsObject() {
        Coordinate c1 = new Coordinate(1,2);
        Coordinate c2 = c1;
        Object c3 = new Object();
        assertEquals(c1, c2);
        assertNotEquals(c1,c3);
        assertNotEquals(c1,null);
    }



}
