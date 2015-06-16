package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.ClientRMI;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;
import it.polimi.ingsw.cg15.networking.pubsub.SubscriberSocketThread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.Socket;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The network helper. Contains method for socket and RMI communication.
 */
public class NetworkHelper implements Observer {

    /**
     * The socket communicator.
     */
    private SocketCommunicator server;
    
    /**
     * The Remote Game Manager.
     */
    private GameManagerRemote gmRemote=null;
    
    /**
     * The token for the client.
     */
    private static ClientToken ctoken=null;
    
    /**
     * TODO cosa sono io?
     */
    private Map<String,String> args;
    
    /**
     * The Interface for the client view.
     */
    private ViewClientInterface view;

    /**
     * A thread.
     */
    private Thread subThread;

    /**
     * The IP for communication.
     */
    private String ip;
    
    /**
     * The port.
     */
    private int port;
    
    /**
     * Socket type.
     */
    private final int SOCKET = 1;
    
    /**
     * RMI type.
     */
    private final int RMI = 2;
    
    /**
     * Type of communication.
     */
    private int type;
    
    /**
     * Instance of network helper.
     */
    private static NetworkHelper instance = null;
    
    /**
     * The number of player.
     */
    private static int playerNumber;;

    /**
     * The socket constructor.
     * @param ip The IP for socket communication.
     * @param port The port for socket communication.
     */
    private NetworkHelper(String ip, int port){
        this.ip = ip;
        this.port = port;
        this.type=SOCKET;
    }

    /**
     * The RMI constructor.
     * @throws RemoteException
     * @throws AlreadyBoundException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    private NetworkHelper() throws RemoteException, AlreadyBoundException,MalformedURLException, NotBoundException{
        ClientRMI clientRMI = new ClientRMI();
        gmRemote = clientRMI.connect();
        this.type=RMI;
    }

    /**
     * @return The current Network Helper instance.
     */
    public static NetworkHelper getInstance(){
        //RMI di default se non costruito precedentemente
        if(instance==null){
            try {
                instance = new NetworkHelper();
            } catch (RemoteException | MalformedURLException | AlreadyBoundException
                    | NotBoundException e) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception | Malformed URL Exception | Already Bound Exception, | Not Bound Exception", e);
                e.printStackTrace();
            }
        }
        return instance;
    }

    /**
     * Return the Socket Client.
     * @param ip The IP address.
     * @param port the port.
     * @return Client Socket.
     */
    public static NetworkHelper getClientSocket(String ip, int port) {
        instance = new NetworkHelper(ip,port);
        return instance;
    }
    
    /**
     * Return the RMI Client. 
     * @return RMI Socket
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws AlreadyBoundException
     * @throws NotBoundException
     */
    public static NetworkHelper getClientRMI() throws RemoteException, MalformedURLException, AlreadyBoundException, NotBoundException{
        instance =new NetworkHelper();
        return instance;
    }

    /**
     * Request the current Client Token.
     */
    public void requestClientToken() {
        //if(!loadTokenFromFile()){
        Event e = new Event(new ClientToken(null, null), "requesttoken");
        Event result = null;
        if(type==SOCKET){
            result = send(e);
            NetworkHelper.ctoken= result.getToken();
        }
        if(type==RMI){
            try {
                result = gmRemote.getClientToken();
                NetworkHelper.ctoken = result.getToken();
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception", e1);
            } 
        }
        //  view.stampa("TOKEN: "+result.getToken().getPlayerToken());
    }

    /**
     * Send a generic message called "event".
     * @param e The event.
     * @return response The response event.
     */
    public synchronized Event send(Event e){
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
        } catch (IOException e1) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception", e);
        }
        server = new SocketCommunicator(socket);
        String toSend = NetworkProxy.eventToJSON(e);
        server.send(toSend);
        String response = server.receive();
        server.close();
        return NetworkProxy.JSONToEvent(response);
    }

    /**
     * Create a new game.
     * @param gameName The name of the new game.
     * @param mapName The name of the game's map.
     */
    public void createGame(String gameName, String mapName) {
        //TODO: gestione errori
        if(ctoken==null){
            requestClientToken();
        }
        args=new HashMap<String, String>();
        args.put("gamename", gameName);
        args.put("mapname", mapName);
        Event e = new Event(ctoken,"creategame",args);
        if(type==SOCKET){
            Event result = send(e);
        }
        if(type==RMI){
            try {
                Event result = gmRemote.createGame(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception", e1);
            }
        }
        System.out.println("Game Created: "+ gameName);
    }

    /**
     * Get the Game List.
     * @return a list with the games.
     */
    public Map<String, String> getGamesList() {
        if(ctoken==null){
            requestClientToken();
        }
        args=new HashMap<String, String>();
        Event e = new Event(ctoken,"listgame",args);
        Event result = null;
        if(type==SOCKET){
            result = send(e);
        }
        if(type==RMI){
            try {
                result = gmRemote.getGameList(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception", e1);
            }
        }
        // loadTokenFromFile();
        return result.getRetValues();
    }
    
    /**
     * Method for joining a game.
     * @param gameToken The game token.
     * @return A message with the response for the action perform.
     */
    public Event joinGame(String gameToken) {
        if(ctoken==null){
            requestClientToken();
        }
        ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(ctoken,"joingame",args);
        Event response = null;
        if(type==SOCKET){
            response = send(e);
            if(response.getRetValues().get("error")!=null){
                System.out.println("ERRORE: " +response.getRetValues().get("error"));
            }
            else{
                subThread =  new Thread(new SubscriberSocketThread(gameToken));
                subThread.start();
                System.out.println(response.getRetValues().get("return"));
                saveTokenToFile(ctoken);
            }
        }
        if(type==RMI){
            try {
                response = gmRemote.joinGame(e);
                if(response.getRetValues().get("error")!=null){
                    System.out.println("ERRORE: " +response.getRetValues().get("error"));
                }
                else{
                    //   setGameToken(gameToken);
                    //TODO SUBSCRIBER RMI
                    subThread =  new Thread(new SubscriberSocketThread(gameToken));
                    subThread.start();
                    System.out.println(response.getRetValues().get("return"));
                    saveTokenToFile(ctoken);
                }
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception", e);
            }
        }
        return response;
    }

    /**
     * @param gameToken The game token.
     * @return An event with the indormation about the game.
     */
    public Map<String, String> getGameInfo(String gameToken) {
        if(ctoken==null){
            requestClientToken();
        }
        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "gameinfo", null);
        Event result=null;   
        if(type==SOCKET){
            result = send(e);
        }
        if(type==RMI){
            try {
                result = gmRemote.getGameInfo(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception", e);
            }
        }
        return result.getRetValues();
    }  

    /**
     * @return The map.
     */
    public String getMap() {
        if(ctoken==null){
            requestClientToken();
        }
        String gameToken = getGameToken();
        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "getmap", null);
        Event result = eventHandler(e);  
        return result.getRetValues().get("map");
    }

    /**
     * @param destination The destination for the action move.
     * @return an event with information about the action performed.
     */
    public Event move(String destination) {
        args = new HashMap<String,String>();
        args.put("destination", destination);
        Event e = new Event(ctoken,"move",args);
        return eventHandler(e);  
    }

    /**
     * @param position The position in which make rumor.
     * @return an event with information about the action performed.
     */
    public Event askSector(String position) {
        args = new HashMap<String,String>();
        args.put("position", position);
        Event e = new Event(ctoken,"asksector",args);
        return eventHandler(e);  
    }

    /**
     * @param card The card to use.
     * @return an event with information about the action performed.
     */
    public Event useCard(String card) {
        args = new HashMap<String, String>();
        args.put("itemcard", card);
        Event e = new Event(ctoken,"useitem",args);
        return eventHandler(e);  
    }

    /**
     * @param target The sector in which check for other players.
     * @return an event with information about the action performed.
     */
    public Event spotlight(String target) {
        args = new HashMap<String, String>();
        args.put("itemcard", "spotlight");
        args.put("target", target); 
        Event e = new Event(ctoken,"useitem",args);
        return eventHandler(e);
    }

    /**
     * Attack action.
     * @return an event with information about the action performed.
     */
    public Event  attack() {
        Event e = new Event(ctoken,"attack",null);
        return eventHandler(e);
    }
    
    /**
     * End the current turn.
     * @return an event with information about the action performed.
     */
    public Event endTurn() {
        Event e = new Event(ctoken,"endturn",null);
        return eventHandler(e);
    }

    /**
     * @return an event with information about the player.
     */
    public Event getPlayerInfo() {        
        if(ctoken==null){
            requestClientToken();
        }
        Event e = new Event(ctoken,"getplayerinfo",null);
        Event result = eventHandler(e);  
        if(result.getRetValues().containsKey("playernumber")){
            NetworkHelper.playerNumber=Integer.parseInt(result.getRetValues().get("playernumber"));
        }
        return result;
    }

    /**
     * @return an event with information about the turn.
     */
    public int getTurnInfo() {
        if(ctoken==null){
            requestClientToken();
        }
        Event e = new Event(ctoken,"getturninfo",null);
        Event result = eventHandler(e);  
        System.err.println(result);
        return Integer.parseInt( result.getRetValues().get("currentplayer"));
    }

    /**
     * @return a list with the cards available.
     */
    public List<String> getAvailableCardsList() {
        List<String> cardList = new ArrayList<String>();
        Event e = new Event(ctoken,"getcardlist",null);
        Event result = eventHandler(e);  
        if(result.actionResult()){
            String size =result.getRetValues().get("cardssize");
            for (String action : result.getRetValues().keySet()) {
                if((!action.equals("return")) && (!action.equals("cardssize")) ){
                    cardList.add(action);  
                }
            }
        }
        return cardList;
    }

    /**
     * @return a list with the available actions for the player.
     */
    public List<String> getAvailableActionsList() {
        List<String> actionList = new ArrayList<String>();
        Event e = new Event(ctoken,"getactionlist",null);
        Event result = eventHandler(e);  
        if(result.actionResult()){
            for (String action : result.getRetValues().keySet()) {
                if(!action.equals("return")){
                    actionList.add(action);  
                }
            }

        }
        return actionList;
    }

    /**
     * Set the game token for the game.
     * @param gameToken The game Token.
     */
    public void setGameToken(String gameToken){
        if(ctoken==null){
            requestClientToken();
        }
        NetworkHelper.ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);
    }

    /**
     * @return The game token for the current game.
     */
    public String getGameToken(){
        return NetworkHelper.ctoken.getGameToken();
    }

    /**
     * @return the current player token.
     */
    public String getPlayerToken() {
        if(ctoken==null){
            requestClientToken();
        }
        return ctoken.getPlayerToken();
    }

    /**
     * @param ctoken The client token.
     */
    public void setToken(ClientToken ctoken) {
        NetworkHelper.ctoken=ctoken;
    }

    /**
     * @param e The event received.
     * @return an event with information about the action performed.
     */
    private Event eventHandler(Event e){
        Event result =null;
        if(type==SOCKET){
            result = send(e);
        }
        if(type==RMI){
            try {
                result = gmRemote.eventHandler(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Remote Exception", e);
            }
        }
        return result;
    }

    /**
     * @param view The client view .
     */
    public void registerGui(ViewClientInterface view){
        this.view = view;
    }

    /** 
     * The method for updating the list of games.
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        Event e = (Event)arg1;
        if(e.getCommand().equals("log")){
            view.log(e);
        }
        if(e.getCommand().equals("pub") && e.getRetValues().containsKey("isstarted")){
            view.setStarted();
        }
        if(e.getCommand().equals("pub") && e.getRetValues().containsKey("currentplayer")){
            view.currentPlayer(Integer.parseInt(e.getRetValues().get("currentplayer")));
        }
        if(e.getCommand().equals("chat")){
            view.chat(e);
        }
        view.stampa(e.toString());
    }

    /**
     * Method for send chat messages.
     * @param message The message to sent.
     */
    public void sendChat(String message) {
        if(ctoken==null){
            requestClientToken();
        }
        String sanitizedMessage = message.replaceAll("[^a-zA-Z0-9\\s]", "");
        args = new HashMap<String,String>();
        args.put("message", sanitizedMessage);
        Event e = new Event(ctoken,"chat",args);
        Event result = eventHandler(e);  
    }

    /**
     * @return The player number of the current player.
     */
    public int getPlayerNumber(){
        getPlayerInfo();
        return NetworkHelper.playerNumber;
    }

    /**
     * @return if it's my turn or not.
     */
    public boolean isMyTurn(){
        getPlayerInfo();        
        return NetworkHelper.playerNumber == getTurnInfo();
    }

    /**
     * @return The position of the current player.
     */
    public String getCurrentPosition() {
        Event response = getPlayerInfo();
        return response.getRetValues().get("currentposition");
    }

    /**
     * Save a list of client token into a file.
     * @param clientToken The client token.
     */
    private void saveTokenToFile(ClientToken clientToken) {
        FileOutputStream outputStream = null;
        try {
            File file = new File("efaios_token.txt");
            outputStream = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            String content = clientToken.getPlayerToken()+","+clientToken.getGameToken();
            byte[] contentInBytes = content.getBytes();
            outputStream.write(contentInBytes);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception", e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception", e);
            }
        }
    }

    /**
     * Restore (load) the client token from a file.
     * @return a Client token.
     */
    private boolean loadTokenFromFile(){
        File file = new File("efaios_token.txt");
        FileInputStream inputStream = null;
        String gameToken=null;
        String playerToken=null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
          Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "File Not Found Exception", e1);
            return false;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception", e1);
        }
        while (line != null) {
            String[] splitted = line.split(",");
            gameToken =splitted[1];
            playerToken = splitted[0];
            System.out.println("LOADED:\n"+"PLAYER TOKEN: "+playerToken+"\nGAME TOKEN: "+gameToken);
            // this.ctoken = new ClientToken(playerToken, gameToken);
            try {
                line = reader.readLine();
            } catch (IOException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception", e1);
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
        Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception", e);
        }
        //TODO: sistemare il resume del gioco (stesso file)
        /*
        if(playerToken !=null && gameToken != null){
            this.ctoken = new ClientToken(playerToken, gameToken);
            return true;
        }
         */
        return false;
    }

}
