package it.polimi.ingsw.cg15.controller.action;

import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.model.cards.SectorCard;

public class DrawSectorCard extends Action<Boolean> {

    
    public DrawSectorCard(GameController gc) {
        super(gc);
        // TODO Auto-generated constructor stub
    }


    @Override
    public Boolean execute() {

        GameController gc = getGameController();
        FieldController fc = gc.getFieldController();
        if(fc.isDangerousSector(gc.getCurrentPlayerInstance().getPlayerPosition())){
            SectorCard card = gc.getCurrentPlayerInstance().drawSectorCard();
            Action noise=null;
            if(card==SectorCard.SECTOR_GREEN){
                noise = new NoiseGreen(gc);
            }
            if(card==SectorCard.SECTOR_GREEN_ITEM){
                noise = new NoiseGreen(gc);
                Action draw = new DrawItemCard(gc);
                draw.execute();
            }
            if(card==SectorCard.SECTOR_RED){
                noise = new NoiseRed(gc);
            }
            if(card==SectorCard.SECTOR_RED_ITEM){
                noise = new NoiseRed(gc);
                Action draw = new DrawItemCard(gc);
                draw.execute();
            }
            noise.execute();
        }
        
        return null;
    }
    

}
