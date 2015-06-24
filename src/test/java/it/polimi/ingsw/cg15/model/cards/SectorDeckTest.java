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
        
        // Testo il mazzo delle carte settore.
        
        List<SectorCard> ritornato = sectorDeckTest.getSectorDeck();
        assertTrue(ritornato.isEmpty());
    }

    @Test
    public void testGetNumberOfCard() {
        
        // Testo il numero dell carte settore.
        
        assertEquals(0, sectorDeckTest.getNumberOfCard());
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
    }


    @Test
    public void swapDeck() {

        // Testo lo scambio del mazzo corrente.

        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
        sectorDeckTest.getSectorDeck();
        assertEquals(1, sectorDeckTest.getNumberOfCard());
        
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
        sectorDeckTest.drawCard();
    }

    @Test
    public void testShuffleDeck() {
        
        // Testo il mescolamento delle carte.
        
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN);
        sectorDeckTest.shuffleDeck();
        assertEquals(3, sectorDeckTest.getNumberOfCard());
    }

    @Test
    public void testDrawCardFull() {
        
        // Testo che se pesco una carta il numero delle carte rimanenti è 0.
        
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        sectorDeckTest.drawCard();
        assertEquals(0, sectorDeckTest.getNumberOfCard());
    }
    
    @Test
    public void testDrawCardEmpty() {
        
        // Testo che non posso pescare se il numero delle carte è 0.
        
        sectorDeckTest.getSectorDeck();
        sectorDeckTest.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        assertEquals(1, sectorDeckTest.getNumberOfCard());
        sectorDeckTest.drawCard();
        
        sectorDeckTest.getSectorDeck().remove(SectorCard.SECTOR_GREEN_ITEM);
        assertEquals(0, sectorDeckTest.getNumberOfCard());
        
        sectorDeckTest.drawCard();

        
    }




}
