package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectorDeck {
	
	private  List<SectorCard> sectorDeck = new ArrayList<SectorCard>();
	private  List<SectorCard> sectorDeckDiscarded = new ArrayList<SectorCard>();
	
	public static final int MAIN_DECK = 1;
	public static final int DISCARTED_DECK =2;
	

	public  List<SectorCard> getSectorDeck(){
		return sectorDeck;
	}
	public  List<SectorCard> getSectorDeck(int selector){
		if(selector==MAIN_DECK)
			return getSectorDeck();
		else if(selector==DISCARTED_DECK){
			return  sectorDeckDiscarded;
		}
		return null;
	}
	
	public int getNumberOfCard(){
		return sectorDeck.size();
	}
	
	
	private void swapDeck(){
		
		if(sectorDeck.isEmpty()){
			sectorDeck = sectorDeckDiscarded;
			sectorDeckDiscarded = new ArrayList<SectorCard>();
			shuffleDeck();
		}
		 
		 
	}
	
	public void shuffleDeck(){
		Collections.shuffle(sectorDeck);

	}
	
	public SectorCard drawCard(){
		if(sectorDeck.isEmpty()){
			shuffleDeck();
		}
		SectorCard drawed = sectorDeck.remove(0);
		sectorDeckDiscarded.add(drawed);
		return drawed;
	}
	
	public boolean insertCard(SectorCard card){
		return sectorDeck.add(card);
		
	}

}
