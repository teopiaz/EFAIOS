package it.polimi.ingsw.cg15.networking.pubsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The broker socket thread.
 */
public class BrokerSocketThread extends Thread {

    /**
     * The new socket, to a specific subscriber, created by ServerSocket.
     */
    private Socket socket;

    /**
     * The output stream.
     */
    private PrintWriter out;

    /**
     * The input stream.
     */
    private InputStreamReader in;

    /**
     * A queue that contains the messages specific to each subscriber
     */
    private Queue<String> buffer;

    /**
     * A string representing the topic.
     */
    private String topic;

    /**
     * When you subscribe to an external client you create a new thread that will represent the specific 
     * connection to the specific client / subscriber. 
     * @param socket The new socket, to a specific subscriber, created by ServerSocket.
     */
    public BrokerSocketThread(Socket socket) {
        this.socket = socket;
        buffer = new ConcurrentLinkedQueue<String>();
        try{
            this.in = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(in);
            this.out = new PrintWriter(socket.getOutputStream());
            this.topic = br.readLine();
        }catch(IOException e){
            Logger.getLogger(BrokerSocketThread.class.getName()).log(Level.SEVERE, "IOException", e);
        }
    }

    /**
     * @return The topic.
     */
    public String getTopic(){
        return this.topic;
    }

    /**
     * This method contains the logic of communication from the publisher to the specific subscriber.
     */
    @Override
    public void run() {
        while(true){
            //Si prova ad estrarre un messaggio dalla coda...
            String msg = buffer.poll();
            //... se c'é lo si invia
            if(msg != null) 
                send(msg);
            else{
                //... altrimenti, per evitare cicli inutili di CPU
                //che possono portare ad utilizzarla inutilmente...
                try {
                    //... si aspetta fin quando la coda non conterrá qualcosa
                    //é necessario sincronizzarsi sull'oggetto monitorato, in modo tale
                    //che il thread corrente possieda il monitor sull'oggetto.
                    synchronized(buffer){
                        buffer.wait();
                    }
                } catch (InterruptedException e) {
                    Logger.getLogger(BrokerSocketThread.class.getName()).log(Level.SEVERE, "InterruptedException", e);
                }
            }
        }
    }

    /**
     * This method puts a message in the queue and notifies waiting threads (in this case to himself) the 
     * presence of a message. 
     * @param msg The message to be inserted.
     */
    public void dispatchMessage(String msg){
        buffer.add(msg);
        //é necessario sincronizzarsi sull'oggetto monitorato
        synchronized(buffer){
            buffer.notify();
        }
    }

    /**
     * This method sends the message to the subscriber via the network.
     * @param msg The message that must be sent.
     */
    private void send(String msg){
        out.println(msg);
        out.flush();
    }

    /**
     * Close the communications.
     */
    public void close(){
        try {
            socket.close();
        } catch (IOException e) {
            Logger.getLogger(BrokerSocketThread.class.getName()).log(Level.SEVERE, "IOException in BrokerSocketThread", e);

        } finally {
            out = null;
            socket = null;
        }
    }

}
