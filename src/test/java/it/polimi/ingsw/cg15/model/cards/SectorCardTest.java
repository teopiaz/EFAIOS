package it.polimi.ingsw.cg15.model.cards;

import static org.junit.Assert.*;


import org.junit.Test;

public class SectorCardTest {


    @Test
    public final void test() {
        assertEquals(SectorCard.SECTOR_RED, SectorCard.valueOf("SECTOR_RED"));
    }

}
