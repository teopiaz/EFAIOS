package it.polimi.ingsw.cg15.networking.pubsub;

/**
 * @author MMP - LMR
 * The broker class.
 */
public class Broker {
    
    /**
     *  A new instance of the broker.
     */
    private static Broker instance = new Broker();
    
    /**
     * The Broker Socket.
     */
    private static BrokerSocket brokerSocket;

    /**
     * Create a new broker.
     */
    private Broker(){
     brokerSocket = BrokerSocket.getInstance();
     Thread brokerSocketThread = new Thread(brokerSocket);
     brokerSocketThread.start();
    }

    /**
     * @return The current instance of the broker.
     */
    public static Broker getInstance(){
        return instance;
    }
    
    /**
     * Publish a message into a topic.
     * @param topic The topic in which publish the message.
     * @param msg The message to publish.
     */
    public static void publish(String topic,String msg){
        BrokerSocket.publish(topic, msg);
    }

    /**
     * @param gameToken The game token of the subscriber to remove.
     */
    public static void removeAllSubscriber(String gameToken) {
      BrokerSocket.removeAllSubscriber(gameToken);
    }

    /**
     * Stop the server.
     */
    public void stopServer() {
       brokerSocket.stopServer();
    }

    /**
     * Start the server.
     */
    public void startServer() {
        brokerSocket.startServer();
    }

}
