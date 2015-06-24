package it.polimi.ingsw.cg15.controller;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameControllerTest {

    static GameState gs = GameInstance.getInstance().addGameInstance();
    Map<String,Player> players = new HashMap<String, Player>();
    GameBox gb = new GameBox(gs, null, players);
    GameController gc = new GameController(gb);
    static GameManager gm = GameManager.getInstance();
    static Map<String, String> args = new HashMap<String, String>();
   static ClientToken currentPlayerToken;

    Player currentPlayer;
    static ClientToken ctoken1 = new ClientToken("playertoken1", null);
    static ClientToken ctoken2 = new ClientToken("playertoken2", null);
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws RemoteException {
        GameManager gm = GameManager.getInstance();


        args = new HashMap<String, String>();
        args.put("gamename", "testendturn");
        args.put("mapname", "test123");
        Event response = gm.createGame(new Event(ctoken1, "creategame", args, null));
        String gameToken = response.getRetValues().get("gameToken");
        ctoken1 = new ClientToken("playertoken1", gameToken);
        ctoken2 = new ClientToken("playertoken2", gameToken);

        Event join1 = new Event(ctoken1, "joingame", null);
        response = gm.joinGame(join1);

        assertEquals("joined", response.getRetValues().get("return"));
        
        Event join2 = new Event(ctoken2, "joingame", null);
        response = gm.joinGame(join2);


        assertEquals("joined", response.getRetValues().get("return"));

        response = gm.startGame(new Event(ctoken1, "startgame", null));
        assertEquals("game_started", response.getRetValues().get("return"));

        Map<String, GameBox> list = gm.getGameBoxList();
        gs = list.get(gameToken).getGameState();
        // assertTrue();

        Event eventoTest = new Event(ctoken1, "getplayerinfo", null);
        response = gm.dispatchMessage(eventoTest);
        int playerNumber = Integer.parseInt(response.getRetValues().get("playernumber"));


    }
   
    @Test
    public void testEndTurn() throws RemoteException{
        String currentNumber1 = getCurrentPlayer();
        
        Event e = new Event(currentPlayerToken, "endturn", null);
        Event response = gm.dispatchMessage(e);
        
        
    }
    
    
    @Test
    public void testChat() throws RemoteException {
        Map<String,String> args = new HashMap<String, String>();
        args.put(Event.MESSAGE, "prova");
        args.put(Event.PLAYER, "1");
        Event chatEvent = new Event(currentPlayerToken, "chat", args);
        Event response = gm.dispatchMessage(chatEvent);
        assertTrue(response.actionResult());
        
        args = new HashMap<String, String>();
        args.put(Event.PLAYER, "1");
         chatEvent = new Event(currentPlayerToken, "chat", args);
         response = gm.dispatchMessage(chatEvent);
        assertFalse(response.actionResult());
        
        
    }
    
    
    
    

  
    
    private String getCurrentPlayer() throws RemoteException{
        
        
        String player1number;
        
        Event eventoTest = new Event(ctoken1,"getplayerinfo",null);
        Event response = gm.dispatchMessage(eventoTest);
        player1number = response.getRetValues().get("playernumber");
        
        if("1".equals(player1number)){
            currentPlayerToken = ctoken1;
        }else{
            currentPlayerToken = ctoken2;
        }
        currentPlayer = gs.getTurnState().getCurrentPlayer();
        return player1number;
    }
    
    
   
}
