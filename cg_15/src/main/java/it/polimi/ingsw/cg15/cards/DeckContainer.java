package it.polimi.ingsw.cg15.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * SectorCard: 4 rosse con oggetto e 6 normali, 4 verdi con oggetto e 6 normali. 
 * 5 carte silenzio, tutte senza oggetti. il retro è un esagono.
 * ItemCard: 2 attacco, 2 teletrasporto, 2 adrenalina, 3 sedativi,
 *  2 luci, 1 difesa, il retro è un quadrato.
 */



public class DeckContainer {
	private static List<SectorCard> sectorDeck = new ArrayList<SectorCard>();
	private static List<SectorCard> sectorDeckDiscarded = new ArrayList<SectorCard>();
	private static List<ItemCard> itemDeck = new ArrayList<ItemCard>();
	private static List<ItemCard> itemDeckDiscarded = new ArrayList<ItemCard>();
	private static List<ItemCard> HatchDeck = new ArrayList<ItemCard>();

	private static int sectorItem=4;
	private static int sector=6;
	private static int sectorSilence=5;

	private static int itemAttack = 2;
	private static int itemTeleport = 2;
	private static int itemAdrenaline=3;
	private static int itemSedatives=3;
	private static int itemSpotlight = 2;
	private static int itemDefense = 1;


	static{
		generateSectorDeck();
		generateItemDeck();
	}

	
	private DeckContainer(){
		
	}

	private static void generateSectorDeck(){
		for(int i=0;i<sectorItem;i++){
			SectorCard red = new SectorRed(true);
			sectorDeck.add(red);
			SectorCard green = new SectorGreen(true);
			sectorDeck.add(green);
		}
		for(int i=0;i<sector;i++){
			SectorCard red = new SectorRed(false);
			sectorDeck.add(red);
			SectorCard green = new SectorGreen(false);
			sectorDeck.add(green);
		}
		for(int i=0;i<sectorSilence;i++){
			SectorCard silence = new SectorSilence();
			sectorDeck.add(silence);
		}

		Collections.shuffle(sectorDeck);
	}

	private static void generateItemDeck() {
		for(int i=0;i<itemAttack;i++){
			ItemCard attack = new ItemAttack();
			itemDeck.add(attack);
		}
		for(int i=0;i<itemTeleport;i++){
			ItemCard teleport = new ItemTeleport();
			itemDeck.add(teleport);
		}
		for(int i=0;i<itemAdrenaline;i++){
			ItemCard adrenaline = new ItemAdrenaline();
			itemDeck.add(adrenaline);
		}
		for(int i=0;i<itemSedatives;i++){
			ItemCard sedatives = new ItemSedatives();
			itemDeck.add(sedatives);
		}
		for(int i=0;i<itemSpotlight;i++){
			ItemCard spotlight = new ItemSpotlight();
			itemDeck.add(spotlight);
		}
		for(int i=0;i<itemDefense;i++){
			ItemCard defense = new ItemDefense();
			itemDeck.add(defense);
		}

		Collections.shuffle(itemDeck);

	}

	public static List<SectorCard> getSectorDeck(){
		return sectorDeck;
	}
	public static List<ItemCard> getItemDeck(){
		return itemDeck;
	}

	public static SectorCard getSectorCard(){
		if(sectorDeck.isEmpty()){
			sectorDeck = sectorDeckDiscarded;
			sectorDeckDiscarded = new ArrayList<SectorCard>();
			Collections.shuffle(sectorDeck);
			System.out.println("sector rimischiato contiene "+sectorDeck.size()+" su 25 ");

		}
		SectorCard drawn =  sectorDeck.remove(0); 
		sectorDeckDiscarded.add(drawn);
		System.out.println("sector pescata "+drawn);
		return drawn;
	}

	public static ItemCard getItemCard(){
		
		if(itemDeck.isEmpty()){
			itemDeck = itemDeckDiscarded;
			itemDeckDiscarded = new ArrayList<ItemCard>();
			Collections.shuffle(itemDeck);
			System.out.println("item rimischiato contiene "+itemDeck.size()+" su 13");

		}
		ItemCard drawn =  itemDeck.remove(0); 
		System.out.println("item pescata "+drawn);
		return drawn;
	}
	public static void discardItemCard(ItemCard card){
		itemDeckDiscarded.add(card);
	}
	
	public static int itemCounter(){
		return itemDeck.size();
	}
	public static int sectorCounter(){
		return sectorDeck.size();
	}


}
