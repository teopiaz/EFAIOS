package it.polimi.ingsw.cg15.networking.pubsub;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.NetworkProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The socket subscriber.
 */
public class SubscriberSocketThread extends Observable implements Runnable {

    /**
     * The socket subscriber.
     */
    private Socket subSocket;

    /**
     * The input buffer reader.
     */
    private BufferedReader in;

    /**
     * The output buffer reader.
     */
    private PrintWriter out;

    /**
     * The IP address.
     */
    private static final String address = "localhost";

    /**
     * The port for communication.
     */
    private static final int port = 7331;

    /**
     * @param topic The topic to subscribe to.
     */
    public SubscriberSocketThread(String topic) {
        try {
            subscribe(topic);
            addObserver(NetworkHelper.getInstance());
        } catch (IOException e) {
            Logger.getLogger(SubscriberSocketThread.class.getName()).log(Level.SEVERE, "IO Exception", e);
        }
    }

    /**
     * After the subscription, this method listens for messages from the publisher.
     */
    @Override
    public void run() {
        while (true) {
            String msg = receive();
            handleMessage(msg);
            try {
                Thread.sleep(5); // aspetta 5ms per ridurre i cicli di clock nel caso in cui il pub vada in crash.
            } catch (InterruptedException e) {
                Logger.getLogger(SubscriberSocketThread.class.getName()).log(Level.SEVERE, "InterruptedException", e);
            }
        }
    }

    /**
     * @param msg The message to be managed.
     */
    private void handleMessage(String msg) {
        if (msg != null) {
            setChanged();
            notifyObservers(NetworkProxy.JSONToEvent(msg));
        }
    }

    /**
     * Method which receives any text messages from the publisher
     * @return A message.
     */
    private String receive() {
        String msg = null;
        try {
            msg = in.readLine();
        } catch (IOException e) {
            Logger.getLogger(SubscriberSocketThread.class.getName()).log(Level.SEVERE, "IOException", e);
        }
        return msg;
    }

    /**
     * Subscribes to the only topic. Create the socket to the subscriber and opens a stream input to receive messages of the publisher.
     * @param topic The topic to manage.
     * @throws UnknownHostException
     * @throws IOException
     */
    private void subscribe(String topic) throws UnknownHostException, IOException {
        subSocket = new Socket(address, port);
        in = new BufferedReader(new InputStreamReader(subSocket.getInputStream()));
        out = new PrintWriter(subSocket.getOutputStream());
        send(topic);
    }

    /**
     * @param msg The message to send.
     */
    private void send(String msg) {
        out.println(msg);
        out.flush();
    }

    /**
     * Close the communications.
     */
    public void close() {
        try {
            subSocket.close();
        } catch (Exception e) {
        } finally {
            in = null;
            subSocket = null;
        }
    }
}
