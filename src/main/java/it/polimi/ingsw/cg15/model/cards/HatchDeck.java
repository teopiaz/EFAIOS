package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HatchDeck {

    private List<HatchCard> hatchDeckMain = new ArrayList<HatchCard>();

    public HatchCard drawCard() {
        if (hatchDeckMain.isEmpty()) {
            return null;
        }
        return hatchDeckMain.remove(0);

    }

    public List<HatchCard> getItemDeck() {
        return hatchDeckMain;

    }

    public int getNumberOfCard() {
        return hatchDeckMain.size();

    }

    public boolean insertCard(HatchCard card) {
        return hatchDeckMain.add(card);

    }

    public void shuffleDeck() {
        Collections.shuffle(hatchDeckMain);

    }

}
