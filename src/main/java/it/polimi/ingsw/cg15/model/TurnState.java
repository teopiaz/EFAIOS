package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.player.Player;

public class TurnState {

    private boolean hasMoved = false;
    private boolean hasAttacked = false;
    private boolean usedItemCard = false;
    private boolean isUnderAdrenaline = false;
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

    public boolean hasMoved() {
        return hasMoved;
    }
    public void setUsedItemCard(boolean usedItemCard) {
        this.usedItemCard = usedItemCard;

    }
    public boolean UsedItemCard() {
        return usedItemCard;
    }
    public boolean HasAttacked() {
        return hasAttacked;
    }
    public void setHasAttacked() {
        hasAttacked = true;
    }

}
