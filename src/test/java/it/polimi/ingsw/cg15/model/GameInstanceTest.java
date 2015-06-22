package it.polimi.ingsw.cg15.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameInstanceTest {
    GameInstance gi = GameInstance.getInstance();



    @Test
    public final void testRemoveGameInstace() {
       GameState gs = gi.addGameInstance();
       gi.removeGameInstace(gs);
       assertEquals(0, gi.getInstanceSize());
        
    }

    @Test
    public final void testRemoveAllGames() {
        gi.addGameInstance();
        gi.addGameInstance();
        gi.addGameInstance();
        gi.removeAllGames();
        assertEquals(0, gi.getInstanceSize());
    }

}
