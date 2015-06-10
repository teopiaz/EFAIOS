package it.polimi.ingsw.cg15.controller.cards;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.cards.HatchDeck;
import it.polimi.ingsw.cg15.model.cards.ItemDeck;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorDeck;

/**
 * @author LMR - MMP
 */
public class CardController {

    private static int sectorItem = 4;
    private static int sector = 6;
    private static int sectorSilence = 5;

    private static int itemAttack = 2;
    private static int itemTeleport = 2;
    private static int itemAdrenaline = 3;
    private static int itemSedatives = 3;
    private static int itemSpotlight = 2;
    private static int itemDefense = 1;
    private static int hatchGreen = 3;
    private static int hatchRed = 3;


    private DeckContainer deckContainer;

    /**
     * 
     */
    public CardController(GameState gs) {
        this.deckContainer = gs.getDeckContainer();
    }
    
    public void generateDecks(){
        generateSectorDeck();
        generateItemDeck();
        generateHatchDeck();
    }





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


    private void generateSectorDeck() {

        SectorDeck sectorDeck = deckContainer.getSectorDeck();


        for (int i = 0; i < sectorItem; i++) {
            sectorDeck.insertCard(SectorCard.SECTOR_RED_ITEM);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN_ITEM);
        }
        for (int i = 0; i < sector; i++) {
            sectorDeck.insertCard(SectorCard.SECTOR_RED);
            /////////TODO: DA LEVARE
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);

            ///FINO QUI
            
            
            
            sectorDeck.insertCard(SectorCard.SECTOR_GREEN);
        }
        for (int i = 0; i < sectorSilence; i++) {
            sectorDeck.insertCard(SectorCard.SECTOR_SILENCE);
        }


        sectorDeck.shuffleDeck();

    }


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
