package it.polimi.ingsw.cg15.networking;

public interface Communicator {

    void send(String msg);

    String receive();

    void close();

}
