package it.polimi.ingsw.cg15.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.player.Player;

import org.hamcrest.core.IsAnything;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TurnStateTest {
    
    TurnState ts = new TurnState();

   

    @Test
    public final void testIsLockedOnDiscardOrUseItem() {
        ts.lockOnDiscardOrUseItem();
        assertTrue(ts.isLockedOnDiscardOrUseItem());
        
    }


    @Test
    public final void testSetCurrentPlayer() {
       Player player = new Player();
       ts.setCurrentPlayer(player);
       assertEquals(player, ts.getCurrentPlayer());
    }

    @Test
    public final void testIsUnderAdrenaline() {
        ts.setUnderAdrenaline();
        assertTrue(ts.isUnderAdrenaline());

    }


    @Test
    public final void testIsUnderSedatives() {
        ts.setUnderSedatives();
        assertTrue(ts.isUnderSedatives());

    }


    @Test
    public final void testResetHasMoved() {
        ts.resetHasMoved();
        assertFalse(ts.hasMoved());

    }

    @Test
    public final void testIsActionInActionList() {
        assertFalse(ts.isActionInActionList("move"));
    }

    @Test
    public final void testAddUseOrDiscard() {
        ts.addUseOrDiscard();
        assertTrue(ts.isActionInActionList("discard"));
    }

}
