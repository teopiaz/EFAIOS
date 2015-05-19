package it.polimi.ingsw.cg15.networking;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class SessionTokenGenerator {
  private static SecureRandom random = new SecureRandom();

  public static String nextSessionId() {
    return new BigInteger(130, random).toString(32);
  }
}