package it.polimi.ingsw.cg15.model.cards;



import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

/*
 * SectorCard: 4 rosse con oggetto e 6 normali, 4 verdi con oggetto e 6 normali. 
 * 5 carte silenzio, tutte senza oggetti. il retro è un esagono.
 * ItemCard: 2 attacco, 2 teletrasporto, 2 adrenalina, 3 sedativi,
 *  2 luci, 1 difesa, il retro è un quadrato.
 */



public class DeckContainer {
	private  List<SectorCard> sectorDeck = new ArrayList<SectorCard>();
	private  List<SectorCard> sectorDeckDiscarded = new ArrayList<SectorCard>();
	private  List<ItemCard> itemDeck = new ArrayList<ItemCard>();
	private  List<ItemCard> itemDeckDiscarded = new ArrayList<ItemCard>();
	private  List<HatchCard> hatchDeck = new ArrayList<HatchCard>();

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

	public  List<HatchCard> getHatchDeck() {
		return hatchDeck;
	}



	public int itemCounter(){
		return itemDeck.size();
	}
	public int sectorCounter(){
		return sectorDeck.size();
	}
	public int hatchCounter(){
		return hatchDeck.size();
	}



}
