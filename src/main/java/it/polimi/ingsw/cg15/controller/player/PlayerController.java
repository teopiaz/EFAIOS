package it.polimi.ingsw.cg15.controller.player;


import it.polimi.ingsw.cg15.model.GameState;
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


    /**
     * @return 
     * 
      */
    //TODO: testare se può essere mai chiamato (per design NON deve essere mai chiamato)
    public boolean moveIsPossible(Coordinate dest) {
        // TODO implement here
    	System.out.println("playerController");
    	return true;

    }

    public void movePlayer(Coordinate dest){
    	Cell destination = gameState.getField().getCell(dest);
    	gameState.getTurnState().getCurrentPlayer().setPosition(destination);

    }


	public boolean hasCard(ItemCard card) {
		Player cp = gameState.getTurnState().getCurrentPlayer();
		for (int i=0;i<cp.getCardListSize();i++) {
			if(cp.getCardById(i).equals(card)){
				return true;
			}
		}
		return false;
	}


	public void removeCard(ItemCard card) {
		Player cp = gameState.getTurnState().getCurrentPlayer();
		cp.removeCard(card);
		
	}
	
	public Coordinate getPlayerPosition(){
	    Player cp = gameState.getTurnState().getCurrentPlayer();
	    return cp.getPosition().getCoordinate();
	}


    public SectorCard drawSectorCard() {
        Player cp = gameState.getTurnState().getCurrentPlayer();
       return gameState.getDeckContainer().getSectorDeck().drawCard();
        
    }
   
    


}