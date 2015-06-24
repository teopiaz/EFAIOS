package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.utils.TimerTurn;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MMP - LMR
 * The class that handle the state of the turn.
 */
public class TurnState {

    /**
     * A variable that says in the current round if the player moved.
     */
    private boolean hasMoved = false;

    /**
     * A variable that says in the current round if the player has attacked.
     */
    private boolean hasAttacked = false;

    /**
     * A variable that says in the current round if the player has used an item card.
     */
    private boolean usedItemCard = false;

    /**
     * A variable that says in the current round if the player is under adrenaline.
     */
    private boolean isUnderAdrenaline = false;

    /**
     * A variable that says in the current round if the player is under sedatives.
     */
    private boolean isUnderSedatives = false;

    /**
     * A variable that says in the current round if the player is locked in that state must discard or use a item card.
     */
    private boolean lockedOnDiscardOrUseItem = false;

    /**
     * The current player.
     */
    private Player currentPlayer = null;

    /**
     * The list of available action.
     */
    private List<ActionEnum> availableActionsList = new ArrayList<ActionEnum>();

    /**
     * The list of locked action.
     */
    private List<ActionEnum> lockedActionsList = new ArrayList<ActionEnum>();

    /**
     * Boolean that says if the cards have been swapped in the current turn.
     */
    private boolean actionListsSwapped = false;


    private TimerTurn timerTurn;

    /**
     * The constructor.
     */
    public TurnState(){
    }

    /**
     * @return if a player is locked in the state in which he have to discard or use the forth item card.
     */
    public boolean isLockedOnDiscardOrUseItem() {
        return lockedOnDiscardOrUseItem;
    }

    /**
     * The method that lock the used in the state in which he have to discard or use the forth item card.
     */
    public void lockOnDiscardOrUseItem() {
        this.lockedOnDiscardOrUseItem = true;
    }



    /**
     * @return the current player.
     */
    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Set a player as a current player.
     * @param currentPlayer the current player.
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * @return if the player is under adrenaline.
     */
    public boolean isUnderAdrenaline() {
        return isUnderAdrenaline;
    }

    /**
     * Set the player under adrenaline.
     */
    public void setUnderAdrenaline(){
        this.isUnderAdrenaline=true;
    }

    /**
     * @return if the player is under sedatives.
     */
    public boolean isUnderSedatives() {
        return isUnderSedatives;
    }

    /**
     * Set the player as under sedatives.
     */
    public void setUnderSedatives(){
        this.isUnderSedatives=true;
    }

    /**
     * The player has moved, it removes the action "move" from the set of available.
     */
    public void setHasMoved() {
        if(availableActionsList.contains(ActionEnum.MOVE)){
            availableActionsList.remove(ActionEnum.MOVE);
            hasMoved = true;
        }
    }

    /**
     * Reset the has moved variable.
     */
    public void resetHasMoved(){
        this.hasMoved=false;
    }

    /**
     * @return if the player has moved or not.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Set that the player as already use an item card.
     */
    public void setUsedItemCard() {
        this.usedItemCard = true;
    }

    /**
     * @return if the player as already use an item card.
     */
    public boolean usedItemCard() {
        return usedItemCard;
    }

    /**
     * @return if the player has attack in the current turn.
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     * Set that the player have attack.
     */
    public void setHasAttacked() {
        hasAttacked = true;
    }

    /**
     * @param action The action to check.
     * @return if a particular action is in the list of available action.
     */
    public boolean isActionInActionList(String action){
        for (ActionEnum actionEnum : availableActionsList) {
            if(actionEnum.toString().equals(action)){
                return true;
            }
        }
        return false;
    }

    /**
     * @return the available action list.
     */
    public List<ActionEnum> getActionList() {
        return availableActionsList;
    }

    /**
     * Remove the available list in order to handle the lock in use or discard the item card.
     */
    public void swapActionsList() {
        List<ActionEnum> temp = availableActionsList;
        availableActionsList = lockedActionsList;
        lockedActionsList=temp;
        actionListsSwapped = !actionListsSwapped;
    }

    /**
     * @return if the list has already has been swapped.
     */
    public boolean areListSwapped(){
        return actionListsSwapped;
    }

    /**
     * Add the ask sector action to the available action.
     */
    public void addAskSectorAction(){
        availableActionsList.add(ActionEnum.ASKSECTOR);
    }

    /**
     * Add the action to use an item card or to discard one of them.
     */
    public void addUseOrDiscard(){
        availableActionsList.add(ActionEnum.USEITEM);
        availableActionsList.add(ActionEnum.DISCARD);
    }

    /**
     * Set the timer of the turn
     */
    public void setTurnTimer(TimerTurn timerTurnTask) {
        this.timerTurn=timerTurnTask;        

    }
    /**
     * Get the timer of the turn
     * @return timerTurn
     */
    public TimerTurn getTimer(){

        return this.timerTurn;
    }
}
