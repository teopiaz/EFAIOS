package it.polimi.ingsw.cg15.gui;

import java.util.Map;

public interface ViewClientInterfaceCLI {
    
    public void stampa(String messaggio);
    public void requestClientToken();
    public Map<String,String> getGamesList();
    public void createGame(String gameName,String mapName);
    public void joinGame(String gameToken);
    public Map<String, String> getGameInfo(String gameToken);
    public void menu();

}
