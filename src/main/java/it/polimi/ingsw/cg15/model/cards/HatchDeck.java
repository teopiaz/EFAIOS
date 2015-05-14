package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HatchDeck {

	private  List<HatchCard> hatchDeck = new ArrayList<HatchCard>();



	public  List<HatchCard> getItemDeck(){
		return hatchDeck;
	}


	public int getNumberOfCard(){
		return hatchDeck.size();
	}


	public HatchCard drawCard(){
		if(hatchDeck.isEmpty()){
			return null;
		}
		return hatchDeck.remove(0);

	}

	public boolean insertCard(HatchCard card){
		return hatchDeck.add(card);

	}

}
