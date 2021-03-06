package it.polimi.ingsw.cg15.controller;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.GameState;
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
    static ClientToken ctoken1 = new ClientToken("playertoken1",null);
    static ClientToken ctoken2 = new ClientToken("playertoken2", null);
    static ClientToken ctoken3 = new ClientToken("playertoken3", null);

    GameState gs;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        
        
        GameManager gm = GameManager.getInstance();
         ctoken1 = new ClientToken("playertoken1",null);
         ctoken2 = new ClientToken("playertoken2", null);

        args=new HashMap<String, String>();
        args.put("gamename", "nome");
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
        

        response = gm.startGame(new Event(ctoken1, "startgame",null));
        assertEquals("game_started", response.getRetValues().get("return"));


    
    }

    @Test
    public void testGetInstance() {
        
        // Testo l'istanza del game manager.
        
        assertEquals(GameManager.getInstance(), gm);
    }

    @Test
    public void testDispatchMessage() throws RemoteException {
        
        // Testo l'invio di messaggi.
        
        Event event = new Event(ctoken, "move", args);
        gm.dispatchMessage(event);
    }

    @Test
    public void testCreateGameAndGetGameList() throws RemoteException {
        
        // Testo la creazione del gioco e la lista di giochi disponibili.
        
        ClientToken ctoken = new ClientToken("playerToken", "gameToken");
        e = new Event(ctoken, "gamelist",null);        
        Event result = gm.getGameList(e);
        assertTrue(result.getRetValues().containsKey("nome"));
    }
    
  
    
    @Test
    public void testGameList() throws RemoteException{
        
        // Testo la lista dei giochi.
        
        args=new HashMap<String, String>();
        Event gamelistEvent = new Event(ctoken3,"listgame",args);
        Event response = gm.eventHandler(gamelistEvent);
        String gameToken = response.getRetValues().get("nome");

        Event e = new Event(new ClientToken("playerToken", gameToken), "gameinfo", null);
        response = gm.eventHandler(e);
        
        String mapName = response.getRetValues().get("mapName");
        assertEquals("test123", mapName);
        
    }
    
    @Test
    public void testGetGameToken() throws RemoteException{
        
        // Testo il token del gioco.
    
        Event e = new Event(new ClientToken(null, null), "requesttoken");

        Event response = gm.eventHandler(e);
        
        ClientToken playerToken = response.getToken();
        
        assertNotEquals(playerToken, null);


    }
    


    
    
    @Test
    public void testGetMap() throws RemoteException{
        
        // Testo il ritorno corretto della mappa.
        
        Event e = new Event(ctoken1,"getmap",null);
        Event response = gm.eventHandler(e);
       String value = "1,1,0\n1,2,0\n1,3,0\n1,4,0\n1,5,0\n1,6,0\n1,7,0\n1,8,0\n1,9,0\n1,10,0\n1,11,0\n1,12,0\n1,13,0\n1,14,0\n1,15,0\n1,16,0\n1,17,0\n1,18,0\n1,19,0\n1,20,0\n1,21,0\n1,22,0\n1,23,0\n2,1,0\n2,2,0\n2,3,0\n2,4,0\n2,5,0\n2,6,0\n2,7,0\n2,8,0\n2,9,0\n2,10,0\n2,11,0\n2,12,0\n2,13,0\n2,14,0\n2,15,0\n2,16,0\n2,17,0\n2,18,0\n2,19,0\n2,20,0\n2,21,0\n2,22,0\n2,23,0\n3,1,0\n3,2,0\n3,3,0\n3,4,0\n3,5,0\n3,6,0\n3,7,0\n3,8,0\n3,9,0\n3,10,0\n3,11,0\n3,12,0\n3,13,0\n3,14,0\n3,15,0\n3,16,0\n3,17,0\n3,18,0\n3,19,0\n3,20,0\n3,21,0\n3,22,0\n3,23,0\n4,1,0\n4,2,0\n4,3,0\n4,4,0\n4,5,0\n4,6,0\n4,7,0\n4,8,0\n4,9,0\n4,10,0\n4,11,0\n4,12,1\n4,13,0\n4,14,0\n4,15,0\n4,16,0\n4,17,0\n4,18,0\n4,19,0\n4,20,0\n4,21,0\n4,22,0\n4,23,0\n5,1,0\n5,2,0\n5,3,0\n5,4,0\n5,5,0\n5,6,0\n5,7,0\n5,8,0\n5,9,0\n5,10,1\n5,11,1\n5,12,1\n5,13,1\n5,14,1\n5,15,0\n5,16,0\n5,17,0\n5,18,0\n5,19,0\n5,20,0\n5,21,0\n5,22,0\n5,23,0\n6,1,0\n6,2,0\n6,3,0\n6,4,0\n6,5,0\n6,6,0\n6,7,0\n6,8,0\n6,9,0\n6,10,1\n6,11,3\n6,12,0\n6,13,3\n6,14,1\n6,15,0\n6,16,0\n6,17,0\n6,18,0\n6,19,0\n6,20,0\n6,21,0\n6,22,0\n6,23,0\n7,1,0\n7,2,0\n7,3,0\n7,4,0\n7,5,0\n7,6,0\n7,7,0\n7,8,0\n7,9,0\n7,10,1\n7,11,4\n7,12,1\n7,13,5\n7,14,1\n7,15,0\n7,16,0\n7,17,0\n7,18,0\n7,19,0\n7,20,0\n7,21,0\n7,22,0\n7,23,0\n8,1,0\n8,2,0\n8,3,0\n8,4,0\n8,5,0\n8,6,0\n8,7,0\n8,8,0\n8,9,0\n8,10,0\n8,11,2\n8,12,1\n8,13,2\n8,14,0\n8,15,0\n8,16,0\n8,17,0\n8,18,0\n8,19,0\n8,20,0\n8,21,0\n8,22,0\n8,23,0\n9,1,0\n9,2,0\n9,3,0\n9,4,0\n9,5,0\n9,6,0\n9,7,0\n9,8,0\n9,9,0\n9,10,0\n9,11,0\n9,12,0\n9,13,0\n9,14,0\n9,15,0\n9,16,0\n9,17,0\n9,18,0\n9,19,0\n9,20,0\n9,21,0\n9,22,0\n9,23,0\n10,1,0\n10,2,0\n10,3,0\n10,4,0\n10,5,0\n10,6,0\n10,7,0\n10,8,0\n10,9,0\n10,10,0\n10,11,0\n10,12,0\n10,13,0\n10,14,0\n10,15,0\n10,16,0\n10,17,0\n10,18,0\n10,19,0\n10,20,0\n10,21,0\n10,22,0\n10,23,0\n11,1,0\n11,2,0\n11,3,0\n11,4,0\n11,5,0\n11,6,0\n11,7,0\n11,8,0\n11,9,0\n11,10,0\n11,11,0\n11,12,0\n11,13,0\n11,14,0\n11,15,0\n11,16,0\n11,17,0\n11,18,0\n11,19,0\n11,20,0\n11,21,0\n11,22,0\n11,23,0\n12,1,0\n12,2,0\n12,3,0\n12,4,0\n12,5,0\n12,6,0\n12,7,0\n12,8,0\n12,9,0\n12,10,0\n12,11,0\n12,12,0\n12,13,0\n12,14,0\n12,15,0\n12,16,0\n12,17,0\n12,18,0\n12,19,0\n12,20,0\n12,21,0\n12,22,0\n12,23,0\n13,1,0\n13,2,0\n13,3,0\n13,4,0\n13,5,0\n13,6,0\n13,7,0\n13,8,0\n13,9,0\n13,10,0\n13,11,0\n13,12,0\n13,13,0\n13,14,0\n13,15,0\n13,16,0\n13,17,0\n13,18,0\n13,19,0\n13,20,0\n13,21,0\n13,22,0\n13,23,0\n14,1,0\n14,2,0\n14,3,0\n14,4,0\n14,5,0\n14,6,0\n14,7,0\n14,8,0\n14,9,0\n14,10,0\n14,11,0\n14,12,0\n14,13,0\n14,14,0\n14,15,0\n14,16,0\n14,17,0\n14,18,0\n14,19,0\n14,20,0\n14,21,0\n14,22,0\n14,23,0\n";
        String map = response.getRetValues().get("map");
        
        assertEquals(value, map);
        
    }
    


}
