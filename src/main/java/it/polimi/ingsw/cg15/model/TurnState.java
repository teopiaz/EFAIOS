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
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }
    public boolean isUnderAdrenaline() {
        return isUnderAdrenaline;
    }
    public void setUnderAdrenaline(){
        this.isUnderAdrenaline=true;
    }
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
    public void setUsedItemCard(boolean usedItemCard) {
        this.usedItemCard = usedItemCard;

    }

}
