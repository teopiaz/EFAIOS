package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class contains information about the hatch cards and methods to access them.
 * @author MMP - LMR
 */
public class HatchDeck {

    private List<HatchCard> hatchDeckMain = new ArrayList<HatchCard>();

    /**
     * @return the card that I fished from the deck-type hatch.
     */
    public HatchCard drawCard() {
        if (hatchDeckMain.isEmpty()) {
            return null;
        }
        return hatchDeckMain.remove(0);
    }

    /**
     * @return the deck of the hatch cards.
     */
    public List<HatchCard> getHatchDeck() {
        return hatchDeckMain;

    }

    /**
     * @return the number of cards still in the deck hatch.
     */
    public int getNumberOfCard() {
        return hatchDeckMain.size();

    }

    /**
     * @param card to add to the deck
     * @return 
     */
    public boolean insertCard(HatchCard card) {
        return hatchDeckMain.add(card);
    }

    /**
     * Method that shuffles the cards.
     */
    public void shuffleDeck() {
        Collections.shuffle(hatchDeckMain);
    }

}
