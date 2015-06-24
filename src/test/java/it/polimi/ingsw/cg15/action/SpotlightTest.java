package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.model.ActionEnum;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class SpotlightTest {

    static GameManager gm =GameManager.getInstance();
    static Map<String, String> args = new HashMap<String, String>();

    static String gameToken;

    ClientToken currentPlayerToken;
    GameState gs;


    @Before
    public void setup() throws RemoteException{
        GameManager gm = GameManager.getInstance();
        ClientToken ctoken1 = new ClientToken("playertoken1",null);
        ClientToken ctoken2 = new ClientToken("playertoken2", null);

        args=new HashMap<String, String>();
        args.put("gamename", "testmove");
        args.put("mapname", "test123");
        Event response = gm.createGame(new Event(ctoken1,"creategame",args,null));
        String gameToken = response.getRetValues().get("gameToken");

        ctoken1 = new ClientToken("playertoken1",gameToken );
        ctoken2 = new ClientToken("playertoken2", gameToken);  

        Event join1 = new Event(ctoken1,"joingame",null);
        response = gm.joinGame(join1);
        assertEquals("joined", response.getRetValues().get("return"));
        Event join2 = new Event(ctoken2,"joingame",null);
        response = gm.joinGame(join2);
        assertEquals("joined", response.getRetValues().get("return"));


        response = gm.eventHandler((new Event(ctoken1, "startgame",null)));
        assertEquals("game_started", response.getRetValues().get("return"));


        Map<String, GameBox> list = gm.getGameBoxList();
        gs = list.get(gameToken).getGameState();



        Event eventoTest = new Event(ctoken1,"getplayerinfo",null);
        response = gm.dispatchMessage(eventoTest);
        int playerNumber = Integer.parseInt( response.getRetValues().get("playernumber"));
        if(playerNumber == 1){
            currentPlayerToken = ctoken1;
        }else{
            currentPlayerToken = ctoken2;
        }
    }

    @Test
    public final void testSpotlight() throws RemoteException {
        
        // Testo l'azione della carta oggetto luce.
        
        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        
        String target;
        PlayerType type;
        if(currentPlayer.getPlayerType()==PlayerType.HUMAN){
            target = "N07";
            type=PlayerType.HUMAN;
        }
        else{
            target = "J07";
            type=PlayerType.ALIEN;


        }


        gs.getTurnState().getActionList().add(ActionEnum.USEITEM);


        
        
        currentPlayer.setPlayerType(PlayerType.ALIEN);

        
        args = new HashMap<String, String>();
        args.put("itemcard", "spotlight");
        args.put("target", target); 

        Event spotlightEvent = new Event(currentPlayerToken,"useitem",args);
        Event result = gm.dispatchMessage(spotlightEvent);
        assertTrue(result.getRetValues().containsKey("error"));

        
        
        
        currentPlayer.setPlayerType(PlayerType.HUMAN);

        args = new HashMap<String, String>();
        args.put("itemcard", "spotlight");
        args.put("target", target); 

        spotlightEvent = new Event(currentPlayerToken,"useitem",args);
        result = gm.dispatchMessage(spotlightEvent);
        assertTrue(result.getRetValues().containsKey("error"));
        
        currentPlayer.addCard(ItemCard.ITEM_SPOTLIGHT);

        args = new HashMap<String, String>();
        args.put("itemcard", "spotlight");
        args.put("target", target); 

        spotlightEvent = new Event(currentPlayerToken,"useitem",args);
        result = gm.dispatchMessage(spotlightEvent);
        result.getRetValues().remove("return");
        boolean test=false;
        for (Entry<String,String> ret : result.getRetValues().entrySet()) {
            if(type==PlayerType.HUMAN && ret.getValue().equals("M07")){
                test=true;
            }
            if(type==PlayerType.ALIEN && ret.getValue().equals("K07")){
                test=true;
            }
        }
        assertTrue(test);

        args = new HashMap<String, String>();
        args.put("itemcard", "spotlight");
        args.put("target", target); 

        spotlightEvent = new Event(currentPlayerToken,"useitem",args);
        result = gm.dispatchMessage(spotlightEvent);
        assertTrue(result.getRetValues().containsKey("error"));

        currentPlayer.addCard(ItemCard.ITEM_SPOTLIGHT);

    }

}
