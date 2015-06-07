package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.Map;

/**
 * @author MMP - LMR
 * This class is the action of fishing a card type field. 
 * It occurs when the player is in a cell area of the game called "dangerous area". 
 * The drawn card can be divided into 5 types.
 */
public class DrawSectorCard extends Action {

    Event e;

    /**
     * @param gc the game controller
     * @param e 
     */
    public DrawSectorCard(GameController gc, Event request) {
        super(gc);
        this.e = request;
        // TODO Auto-generated constructor stub
    }


    @Override
    public Event execute() {

        GameController gc = getGameController();
        FieldController fc = gc.getFieldController();
        Player currentPlayer = getGameController().getCurrentPlayer();
        PlayerController pc= gc.getPlayerInstance(currentPlayer);

        Map<String,String> retValues = e.getRetValues();

        if(fc.isDangerousSector(pc.getPlayerPosition())){

            retValues.put("sectortype", "dangerous");           
            e=new Event(e, retValues);

            SectorCard card = pc.drawSectorCard();
            Action noise=null;
            Event afterItemDraw = new Event(e, retValues);


            //NON PESCO L'ITEM CARD
            if(card==SectorCard.SECTOR_GREEN){

                noise = new NoiseGreen(gc,e);
                retValues.put("item", "false");

                retValues.put("sectorcard", "sectorgreen");
                e = new Event(e, retValues);

            }

            if(card==SectorCard.SECTOR_RED){
                noise = new NoiseRed(gc,e);
                retValues.put("item", "false");

                retValues.put("sectorcard", "sectorred");
                e = new Event(e, retValues);

            }


            //PESCO L'ITEM CARD
            if(card==SectorCard.SECTOR_GREEN_ITEM){

                retValues.put("sectorcard", "sectorgreen");
                retValues.put("item", "true");

                Event beforeDrawEvent = new Event(e, retValues);

                Action draw = new DrawItemCard(gc,beforeDrawEvent);
                afterItemDraw =  draw.execute();
                noise = new NoiseGreen(gc,afterItemDraw);
                retValues = afterItemDraw.getRetValues();
                retValues.put("return", "true");



            }

            if(card==SectorCard.SECTOR_RED_ITEM){
                retValues.put("sectorcard", "sectorred");
                retValues.put("item", "true");
                Event beforeDrawEvent = new Event(e, retValues);
                Action draw = new DrawItemCard(gc,beforeDrawEvent);
                afterItemDraw =  draw.execute();
                noise = new NoiseRed(gc,afterItemDraw);
                
                retValues = afterItemDraw.getRetValues();
            }


            if(card==SectorCard.SECTOR_SILENCE){
                retValues.put("sectorcard", "silence");
                retValues.put("noise", "false");

                return new Event(e, retValues);
            }
            

            retValues.put("return", "true");

            //faccio rumore
            //TODO: publish noise
            e = noise.execute();
            return e;
        }
        retValues.put("sectortype", "safe");
        retValues.put("noise", "false");


        return new Event(e, retValues);
    }


}
