package it.polimi.ingsw.cg15.model;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.player.Player;

import org.junit.Test;

public class TurnStateTest {
    
    TurnState ts = new TurnState();

   

    @Test
    public final void testIsLockedOnDiscardOrUseItem() {
        
        // Testo il blocco del giocatore quando possiede già 4 carte oggetto.
        
        ts.lockOnDiscardOrUseItem();
        assertTrue(ts.isLockedOnDiscardOrUseItem());
        
    }


    @Test
    public final void testSetCurrentPlayer() {
        
        // Testo il posizionamento corretto del giocatore corrente.
        
       Player player = new Player();
       ts.setCurrentPlayer(player);
       assertEquals(player, ts.getCurrentPlayer());
    }

    @Test
    public final void testIsUnderAdrenaline() {
        
        // Testo che il giocatore sia soto adrenalina.
        
        ts.setUnderAdrenaline();
        assertTrue(ts.isUnderAdrenaline());

    }


    @Test
    public final void testIsUnderSedatives() {
        
        // Testo che il giocatore sia sotto sedativi.
        
        ts.setUnderSedatives();
        assertTrue(ts.isUnderSedatives());

    }


    @Test
    public final void testResetHasMoved() {
        
        // Testo che il giocatore possa muoversi correttamente dopo che è finito il suo turno e si è già mosso.
        
        ts.resetHasMoved();
        assertFalse(ts.hasMoved());

    }

    @Test
    public final void testIsActionInActionList() {
        
        // Testo la presenza delle azioni nella lista di azioni disponibili.
        
        assertFalse(ts.isActionInActionList("move"));
    }

    @Test
    public final void testAddUseOrDiscard() {
        
        // Testo l'aggiunta dell'azione usa o scarta carta oggetto.
        
        ts.addUseOrDiscard();
        assertTrue(ts.isActionInActionList("discard"));
    }

}
