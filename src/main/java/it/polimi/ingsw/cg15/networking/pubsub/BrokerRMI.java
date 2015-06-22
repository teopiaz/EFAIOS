package it.polimi.ingsw.cg15.networking.pubsub;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BrokerRMI implements BrokerRMIInterface {

    private static Map<String, List<SubscriberRMIInterface>> topicMap = new HashMap<String, List<SubscriberRMIInterface>>();

    public BrokerRMI() throws RemoteException {

    }

    /**
     * 
     * @param msg message to be published to all the subscribers This is not a
     *            remote method, however it calls the remote method
     *            dispatchMessage for each Subscriber.
     * @param msg2
     */
    public static void publish(String topic, String msg) {

        if (topicMap.containsKey(topic)) {
            List<SubscriberRMIInterface> subscribers = topicMap.get(topic);

            if (!subscribers.isEmpty()) {
                for (SubscriberRMIInterface sub : subscribers) {
                    try {
                        sub.dispatchMessage(msg);
                    } catch (RemoteException e) {
                        Logger.getLogger(BrokerSocketThread.class.getName()).log(Level.SEVERE,
                                "RemoteException", e);
                    }
                }
            }

        }

    }

    /**
     * @param r   is the Subcriber's remote interface that the broker can use to
     *            publish messages The method updates the list of subscriber
     *            interfaces that are subscribed to the broker
     */
    @Override
    public void subscribe(String topic, SubscriberRMIInterface r) {
        addToMap(topic, r);
    }

    private void addToMap(String topic, SubscriberRMIInterface subscriber) {
        if (!topicMap.containsKey(topic)) {
            List<SubscriberRMIInterface> subThreads = new ArrayList<SubscriberRMIInterface>();
            subThreads.add(subscriber);
            topicMap.put(topic, subThreads);
        } else {
            topicMap.get(topic).add(subscriber);
        }
    }
}
