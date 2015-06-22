package it.polimi.ingsw.cg15.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.player.Player;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameStateTest {
    
    GameState gs = new GameState(null, null);

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public final void testAddPlayer() {
        Player player = new Player();
        Player player2 = gs.addPlayer(player);
        assertEquals(player, player2);
        
        gs.addPlayer(new Player());
        gs.addPlayer(new Player());
        gs.addPlayer(new Player());
        gs.addPlayer(new Player());
        gs.addPlayer(new Player());
        gs.addPlayer(new Player());
        gs.addPlayer(new Player());
        assertEquals(gs.addPlayer(new Player()),null);
        
    }
    
    @Test
    public final void testSetEnded() {
        gs.setEnded();
        assertTrue(gs.isEnded());
    }
    
    

}
