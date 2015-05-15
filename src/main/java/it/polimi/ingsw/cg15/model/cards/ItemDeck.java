package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ItemDeck {

	private  List<ItemCard> itemDeck = new ArrayList<ItemCard>();
	private  List<ItemCard> itemDeckDiscarded = new ArrayList<ItemCard>();

	public static final int MAIN_DECK = 1;
	public static final int DISCARTED_DECK =2;


	public  List<ItemCard> getItemDeck(){
		return itemDeck;
	}
	public  List<ItemCard> getItemDeck(int selector){
		if(selector==MAIN_DECK)
			return getItemDeck();
		else if(selector==DISCARTED_DECK){
			return  itemDeckDiscarded;
		}
		return null;
	}

	public int getNumberOfCard(){
		return itemDeck.size();
	}


	private void swapDeck(){

		if(itemDeck.isEmpty()){
			itemDeck = itemDeckDiscarded;
			itemDeckDiscarded = new ArrayList<ItemCard>();
			Collections.shuffle(itemDeck);
		}

	}
	public void shuffleDeck(){
		Collections.shuffle(itemDeck);

	}

	public ItemCard drawCard(){
		if(itemDeck.isEmpty()){
			shuffleDeck();
		}
		ItemCard drawed = itemDeck.remove(0);
		itemDeckDiscarded.add(drawed);
		return drawed;
	}

	public boolean insertCard(ItemCard card){
		return itemDeck.add(card);

	}

}
