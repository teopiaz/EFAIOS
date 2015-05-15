package it.polimi.ingsw.cg15.model.cards;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SectorDeckTest {

    
    private SectorDeck sectorDeckTest;
    
    
    @Before
    public void setUp() throws Exception {
        sectorDeckTest = new SectorDeck();
    }

    
    @Test
    public void testGetSectorDeck() {
       List<SectorCard> ritornato = sectorDeckTest.getSectorDeck(SectorDeck.DISCARTED_DECK);
        assertTrue(ritornato.isEmpty());
    }
 

    @Test
    public void testGetNumberOfCard() {
        assertEquals(0, sectorDeckTest.getNumberOfCard());
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
    }

    /*
     * TODO: Fare questo test.
     */
    @Test
    public void swapDeck() {
        
    }
    
    
    @Test
    public void testShuffleDeck() {
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
        sectorDeckTest.shuffleDeck();
       assertEquals(3, sectorDeckTest.getNumberOfCard()); 
    }

    
    @Test
    public void testDrawCard() {
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        sectorDeckTest.drawCard();
        assertEquals(0,sectorDeckTest.getNumberOfCard());
    }

    
}
