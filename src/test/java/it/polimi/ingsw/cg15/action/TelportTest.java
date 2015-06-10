package it.polimi.ingsw.cg15.action;

import org.junit.BeforeClass;

public class TelportTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }
    /*

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
    */

}
