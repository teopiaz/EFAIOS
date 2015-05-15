package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.controller.cards.strategy.CardStrategy;
import it.polimi.ingsw.cg15.controller.cards.strategy.SectorGreen;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;
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
    	Field map = new Field(9,9);
/*
    	List<SectorCard> sec = DeckContainer.getSectorDeck();
    	List<ItemCard> item = DeckContainer.getItemDeck();

    	Player umano = new Human(new Cell(1,1,map,CellColor.WHITE));
    	
    	umano.drawSectorCard();
    	
    	*/
    	CardStrategy prova = new SectorGreen(true);
    	
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

    	map.printMap();
    		
    	
    	Cell CELLA1 = new Cell(new Coordinate(1, 1), map, Sector.HUMAN);
        Cell CELLA2 = new Cell(new Coordinate(2, 2), map, Sector.HUMAN);
        
        System.out.println( CELLA1.getCoordinate().getDistance(CELLA2.getCoordinate())  );

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
