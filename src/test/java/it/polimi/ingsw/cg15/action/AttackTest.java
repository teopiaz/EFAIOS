package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.model.ActionEnum;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class AttackTest {

    static GameManager gm =GameManager.getInstance();
    static Map<String, String> args = new HashMap<String, String>();

    static String gameToken;

    GameState gs;
    Player currentPlayer;
    ClientToken ctoken1 = new ClientToken("playertoken1",null);
    ClientToken ctoken2 = new ClientToken("playertoken2", null);
    ClientToken currentPlayerToken = ctoken1;

    
    @Before
    public void setup() throws RemoteException{


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



    }


    @Test
    public final void test() throws RemoteException {
    
        getCurrentPlayer();
        
        Event response;
        String destination = "L07";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);
        
        String position;
        position = currentPlayer.getPosition().getLabel();
        assertEquals(destination, position);
        
        currentPlayer.setPlayerType(PlayerType.ALIEN);
        gs.getTurnState().getActionList().add(ActionEnum.ATTACK);
        
        Event attackEvent = new Event(currentPlayerToken,"attack",null);
        Event result = gm.dispatchMessage(attackEvent);
        System.out.println(result);

        int killedPlayer =Integer.parseInt(result.getRetValues().get("killcount"));
        assertEquals(0, killedPlayer);
      

        
       /* 
        Event endEvent = new Event(currentPlayerToken,"endturn",null);
        response = gm.dispatchMessage(endEvent);
        System.out.println("Fine Turno"+response);


        getCurrentPlayer();
        
        currentPlayer.setPlayerType(PlayerType.HUMAN);
        gs.getTurnState().getActionList().add(ActionEnum.USEITEM);

        currentPlayer.addCard(ItemCard.ITEM_ATTACK);

        
        destination = "L07";
        args = new HashMap<String,String>();
        args.put("destination", destination);
        eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);
        
        position = currentPlayer.getPosition().getLabel();
        System.out.println(destination+position);
        assertEquals(destination, position);
        
        args = new HashMap<String, String>();
        args.put("itemcard", "attack");
        Event attackItem = new Event(currentPlayerToken,"useitem",args);
        result = gm.dispatchMessage(attackItem);
        
        System.out.println(result);

        killedPlayer =Integer.parseInt(result.getRetValues().get("killcount"));
        */
        }
    
    

    
    private void getCurrentPlayer() throws RemoteException{

        currentPlayerToken = ctoken1;
        Event eventoTest = new Event(currentPlayerToken,"getturninfo",null);
        Event response = gm.dispatchMessage(eventoTest);
        System.out.println("TURN INFO:"+response);
        int playerNumber = Integer.parseInt( response.getRetValues().get("currentplayer"));
        if(playerNumber == 1){
            currentPlayerToken = ctoken1;
        }else{
            currentPlayerToken = ctoken2;
        }
        
        currentPlayer = gs.getTurnState().getCurrentPlayer();

    }
}
