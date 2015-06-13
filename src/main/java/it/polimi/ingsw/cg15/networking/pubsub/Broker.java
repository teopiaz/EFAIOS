package it.polimi.ingsw.cg15.networking.pubsub;




public class Broker {
    private static Broker instance = new Broker();
    private static BrokerSocket brokerSocket;

    private Broker(){
        
     brokerSocket = BrokerSocket.getInstance();
     Thread brokerSocketThread = new Thread(brokerSocket);
     brokerSocketThread.start();

    }

    public static Broker getInstance(){
        return instance;
    }
    
  
    
  



    public static void publish(String topic,String msg){
        BrokerSocket.publish(topic, msg);
        
    }



    public static void removeAllSubscriber(String gameToken) {
      BrokerSocket.removeAllSubscriber(gameToken);
    }

    public void stopServer() {
       brokerSocket.stopServer();
        
    }

    public void startServer() {
        brokerSocket.startServer();
        
    }


}
