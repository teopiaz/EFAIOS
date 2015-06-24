package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.model.ActionEnum;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class AttackTest {

    static GameManager gm =GameManager.getInstance();
    static Map<String, String> args = new HashMap<String, String>();

    static String gameToken;

    static GameState gs;
    Player currentPlayer;
    static ClientToken ctoken1 = new ClientToken("playertoken1",null);
    static ClientToken ctoken2 = new ClientToken("playertoken2", null);
    ClientToken currentPlayerToken = ctoken1;
    static Map<String, GameBox> list;
    static GameBox gb;

    
    @BeforeClass
    public  static void setup() throws RemoteException{
        


        GameInstance gameInstance = GameInstance.getInstance();
        gameInstance.removeAllGames();
        args=new HashMap<String, String>();
        args.put("gamename", "testattack");
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


        list = gm.getGameBoxList();
        gs = list.get(gameToken).getGameState();
        gb= list.get(gameToken);



    }


    @Test
    public final void testAttack() throws RemoteException {
        
        // Testo l'azione attacco.
    
        ClientToken tokenPlayer1 = getPlayerToken("1");
        Map<String,Player> playerMap = gb.getPlayers();
        GameState gameState = gb.getGameState();
        currentPlayer = gameState.getTurnState().getCurrentPlayer();
            int playernumb =    gb.getPlayers().get( tokenPlayer1.getPlayerToken() ).getPlayerNumber(); 
        
        currentPlayer.setPlayerType(PlayerType.ALIEN);
        gs.getTurnState().getActionList().add(ActionEnum.ATTACK);
        
        currentPlayer = gameState.getTurnState().getCurrentPlayer();
        Event attackEvent = new Event(tokenPlayer1,"attack",null);
        Event result = gm.dispatchMessage(attackEvent);

        int killedPlayer =Integer.parseInt(result.getRetValues().get("killcount"));
        assertEquals(0, killedPlayer);
      
    }
        
        
        @Test 
        public void testDefense(){
            
            // Testo l'azione difesa.
        
        Event attackEvent = new Event(currentPlayerToken,"attack",null);
        currentPlayer = gs.getTurnState().getCurrentPlayer();

        currentPlayer.addCard(ItemCard.ITEM_DEFENSE);

        GameController gc = new GameController(gb);
        Action defense = new Defend(gc, currentPlayer, attackEvent);
        defense.execute();
        assertFalse(currentPlayer.getCardList().contains(ItemCard.ITEM_DEFENSE));
        
        }
    


        @Test 
        public void testEvolve(){
            
            // Testo l'evoluzione del giocatore da alieno a superalieno.
            
            currentPlayer = gs.getTurnState().getCurrentPlayer();

            currentPlayer.setPlayerType(PlayerType.ALIEN);
            GameController gc = new GameController(gb);
            gc.getPlayerInstance(currentPlayer).evolve();
            assertEquals(PlayerType.SUPERALIEN, currentPlayer.getPlayerType());
            
        }
    
    
    
    
    
    
    private ClientToken getPlayerToken(String n) throws RemoteException{
        
        Map<String,ClientToken> playermap = new HashMap<String, ClientToken>();
        
        Event eventoTest = new Event(ctoken1,"getplayerinfo",null);
        Event response = gm.dispatchMessage(eventoTest);
        String player1number = response.getRetValues().get("playernumber");
        
        if("1".equals(player1number)){
            playermap.put("1", ctoken1);
            playermap.put("2", ctoken2);
        }else{
            playermap.put("2", ctoken1);
            playermap.put("1", ctoken2);
        }
        
        return playermap.get(n);      
    }
    
    private ClientToken getCurrentPlayer() throws RemoteException{
        
        
        String playernumber;
       
        Event eventoTurn = new Event(ctoken1,"getturninfo",null);
         Event response = gm.dispatchMessage(eventoTurn);
        playernumber = response.getRetValues().get("currentplayer");
        
       return  getPlayerToken(playernumber);

    }
    
    
    
}
