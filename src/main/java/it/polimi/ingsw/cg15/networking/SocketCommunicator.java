package it.polimi.ingsw.cg15.networking;


import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class SocketCommunicator implements Communicator {

	Socket socket;
	Scanner in;
	PrintWriter out;
	
	public SocketCommunicator(Socket s) {
		socket=s;
        try {
            in = new Scanner(socket.getInputStream());
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException ex) {
            throw new AssertionError("some weird configuration problem occured or pebkac and you haven't opened the socket yet", ex);
        }
	}

	@Override
	public void send(String msg){
		out.println(msg);
		out.flush(); 
	}

    @Override
	public String receive(){
		return in.nextLine();
	}

    @Override
	public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("something wrong happened while closing a socket, who cares? I don't need it anymore: " + e.getMessage());
        } finally {
        		socket = null;
        }
    }

	
	
	
}
