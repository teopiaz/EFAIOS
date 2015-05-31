package it.polimi.ingsw.cg15.networking.pubsub;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class SubscriberThread extends Thread {
	private Socket subSocket;
	private BufferedReader in;
    private PrintWriter out;
	private final String address = "localhost";
	private final int port = 7331;
	private String topic;

	
	/**
	 * Non appena il thread viene instanziato, ci si sottoscrive al broker.
	 * NB. Non é stato implementato il concetto di topic; questo viene lasciato come 
	 * compito agli studenti.
	 */
	public SubscriberThread(String topic){
		try {
		    this.topic=topic;
			subscribe(topic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

    /**
	 * Dopo aver effettuato la sottoscrizione, questo metodo
	 * rimane in ascolto di messaggi da parte del publisher.
	 */
	@Override
	public void run() {
		while(true){
			receive();
			try {
				//aspetta 5ms per ridurre i cicli di clock
				//soprattutto nel caso in cui il publisher vada in crash
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * Metodo che riceve eventuali messaggi di testo dal publisher
	 * @return
	 */
	private String receive() {
		String msg = null;
		try {
			msg = in.readLine();
			if(msg!=null){
				System.out.println("Topic: "+"received message: "+msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return msg;
	}
	
	/**
	 * Effettua la sottoscrizione al solo ed unico topic, 
	 * i.e., crea la socket verso il subscriber e apre uno stream in ingresso per ricevere
	 * i messaggi del publisher.
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	private void subscribe(String topic) throws UnknownHostException, IOException{
		subSocket = new Socket(address, port);
	    in = new BufferedReader(
	        new InputStreamReader(subSocket.getInputStream()));
	    
	    out = new PrintWriter(subSocket.getOutputStream());
	    send(topic);
	    
	}
	   private void send(String msg){
	        out.println(msg);
	        out.flush();
	    }
	
	private void close(){
		try{
			subSocket.close();
		}catch(Exception e){
		} finally {
			in=null;
			subSocket=null;
			System.gc();
		}
		
	}
}
