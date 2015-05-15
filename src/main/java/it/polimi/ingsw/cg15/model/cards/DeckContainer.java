package it.polimi.ingsw.cg15.model.cards;


/*
 * Questa classe contiene i mazzi di gioco e possiede i metodi per gestirli. In particolare i tipi di mazzi sono tre:
 * SectorCard: 4 rosse con oggetto e 6 normali, 4 verdi con oggetto e 6 normali. 5 carte silenzio, tutte senza oggetti. il retro è un esagono.
 * ItemCard: 2 attacco, 2 teletrasporto, 2 adrenalina, 3 sedativi, 2 luci, 1 difesa, il retro è un quadrato.
 * HatchCard: TODO: aggiungere quante carte hatch ci sono
 */


public class DeckContainer {


    SectorDeck sectorDeck;
    ItemDeck itemDeck;
    HatchDeck hatcDeck;


    /*
     * Costruttore della classe che istanzia i tre tipi di mazzi pesenti. 
     */
    public DeckContainer(){
        SectorDeck sectorDeck = new SectorDeck();
        ItemDeck itemDeck = new ItemDeck();
        HatchDeck hatchDeck = new HatchDeck();
    }


    /*
     * Questo metodo ritorna il mazzo delle carte settore.
     */
    public SectorDeck getSectorDeck() {
        return sectorDeck;
    }


    /*
     * Questo metodo ritorna il mazzo delle carte oggetto.
     */
    public ItemDeck getItemDeck() {
        return itemDeck;
    }


    /*
     * Questo metodo ritorna il mazzo delle carte hatch.
     */
    public HatchDeck getHatchDeck() {
        return hatcDeck;
    }


}
