package it.polimi.ingsw.cg15.networking;

import static org.junit.Assert.*;

import org.junit.Test;

public class SessionTokenGeneratorTest {

    @Test
    public void testNextSessionId() {
        String token = SessionTokenGenerator.nextSessionId();
        int lenght = token.length();
        assertEquals(26, lenght);
    }

}
