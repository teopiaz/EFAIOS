package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SessionTokenGenerator;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author  MMP - LMR
 *  Class that contains methods to start a game, join a game already created, create a game.
 */
public class GameManager implements GameManagerRemote {

    /**
     * The token of the player.
     */
    private ClientToken token;

    /**
     * The unique reference to the Game Manager.
     */
    private static GameManager singletonInstance = new GameManager();

    /**
     * A list of Game Box.
     */
    private Map<String,GameBox> gameBoxList = new HashMap<String,GameBox>();

    private boolean gameTimer=true;;


    /**
     * The constructor.
     */
    private GameManager(){
    }

    /**
     * @return the instance of the Game Manager.
     */
    public static GameManager getInstance(){
        return singletonInstance;
    }

    /**
     * Dispatch the event to the requested game instance
     * @param e Received event.
     */
    @Override
    public Event dispatchMessage(Event e) throws RemoteException{
        String gameToken = e.getToken().getGameToken();
        if(!gameBoxList.containsKey(gameToken)){
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, "Client" + e.getToken().getPlayerToken() + " tried to access an invalid game" );
            return new Event(e, "error game not avaible");
        }


        GameController controller = new GameController(gameBoxList.get(gameToken));
        return controller.eventHandler(e);
    }

    /**
     * Method that provides access to the current list of games.
     * @param e The event that I received and that I have to worry about managing.
     * @return A new event with the requested list. 
     */
    @Override
    public Event getGameList(Event e) throws RemoteException{
        Map<String,String> result = new HashMap<String, String>();
        for (Entry<String,GameBox> game : gameBoxList.entrySet()) {
            GameBox g = game.getValue();
            result.put(g.getGameState().getName(), g.getGameToken());
        }
        Event event=new Event(e,result);
        return event;
    }

    /** 
     * Method that returns various information about the game. The name and number of the players, the name of the map.
     * @param e The event that I received and that I have to worry about managing.
     * @return event The event with the info.
     */
    @Override
    public Event getGameInfo(Event e) throws RemoteException{
        Event event = e;
        ClientToken ctoken = e.getToken();
        String gameToken = ctoken.getGameToken();
        if(gameBoxList.containsKey(gameToken)){
            GameBox gb = gameBoxList.get(gameToken);
            Map<String,String> retValues = new HashMap<String, String>();
            retValues.put("name",gb.getGameState().getName());
            retValues.put("playercount",Integer.toString(gb.getPlayers().size()));
            retValues.put("mapName",gb.getGameState().getMapName());
            event = new Event(e,retValues);
        }
        return event;
    }

    /** 
     * Method that recognizes a list of a series of actions to create a game, list the game, join game already existing, start the game and return the games info.
     * @param e The event that I received and that I have to worry about managing.
     * @return event The event with the info.
     */
    @Override
    public Event eventHandler(Event e) throws RemoteException {
        Event response=null;

        if(gameBoxList.containsKey(e.getToken().getGameToken()) ){
            GameBox gameBox = gameBoxList.get(e.getToken().getGameToken());
            if( gameBox.toRemove()){
                removeGame(gameBox.getGameToken());
            }
        }

                if(e.getToken().getPlayerToken()!=null){
                    String command = e.getCommand().toLowerCase();
                    switch(command){
                    case "creategame": {
                        response =createGame(e);
                        break;
                    }
                    case "listgame": {
                        response=getGameList(e);
                        break;
                    }
                    case "joingame":{
                        response =joinGame(e);
                        break;
                    }
                    case "startgame":{
                        response =startGame(e);
                        break;
                    }
                    case "gameinfo":{
                        response =getGameInfo(e);
                        break;
                    }
                    default:{ 
                        response=dispatchMessage(e);
                        break;
                    }
                    }
                }
                else{
                    response = getClientToken();
                }
            
        return response;
    }

    /** 
     * Method that start the game. Checks that are more than 2 players and that the game is not already started. Make ready to start the first round.
     * @param e The event that I received and that I have to worry about managing.
     * @return event The event with the info.
     */
    @Override
    public Event startGame(Event e)  throws RemoteException{
        String gameToken = e.getToken().getGameToken();
        if(!gameBoxList.containsKey(gameToken)){
            return new Event(e,"error","invalid_game");
        }
        if(gameBoxList.get(gameToken).getPlayers().size() < 2){
            Logger.getLogger(GameManager.class.getName()).log(Level.INFO,"player size=" + gameBoxList.get(gameToken).getPlayers().size());
            return new Event(e,"error","wait for more players");
        }
        GameBox gameBox = gameBoxList.get(gameToken);
        if(gameBox.getGameState().isStarted()){
            return new Event(e, "game_already_started");
        }
        gameBox.getGameState().setStarted();

        //prepara tutto per iniziare il primo turno
        GameController controller = new GameController(gameBoxList.get(gameToken));
        controller.initGame(gameBox.getGameState().getMapName());
        ClientToken ctoken = new ClientToken("", gameToken); 
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("isstarted", "true");
        Event pub = new Event(ctoken, "pub", null, retValues);
        Broker.publish(gameToken,NetworkProxy.eventToJSON(pub));
        return new Event(e,"game_started");
    }

    /** 
     * Method that returns the Token of the Client.
     * @return event The event with the player token.
     */
    @Override
    public Event getClientToken()  throws RemoteException{
        ClientToken ctoken = new ClientToken(SessionTokenGenerator.nextSessionId(), null);
        return new Event(ctoken,ctoken.getPlayerToken());

    }

    /** 
     * Method that manages the process of adding a player to the current game. 
     * @param e The event that I received and that I have to worry about managing.
     * @return event The event with the response for the process of joining.
     */
    @Override
    public synchronized  Event joinGame(Event e)  throws RemoteException{
        token = e.getToken();
        Map<String,String> retValues = new HashMap<String, String>();
        String gameToken = token.getGameToken();
        if(!gameBoxList.containsKey(gameToken)){
            retValues.put(Event.ERROR,"invalid_game");
            retValues.put(Event.RETURN,Event.FALSE);
            return new Event(e, retValues);
        }
        if(gameBoxList.get(gameToken).getPlayers().size() >= 8){
            retValues.put(Event.ERROR,"game_full");
            retValues.put(Event.RETURN,Event.FALSE);
            return new Event(e, retValues);
        }
        GameBox gameBox = gameBoxList.get(gameToken);
        if(gameBox.getGameState().isStarted()){
            retValues.put(Event.ERROR,"game_already_started");
            retValues.put(Event.RETURN,Event.FALSE);
            return new Event(e, retValues);
        }
        gameBox.getPlayers().put(token.getPlayerToken(), gameBox.getGameState().addPlayer(new Player()));

        if(gameTimer == false && (gameBoxList.get(gameToken).getPlayers().size() >=2) ){
            Event ev = new Event(token,"startgame",null);
            return new Event(ev,"joined");
        }
        //thread per timeout 
        if((gameBoxList.get(gameToken).getPlayers().size() >=2) && gameTimer==true ){
            Runnable timerThread = new Runnable() {

                Timer timeout = new Timer();

                @Override
                public void run() {
                    timeout.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            Event e = new Event(token,"startgame",null);
                            try {
                                startGame(e);
                            } catch (RemoteException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
                        }
                    }, 1000*5);
                }
            };
            timerThread.run();

        }
        return new Event(e,"joined");
    }

    /**
     * Create a new instance of a game with his game token.
     * @param e an Event containing "gamename" and "mapname" as argument values
     * @return a new event with the game token
     */
    @Override
    public Event createGame(Event e) throws RemoteException{
        //creo un token della partita
        String gameToken = SessionTokenGenerator.nextSessionId();
        //creo un istanza del modello
        GameState gameState = GameInstance.getInstance().addGameInstance();
        gameState.setName(e.getArgs().get("gamename"));
        String mapName = e.getArgs().get("mapname");
        gameState.setMapName(mapName);
        Map<String,Player> players = new HashMap<String, Player>();
        GameBox gameBox = new GameBox(gameState,gameToken,players);   
        gameBoxList.put(gameToken, gameBox);
        Map<String,String> result = new HashMap<String, String>();
        result.put("gameToken", gameToken);
        Event event = new Event(e,result);
        return event;
    }

   

    /**
     * Method that returns a list of Game Box.
     * @return a list with the Game Box.
     */
    public Map<String, GameBox> getGameBoxList() {
        return gameBoxList;
    }

    /**
     * method that eliminates a match
     * @param gameToken The Game Token for the match to delete.
     */
    public void removeGame(String gameToken) {
        GameState gs = gameBoxList.get(gameToken).getGameState();
        GameInstance.getInstance().removeGameInstace(gs);
        Broker.removeAllSubscriber(gameToken);
        gameBoxList.remove(gameToken);
    }

    
    /**
     * enable or disable the timeout for game start
     * @param true or false
     */
     public void setTimer(boolean value){
     this.gameTimer=value;
     }
}
