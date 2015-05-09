package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.controller.cards.strategy.SectorCardStrategy;
import it.polimi.ingsw.cg15.controller.cards.strategy.SectorGreen;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */

public class App 
{
	int test;
	private static final Logger LOGGER = Logger.getIstance();
	public App(){
	test=10;
	
	
	}
	
	
    public static void main( String[] args )
    {
    	Field map = new Field(3,5);
/*
    	List<SectorCard> sec = DeckContainer.getSectorDeck();
    	List<ItemCard> item = DeckContainer.getItemDeck();

    	Player umano = new Human(new Cell(1,1,map,CellColor.WHITE));
    	
    	umano.drawSectorCard();
    	
    	*/
    	SectorCardStrategy prova = new SectorGreen(true);
    	
    	prova.action();
       /*	
    	for (SectorCard sectorCard : sec) {
		    System.out.println(sectorCard.hasItemIcon() +" "+ sectorCard.getClass().toString());
			
		}
       	
    	for (ItemCard itemCard : item) {
		    System.out.println(itemCard.getClass().toString());

		}
    	
    	while(DeckContainer.itemCounter()>1){
    	DeckContainer.getItemCard();
    	}
    	ItemCard card = DeckContainer.getItemCard();
    	System.out.println(card + " ultima");
    	DeckContainer.discardItemCard(card);
    	card = DeckContainer.getItemCard();
    	System.out.println(card + " rimessa e ripescata");
*/
    	
    	//int d = map.getCell(20, 2).distance(map.getCell(3, 4));
    	//System.out.println(d);

    //	map.printMap();
    	
    	 List<ItemCard> itemDeck = new ArrayList<ItemCard>();
    	 List<ItemCard> itemDeck2 = new ArrayList<ItemCard>();

    	 itemDeck.add(ItemCard.ITEM_ATTACK);
    	 itemDeck.add(ItemCard.ITEM_DEFENSE);
    	 itemDeck.add(ItemCard.ITEM_SPOTLIGHTS);
    	 itemDeck.add(ItemCard.ITEM_SPOTLIGHTS);
    	 itemDeck.add(ItemCard.ITEM_SPOTLIGHTS);
    	 itemDeck.add(ItemCard.ITEM_ATTACK);
    	 
    	 for (ItemCard itemCard : itemDeck) {
			System.out.println(itemCard.toString());
		}
    	 ItemCard drawn =  itemDeck.remove(0); 

 		System.out.println("pescato "+drawn.toString());
 		itemDeck2.add(drawn);
 		
    	LOGGER.debug("Hello World");
    	 for (ItemCard itemCard : itemDeck) {
 			System.out.println(itemCard.toString());
 		}
    }
}
