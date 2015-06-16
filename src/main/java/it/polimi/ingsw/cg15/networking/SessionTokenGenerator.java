package it.polimi.ingsw.cg15.networking;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * @author MMP - LMR
 * The token generator for the current session.
 */
public final class SessionTokenGenerator {

    /**
     * Generate a new random.
     */
    private static SecureRandom random = new SecureRandom();

    /**
     * The ID for the session.
     * @return the token.
     */
    public static String nextSessionId() {
        return new BigInteger(130, random).toString(32);
    }

}