package it.polimi.ingsw.cg15.networking.pubsub;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Broker extends Thread{
    private final int portNumber = 7331;
    private boolean listening = true;
    //private ArrayList<BrokerThread> subscribers = new ArrayList<BrokerThread>();
    private Map<String,List<BrokerThread>> topicMap =  new HashMap<String,List<BrokerThread>>();
    private static Broker instance = new Broker();

    private Broker(){

    }

    public static Broker getInstance(){
        return instance;
    }
    
    public static void main(String[] args) {
        System.out.println("Starting the Broker Service");
        Broker broker = Broker.getInstance();
        broker.start();

       // System.out.println("Write something to publish on topic "+broker.topic+": ");
        Scanner stdin = new Scanner(System.in);
        try {
            while (true) {
                String topic = stdin.nextLine();
                
                String inputLine = stdin.nextLine();
                
                broker.publish(topic,inputLine);
            }
        }catch(NoSuchElementException e) {}

    }

    /**
     * 1)
     * Vogliamo creare un servizio Broker che rimanga in ascolto per eventuali sottoscrizioni.
     * Quindi creaiamo una ServerSocket e, per accettare sottoscrizioni multiple, creiamo un loop.
     * 	Quindi ci mettiamo in attesa che un client esterno si sottoscriva (brokerSocket.accept, 
     * 	The method blocks until a connection is made. A new Socket is created); 
     * 	quando ció avviene viene creato un nuovo thread, brokerThread, che rappresenterá la specifica connessione
     *  allo specifico client/subscriber. 
     *  Il brokerThread viene avviato e salvato nella lista che raggruppa i subscribers.
     *  Quindi la ServerSocket torna in ascolto di altre connessioni.
     */
    @Override
    public void run() {
        try(ServerSocket brokerSocket = new ServerSocket(portNumber)){
            while(listening){
                BrokerThread brokerThread = new BrokerThread(brokerSocket.accept());
                String topic = brokerThread.getTopic();
                addToMap(topic,brokerThread);
                brokerThread.start();
                System.out.println("Adding new subscriber");
            }
        }catch(IOException e){
            System.err.println("Cannot listen on port: "+portNumber);
            System.exit(-1);
        }
    }


    private void addToMap(String topic, BrokerThread subscriber) {

        if(!topicMap.containsKey(topic)){
            List<BrokerThread> subThreads = new ArrayList<BrokerThread>();
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
    private void publish(String topic,String msg){

        System.out.println(topicMap.containsKey(topic));
        for (Entry<String,List<BrokerThread>> str : topicMap.entrySet()) {
            System.out.println(str.getKey().equals("topic1"));
        }
        if(topicMap.containsKey(topic)){
            List<BrokerThread> subscribers = topicMap.get(topic);
            if(!subscribers.isEmpty()){
                System.out.println("Publishing message");
                for (BrokerThread sub : subscribers) {
                    sub.dispatchMessage(msg);
                }
            }
            else{
                System.err.println("No subscribers!!");

            }
        }

    }
}
