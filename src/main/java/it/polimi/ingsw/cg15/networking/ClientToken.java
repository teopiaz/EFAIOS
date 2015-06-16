package it.polimi.ingsw.cg15.networking;

import java.io.Serializable;

/**
 * @author MMP - LMR
 * The client token class.
 */
public class ClientToken implements Serializable{

    /**
     * The player token.
     */
    private final String playerToken;
    
    /**
     * The game token.
     */
    private final String gameToken;

    /**
     * The constructor.
     * @param playerToken The Player Token.
     * @param gameToken The Game Token.
     */
    public ClientToken(String playerToken, String gameToken){
        this.playerToken = playerToken;
        this.gameToken=gameToken;
    }
    
    /**
     * @return The game token.
     */
    public String getGameToken(){
        return gameToken;
    }

    /**
     * @return The player token.
     */
    public String getPlayerToken(){
        return playerToken;
    }
    
    /**
     * Transform into a string.
     */
    @Override
    public String toString() {
        return "ClientToken [playerToken=" + playerToken + ", gameToken=" + gameToken + "]";
    }

}
