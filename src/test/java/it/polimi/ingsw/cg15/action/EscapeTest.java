package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.assertEquals;
import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.HatchCard;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

public class EscapeTest {


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
    public void testExecute() throws RemoteException {
        
        // Testo l'esecuzione della escape.
        
        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        currentPlayer.setPlayerType(PlayerType.HUMAN);
        
        List<HatchCard> hatchCardDeck = gs.getDeckContainer().getHatchDeck().getHatchDeck();
        hatchCardDeck.clear();
        hatchCardDeck.add(HatchCard.HATCH_GREEN);
        
        currentPlayer.setPosition(gs.getField().getHumanStartingPosition());
        
        gs.getField().getHumanStartingPosition().addPlayer(currentPlayer);
        
        String destination = "K06";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        Event response = gm.dispatchMessage(eMove);


        
        assertEquals(Player.WIN, currentPlayer.getStatus());
        
        
        
    }

    

    @Test
    public void testEscapeFail() throws RemoteException {
        
        FieldController fc = new FieldController(gs);
        
        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        currentPlayer.setPlayerType(PlayerType.HUMAN);
        
        for (Entry<Coordinate,Boolean> hSector: gs.getField().getHatchSectorsList().entrySet()) {
            fc.blockHatchSector(hSector.getKey());
        }
        
        
        List<HatchCard> hatchCardDeck = gs.getDeckContainer().getHatchDeck().getHatchDeck();
        hatchCardDeck.clear();
        hatchCardDeck.add(HatchCard.HATCH_GREEN);
        
        currentPlayer.setPosition(gs.getField().getHumanStartingPosition());
        
        gs.getField().getHumanStartingPosition().addPlayer(currentPlayer);
        
        Event response;
        String destination = "K06";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);


        
        assertEquals(Player.INGAME, currentPlayer.getStatus());
        
        
        
        
        
        
    }

}
