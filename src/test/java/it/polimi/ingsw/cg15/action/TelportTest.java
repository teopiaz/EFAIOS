package it.polimi.ingsw.cg15.action;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.controller.FieldController;
import it.polimi.ingsw.cg15.controller.GameBox;
import it.polimi.ingsw.cg15.controller.GameController;
import it.polimi.ingsw.cg15.controller.cards.CardController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;

import org.junit.BeforeClass;
import org.junit.Test;

public class TelportTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Test
    public void testTeleport() {
        
        GameState gs = GameInstance.getInstance().addGameInstance();
        CardController cc = new CardController(gs);
        GameBox gameBox = new GameBox(gs, null, "prova", null);
        GameController gc = new GameController(gameBox);
        FieldController fc = gc.getFieldController();
        fc.loadMap("galilei");
        Cell cella1 = gs.getField().getCell(new Coordinate(6,12));
        System.out.println(cella1.getLabel());
        
        
        fc.setHumanStartingPosition(cella1.getCoordinate());

        Player player = new Player(cella1, PlayerType.HUMAN);
        gs.getTurnState().setCurrentPlayer(player);
        cella1.addPlayer(player);
        gs.addPlayer(player);

        
        GameState STATO =gs;
        System.out.println("do la carta");

        gs.getTurnState().getCurrentPlayer().addCard(ItemCard.ITEM_TELEPORT);
        System.out.println("carta data");

       Action teleport = new Teleport(gc);
       System.out.println("azione creata");

       teleport.execute();
       
       assertEquals(gs.getTurnState().getCurrentPlayer().getCardListSize(),0);
       assertEquals(gs.getTurnState().getCurrentPlayer().getPosition(), cella1);
       System.out.println("ho finito");
        
        
        
    }

}
