package it.polimi.ingsw.cg15.model.cards;

import static org.junit.Assert.*;


import org.junit.Test;

public class HatchCardTest {


    @Test
    public final void test() {
        
        // Testo l'enumerazione.
        
        assertEquals(HatchCard.HATCH_GREEN, HatchCard.valueOf("HATCH_GREEN"));
    }

}
