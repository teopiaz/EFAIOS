package it.polimi.ingsw.cg15;


import java.util.*;

/**
 * 
 */
public abstract class Player {


    /**
     * 
     */
    private Cell position;

    /**
     * 
     */
    public Card[] cards;


    /**
     * 
     */
    public Player() {
    }
    
    /**
     * @return
     */
    public abstract void muovi();

    /**
     * @return
     */
    private void pesca() {

    }

}