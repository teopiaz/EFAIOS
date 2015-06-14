package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.TurnState;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;

import java.util.List;

/**
 * @author LMR - MMP
 */
public class PlayerController {

    private GameState gameState;
    public static int FIRST_PLAYER = 1;


    /**
     * 
     */
    public PlayerController(GameState state) {
        this.gameState = state;
    }

    public SectorCard drawSectorCard() {
        return gameState.getDeckContainer().getSectorDeck().drawCard();
    }

    public HatchCard drawHatchCard() {
        return gameState.getDeckContainer().getHatchDeck().drawCard();
    }

    public Coordinate getPlayerPosition() {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        return cp.getPosition().getCoordinate();
    }

    public boolean canUseCard(){
        return false;
    }

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
     * @return
     * 
     */
    // TODO: testare se può essere mai chiamato (per design NON deve essere mai
    // chiamato)
    public boolean moveIsPossible(Coordinate dest) {
        return true;

    }

    public void movePlayer(Coordinate dest) {
        TurnState ts = gameState.getTurnState();
        Player cp = ts.getCurrentPlayer();
        cp.getPosition().removePlayer(cp);
        Cell destination = gameState.getField().getCell(dest);
        cp.setPosition(destination);
        destination.addPlayer(cp);
        gameState.getTurnState().setHasMoved();

    }

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

    public void removeCard(ItemCard card) {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        cp.removeCard(card);

    }


    public void setOnAdrenaline() {
        gameState.getTurnState().setUnderAdrenaline();

    }

    public boolean itemCardUsed() {
        return gameState.getTurnState().usedItemCard();
    }

    public boolean hasAttacked() {
        return gameState.getTurnState().hasAttacked();

    }

    public void setHasAttacked() {
        gameState.getTurnState().setHasAttacked();

    }

    public boolean canDrawItemCard() {
        return gameState.getTurnState().getCurrentPlayer().getCardListSize() < Player.MAX_ITEMCARD;

    }

    public ItemCard drawItemCard() {

        ItemCard card = gameState.getDeckContainer().getItemDeck().drawCard();
        TurnState ts = gameState.getTurnState();
        Player cp = ts.getCurrentPlayer();
        cp.addCard(card);
        return card;

    }

    public Player getPlayerById(int id){
        List<Player> playerList = gameState.getPlayerList();
        for (Player player : playerList) {
            if(player.getPlayerNumber()==id)
                return player;
        }
        return null;
    }

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

    public boolean isUnderSedatives() {
        return gameState.getTurnState().isUnderSedatives();
    }
    public void setUnderSedatives() {
        gameState.getTurnState().setUnderAdrenaline();
    }

    public boolean evolve() {

        return false;
    }

    public boolean escape() {
        return false;

    }

    public void setItemUsed() {
        gameState.getTurnState().setUsedItemCard();

    }

}
