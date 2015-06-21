
package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MMP - LMR
 * This class specifies the logical card type object teleport.
 * It allows you to make lights into a specific sector and reveale if there are any players.
 */
public class Spotlight extends Action {

    /**
     * The event.
     */
    Event e;
    
    /**
     * @param gc The game controller.
     */
    public Spotlight(GameController gc,Event e) {
        super(gc);
        this.e=e;
    }

    /**
     * It contains the logic of the spotlight action.
     * @return a message with the list of return values.
     * @see it.polimi.ingsw.cg15.action.Action#execute()
     */
    @Override
    public Event execute() {
        Map<String,String> retValues;
        if(e.getRetValues()==null){
            retValues = new HashMap<String, String>();
        }else{
            retValues = e.getRetValues();
        }
        PlayerController pc = getCurrentPlayerController();
        if(!pc.canUseCard()){
            retValues.put(Event.RETURN, Event.FALSE);
            retValues.put(Event.ERROR,"solo gli umani possono usare le carte oggetto");
            return new Event(e, retValues);
        }
        if(pc.itemCardUsed()){
            retValues.put(Event.RETURN, Event.FALSE);
            retValues.put(Event.ERROR,"carta già usata in questo turno");
            return new Event(e, retValues);
        }
        if(pc.hasCard(ItemCard.ITEM_SPOTLIGHT)){
            Map<String,String> retPub = new HashMap<String, String>();
            pc.removeCard(ItemCard.ITEM_SPOTLIGHT);
            pc.setItemUsed();
            String strTarget = e.getArgs().get("target");
            Coordinate target = Coordinate.getByLabel(strTarget);
            Player currentPlayer = getGameController().getCurrentPlayer();
            FieldController fc =  getGameController().getFieldController();
            List<Coordinate> list = target.getNeighborsList();
            list.add(target);
            for (Coordinate coordinate : list) {
                List<Player> playersInSector = fc.getPlayersInSector(coordinate);
                if(playersInSector!=null){
                    for (Player player : playersInSector) {
                        if(player!=currentPlayer){
                            retValues.put(Integer.toString(player.getPlayerNumber()), player.getPosition().getLabel());
                            retPub.put(Integer.toString(player.getPlayerNumber()), player.getPosition().getLabel());
                        }
                    }
                }
            }

            
            String currentPlayerNumber = Integer.toString( getGameController().getCurrentPlayer().getPlayerNumber() );
            retPub.put("player", currentPlayerNumber);
            retPub.put("card","spotlight");
            Event toPublish = new Event(new ClientToken("", getGameController().getGameToken()),"log",null, retPub);
            Broker.publish(getGameController().getGameToken(), NetworkProxy.eventToJSON(toPublish));


            retValues.put(Event.RETURN, Event.TRUE);
            return new Event(e, retValues);
        }
        retValues.put(Event.RETURN, Event.FALSE);
        retValues.put(Event.ERROR,"carta non posseduta");
        return new Event(e, retValues);
    }
    
}
