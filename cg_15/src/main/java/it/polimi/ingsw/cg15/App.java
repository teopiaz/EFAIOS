package it.polimi.ingsw.cg15;

import java.awt.MultipleGradientPaint.ColorSpaceType;
import java.util.List;

import it.polimi.ingsw.cg15.cards.DeckContainer;
import it.polimi.ingsw.cg15.cards.ItemCard;
import it.polimi.ingsw.cg15.cards.SectorCard;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.CellColor;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Human;
import it.polimi.ingsw.cg15.model.player.Player;

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

    	List<SectorCard> sec = DeckContainer.getSectorDeck();
    	List<ItemCard> item = DeckContainer.getItemDeck();
    	
    	Player umano = new Human(new Cell(1,1,map,CellColor.WHITE));
    	
    	umano.drawSectorCard();
    	
    	
    	
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
    	
    	LOGGER.debug("Hello World");
    	
    }
}
