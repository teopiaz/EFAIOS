package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MMP - LMR
 * This is the class of the model that contains information about the card deck object.
 */
public class ItemDeck {

    /**
     *  The main deck of cards, the one where fishing for players. It is identified by the number 1.
     */
    public static final int MAIN_DECK = 1;

    /**
     * The discard deck because already used. It can be recovered via the selector value of 2.
     */
    public static final int DISCARTED_DECK = 2;

    /**
     * This is the deck of cards that is item that are in use that have yet to be drawn.
     */
    private List<ItemCard> itemDeckMain = new ArrayList<ItemCard>();

    /**
     * This is the deck of cards that have been discarded by the players. 
     * They are those that will be used to create a new deck in case the cards in the deck main runs out.
     */
    private List<ItemCard> itemDeckDiscarded = new ArrayList<ItemCard>();

    /**
     * @return the card that I draw from the deck-type sector.
     */
    public ItemCard drawCard() {
        if (itemDeckMain.isEmpty()) {
            swapDeck();
        }
        ItemCard drawed = itemDeckMain.remove(0);
        itemDeckDiscarded.add(drawed);
        return drawed;
    }

    /**
     * @return the card deck object involved.
     */
    public List<ItemCard> getItemDeck() {
        return itemDeckMain;
    }

    /**
     * @return the card deck object involved.
     */
    public List<ItemCard> getDiscardedItemDeck() {
        return itemDeckDiscarded;
    }



    /**
     * @param card The card to add to the deck.
     * @return the card that has just been inserted into the deck.
     */
    public boolean insertCard(ItemCard card) {
        return itemDeckMain.add(card);
    }

    /**
     * Method that shuffles the cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(itemDeckMain);
    }

    /**
     * This function allows you to re-create the Item deck if the current deck is empty.
     */
    private void swapDeck() {
        itemDeckMain = itemDeckDiscarded;
        itemDeckDiscarded = new ArrayList<ItemCard>();
        Collections.shuffle(itemDeckMain);
    }

    /**
     * @return the number of cards still in the deck item.
     */
    public int getNumberOfCard() {
        return itemDeckMain.size();
    }

    /**
     * @param card to add to the discarded deck
     * @return the card that has just been inserted into the deck
     */
    public void addToDiscardedDeck(ItemCard card) {
        itemDeckDiscarded.add(card);
    }

}
