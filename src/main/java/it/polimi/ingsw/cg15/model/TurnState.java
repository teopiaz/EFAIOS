package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.player.Player;

public class TurnState {

    private Boolean hasMoved = false;
    private Boolean hasAttacked = false;
    private Boolean usedItemCard = false;
    private Player currentPlayer = null;

    public void setHasMoved(Boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    public void setHasAttacked(Boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    public void setUsedItemCard(Boolean usedItemCard) {
        this.usedItemCard = usedItemCard;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

}
