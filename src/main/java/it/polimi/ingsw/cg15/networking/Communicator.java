package it.polimi.ingsw.cg15.networking;

/**
 * @author MMP - LMR
 * The interface for the communication.
 */
public interface Communicator {

    /**
     * @param msg The message.
     */
    void send(String msg);

    /**
     * @return The string received.
     */
    String receive();

    /**
     * Close the communication.
     */
    void close();

}
