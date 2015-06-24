package it.polimi.ingsw.cg15.networking;

import static org.junit.Assert.*;

import org.junit.Test;

public class SessionTokenGeneratorTest {

    @Test
    public void testNextSessionId() {
        
        // Testo la generazione del token della sessione.
        
        String token = SessionTokenGenerator.nextSessionId();

        assertNotEquals(token, null);
    }

}
