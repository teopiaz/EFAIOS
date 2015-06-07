package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.controller.cards.CardController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class MoveTest {


    static GameManager gm =GameManager.getInstance();
    static  Event e;
    static Map<String, String> args = new HashMap<String, String>();
    static ClientToken ctoken;
    static String gameToken;



    @Test
    public void testMove() throws RemoteException {
        GameManager gm = GameManager.getInstance();
        ClientToken ctoken1 = new ClientToken("playertoken1",null);
        ClientToken ctoken2 = new ClientToken("playertoken2", null);

        args=new HashMap<String, String>();
        args.put("gamename", "testmove");
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

        Map<String, GameBox> list = gm.getGameBoxList();
       // assertTrue();

    }
    /*
    @Test
    public void testMove() {
        GameState gs = GameInstance.getInstance().addGameInstance();
        CardController cc = new CardController(gs);
        cc.generateDecks();
        GameBox gameBox = new GameBox(gs, null, "prova", null);
        GameController gc = new GameController(gameBox);
        FieldController fc = gc.getFieldController();
        fc.loadMap("galilei");
        Cell startingPosition = gs.getField().getCell(new Coordinate(5,2));


        Player player = new Player(startingPosition, PlayerType.HUMAN);
        startingPosition.addPlayer(player);
        gs.getTurnState().setCurrentPlayer(player);

        gs.addPlayer(player);

        PlayerController pc = gc.getPlayerInstance(player);

        Cell provaCella = gs.getField().getCell(new Coordinate(5,2));

        pc.movePlayer(provaCella.getCoordinate());
        gs.getTurnState().resetHasMoved();
        Coordinate dest = new Coordinate(5, 3);

        Event e = new Event(token, retValue)

        Action move = new Move(gc, e)
        boolean b = move.execute();

        Coordinate c = gs.getTurnState().getCurrentPlayer().getPosition().getCoordinate();

        assertEquals(c,dest);
    }
     */

}
