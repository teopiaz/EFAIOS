package it.polimi.ingsw.cg15;

import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.ClientRMI;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;
import it.polimi.ingsw.cg15.networking.pubsub.BrokerRMIInterface;
import it.polimi.ingsw.cg15.networking.pubsub.SubscriberRMI;
import it.polimi.ingsw.cg15.networking.pubsub.SubscriberRMIInterface;
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
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
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
    private static GameManagerRemote gmRemote = null;

    private static BrokerRMIInterface broker = null;

    /**
     * The token for the client.
     */
    private static ClientToken ctoken = null;

    private Map<String, String> args;

    /**
     * The Interface for the client view.
     */
    private static ViewClientInterface view;

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
     * Save slot for the token
     */
    private String slot = "1";

    /**
     * Socket type.
     */
    private static final int SOCKET = 1;

    /**
     * RMI type.
     */
    private static final int RMI = 2;

    /**
     * The position.
     */
    private static String position;

    /**
     * Type of communication.
     */
    private static int type;

    /**
     * Instance of network helper.
     */
    private static NetworkHelper instance = null;

    /**
     * The number of player.
     */
    private static int playerNumber;

    private static boolean isHuman = false;

    /**
     * The socket constructor.
     * 
     * @param ip The IP for socket communication.
     * @param port The port for socket communication.
     */
    private NetworkHelper(String ip, int port) {
        this.ip = ip;
        this.port = port;
        NetworkHelper.type = SOCKET;
    }

    /**
     * The RMI constructor.
     * 
     * @throws RemoteException
     * @throws AlreadyBoundException
     * @throws MalformedURLException
     * @throws NotBoundException
     */
    private NetworkHelper() throws RemoteException {
        ClientRMI clientRMI = new ClientRMI();
        gmRemote = clientRMI.connect();
        NetworkHelper.type = RMI;
    }

    /**
     * @return The current Network Helper instance.
     */
    public static NetworkHelper getInstance() {
        // RMI di default se non costruito precedentemente
        if (instance == null) {
            try {
                instance = new NetworkHelper();
            } catch (RemoteException e) {
                Logger.getLogger(NetworkHelper.class.getName())
                .log(Level.SEVERE,
                        "Remote Exception | Malformed URL Exception | Already Bound Exception, | Not Bound Exception",
                        e);
            }
        }
        return instance;
    }

    /**
     * Return the Socket Client.
     * 
     * @param ip The IP address.
     * @param port the port.
     * @return Client Socket.
     */
    public static NetworkHelper getClientSocket(String ip, int port) {
        instance = new NetworkHelper(ip, port);
        return instance;
    }

    /**
     * Return the RMI Client.
     * 
     * @return RMI Socket
     * @throws RemoteException
     * @throws MalformedURLException
     * @throws AlreadyBoundException
     * @throws NotBoundException
     */
    public static NetworkHelper getClientRMI() throws RemoteException {
        instance = new NetworkHelper();
        return instance;
    }

    /**
     * Request the current Client Token.
     */
    public void requestClientToken() {

        Event e = new Event(new ClientToken(null, null), "requesttoken");
        Event result = null;
        if (type == SOCKET) {
            result = send(e);
            NetworkHelper.ctoken = result.getToken();
        }
        if (type == RMI) {
            try {
                result = gmRemote.getClientToken();
                NetworkHelper.ctoken = result.getToken();
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                        "Remote Exception in requestClientToken ", e1);
            }
        }

    }

    /**
     * Send a generic message called "event".
     * 
     * @param e
     *            The event.
     * @return response The response event.
     */
    public synchronized Event send(Event e) {
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
        } catch (IOException e1) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                    "IO Exception in send", e1);
        }
        server = new SocketCommunicator(socket);
        String toSend = NetworkProxy.eventToJSON(e);
        server.send(toSend);
        String response = server.receive();
        server.close();
        return NetworkProxy.jsonToEvent(response);
    }

    /**
     * Create a new game.
     * 
     * @param gameName The name of the new game.
     * @param mapName The name of the game's map.
     */
    public void createGame(String gameName, String mapName) {
        if (ctoken == null) {
            requestClientToken();
        }
        args = new HashMap<String, String>();
        args.put("gamename", gameName);
        args.put("mapname", mapName);
        Event e = new Event(ctoken, "creategame", args);
        if (type == SOCKET) {
            send(e);
        }
        if (type == RMI) {
            try {
                gmRemote.createGame(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                        "Remote Exception in createGame", e1);
            }
        }
    }

    /**
     * Get the Game List.
     * 
     * @return a list with the games.
     */
    public Map<String, String> getGamesList() {
        if (ctoken == null) {
            requestClientToken();
        }
        args = new HashMap<String, String>();
        Event e = new Event(ctoken, "listgame", args);
        Event result = null;
        if (type == SOCKET) {
            result = send(e);
        }
        if (type == RMI) {
            try {
                result = gmRemote.getGameList(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                        "Remote Exception in getGamesList", e1);
            }
        }
        return result.getRetValues();
    }

    /**
     * Method for joining a game.
     * 
     * @param gameToken
     *            The game token.
     * @return A message with the response for the action perform.
     */
    public Event joinGame(String gameToken) {
        if (ctoken == null) {
            requestClientToken();
        }
        ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(ctoken, "joingame", args);
        Event response = null;
        if (type == SOCKET) {
            response = send(e);
            if (response.getRetValues().get("error") != null) {
                view.stampa("Error in join game");
            } else {
                subThread = new Thread(new SubscriberSocketThread(gameToken));
                subThread.start();
                saveTokenToFile(ctoken);
            }
        }
        if (type == RMI) {
            try {
                response = gmRemote.joinGame(e);
                if (response.getRetValues().get("error") != null) {
                    view.stampa("Error in join game");
                } else {
                    joinRMI(gameToken);
                }
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                        "Remote Exception in joinGame", e1);
            }
        }
        return response;
    }

    /**
     * Join the RMI communication.
     * @param gameToken The game token.
     */
    private void joinRMI(String gameToken) {

        SubscriberRMI subRMI = new SubscriberRMI();

        try {

            Registry registry = LocateRegistry.getRegistry(7700);

            broker = (BrokerRMIInterface) registry.lookup("Broker");
            SubscriberRMIInterface remoteSub = (SubscriberRMIInterface) UnicastRemoteObject
                    .exportObject(subRMI, 0);
            broker.subscribe(gameToken, remoteSub);

        } catch (NotBoundException | RemoteException e1) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                    "Remote Exception in joinRMI", e1);

        }

        saveTokenToFile(ctoken);
    }

    /**
     * @param gameToken
     *            The game token.
     * @return An event with the indormation about the game.
     */
    public Map<String, String> getGameInfo(String gameToken) {
        if (ctoken == null) {
            requestClientToken();
        }
        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "gameinfo", null);
        Event result = null;
        if (type == SOCKET) {
            result = send(e);
        }
        if (type == RMI) {
            try {
                result = gmRemote.getGameInfo(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                        "Remote Exception in getGameInfo", e1);
            }
        }
        return result.getRetValues();
    }

    /**
     * @return The map.
     */
    public String getMap() {
        if (ctoken == null) {
            requestClientToken();
        }
        String gameToken = getGameToken();
        ClientToken token = new ClientToken(ctoken.getPlayerToken(), gameToken);
        Event e = new Event(token, "getmap", null);
        Event result = eventHandler(e);
        return result.getRetValues().get("map");
    }

    /**
     * @param destination
     *            The destination for the action move.
     * @return an event with information about the action performed.
     */
    public Event move(String destination) {
        args = new HashMap<String, String>();
        args.put("destination", destination);
        Event e = new Event(ctoken, "move", args);
        return eventHandler(e);
    }

    /**
     * @param position
     *            The position in which make rumor.
     * @return an event with information about the action performed.
     */
    public Event askSector(String position) {
        args = new HashMap<String, String>();
        args.put("position", position);
        Event e = new Event(ctoken, "asksector", args);
        return eventHandler(e);
    }

    /**
     * @param card
     *            The card to use.
     * @return an event with information about the action performed.
     */
    public Event useCard(String card) {
        args = new HashMap<String, String>();
        args.put("itemcard", card);
        Event e = new Event(ctoken, "useitem", args);
        return eventHandler(e);
    }

    /**
     * @param target
     *            The sector in which check for other players.
     * @return an event with information about the action performed.
     */
    public Event spotlight(String target) {
        args = new HashMap<String, String>();
        args.put("itemcard", "spotlight");
        args.put("target", target);
        Event e = new Event(ctoken, "useitem", args);
        return eventHandler(e);
    }

    /**
     * Attack action.
     * 
     * @return an event with information about the action performed.
     */
    public Event attack() {
        Event e = new Event(ctoken, "attack", null);
        return eventHandler(e);
    }

    /**
     * End the current turn.
     * 
     * @return an event with information about the action performed.
     */
    public Event endTurn() {
        Event e = new Event(ctoken, "endturn", null);
        return eventHandler(e);
    }

    /**
     * @return an event with information about the player.
     */
    public Event getPlayerInfo() {
        if (ctoken == null) {
            requestClientToken();
        }
        Event e = new Event(ctoken, "getplayerinfo", null);
        Event result = eventHandler(e);
        if (result.getRetValues().containsKey("playernumber")) {
            NetworkHelper.playerNumber = Integer
                    .parseInt(result.getRetValues().get("playernumber"));
            if ("Human".equals(result.getRetValues().get("playertype"))) {
                isHuman = true;
            } else {
                isHuman = false;
            }
            position = result.getRetValues().get("currentposition");
        }
        return result;
    }

    /**
     * @return an event with information about the turn.
     */
    public int getTurnInfo() {
        if (ctoken == null) {
            requestClientToken();
        }
        Event e = new Event(ctoken, "getturninfo", null);
        Event result = eventHandler(e);
        return Integer.parseInt(result.getRetValues().get(Event.CURRENTPLAYER));
    }

    /**
     * @return a list with the cards available.
     */
    public List<String> getAvailableCardsList() {
        List<String> cardList = new ArrayList<String>();
        Event e = new Event(ctoken, "getcardlist", null);
        Event result = eventHandler(e);
        if (result.actionResult()) {
            for (String action : result.getRetValues().keySet()) {
                if ((!action.equals(Event.RETURN)) && (!"cardssize".equals(action))) {
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
        Event e = new Event(ctoken, "getactionlist", null);
        Event result = eventHandler(e);
        if (result.actionResult()) {
            for (String action : result.getRetValues().keySet()) {
                if (!Event.RETURN.equals(action)) {
                    actionList.add(action);
                }
            }

        }
        return actionList;
    }

    /**
     * Set the game token for the game.
     * 
     * @param gameToken
     *            The game Token.
     */
    public void setGameToken(String gameToken) {
        if (ctoken == null) {
            requestClientToken();
        }
        NetworkHelper.ctoken = new ClientToken(ctoken.getPlayerToken(), gameToken);
    }

    /**
     * @return The game token for the current game.
     */
    public String getGameToken() {
        return NetworkHelper.ctoken.getGameToken();
    }

    /**
     * @return the current player token.
     */
    public String getPlayerToken() {
        if (ctoken == null) {
            requestClientToken();
        }
        return ctoken.getPlayerToken();
    }

    /**
     * @param ctoken
     *            The client token.
     */
    public void setToken(ClientToken ctoken) {
        NetworkHelper.ctoken = ctoken;
    }

    /**
     * @param e
     *            The event received.
     * @return an event with information about the action performed.
     */
    private synchronized Event eventHandler(Event e) {

        Event result = null;
        if (type == SOCKET) {
            result = send(e);
        }
        if (type == RMI) {
            try {
                result = gmRemote.eventHandler(e);
            } catch (RemoteException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                        "Remote Exception in eventHandler", e1);
            }
        }

        return result;
    }

    /**
     * @param view
     *            The client view .
     */
    public void registerGui(ViewClientInterface view) {
        NetworkHelper.view = view;
    }

    /**
     * The method for updating the list of games.
     * 
     * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
     */
    @Override
    public void update(Observable arg0, Object arg1) {
        Event e = (Event) arg1;
        if ("log".equals(e.getCommand())) {
            view.log(e);
        }
        if ("pub".equals(e.getCommand()) && e.getRetValues().containsKey("isstarted")) {
            view.setStarted();
        }
        if ("pub".equals(e.getCommand()) && e.getRetValues().containsKey(Event.CURRENTPLAYER)) {
            view.currentPlayer(Integer.parseInt(e.getRetValues().get(Event.CURRENTPLAYER)));
        }
        if ("chat".equals(e.getCommand())) {
            view.chat(e);
        }
        if ("endgame".equals(e.getCommand())) {
            view.endGame(e);
        }
    }

    /**
     * Method for send chat messages.
     * 
     * @param message
     *            The message to sent.
     */
    public void sendChat(String message) {
        if (ctoken == null) {
            requestClientToken();
        }
        String sanitizedMessage = message.replaceAll("[^a-zA-Z0-9\\s]", "");
        args = new HashMap<String, String>();
        args.put("message", sanitizedMessage);
        Event e = new Event(ctoken, "chat", args);
        eventHandler(e);
    }

    /**
     * @return The player number of the current player.
     */
    public int getPlayerNumber() {
        getPlayerInfo();
        return NetworkHelper.playerNumber;
    }

    /**
     * @return if it's my turn or not.
     */
    public boolean isMyTurn() {
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
     * 
     * @param clientToken
     *            The client token.
     */
    private void saveTokenToFile(ClientToken clientToken) {
        FileOutputStream outputStream = null;
        try {
            File file = new File("efaios_token"+slot+".txt");
            outputStream = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            String content = clientToken.getPlayerToken() + "," + clientToken.getGameToken();
            byte[] contentInBytes = content.getBytes();
            outputStream.write(contentInBytes);
            outputStream.flush();
            outputStream.close();
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.INFO, "Token "+clientToken+" salvato in slot "+slot);

        } catch (IOException e) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception in saveTokenToFile", e);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                Logger.getLogger(NetworkHelper.class.getName())
                .log(Level.SEVERE, "IO Exception in saveTokenToFile2", e);
            }
        }
    }

    /**
     * Restore (load) the client token from a file.
     * 
     * @return a Client token.
     */
    private boolean loadTokenFromFile() {

        File file = new File("efaios_token"+slot+".txt");
        FileInputStream inputStream = null;
        String gameToken = null;
        String playerToken = null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE,
                    "File Not Found",e1);
            return false;
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e1) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception in loadTokenFromFile", e1);
        }
        while (line != null) {
            String[] splitted = line.split(",");
            gameToken = splitted[1];
            playerToken = splitted[0];
            this.ctoken = new ClientToken(playerToken, gameToken);
            try {
                line = reader.readLine();
            } catch (IOException e1) {
                Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception in loadTokenFromFile read",
                        e1);
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "IO Exception in loadTokenFromFile close", e);
        }

        if(playerToken !=null && gameToken != null){ 
            this.ctoken = new ClientToken(playerToken, gameToken); 
            return true; 
        }

        return false;
    }

    /**
     * @return The type.
     */
    public int getType() {
        return type;
    }

    /**
     * @return The player information (human).
     */
    public boolean isHuman() {
        getPlayerInfo();
        return isHuman;
    }

    /**
     * @return The current player position.
     */
    public String getPlayerPosition() {
        getPlayerInfo();
        return position;
    }

    /**
     *  Load the token from previus saved file
     * @param string slot
     * @return a boolean for the succesfully 
     */
    public boolean loadToken(String string) {
        this.slot=string;
        if(loadTokenFromFile()){

            if (type == SOCKET) {
                subThread = new Thread(new SubscriberSocketThread(ctoken.getGameToken()));
                subThread.start();
                saveTokenToFile(ctoken);
            }
            if (type == RMI) {

                joinRMI(ctoken.getGameToken());

            }
            return true;
        }
        return false;
    }

    public boolean saveMapToServer(String mapName,String savedMap) {
        if (ctoken == null) {
            requestClientToken();
        }
        args = new HashMap<String, String>();
        args.put("map", savedMap);
        args.put("mapname", mapName);

        Event e = new Event(ctoken, "savemap", args);
        System.out.println("RICHIESTA "+e);
        Event result = eventHandler(e);
        System.out.println("RISPOSTA "+result);
        return result.actionResult();

    }

}
