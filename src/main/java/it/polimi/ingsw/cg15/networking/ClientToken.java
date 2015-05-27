package it.polimi.ingsw.cg15.networking;

public class ClientToken {


    private final String playerToken;
    private final String gameToken;

    public ClientToken(String playerToken, String gameToken){
        this.playerToken = playerToken;
        this.gameToken=gameToken;
    }
    
    public String getGameToken(){
        return gameToken;
    }

    public String getPlayerToken(){
        
        return playerToken;
    }
    
    
    @Override
    public String toString() {
        return "ClientToken [playerToken=" + playerToken + ", gameToken=" + gameToken + "]";
    }



}
