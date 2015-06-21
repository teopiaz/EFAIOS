package it.polimi.ingsw.cg15.model.cards;

/**
 * @author MMP - LMR
 * This class contains the decks of the game and has methods to handle them. 
 * In particular types of decks are three: 
 * SectorCard: 4 red and covered with 6 normal, 4 green with item 6 and normal. 5 cards silence, without any objects. the back is a hexagon. 
 * ItemCard: 2 attack, teleportation 2, 2 adrenaline, 3 sedative, 2 lights, 1 defense, the back is a square. 
 * HatchCard: 3 broken hatch (red) 3 working hatch (green)
 */
public class DeckContainer {

    /**
     * This is the deck of cards sector.
     */
    SectorDeck sectorDeck;
    
    /**
     * This is the deck of cards object.
     */
    ItemDeck itemDeck;
    
    /**
     * This is the card deck hatch.
     */
    HatchDeck hatchDeck;

    /**
     * Class constructor that instantiates the three types of clusters present.
     */
    public DeckContainer() {
        sectorDeck = new SectorDeck();
        itemDeck = new ItemDeck();
        hatchDeck = new HatchDeck();
    }

    /**
     * @return the deck of sector cards.
     */
    public SectorDeck getSectorDeck() {
        return sectorDeck;
    }
    
    /**
     * @return the deck of the item cards.
     */
    public ItemDeck getItemDeck() {
        return itemDeck;
    }

    /**
     * @return the deck of the hatch cards.
     */
    public HatchDeck getHatchDeck() {
        return hatchDeck;
    }

}
