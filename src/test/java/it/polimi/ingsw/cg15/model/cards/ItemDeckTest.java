package it.polimi.ingsw.cg15.model.cards;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class ItemDeckTest {

 private ItemDeck itemDeckTest;
 
 @Before
 public void setUp() throws Exception {
 itemDeckTest = new ItemDeck();
 }

 @Test
 public void testDrawCard() {
 itemDeckTest.insertCard(ItemCard.ITEM_ADRENALINE);
 ItemCard card =itemDeckTest.drawCard();
 assertEquals(ItemCard.ITEM_ADRENALINE, card);
 itemDeckTest.drawCard();
 
 }

 @Test
 public void testGetItemDeck() {
 List<ItemCard> ritornato = itemDeckTest.getItemDeck();
 assertTrue(ritornato.isEmpty());
 }

 @Test
 public void testGetItemDeckInt() {
 List<ItemCard> ritornato = itemDeckTest.getItemDeck();
 assertTrue(ritornato.isEmpty());
 }

 @Test
 public void testGetNumberOfCard() {
 assertEquals(0, itemDeckTest.getNumberOfCard());
 itemDeckTest.insertCard(ItemCard.ITEM_ADRENALINE);
 assertEquals(1, itemDeckTest.getNumberOfCard());
 }


 @Test
 public void testShuffleDeck() {
 itemDeckTest.insertCard(ItemCard.ITEM_ADRENALINE);
 itemDeckTest.insertCard(ItemCard.ITEM_ATTACK);
 itemDeckTest.insertCard(ItemCard.ITEM_DEFENSE);
 itemDeckTest.shuffleDeck();
 assertEquals(3, itemDeckTest.getNumberOfCard());
 }

 @Test
 public void testAddToDiscardedDeck() {     
     itemDeckTest.addToDiscardedDeck(ItemCard.ITEM_ADRENALINE);
     assertEquals(1,itemDeckTest.getDiscardedItemDeck().size());
     
     
 }

}
