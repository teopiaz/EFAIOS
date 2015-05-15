package it.polimi.ingsw.cg15.model.cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SectorDeck {

    private  List<SectorCard> sectorDeck = new ArrayList<SectorCard>();
    private  List<SectorCard> sectorDeckDiscarded = new ArrayList<SectorCard>();

    public static final int MAIN_DECK = 1;
    public static final int DISCARTED_DECK =2;


    public  List<SectorCard> getSectorDeck(){
        return sectorDeck;
    }
    
    
    /*
     *  Inserire cosa faccio.
     */
    public  List<SectorCard> getSectorDeck(int selector){
        if(selector==MAIN_DECK)
            return getSectorDeck();
        else if(selector==DISCARTED_DECK){
            return  sectorDeckDiscarded;
        }
        return null;
    }

    
    /*
     * Return the number of the cards.
     */
    public int getNumberOfCard(){
        return sectorDeck.size();
    }



    private void swapDeck(){
        if(sectorDeck.isEmpty()){
            sectorDeck = sectorDeckDiscarded;
            sectorDeckDiscarded = new ArrayList<SectorCard>();
            shuffleDeck();
        }
    }

    
    /*
     * Shuffle the deck.
     */
    public void shuffleDeck(){
        Collections.shuffle(sectorDeck);

    }

    
    /*
     * Ritorna una carta.
     */
    public SectorCard drawCard(){
        if(sectorDeck.isEmpty()){
            shuffleDeck();
        }
        SectorCard drawed = sectorDeck.remove(0);
        sectorDeckDiscarded.add(drawed);
        return drawed;
    }

    
    
    /*
     * Inserisce una carta nel mazzo delle carte.
     */
    public boolean insertCard(SectorCard card){
        return sectorDeck.add(card);

    }

}
