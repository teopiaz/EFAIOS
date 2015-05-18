package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectorDeck {

    public static final int MAIN_DECK = 1;
    public static final int DISCARTED_DECK = 2;

    private List<SectorCard> sectorDeckMain = new ArrayList<SectorCard>();
    private List<SectorCard> sectorDeckDiscarded = new ArrayList<SectorCard>();

    /*
     * Ritorna una carta.
     */
    public SectorCard drawCard() {
        if (sectorDeckMain.isEmpty()) {
            swapDeck();
        }
        SectorCard drawed = sectorDeckMain.remove(0);
        sectorDeckDiscarded.add(drawed);
        return drawed;
    }

    /*
     * Return the number of the cards.
     */
    public int getNumberOfCard() {
        return sectorDeckMain.size();
    }

    public List<SectorCard> getSectorDeck() {
        return sectorDeckMain;
    }

    /*
     * Inserire cosa faccio.
     */
    public List<SectorCard> getSectorDeck(int selector) {
        if (selector == MAIN_DECK)
            return getSectorDeck();
        else if (selector == DISCARTED_DECK) {
            return sectorDeckDiscarded;
        }
        return null;
    }

    /*
     * Inserisce una carta nel mazzo delle carte.
     */
    public boolean insertCard(SectorCard card) {
        return sectorDeckMain.add(card);

    }

    /*
     * Shuffle the deck.
     */
    public void shuffleDeck() {
        Collections.shuffle(sectorDeckMain);

    }

    private void swapDeck() {
        if (sectorDeckMain.isEmpty()) {
            sectorDeckMain = sectorDeckDiscarded;
            sectorDeckDiscarded = new ArrayList<SectorCard>();
            shuffleDeck();
        }

    }

}
