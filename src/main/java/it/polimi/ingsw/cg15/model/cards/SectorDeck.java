package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author MMP - LMR
 * This class contains information about the sector cards and methods to access them.
 */
public class SectorDeck {

    /**
     * The main deck of cards, the one where fishing for players. It is identified by the number 1.
     */
    public static final int MAIN_DECK = 1;

    /**
     * The discard deck because already used. It can be recovered via the selector value of 2.
     */
    public static final int DISCARTED_DECK = 2;

    /**
     * This is the deck of cards that is sector that are in use that have yet to be drawn.
     */
    private List<SectorCard> sectorDeckMain = new ArrayList<SectorCard>();

    /**
     * This is the deck of cards that have been discarded by the players. 
     * They are those that will be used to create a new deck in case the cards in the deck main runs out.
     */
    private List<SectorCard> sectorDeckDiscarded = new ArrayList<SectorCard>();

    /**
     * @return the card that I fished from the deck-type sector.
     */
    public SectorCard drawCard() {
        if (sectorDeckMain.isEmpty()) {
            swapDeck();
        }
        SectorCard drawed = sectorDeckMain.remove(0);
        sectorDeckDiscarded.add(drawed);
        return drawed;
    }

    /**
     * @return the number of cards still in the deck sector.
     */
    public int getNumberOfCard() {
        return sectorDeckMain.size();
    }

    /**
     * @return the sector card deck.
     */
    public List<SectorCard> getSectorDeck() {
        return sectorDeckMain;
    }

    /**
     * @param selector The selector for the type of deck.
     * @return the sector card deck involved.
     */
    public List<SectorCard> getSectorDeck(int selector) {
        if (selector == MAIN_DECK)
            return getSectorDeck();
        else if (selector == DISCARTED_DECK) {
            return sectorDeckDiscarded;
        }
        return null;
    }

    /**
     * @param card to add to the deck
     * @return the card added.
     */
    public boolean insertCard(SectorCard card) {
        return sectorDeckMain.add(card);
    }

    
    public boolean removeCard(SectorCard card) {
        return sectorDeckMain.remove(card);
    }


    /**
     * Method that shuffles the cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(sectorDeckMain);
    }

    /**
     * This function allows you to re-create the card deck if the current deck is empty.
     */
    private void swapDeck() {
            sectorDeckMain = sectorDeckDiscarded;
            sectorDeckDiscarded = new ArrayList<SectorCard>();
            shuffleDeck();
    }

}
