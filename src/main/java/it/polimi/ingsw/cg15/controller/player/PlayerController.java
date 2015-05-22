package it.polimi.ingsw.cg15.controller.player;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.TurnState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;

/**
 * @author LMR - MMP
 */
public class PlayerController {

    private GameState gameState;

    /**
     * 
     */
    public PlayerController(GameState state) {
        this.gameState = state;
    }

    public SectorCard drawSectorCard() {
        return gameState.getDeckContainer().getSectorDeck().drawCard();
    }

    public Coordinate getPlayerPosition() {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        return cp.getPosition().getCoordinate();
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
        // TODO implement here
        System.out.println("playerController");
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
        if (player.killPlayer()) {
            return true;
        }
        return false;
    }

    public void removeCard(ItemCard card) {
        Player cp = gameState.getTurnState().getCurrentPlayer();
        cp.removeCard(card);

    }

    // TODO: testare se può essere mai chiamato (per design NON deve essere mai
    // chiamato)
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
        return gameState.getDeckContainer().getItemDeck().drawCard();
    }

}
