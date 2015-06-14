package it.polimi.ingsw.cg15.gui;

import it.polimi.ingsw.cg15.networking.Event;


public interface ViewClientInterface {

    public void stampa(String messaggio);

    public void log(Event e);

    public void chat(Event e);

    public void setStarted();

    public void currentPlayer(int currentPlayer);

}
