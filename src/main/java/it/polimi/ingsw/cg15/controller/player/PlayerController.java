package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.TurnState;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.List;

/**
 * @author MMP - LMR
 * The generic player controller.
 */
public class PlayerController {

    /**
     *  The state of the game.
     */
    private GameState gameState;

    /**
     * The first player of the turn.
     */
    public static final int FIRST_PLAYER = 1;

    /**
     * The constructor.
     * @param state The current game state.
     */
    public PlayerController(GameState state) {
        this.gameState = state;
    }

    /**
     * Check if I can use a item card.
     * @return the ability to use a card.
     */
    public SectorCard drawSectorCard() {
        return gameState.getDeckContainer().getSectorDeck().drawCard();
    }

    /**
     * @return the hatch card that i draw from the hatch deck.
     */
    public HatchCard drawHatchCard() {
        return gameState.getDeckContainer().getHatchDeck().drawCard();
    }

    /**
     * @return The current player position.
     */
    public Coordinate getPlayerPosition() {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        return cp.getPosition().getCoordinate();
    }

    /**
     * Check if I can use a item card.
     * @see it.polimi.ingsw.cg15.controller.player.PlayerController#canUseCard()
     */
    public boolean canUseCard(){
        return false;
    }

    /**
     * @param card The card to verify.
     * @return a boolean that says if I have or not the item card.
     */
    public boolean hasCard(ItemCard card) {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        for (int i = 0; i < cp.getCardListSize(); i++) {
            if (cp.getCardById(i).equals(card)) {
                return true;
            }
        }
        return false;
    }


    /**
     * @param dest The destination in which i want to check if the move is possible.
     * @return true ???
     */
    public boolean moveIsPossible(Coordinate dest) {
        return true;
    }

    /**
     * Move a player into a destination.
     * @param dest In which move the current player.
     */
    public void movePlayer(Coordinate dest) {
        TurnState ts = gameState.getTurnState();
        Player cp = ts.getCurrentPlayer();
        cp.getPosition().removePlayer(cp);
        Cell destination = gameState.getField().getCell(dest);
        cp.setPosition(destination);
        destination.addPlayer(cp);
        gameState.getTurnState().setHasMoved();
    }

    /**
     * Method to kill a player.
     * @param player The player to kill.
     * @return a boolean that says if I killed or not.
     */
    public boolean killPlayer(Player player) {
        if(!player.isAlive()){
            return false;
        }
        if (player.killPlayer()) {
            player.getPosition().removePlayer(player);
            for (ItemCard card : player.getCardList()) {
                removeCard(card);
                gameState.getDeckContainer().getItemDeck().addToDiscardedDeck(card);
            }
            return true;
        }
        return false;
    }

    /**
     * @param card The card to remove.
     */
    public void removeCard(ItemCard card) {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        cp.removeCard(card);
    }

    /**
     * Set the player under adrenaline.
     */
    public void setOnAdrenaline() {
        gameState.getTurnState().setUnderAdrenaline();
    }

    /**
     * @return if I use an item or not in the current turn state.
     */
    public boolean itemCardUsed() {
        return gameState.getTurnState().usedItemCard();
    }

    /**
     * @return if I has attack or not in the current turn state.
     */
    public boolean hasAttacked() {
        return gameState.getTurnState().hasAttacked();
    }

    /**
     * Set has attacked to yes.
     */
    public void setHasAttacked() {
        gameState.getTurnState().setHasAttacked();
    }

    /**
     * @return the possibility to draw another item card.
     */
    public boolean canDrawItemCard() {
        return gameState.getTurnState().getCurrentPlayer().getCardListSize() < Player.MAX_ITEMCARD;
    }

    /**
     * @return the item card that i draw from the item deck.
     */
    public ItemCard drawItemCard() {
        ItemCard card = gameState.getDeckContainer().getItemDeck().drawCard();
        TurnState ts = gameState.getTurnState();
        Player cp = ts.getCurrentPlayer();
        cp.addCard(card);
        return card;
    }

    /**
     * @param id The id of a player.
     * @return the player itself from an id.
     */
    public Player getPlayerById(int id){
        List<Player> playerList = gameState.getPlayerList();
        for (Player player : playerList) {
            if(player.getPlayerNumber()==id)
                return player;
        }
        return null;
    }

    /**
     * @return The next player in the turn state.
     */
    public Player getNextPlayer(){
        List<Player> playerList = gameState.getPlayerList();
        int numPlayer = playerList.size();
        int currentPlayerIndex = gameState.getTurnState().getCurrentPlayer().getPlayerNumber();
        if(currentPlayerIndex+1>numPlayer){
            return getPlayerById(FIRST_PLAYER);
        }
        else{
            return getPlayerById(currentPlayerIndex+1);
        }
    }

    /**
     * @return if a player is under sedatives.
     */
    public boolean isUnderSedatives() {
        return gameState.getTurnState().isUnderSedatives();
    }

    /**
     * @return if a player is under sedatives.
     */
    public void setUnderSedatives() {
        gameState.getTurnState().setUnderSedatives();
    }

    /**
     * @return the ability of the player to evolve.
     */
    public boolean evolve() {
        return false;
    }

    /**
     * @return the ability of the player to escape.
     */
    public boolean escape() {
        return false;
    }

    /**
     * Set that in the current turn state I used an item card.
     */
    public void setItemUsed() {
        gameState.getTurnState().setUsedItemCard();
    }

}
