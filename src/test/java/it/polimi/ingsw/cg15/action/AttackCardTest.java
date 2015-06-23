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

import org.junit.Before;
import org.junit.Test;

public class AttackCardTest {
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

        System.out.println(gameToken);

        Event join1 = new Event(ctoken1,"joingame",null);
        response = gm.joinGame(join1);
        assertEquals("joined", response.getRetValues().get("return"));
        Event join2 = new Event(ctoken2,"joingame",null);
        response = gm.joinGame(join2);
        assertEquals("joined", response.getRetValues().get("return"));


        response = gm.startGame(new Event(ctoken1, "startgame",null));
        assertEquals("game_started", response.getRetValues().get("return"));


        Map<String, GameBox> list = gm.getGameBoxList();
        gs = list.get(gameToken).getGameState();
        // assertTrue();



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
    public final void test() throws RemoteException {
        
        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.setPlayerType(PlayerType.HUMAN);
        gs.getTurnState().getActionList().add(ActionEnum.USEITEM);
        Event attackcardEvent;
        Event result;

        currentPlayer.removeCard(ItemCard.ITEM_ATTACK);
        args = new HashMap<String, String>();
        args.put("itemcard", "attack");
         attackcardEvent = new Event(currentPlayerToken,"useitem",args);
        System.out.println(attackcardEvent);
         result = gm.dispatchMessage(attackcardEvent);
        System.out.println(result);

        
        currentPlayer.addCard(ItemCard.ITEM_ATTACK);
        
        args = new HashMap<String, String>();
        args.put("itemcard", "attack");
         attackcardEvent = new Event(currentPlayerToken,"useitem",args);
         result = gm.dispatchMessage(attackcardEvent);
        
        args = new HashMap<String, String>();
        args.put("itemcard", "attack");
         attackcardEvent = new Event(currentPlayerToken,"useitem",args);
         result = gm.dispatchMessage(attackcardEvent);
        System.err.println(result);
        
        System.out.println(currentPlayer.getPosition().getLabel());
        
       
        
    }

}
