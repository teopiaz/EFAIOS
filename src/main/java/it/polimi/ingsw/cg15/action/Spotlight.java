
package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.List;
import java.util.Map;

public class Spotlight extends Action {

    Event e;
    /**
     * @param gc the game controller
     */
    public Spotlight(GameController gc,Event e) {
        super(gc);
        this.e=e;
        // TODO Auto-generated constructor stub
    }

    @Override
    public Event execute() {
        Map<String,String> retValues = e.getRetValues();
        PlayerController pc = getCurrentPlayerController();
        if(!pc.canUseCard()){
            retValues.put("return", "false");
            retValues.put("error","solo gli umani possono usare le carte oggetto");
            return new Event(e, retValues);
        }
        if(pc.itemCardUsed()){
            retValues.put("return", "false");
            retValues.put("error","carta già usata in questo turno");
            return new Event(e, retValues);
        }
        if(pc.hasCard(ItemCard.ITEM_SPOTLIGHT)){
            pc.removeCard(ItemCard.ITEM_SPOTLIGHT);

            String strTarget = e.getArgs().get("target");
            Coordinate target = Coordinate.getByLabel(strTarget);
            Player currentPlayer = getGameController().getCurrentPlayer();

            FieldController fc =  getGameController().getFieldController();

            List<Coordinate> list = target.getNeighborsList();
            list.add(target);
            for (Coordinate coordinate : list) {
                List<Player> playersInSector = fc.getPlayersInSector(coordinate);
                for (Player player : playersInSector) {
                    if(player!=currentPlayer){
                        retValues.put(Integer.toString(player.getPlayerNumber()), player.getPosition().getLabel());

                    }
                }
            }
            

            retValues.put("return", "true");
            return new Event(e, retValues);
        }
        retValues.put("return", "false");
        retValues.put("error","carta non posseduta");
        return new Event(e, retValues);

    }
}
