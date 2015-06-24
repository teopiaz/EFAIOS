package it.polimi.ingsw.cg15.model.cards;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class HatchDeckTest {

    private HatchDeck hatchDeckTest;

    @Before
    public void setUp() throws Exception {
        hatchDeckTest = new HatchDeck();
    }

    @Test
    public void testDrawCard() {

        // Testo la pesca di una carta.

        hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
        HatchCard card =hatchDeckTest.drawCard();
        assertEquals(HatchCard.HATCH_GREEN, card);
        hatchDeckTest.drawCard();

    }

    @Test
    public void testGetHatchDeck() {
        
        // Testo il ritorno dei mazzi di carte.
        
        List<HatchCard> ritornato = hatchDeckTest.getHatchDeck();
        assertTrue(ritornato.isEmpty());
    }

    @Test
    public void testGetHatchDeckInt() {
        
        // Testo il ritorno del mazzo degli oggetti di fuga.
        
        List<HatchCard> ritornato = hatchDeckTest.getHatchDeck();
        assertTrue(ritornato.isEmpty());
    }

    @Test
    public void testGetNumberOfCard() {
        
        // Testo il ritorno del numero delle carte.
        
        assertEquals(0, hatchDeckTest.getNumberOfCard());
        hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
        assertEquals(1, hatchDeckTest.getNumberOfCard());
    }


    @Test
    public void testShuffleDeck() {
        
        // Testo il mescolamento delle carte del mazzo.
        
        hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
        hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
        hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
        hatchDeckTest.shuffleDeck();
        assertEquals(3, hatchDeckTest.getNumberOfCard());
    }



}
