package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.player.Player;

/**
 * @author MMP - LMR
 * This class is the action of fishing a card type field. 
 * It occurs when the player is in a cell area of the game called "dangerous area". 
 * The drawn card can be divided into 5 types.
 */
public class DrawSectorCard extends Action {


    /**
     * @param gc the game controller
     */
    public DrawSectorCard(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean execute() {

        GameController gc = getGameController();
        FieldController fc = gc.getFieldController();
        Player currentPlayer = getGameController().getCurrentPlayer();
        PlayerController pc= gc.getPlayerInstance(currentPlayer);
        if(fc.isDangerousSector(pc.getPlayerPosition())){
            SectorCard card = pc.drawSectorCard();
            Action noise=null;
            if(card==SectorCard.SECTOR_GREEN){
                noise = new NoiseGreen(gc,false);
            }
            if(card==SectorCard.SECTOR_GREEN_ITEM){
                noise = new NoiseGreen(gc,true);
                Action draw = new DrawItemCard(gc);
                draw.execute();
            }
            if(card==SectorCard.SECTOR_RED){
                noise = new NoiseRed(gc,false);
            }
            if(card==SectorCard.SECTOR_RED_ITEM){
                noise = new NoiseRed(gc,true);
                Action draw = new DrawItemCard(gc);
                draw.execute();
            }
            if(card==SectorCard.SECTOR_SILENCE){
                return true;
                }
            if(noise.execute()){
                return true;
            }
        }

        return false;
    }


}
