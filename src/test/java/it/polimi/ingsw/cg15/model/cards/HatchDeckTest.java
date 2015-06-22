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
 hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
 HatchCard card =hatchDeckTest.drawCard();
 assertEquals(HatchCard.HATCH_GREEN, card);
 hatchDeckTest.drawCard();
 
 }

 @Test
 public void testGetHatchDeck() {
 List<HatchCard> ritornato = hatchDeckTest.getHatchDeck();
 assertTrue(ritornato.isEmpty());
 }

 @Test
 public void testGetHatchDeckInt() {
 List<HatchCard> ritornato = hatchDeckTest.getHatchDeck();
 assertTrue(ritornato.isEmpty());
 }

 @Test
 public void testGetNumberOfCard() {
 assertEquals(0, hatchDeckTest.getNumberOfCard());
 hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
 assertEquals(1, hatchDeckTest.getNumberOfCard());
 }


 @Test
 public void testShuffleDeck() {
 hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
 hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
 hatchDeckTest.insertCard(HatchCard.HATCH_GREEN);
 hatchDeckTest.shuffleDeck();
 assertEquals(3, hatchDeckTest.getNumberOfCard());
 }



}
