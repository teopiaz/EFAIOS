package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class TurnState {

    private boolean hasMoved = false;
    private boolean hasAttacked = false;
    private boolean usedItemCard = false;
    private boolean isUnderAdrenaline = false;
    private boolean isUnderSedatives = false;
    private boolean lockedOnDiscardOrUseItem = false;
    private List<ActionEnum> availableActionsList = new ArrayList<ActionEnum>();
    private List<ActionEnum> lockedActionsList = new ArrayList<ActionEnum>();
    private boolean actionlistsSwapped = false;
    
    public TurnState(){
    }

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
    public boolean isUnderSedatives() {
        return isUnderSedatives;
    }
    public void setUnderSedatives(){
        this.isUnderSedatives=true;
    }
    public void setHasMoved() {
        if(availableActionsList.contains(ActionEnum.MOVE)){
            availableActionsList.remove(ActionEnum.MOVE);
            hasMoved = true;

        }
    }

    public void resetHasMoved(){
        this.hasMoved=false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }
    public void setUsedItemCard() {
        this.usedItemCard = true;

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


    public boolean isActionInActionList(String action){
        for (ActionEnum actionEnum : availableActionsList) {
            if(actionEnum.toString().equals(action)){
                return true;
            }
        }
        return false;
    }

    public List<ActionEnum> getActionList() {
        return availableActionsList;
    }

    public void swapActionsList() {
         List<ActionEnum> temp = availableActionsList;
         availableActionsList = lockedActionsList;
         lockedActionsList=temp;
         actionlistsSwapped = !actionlistsSwapped;
    }
    public boolean areListSwapped(){
        return actionlistsSwapped;
    }
    public void addAskSectorAction(){
        availableActionsList.add(ActionEnum.ASKSECTOR);
    }
    public void addUseOrDiscard(){
        availableActionsList.add(ActionEnum.USEITEM);
        availableActionsList.add(ActionEnum.DISCARD);

    }
}
