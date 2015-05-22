package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TurnState {

    private boolean hasMoved = false;
    private boolean hasAttacked = false;
    private boolean usedItemCard = false;
    private boolean isUnderAdrenaline = false;
    private boolean lockedOnDiscardOrUseItem = false;
    private List<String> avaibleActionsList = new ArrayList<String>();
    private List<String> lockedActionsList = new ArrayList<String>();

    
    public boolean isLockedOnDiscardOrUseItem() {
        return lockedOnDiscardOrUseItem;
    }
    public void lockOnDiscardOrUseItem() {
        this.lockedOnDiscardOrUseItem = true;
    }
    private Player currentPlayer = null;


    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public boolean isUnderAdrenaline() {
        return isUnderAdrenaline;
    }
    public void setUnderAdrenaline(){
        this.isUnderAdrenaline=true;
    }
    public void setHasMoved() {
        this.hasMoved = true;
    }
    
    public void resetHasMoved(){
        this.hasMoved=false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
    public void setUsedItemCard(boolean usedItemCard) {
        this.usedItemCard = usedItemCard;

    }
    public boolean usedItemCard() {
        return usedItemCard;
    }
    public boolean hasAttacked() {
        return hasAttacked;
    }
    public void setHasAttacked() {
        hasAttacked = true;
    }

}
