package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.fail;
import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.GameManager;
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
        GameBox gameBox = new GameBox(GameInstance.getInstance().addGameInstance(), null, "prova");
        GameController gc = new GameController(gameBox);
        GameState gs = gameBox.getGameState();
        FieldController fc = gc.getFieldController();
        fc.loadMap("galilei");
        
        Cell startingPosition = gs.getField().getCell(new Coordinate(1, 1));
        Player player = new Player(startingPosition, PlayerType.ALIEN);
        gs.getTurnState().setCurrentPlayer(player);

        System.out.println("porcodio");
        PlayerController pc = gc.getPlayerInstance(player);
        System.out.println(pc+"    ad");

        pc.movePlayer(new Coordinate(1, 1));
        Coordinate dest = new Coordinate(1, 2);
        System.out.println("asd");
        boolean bo = pc.moveIsPossible(dest);
        System.out.println(bo+"a");
        Action move = new Move<Boolean>(gc, dest);
        move.execute();
        
        
    }

    @Test
    public void testMove() {
        fail("Not yet implemented");
    }

}
