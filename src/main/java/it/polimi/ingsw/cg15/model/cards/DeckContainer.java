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

SectorDeck sectorDeck;
ItemDeck itemDeck;
HatchDeck hatcDeck;


public DeckContainer(){
	SectorDeck sectorDeck = new SectorDeck();
	ItemDeck itemDeck = new ItemDeck();
	HatchDeck hatchDeck = new HatchDeck();
}


/**
 * @return the sectorDeck
 */
public SectorDeck getSectorDeck() {
	return sectorDeck;
}


/**
 * @return the itemDeck
 */
public ItemDeck getItemDeck() {
	return itemDeck;
}


/**
 * @return the hatcDeck
 */
public HatchDeck getHatchDeck() {
	return hatcDeck;
}


}
