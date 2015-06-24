package it.polimi.ingsw.cg15.model.field;

import static org.junit.Assert.*;

import org.junit.Test;

public class SectorTest {



    @Test
    public final void test() {
        
        // Testo l'enumerazione.
        
        assertEquals(Sector.ALIEN, Sector.valueOf("ALIEN"));
    }

}
