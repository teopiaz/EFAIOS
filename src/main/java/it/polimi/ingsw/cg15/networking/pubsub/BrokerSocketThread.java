package it.polimi.ingsw.cg15.networking.pubsub;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.ConcurrentLinkedQueue;

public class BrokerSocketThread extends Thread {
	/*La nuova socket, verso uno specifico subscriber, creata dalla ServerSocket*/
	private Socket socket;
	/* Abbiamo soltanto bisogno di recapitare il messaggio. 
	 * Il pattern non prevede la ricezione, da parte del publisher, di alcun messaggio (
	 * se non la richiesta di sottoscrizione che tuttavia viene catturata dalla accept nella ServerSocket)*/
	private PrintWriter out;
	
	private InputStreamReader in;

	//private BufferedReader in;
	
	//Una coda che contiene i messaggi, specifici per ciascun subscriber
	private ConcurrentLinkedQueue<String> buffer;
    private String topic;
	
	/**
	 * Quando un client esterno si sottoscrive viene creato un nuovo thread
	 * che rappresenterá la specifica connessione allo specifico client/subscriber. 
	 * @param socket La nuova socket, verso uno specifico subscriber, creata dalla ServerSocket
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
			e.printStackTrace();
		}
	}
	
	public String getTopic(){
	   return this.topic;
	}
	
	/**
	 * Questo metodo contiene, di fatto, la logica della comunicazione dal publisher
	 * allo specifico subscriber. 
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
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Questo metodo inserisce un messaggio nella coda
	 * e notifica ai thread in attesa (in questo caso a se stesso) la presenza di un messaggio.
	 * @param msg Il messaggio da inserire.
	 */
	public void dispatchMessage(String msg){
		buffer.add(msg);
		//é necessario sincronizzarsi sull'oggetto monitorato
		synchronized(buffer){
			buffer.notify();
		}
	}
	
	/**
	 * Questo metodo invia il messaggio al subscriber tramite la rete
	 * @param msg
	 */
	private void send(String msg){
		out.println(msg);
		out.flush();
	}
	
	public void close(){
		try {
			socket.close();
		} catch (IOException e) {
		} finally {
			out = null;
			socket = null;
		}
	}

}
