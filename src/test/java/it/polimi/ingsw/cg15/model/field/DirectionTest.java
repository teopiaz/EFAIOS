package it.polimi.ingsw.cg15.model.field;

import static org.junit.Assert.*;

import org.junit.Test;

public class DirectionTest {



    @Test
    public final void test() {
        
        // Testo l'enumerazione.
        
        assertEquals(Direction.NE, Direction.valueOf("NE"));
    }

}
