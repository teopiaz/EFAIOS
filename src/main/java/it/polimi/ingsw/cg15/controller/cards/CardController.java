package it.polimi.ingsw.cg15.controller.cards;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.cards.HatchDeck;
import it.polimi.ingsw.cg15.model.cards.ItemDeck;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorDeck;

//TODO se field controller sta dentro controller anche card controller mettiamolo dentro controller. Penso abbia più sens che avere un package con dentro una sola classe.

/**
 * @author LMR - MMP
 * The class that have the method to control the various type of cards.
 */
public class CardController {

    /**
     * The number of the sector Item Card.
     */
    private static int sectorItem = 4;
    
    /**
     * The number of the Sector Card.
     */
    private static int sector = 6;

    /**
     * The number of Silence Sector.
     */
    private static int sectorSilence = 5;

    /**
     * The number of the Attack Item Card.
     */
    private static int itemAttack = 2;
    
    /**
     * The number of the Teleport Item Card.
     */
    private static int itemTeleport = 2;
    
    /**
     * The number of the Adrenaline Item Card.
     */
    private static int itemAdrenaline = 3;
    
    /**
     * The number of the Sedatives Item Card.
     */
    private static int itemSedatives = 3;
    
    /**
     * The number of the Spotlight Item Card.
     */
    private static int itemSpotlight = 2;
    
    /**
     * The number of the Defense Item Card.
     */
    private static int itemDefense = 100;
    
    /**
     * The number of the Green Hatch Card.
     */
    private static int hatchGreen = 3;
    
    /**
     * The number of the Red Hatch Card.
     */
    private static int hatchRed = 3;

    /**
     * The deck container.
     */
    private DeckContainer deckContainer;

    /**
     * The constructor.
     */
    public CardController(GameState gs) {
        this.deckContainer = gs.getDeckContainer();
    }
    
    /**
     * A method that generates the various decks.
     */
    public void generateDecks(){
        generateSectorDeck();
        generateItemDeck();
        generateHatchDeck();
    }

    /**
     * Method that generate the Item Deck
     */
    private void generateItemDeck() {
        ItemDeck itemDeck = deckContainer.getItemDeck();
        for(int i=0;i<itemAttack;i++){
            itemDeck.insertCard(ItemCard.ITEM_ATTACK);
        }
        for(int i=0;i<itemTeleport;i++){
            itemDeck.insertCard(ItemCard.ITEM_TELEPORT);
        }
        for(int i=0;i<itemAdrenaline;i++){
            itemDeck.insertCard(ItemCard.ITEM_ADRENALINE);
        }
        for(int i=0;i<itemSedatives;i++){
            itemDeck.insertCard(ItemCard.ITEM_SEDATIVES);
        }
        for(int i=0;i<itemSpotlight;i++){
            itemDeck.insertCard(ItemCard.ITEM_SPOTLIGHT);
        }
        for(int i=0;i<itemDefense;i++){
            itemDeck.insertCard(ItemCard.ITEM_DEFENSE);
        }
        itemDeck.shuffleDeck();
    }

    /**
     * Method that generate the Sector Deck.
     */
    private void generateSectorDeck() {
        SectorDeck sectorDeck = deckContainer.getSectorDeck();
        for (int i = 0; i < sectorItem; i++) {
            sectorDeck.insertCard(SectorCard.SECTOR_RED_ITEM);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        }
        for (int i = 0; i < sector; i++) {
            sectorDeck.insertCard(SectorCard.SECTOR_RED);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
        }
        for (int i = 0; i < sectorSilence; i++) {
            sectorDeck.insertCard(SectorCard.SECTOR_SILENCE);
        }
        sectorDeck.shuffleDeck();
    }

    /**
     * Method that generate the Hatch Deck.
     */
    private void generateHatchDeck() {
        HatchDeck hatchDeck = deckContainer.getHatchDeck();
        for (int i = 0; i < hatchGreen; i++) {
            hatchDeck.insertCard(HatchCard.HATCH_GREEN);
        }
        for (int i = 0; i < hatchRed; i++) {
            hatchDeck.insertCard(HatchCard.HATCH_RED);
        }
        hatchDeck.shuffleDeck();
    }
    
}
