package it.polimi.ingsw.cg15.networking.pubsub;


import it.polimi.ingsw.cg15.networking.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class BrokerSocket extends Thread implements Server{
    private static final int PORT = 7331;
    private static boolean listening = false;
    private static Map<String,List<BrokerSocketThread>> topicMap =  new HashMap<String,List<BrokerSocketThread>>();
    private static BrokerSocket instance = new BrokerSocket();
    private static Object lock = new Object();


    
    private BrokerSocket(){

    }

    public static BrokerSocket getInstance(){
        return instance;
    }
    
  
    
  

    /**
     * 1)
     * Vogliamo creare un servizio Broker che rimanga in ascolto per eventuali sottoscrizioni.
     * Quindi creaiamo una ServerSocket e, per accettare sottoscrizioni multiple, creiamo un loop.
     *  Quindi ci mettiamo in attesa che un client esterno si sottoscriva (brokerSocket.accept, 
     *  The method blocks until a connection is made. A new Socket is created); 
     *  quando ció avviene viene creato un nuovo thread, brokerThread, che rappresenterá la specifica connessione
     *  allo specifico client/subscriber. 
     *  Il brokerThread viene avviato e salvato nella lista che raggruppa i subscribers.
     *  Quindi la ServerSocket torna in ascolto di altre connessioni.
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
            System.err.println("Cannot listen on port: "+PORT);
        }
                }
            }
        }
    }


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
     * 2)
     * Questo metodo manda un messaggio a tutti i subscriber presenti nella lista.
     * Di fatto la publish si riduce ad iterare sulla lista subscriber ed a chiamare
     * un metodo interno a ciascun subscriber, il quale a sua volta gestirá l'invio del messaggio.
     * @param msg Il messaggio da mandare.
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

    @Override
    public void startServer() {
        listening = true;
    }

    @Override
    public void stopServer() {
        listening=false;
    }

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
