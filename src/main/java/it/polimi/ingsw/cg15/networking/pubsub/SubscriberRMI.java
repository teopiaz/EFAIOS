package it.polimi.ingsw.cg15.networking.pubsub;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.NetworkProxy;

import java.util.Observable;

public class SubscriberRMI extends Observable implements SubscriberRMIInterface {

	/**
	 * 
	 * @param name The name of the subscriber
	 */
	public SubscriberRMI(String name) {
		super();
		this.name = name;
		addObserver(NetworkHelper.getInstance());
		
	}

	private String name;
	
	
	/**
	 * @param msg is the message sent by the broker by invoking subscriber's remote interface
	 * the method simply prints the message received by the broker
	 */
	@Override
	public void dispatchMessage(String msg) {
		System.out.println("Subscriber-"+name+" received message: "+msg);
		 setChanged();
         notifyObservers(NetworkProxy.JSONToEvent(msg));
	}

}
