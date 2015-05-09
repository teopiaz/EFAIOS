package it.polimi.ingsw.cg15.controller.cards;


import it.polimi.ingsw.cg15.controller.cards.strategy.*;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.cards.ItemCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author LMR - MMP
 */
public class CardController  {


	private static int sectorItem=4;
	private static int sector=6;
	private static int sectorSilence=5;

	private static int itemAttack = 2;
	private static int itemTeleport = 2;
	private static int itemAdrenaline=3;
	private static int itemSedatives=3;
	private static int itemSpotlight = 2;
	private static int itemDefense = 1;
	private static int hatchGreen = 3;
	private static int hatchRed = 3;
	
	private GameState gs;
	private DeckContainer deck;
	
	private Map<Enum,CardStrategy> strategyList = new HashMap<Enum, CardStrategy>();
	

	
    /**
     * 
     */
    public CardController(GameState gs) {
    	this.gs = gs;
    	this.deck = gs.getDeckContainer();
    	generateSectorDeck();
    	generateItemDeck();
    }



    /**
     * 
     */
    public void setCardStrategy() {
        // TODO implement here
    }

    /**
     * 
     */
    public void drawSectorCard() {
        // TODO implement here
    }

    /**
     * 
     */
    public void generateDecks() {
        // TODO implement here
    }
    
    
    private void generateSectorDeck(){
    	
    	List<SectorCard> sectorDeck = deck.getSectorDeck();
    	
		for(int i=0;i<sectorItem;i++){
			sectorDeck.add(SectorCard.SECTOR_RED_ITEM);
			sectorDeck.add(SectorCard.SECTOR_GREEN_ITEM);
		}
		for(int i=0;i<sector;i++){
			sectorDeck.add(SectorCard.SECTOR_RED);
			sectorDeck.add(SectorCard.SECTOR_GREEN);
		}
		for(int i=0;i<sectorSilence;i++){
			sectorDeck.add(SectorCard.SECTOR_SILENCE);
		}

		Collections.shuffle(sectorDeck);
		
	}
    
	
	private void generateItemDeck() {
		
    	List<ItemCard> itemDeck = deck.getItemDeck();
		
		for(int i=0;i<itemAttack;i++){
			itemDeck.add(ItemCard.ITEM_ATTACK);
		}
		for(int i=0;i<itemTeleport;i++){
			itemDeck.add(ItemCard.ITEM_TELEPORT);
		}
		for(int i=0;i<itemAdrenaline;i++){
			itemDeck.add(ItemCard.ITEM_ADRENALINE);
		}
		for(int i=0;i<itemSedatives;i++){
			itemDeck.add(ItemCard.ITEM_SEDATIVES);
		}
		for(int i=0;i<itemSpotlight;i++){
			itemDeck.add(ItemCard.ITEM_SPOTLIGHTS);
		}
		for(int i=0;i<itemDefense;i++){
			itemDeck.add(ItemCard.ITEM_DEFENSE);
		}

		Collections.shuffle(itemDeck);

	}
	
	 private void generateHatchDeck(){
	    	
	    	List<HatchCard>hatchDeck = deck.getHatchDeck();
	    	
			for(int i=0;i<hatchGreen;i++){
				hatchDeck.add(HatchCard.HATCH_GREEN);
			}
			for(int i=0;i<hatchRed;i++){
				hatchDeck.add(HatchCard.HATCH_RED);
			}

			Collections.shuffle(hatchDeck);
			
		}
	
	


	public  SectorCard getSectorCard(){
    	List<SectorCard> sectorDeck = deck.getSectorDeck();
    	List<SectorCard> sectorDeckDiscarded = deck.getSectorDeck();

    	
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
	
	/*
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
	
*/
	
	
	private void buildStrategyMap(){
		strategyList.put(SectorCard.SECTOR_GREEN,new SectorGreen(false) );
		strategyList.put(SectorCard.SECTOR_GREEN_ITEM,new SectorGreen(true) );
		strategyList.put(SectorCard.SECTOR_RED,new SectorRed(false) );
		strategyList.put(SectorCard.SECTOR_RED_ITEM,new SectorRed(true) );
		strategyList.put(SectorCard.SECTOR_SILENCE,new SectorSilence() );
//TODO: completare la lista con le strategie degli item
		
		
	}

}