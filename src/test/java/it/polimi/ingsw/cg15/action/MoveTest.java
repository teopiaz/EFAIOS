package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class MoveTest {

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
    public void testMovePickCard() throws RemoteException {
        
        // Mi muovo e pesco una carta.

        List<ItemCard> itemCardDeck = gs.getDeckContainer().getItemDeck().getItemDeck();
        itemCardDeck.clear();
        itemCardDeck.add(ItemCard.ITEM_ADRENALINE);


        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_RED_ITEM);


        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        currentPlayer.setPlayerType(PlayerType.SUPERALIEN);
        Event response;
        String destination = "L07";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);
        String position = null;
        if(response.actionResult()){
            position = response.getRetValues().get("destination");
        }

        position = currentPlayer.getPosition().getLabel();
        assertEquals(destination, position);

        assertEquals(ItemCard.ITEM_ADRENALINE, currentPlayer.getCardById(0));


        Event getCardEvent = new Event(currentPlayerToken,"getcardlist",null);
        response = gm.dispatchMessage(getCardEvent);

        assertEquals("1",response.getRetValues().get("cardssize"));
        assertEquals(true,response.getRetValues().containsKey("adrenaline"));

    }

    @Test
    public void testMovePickCardToDistant() throws RemoteException {

        // Un giocatore non può spostarsi in una cella troppo lontana.

        List<ItemCard> itemCardDeck = gs.getDeckContainer().getItemDeck().getItemDeck();
        itemCardDeck.clear();
        itemCardDeck.add(ItemCard.ITEM_SEDATIVES);


        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_GREEN_ITEM);


        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        currentPlayer.setPlayerType(PlayerType.ALIEN);
        Event response;
        String destination = "L10";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);

        assertTrue(response.getRetValues().containsKey("error"));     
    }

    @Test
    public void testMoveSafeSector() throws RemoteException {

        // Un giocatore si sposta in una cella safe.

        List<ItemCard> itemCardDeck = gs.getDeckContainer().getItemDeck().getItemDeck();
        itemCardDeck.clear();
        itemCardDeck.add(ItemCard.ITEM_SEDATIVES);


        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_GREEN_ITEM);


        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        currentPlayer.setPlayerType(PlayerType.ALIEN);
        Event response;
        String destination = "M08";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);

        assertTrue(!response.getRetValues().containsKey("error"));     


    }


    @Test
    public void testMovePick4Card() throws RemoteException {
        
        // Mi muovo ma ho già 4 carte oggetto.

        List<ItemCard> itemCardDeck = gs.getDeckContainer().getItemDeck().getItemDeck();
        itemCardDeck.clear();
        itemCardDeck.add(ItemCard.ITEM_ADRENALINE);
        itemCardDeck.add(ItemCard.ITEM_ADRENALINE);
        itemCardDeck.add(ItemCard.ITEM_ADRENALINE);


        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_GREEN_ITEM);


        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        currentPlayer.getCardList().clear();
        currentPlayer.addCard(ItemCard.ITEM_DEFENSE);
        currentPlayer.addCard(ItemCard.ITEM_DEFENSE);
        currentPlayer.addCard(ItemCard.ITEM_DEFENSE);
        currentPlayer.setPlayerType(PlayerType.HUMAN);

        Event response;
        String destination = "L07";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);

        String position = null;
        if(response.actionResult()){
            position = response.getRetValues().get("destination");
        }

        assertTrue(response.getRetValues().containsKey("error"));


    }


    @Test
    public void testMoveAskSector() throws RemoteException{
        
        // Mi muovo in una cella in cui dopo l'estrazione di una carta settore verde mi viene chiesto di inserire un settore
        // in cui fare rumore.

        Player currentPlayer = gs.getTurnState().getCurrentPlayer();

        currentPlayer.setPlayerType(PlayerType.ALIEN);

        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_GREEN);

        Event response;
        String destination = "L07";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);
        String position = null;
        if(response.actionResult()){
            assertTrue(response.getRetValues().containsKey("asksector"));
        }


        String noiseTarget = "L07";
        args = new HashMap<String,String>();
        args.put("position", noiseTarget);
        Event askEvent = new Event(currentPlayerToken,"asksector",args);


        response = gm.dispatchMessage(askEvent);


        List<String>actionList = new ArrayList<String>();
        Event getActionEvent = new Event(currentPlayerToken,"getactionlist",null);
        Event result;
        result = gm.dispatchMessage(getActionEvent);


        for (String action : result.getRetValues().keySet()) {
            if(!action.equals("return")){
                actionList.add(action);  
            }
        }
        assertTrue(!actionList.contains("asksector"));

    }

    @Test
    public void testMoveAskSectorFail() throws RemoteException{

        // Mi muovo in una cella inesistente.

        Player currentPlayer = gs.getTurnState().getCurrentPlayer();

        currentPlayer.setPlayerType(PlayerType.ALIEN);


        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_GREEN);


        Event response;
        String destination = "L07";
        Map<String,String> args = new HashMap<String,String>();
        args.put("destination", destination);
        Event eMove = new Event(currentPlayerToken,"move",args);
        response = gm.dispatchMessage(eMove);
        String position = null;
        if(response.actionResult()){
            assertTrue(response.getRetValues().containsKey("asksector"));
        }


        String noiseTarget = "Z99";
        args = new HashMap<String,String>();
        args.put("position", noiseTarget);
        Event askEvent = new Event(currentPlayerToken,"asksector",args);


        response = gm.dispatchMessage(askEvent);
        assertTrue(response.getRetValues().containsKey("error"));


    }

    
}
