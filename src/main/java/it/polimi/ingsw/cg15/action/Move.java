package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.ActionEnum;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Sector;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * This class extends the class action and is therefore one of the possible actions to be taken during a turn. 
 * In particular, it is the action that allows the player to move to a cell in the map. 
 * Also, if the selected cell is a dangerous sector it is concerned to call the functionality that allows you to draw a card sector.
 */
public class Move extends Action {

    /**
     * It is the cell that will be the destination for the player.
     */
    private Coordinate dest;
    Event e;

    /**
     * @param gc the Game Controller
     * @param dest It is the cell that will be the destination for the player. 
     */
    public Move(GameController gc, Event request) {
        super(gc);
        this.e=request;

    }

    @Override
    public Event execute() {


        PlayerController pc = getCurrentPlayerController();



        String destString = e.getArgs().get("destination").toUpperCase();
        this.dest = Coordinate.getByLabel(destString);
        if(getGameController().getFieldController().existInMap(dest)){



            if (pc.moveIsPossible(dest)) {
                pc.movePlayer(dest);
                Event response;
                if(!pc.isUnderSedatives()){
                    Action draw = new DrawSectorCard(getGameController(),e);
                    response = draw.execute();
                }
                else{
                    Map<String,String> retVal = new HashMap<String, String>();
                    retVal.put("sedatives", Event.TRUE);
                    response = new Event(e, retVal);
                }

                Map<String,String> retValues = response.getRetValues();
                response = new Event(response, retValues);
                Action escape = new Escape(getGameController(),response);
                response = escape.execute();

                retValues = response.getRetValues();
                getGameController().removeAction(ActionEnum.MOVE);
                retValues = response.getRetValues();
                retValues.put("return", Event.TRUE);
                retValues.put("destination", dest.toString());





                int currentPlayer = getGameController().getCurrentPlayer().getPlayerNumber();

                Map<String,String> pubRet = new HashMap<String, String>();
                pubRet.put("player", Integer.toString(currentPlayer));
                if(getGameController().getCurrentPlayer().getPosition().getSectorType()==Sector.WHITE){
                    pubRet.put("move","safesector" );
                }
                if(getGameController().getCurrentPlayer().getPosition().getSectorType()==Sector.GREY){
                    pubRet.put("move","unsafesector" );
                }
                if(getGameController().getCurrentPlayer().getPosition().getSectorType()==Sector.HATCH){
                    pubRet.put("move","hatchsector" );
                }
                Event toPublish = new Event(new ClientToken("", e.getToken().getGameToken()),"log",null, pubRet);
                Broker.publish(e.getToken().getGameToken(), NetworkProxy.eventToJSON(toPublish));


                return new Event(e, retValues);
            }
        }
        Logger.getLogger(Move.class.getName()).log(Level.INFO, "Action Move:  impossible to move");

        Map<String,String> retValues = e.getRetValues();
        retValues.put(Event.ERROR, "impossibile eseguire lo spostamento");
        retValues.put("return", Event.FALSE);

        return new Event(e, retValues);
    }

}
