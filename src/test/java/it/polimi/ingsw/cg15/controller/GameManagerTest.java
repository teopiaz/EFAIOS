package it.polimi.ingsw.cg15.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class GameManagerTest {

    static GameManager gm =GameManager.getInstance();
    static  Event e;
    static Map<String, String> args = new HashMap<String, String>();
    static ClientToken ctoken;
    static String gameToken;


    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        ctoken = new ClientToken("playerToken", null);
        args.put("gamename","prova_nome_partita");

        e = new Event(ctoken, "creategame",args);

        Event result = gm.createGame(e);

        ctoken = new ClientToken(e.getToken().getPlayerToken(), result.getRetValues().get("return"));
        gameToken = ctoken.getGameToken();
    }

    @Test
    public void testGetInstance() {
        assertEquals(GameManager.getInstance(), gm);
    }

    @Test
    public void testDispatchMessage() throws RemoteException {
        Event event = new Event(ctoken, "move", args);
        gm.dispatchMessage(event);
    }

    @Test
    public void testCreateGameAndGetGameList() throws RemoteException {
        ClientToken ctoken = new ClientToken("playerToken", "gameToken");
        e = new Event(ctoken, "gamelist",null);        
        Event result = gm.getGameList(e);
        assertTrue(result.getRetValues().containsKey("prova_nome_partita"));
    }
    
    @Test
    public void testGameCreationAndStart() throws RemoteException{
        GameManager gm = GameManager.getInstance();
        ClientToken ctoken1 = new ClientToken("playertoken1",null);
        ClientToken ctoken2 = new ClientToken("playertoken2", null);

        args=new HashMap<String, String>();
        args.put("gamename", "nome");
        args.put("mapname", "fermi");
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



    }



}
