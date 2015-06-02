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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author matteo
 *
 */
public class GameManager implements GameManagerRemote {

    private ClientToken token;
    private GameInstance gameInstance;
    private static GameManager singletonInstance = new GameManager();


    private Map<String,GameBox> gameBoxList = new HashMap<String,GameBox>();

    //ExecutorService executor = Executors.newCachedThreadPool();





    private GameManager(){
        gameInstance = GameInstance.getInstance();
    }

    public static GameManager getInstance(){
        return singletonInstance;
    }

    /**
     * Dispatch the event to the requested game instance
     * @param e received event
     */
    public Event dispatchMessage(Event e) throws RemoteException{
        String gameToken = e.getToken().getGameToken();
        if(!gameBoxList.containsKey(gameToken)){
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, "Client" + e.getToken().getPlayerToken() + " tried to access an invalid game" );

            return new Event(e, "error game not avaible");
        }

        try {
            gameBoxList.get(gameToken).getQueue().put(e);
        } catch (InterruptedException e1) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE,"InterruptedException on BlockingQueue",e1 );
        }
        GameController controller = new GameController(gameBoxList.get(gameToken));
        return controller.eventHandler(e);
        //executor.execute(controller);
    }

    /**
     * 
     * @param e received event
     * @return a new eventh with the requested list
     */
    public Event getGameList(Event e) throws RemoteException{

        Map<String,String> result = new HashMap<String, String>();
        for (Entry<String,GameBox> game : gameBoxList.entrySet()) {
            GameBox g = game.getValue();
            result.put(g.getGameState().getName(), g.getGameToken());
        }

        Event event=new Event(e,result);
        return event;
    }

    public Event getGameInfo(Event e) throws RemoteException{

        Event event = e;
        ClientToken ctoken = e.getToken();
        String gameToken = ctoken.getGameToken();
        System.out.println("Test esiste"+gameBoxList.containsKey(gameToken));
        if(gameBoxList.containsKey(gameToken)){
            GameBox gb = gameBoxList.get(gameToken);
            System.out.println(gb);
            Map<String,String> retValues = new HashMap<String, String>();

            retValues.put("name",gb.getGameState().getName());
            retValues.put("playercount",Integer.toString(gb.getPlayers().size()));
            retValues.put("mapName",gb.getGameState().getMapName());
            event = new Event(e,retValues);
        }
        System.out.println("GAMEINFO: "+e);
        return event;

    }



    public Event eventHandler(Event e) throws RemoteException {
        Event response=null;

        if(e.getToken().getPlayerToken()!=null){
            String command = e.getCommand();
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

    public Event startGame(Event e)  throws RemoteException{
        ClientToken token = e.getToken();
        String gameToken = token.getGameToken();

        if(!gameBoxList.containsKey(gameToken)){
            return new Event(e,"error","invalid_game");
        }
        if(gameBoxList.get(gameToken).getPlayers().size() < 2){
            Logger.getLogger(GameManager.class.getName()).log(Level.INFO,"player size=" + gameBoxList.get(gameToken).getPlayers().size());
            return new Event(e,"error","wait for more players");
        }
        GameBox gameBox = gameBoxList.get(gameToken);
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

    public Event getClientToken()  throws RemoteException{
        ClientToken ctoken = new ClientToken(SessionTokenGenerator.nextSessionId(), null);
        return new Event(ctoken,ctoken.getPlayerToken());

    }

    public Event joinGame(Event e)  throws RemoteException{
        token = e.getToken();
        String gameToken = token.getGameToken();

        if(!gameBoxList.containsKey(gameToken)){

            return new Event(e,"error","invalid_game");
        }
        if(gameBoxList.get(gameToken).getPlayers().size() >= 8){


            return new Event(e,"error","game_full");
        }


        GameBox gameBox = gameBoxList.get(gameToken);
        if(gameBox.getGameState().isStarted()){
            return new Event(e,"error","game_already_started");

        }
        gameBox.getPlayers().put(token.getPlayerToken(), gameBox.getGameState().addPlayer(new Player()));



        //thread per timeout
        if(gameBoxList.get(gameToken).getPlayers().size() >=2){
            Runnable timerThread = new Runnable() {

                Timer timeout = new Timer();


                @Override
                public void run() {
                    System.out.println("lanciato il timer thread");
                    timeout.schedule(new TimerTask() {

                        @Override
                        public void run() {
                            System.out.println("timer finito");
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
     * Create a new instance of a game with his game token
     * @param e received event
     * @return a new event with the game token
     */
    public Event createGame(Event e) throws RemoteException{
        //creo la coda di eventi
        BlockingQueue<Event> queue = new ArrayBlockingQueue<Event>(10,true);
        //creo un token della partita
        String gameToken = SessionTokenGenerator.nextSessionId();

        //creo un istanza del modello
        GameState gameState = GameInstance.getInstance().addGameInstance();
        gameState.setName(e.getArgs().get("gamename"));
        String mapName = e.getArgs().get("mapname");
        gameState.setMapName(mapName);

        Map<String,Player> players = new HashMap<String, Player>();
        GameBox gameBox = new GameBox(gameState,queue,gameToken,players);   

        gameBoxList.put(gameToken, gameBox);





        Map<String,String> result = new HashMap<String, String>();
        result.put("gameToken", gameToken);
        Event event = new Event(e,result);
        return event;
    }

    public Event getField(Event e) throws RemoteException{

        String gameToken = e.getToken().getGameToken();
        GameBox gb = gameBoxList.get(gameToken);
        String printableMap = gb.getGameState().getField().getPrintableMap();

        Event event = new Event(e.getToken(), printableMap);
        return event;

    }










}
