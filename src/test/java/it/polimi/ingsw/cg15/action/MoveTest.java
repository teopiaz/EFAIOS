package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.cards.SectorCard;
import it.polimi.ingsw.cg15.model.player.Player;
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

        System.out.println(gameToken);

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
    public void testMovePickCard() throws RemoteException {

        List<ItemCard> itemCardDeck = gs.getDeckContainer().getItemDeck().getItemDeck();
        itemCardDeck.clear();
        itemCardDeck.add(ItemCard.ITEM_ADRENALINE);
        for (ItemCard  itemCard : itemCardDeck) {
            System.out.println(itemCard);
        }


        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_RED_ITEM);
        for (SectorCard sectorCard : sectorCardDeck) {
            System.out.println(sectorCard);
        }



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

        Player currentPlayer = gs.getTurnState().getCurrentPlayer();
        position = currentPlayer.getPosition().getLabel();
        assertEquals(destination, position);

        System.out.println(currentPlayer.getCardList());

        assertEquals(ItemCard.ITEM_ADRENALINE, currentPlayer.getCardById(0));
        
        
        Event getCardEvent = new Event(currentPlayerToken,"getcardlist",null);
        response = gm.dispatchMessage(getCardEvent);

        System.out.println(response);
        assertEquals("1",response.getRetValues().get("cardssize"));
        assertEquals(true,response.getRetValues().containsKey("adrenaline"));



    }

    @Test
    public void testMoveAskSector() throws RemoteException{
        List<SectorCard> sectorCardDeck = gs.getDeckContainer().getSectorDeck().getSectorDeck();
        sectorCardDeck.clear();
        sectorCardDeck.add(SectorCard.SECTOR_GREEN);
        for (SectorCard sectorCard : sectorCardDeck) {
            System.out.println(sectorCard);
        }


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




}
