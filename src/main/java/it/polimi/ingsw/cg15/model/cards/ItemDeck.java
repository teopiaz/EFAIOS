package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ItemDeck {

    public static final int MAIN_DECK = 1;
    public static final int DISCARTED_DECK = 2;

    private List<ItemCard> itemDeckMain = new ArrayList<ItemCard>();
    private List<ItemCard> itemDeckDiscarded = new ArrayList<ItemCard>();


    public ItemCard drawCard() {
        if (itemDeckMain.isEmpty()) {
            swapDeck();
        }
        ItemCard drawed = itemDeckMain.remove(0);
        itemDeckDiscarded.add(drawed);
        return drawed;

    }

    public List<ItemCard> getItemDeck() {
        return itemDeckMain;

    }

    public List<ItemCard> getItemDeck(int selector) {
        if (selector == MAIN_DECK)
            return getItemDeck();
        else if (selector == DISCARTED_DECK) {
            return itemDeckDiscarded;
        }
        return null;

    }

    public int getNumberOfCard() {
        return itemDeckMain.size();
    }


    public boolean insertCard(ItemCard card) {
        return itemDeckMain.add(card);


    }

    public void shuffleDeck() {
        Collections.shuffle(itemDeckMain);

    }

    private void swapDeck() {


        if (itemDeckMain.isEmpty()) {
            itemDeckMain = itemDeckDiscarded;
            itemDeckDiscarded = new ArrayList<ItemCard>();
            Collections.shuffle(itemDeckMain);
        }


    }

}
