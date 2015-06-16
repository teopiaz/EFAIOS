package it.polimi.ingsw.cg15.networking;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author MMP - LMR
 * The Socket Communicator class.
 */
public class SocketCommunicator implements Communicator {

    /**
     * The socket.
     */
    Socket socket;
    
    /**
     * The input scanner.
     */
    Scanner in;
    
    /**
     * The output writer.
     */
    PrintWriter out;

    /**
     * @param s The socket communicator selected.
     */
    public SocketCommunicator(Socket s) {
        socket = s;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new AssertionError("Socket not open", ex);
        }
    }

    /**
     * Send a message.
     * @param msg The message to send.
     */
    @Override
    public void send(String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Receive a message.
     */
    @Override
    public String receive() {
        return in.nextLine();
    }

    /**
     * Close the communication.
     * @see it.polimi.ingsw.cg15.networking.Communicator#close()
     */
    @Override
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("IO error on socket " + e.getMessage());
        } finally {
            socket = null;
        }
    }

}
