package it.polimi.ingsw.cg15;


import java.util.*;

/**
 * 
 */
public abstract class Player {


    /**
     * 
     */
    protected Cell position;

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
    public abstract void move(Cell dest);

    /**
     * @return
     */
    private void pesca() {

    }

}