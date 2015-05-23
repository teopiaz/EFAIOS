package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.GameManager;
import it.polimi.ingsw.cg15.controller.cards.CardController;
import it.polimi.ingsw.cg15.controller.player.AlienPlayerController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.HashMap;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

public class MoveTest {

    
    static GameManager gm =GameManager.getInstance();
    static  Event e;
    static Map<String, String> args = new HashMap<String, String>();
    static ClientToken ctoken;
    static String gameToken;
    
    
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
       /*
        ctoken = new ClientToken("playerToken", null);
        args.put("gamename","prova_nome_partita");
        e = new Event(ctoken, "creategame",args);
        Event result = gm.createGame(e);

        ctoken = new ClientToken(e.getToken().getPlayerToken(), result.getRetValues().get(0));
        gameToken = ctoken.getGameToken();
        */
    
        
    }

    @Test
    public void testMove() {
        GameState gs = GameInstance.getInstance().addGameInstance();
        CardController cc = new CardController(gs);
        GameBox gameBox = new GameBox(gs, null, "prova");
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

        Action move = new Move(gc, dest);
        boolean b = move.execute();
        
        Coordinate c = gs.getTurnState().getCurrentPlayer().getPosition().getCoordinate();
        
        assertEquals(c,dest);
    }

}
