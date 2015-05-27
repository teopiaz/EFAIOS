package it.polimi.ingsw.cg15.gui;

import java.util.Map;

public interface ViewClientInterface {
    
    public void stampa(String messaggio);
    public void requestClientToken();
    public Map<String,String> getGamesList();
    public void createGame(String gameName,String mapName);
    public void startGame();

}
