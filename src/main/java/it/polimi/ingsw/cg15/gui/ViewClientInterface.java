package it.polimi.ingsw.cg15.gui;

import it.polimi.ingsw.cg15.networking.Event;

/**
 * @author MMP - LMR
 * The Client Interface class.
 */
public interface ViewClientInterface {

    /**
     * @param messaggio The message to print.
     */
    public void stampa(String messaggio);

    /**
     * @param e The event to log.
     */
    public void log(Event e);

    /**
     * @param e The event to send as chat.
     */
    public void chat(Event e);

    /**
     * Set the current game as started.
     */
    public void setStarted();

    /**
     * @param currentPlayer The current player.
     */
    public void currentPlayer(int currentPlayer);

    /**
     * End the game.
     * @param e The event to handle.
     */
    public void endGame(Event e);

}
