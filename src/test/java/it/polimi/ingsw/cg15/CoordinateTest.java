package it.polimi.ingsw.cg15;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.field.Coordinate;

import java.util.List;

import org.junit.Test;

public class CoordinateTest {

    @Test
    public void testGetNeighborsList() {
        // Testo la lista di adiacenza.
        Coordinate coord = new Coordinate(1, 1);
        List<Coordinate> cordList = coord.getNeighborsList();
        assertTrue(cordList.size() == 6);

    }

}
