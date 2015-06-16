package it.polimi.ingsw.cg15.networking.pubsub;

import it.polimi.ingsw.cg15.networking.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The Broker Socket.
 */
public class BrokerSocket extends Thread implements Server{

    /**
     * The communication port.
     */
    private static final int PORT = 7331;

    /**
     * A variable indicating whether they are listening.
     */
    private static boolean listening = false;

    /**
     * A table with various topics.
     */
    private static Map<String,List<BrokerSocketThread>> topicMap =  new HashMap<String,List<BrokerSocketThread>>();

    /**
     * An instance of Broker Socket.
     */
    private static BrokerSocket instance = new BrokerSocket();

    /**
     * TODO non ho capito cosa serve
     */
    private static Object lock = new Object();

    /**
     * The constructor.
     */
    private BrokerSocket(){
    }

    /**
     * @return The instance of Broker Socket.
     */
    public static BrokerSocket getInstance(){
        return instance;
    }

    /**
     * We want to create a Service Broker that remains listening for any subscriptions.
     * So we create a ServerSocket and, to accept multiple subscriptions, create a loop. 
     * So we put waiting an external client subscribes. The method blocks until a connection is made. 
     * A new socket is created. When this happens it creates a new thread, brokerThread, which represent 
     * the specific connection to the specific client / subscriber. The brokerThread starts and saved in 
     * the list that groups together subscribers. Then the ServerSocket returns to listening for more connections.
     */
    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                if (listening) {
                    try(ServerSocket brokerSocket = new ServerSocket(PORT)){
                        while(listening){
                            BrokerSocketThread brokerThread = new BrokerSocketThread(brokerSocket.accept());
                            String topic = brokerThread.getTopic();
                            addToMap(topic,brokerThread);
                            brokerThread.start();
                            System.out.println("Adding new subscriber");
                        }
                    }catch(IOException e){
                        Logger.getLogger(BrokerSocket.class.getName()).log(Level.SEVERE, "Cannot listen on port 7331", e);
                    }
                }
            }
        }
    }

    /**
     * @param topic The topic to add into the current table.
     * @param subscriber The subscriber.
     */
    private void addToMap(String topic, BrokerSocketThread subscriber) {
        if(!topicMap.containsKey(topic)){
            List<BrokerSocketThread> subThreads = new ArrayList<BrokerSocketThread>();
            subThreads.add(subscriber);
            topicMap.put(topic, subThreads);
        }else{
            topicMap.get(topic).add(subscriber);
        }
    }

    /**
     * This method sends a message to all subscribers on the list. 
     * In fact, the publish is reduced to iterate on the subscriber list and call a method internal to each subscriber, 
     * which handle the transmission of the message.
     * @param topic The topic.
     * @param msg The message to send.
     */
    public static void publish(String topic,String msg){
        System.out.println(topicMap.containsKey(topic));
        for (Entry<String,List<BrokerSocketThread>> str : topicMap.entrySet()) {
            System.out.println("topic".equals(str.getKey().equals("topic1")));
        }
        if(topicMap.containsKey(topic)){
            List<BrokerSocketThread> subscribers = topicMap.get(topic);
            if(!subscribers.isEmpty()){
                System.out.println("Publishing message: TOPIC: "+topic+" MSG: "+msg);
                for (BrokerSocketThread sub : subscribers) {
                    sub.dispatchMessage(msg);
                }
            }
            else{
                System.err.println("No subscribers!!");
            }
        }
    }

    /**
     * Start the server.
     * @see it.polimi.ingsw.cg15.networking.Server#startServer()
     */
    @Override
    public void startServer() {
        listening = true;
    }

    /**
     * Stop the server.
     * @see it.polimi.ingsw.cg15.networking.Server#stopServer()
     */
    @Override
    public void stopServer() {
        listening=false;
    }

    /**
     * Remove all the subscribers.
     * @param gameToken The game token to remove.
     */
    public static void removeAllSubscriber(String gameToken) {
        if(topicMap.containsKey(gameToken)){
            List<BrokerSocketThread> list = topicMap.get(gameToken);    
            for (BrokerSocketThread brokerThread : list) {
                brokerThread.close();
            }
            topicMap.remove(gameToken);
        }
    }
    
}
