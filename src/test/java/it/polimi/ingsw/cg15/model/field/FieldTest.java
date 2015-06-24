package it.polimi.ingsw.cg15.model.field;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FieldTest {
    
    
Field map;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        map = new Field();
    }

    @Test
    public final void testSetHatchBroken() {
        
        // Testo la lista dei settori di fuga.
        
        Coordinate coord = new Coordinate(1, 1);
        map.addCell(coord, Sector.HATCH);
        map.setHatchBroken(coord);
        assertFalse(map.getHatchSectorStatus(coord));

        map.addHatchToList(coord);
        
        assertTrue(map.getHatchSectorStatus(coord));
        map.setHatchBroken(coord);
        assertFalse(map.getHatchSectorStatus(coord));

        
    }

    @Test
    public void testIsReachable() {
        
        // Testo la possibilità o meno di raggiungee un settore.
        
        map.addCell(new Coordinate(1, 1), Sector.ALIEN);
        map.addCell(new Coordinate(1, 2), Sector.ALIEN);
        map.addCell(new Coordinate(1, 3), Sector.ALIEN);
        map.addCell(new Coordinate(2, 3), Sector.ALIEN);

        Cell cell1 = map.getCell(new Coordinate(1, 1));
        Cell cell2 = map.getCell(new Coordinate(2, 3));

        assertFalse(map.isReachable(cell1, cell2, 1));
        assertTrue(map.isReachable(cell1, cell2, 3));
    }
    
    @Test
    public void testPrintableMap(){
        
        // Testo la stampa della mappa.
        
        String testPrintMap ="1,1,5\n1,2,3\n1,3,0\n1,4,0\n1,5,0\n1,6,0\n1,7,0\n1,8,0\n1,9,0\n1,10,0\n1,11,0\n1,12,0\n1,13,0\n1,14,0\n1,15,0\n1,16,0\n1,17,0\n1,18,0\n1,19,0\n1,20,0\n1,21,0\n1,22,0\n1,23,0\n2,1,0\n2,2,0\n2,3,1\n2,4,2\n2,5,0\n2,6,0\n2,7,0\n2,8,0\n2,9,0\n2,10,0\n2,11,0\n2,12,0\n2,13,0\n2,14,0\n2,15,0\n2,16,0\n2,17,0\n2,18,0\n2,19,0\n2,20,0\n2,21,0\n2,22,0\n2,23,0\n3,1,0\n3,2,0\n3,3,4\n3,4,0\n3,5,0\n3,6,0\n3,7,0\n3,8,0\n3,9,0\n3,10,0\n3,11,0\n3,12,0\n3,13,0\n3,14,0\n3,15,0\n3,16,0\n3,17,0\n3,18,0\n3,19,0\n3,20,0\n3,21,0\n3,22,0\n3,23,0\n4,1,0\n4,2,0\n4,3,0\n4,4,0\n4,5,0\n4,6,0\n4,7,0\n4,8,0\n4,9,0\n4,10,0\n4,11,0\n4,12,0\n4,13,0\n4,14,0\n4,15,0\n4,16,0\n4,17,0\n4,18,0\n4,19,0\n4,20,0\n4,21,0\n4,22,0\n4,23,0\n5,1,0\n5,2,0\n5,3,0\n5,4,0\n5,5,0\n5,6,0\n5,7,0\n5,8,0\n5,9,0\n5,10,0\n5,11,0\n5,12,0\n5,13,0\n5,14,0\n5,15,0\n5,16,0\n5,17,0\n5,18,0\n5,19,0\n5,20,0\n5,21,0\n5,22,0\n5,23,0\n6,1,0\n6,2,0\n6,3,0\n6,4,0\n6,5,0\n6,6,0\n6,7,0\n6,8,0\n6,9,0\n6,10,0\n6,11,0\n6,12,0\n6,13,0\n6,14,0\n6,15,0\n6,16,0\n6,17,0\n6,18,0\n6,19,0\n6,20,0\n6,21,0\n6,22,0\n6,23,0\n7,1,0\n7,2,0\n7,3,0\n7,4,0\n7,5,0\n7,6,0\n7,7,0\n7,8,0\n7,9,0\n7,10,0\n7,11,0\n7,12,0\n7,13,0\n7,14,0\n7,15,0\n7,16,0\n7,17,0\n7,18,0\n7,19,0\n7,20,0\n7,21,0\n7,22,0\n7,23,0\n8,1,0\n8,2,0\n8,3,0\n8,4,0\n8,5,0\n8,6,0\n8,7,0\n8,8,0\n8,9,0\n8,10,0\n8,11,0\n8,12,0\n8,13,0\n8,14,0\n8,15,0\n8,16,0\n8,17,0\n8,18,0\n8,19,0\n8,20,0\n8,21,0\n8,22,0\n8,23,0\n9,1,0\n9,2,0\n9,3,0\n9,4,0\n9,5,0\n9,6,0\n9,7,0\n9,8,0\n9,9,0\n9,10,0\n9,11,0\n9,12,0\n9,13,0\n9,14,0\n9,15,0\n9,16,0\n9,17,0\n9,18,0\n9,19,0\n9,20,0\n9,21,0\n9,22,0\n9,23,0\n10,1,0\n10,2,0\n10,3,0\n10,4,0\n10,5,0\n10,6,0\n10,7,0\n10,8,0\n10,9,0\n10,10,0\n10,11,0\n10,12,0\n10,13,0\n10,14,0\n10,15,0\n10,16,0\n10,17,0\n10,18,0\n10,19,0\n10,20,0\n10,21,0\n10,22,0\n10,23,0\n11,1,0\n11,2,0\n11,3,0\n11,4,0\n11,5,0\n11,6,0\n11,7,0\n11,8,0\n11,9,0\n11,10,0\n11,11,0\n11,12,0\n11,13,0\n11,14,0\n11,15,0\n11,16,0\n11,17,0\n11,18,0\n11,19,0\n11,20,0\n11,21,0\n11,22,0\n11,23,0\n12,1,0\n12,2,0\n12,3,0\n12,4,0\n12,5,0\n12,6,0\n12,7,0\n12,8,0\n12,9,0\n12,10,0\n12,11,0\n12,12,0\n12,13,0\n12,14,0\n12,15,0\n12,16,0\n12,17,0\n12,18,0\n12,19,0\n12,20,0\n12,21,0\n12,22,0\n12,23,0\n13,1,0\n13,2,0\n13,3,0\n13,4,0\n13,5,0\n13,6,0\n13,7,0\n13,8,0\n13,9,0\n13,10,0\n13,11,0\n13,12,0\n13,13,0\n13,14,0\n13,15,0\n13,16,0\n13,17,0\n13,18,0\n13,19,0\n13,20,0\n13,21,0\n13,22,0\n13,23,0\n14,1,0\n14,2,0\n14,3,0\n14,4,0\n14,5,0\n14,6,0\n14,7,0\n14,8,0\n14,9,0\n14,10,0\n14,11,0\n14,12,0\n14,13,0\n14,14,0\n14,15,0\n14,16,0\n14,17,0\n14,18,0\n14,19,0\n14,20,0\n14,21,0\n14,22,0\n14,23,0\n";
        map.addCell(new Coordinate(1, 1), Sector.ALIEN);
        map.addCell(new Coordinate(1, 2), Sector.HATCH);
        map.addCell(new Coordinate(3, 3), Sector.HUMAN);
        map.addCell(new Coordinate(2, 3), Sector.GREY);
        map.addCell(new Coordinate(2, 4), Sector.WHITE);
                
        assertEquals(testPrintMap, map.getPrintableMap());

    }
    
}
