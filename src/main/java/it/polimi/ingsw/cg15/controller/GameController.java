package it.polimi.ingsw.cg15.controller;


import it.polimi.ingsw.cg15.action.Action;
import it.polimi.ingsw.cg15.action.Adrenaline;
import it.polimi.ingsw.cg15.action.AskSector;
import it.polimi.ingsw.cg15.action.Attack;
import it.polimi.ingsw.cg15.action.Move;
import it.polimi.ingsw.cg15.action.Sedatives;
import it.polimi.ingsw.cg15.action.Teleport;
import it.polimi.ingsw.cg15.controller.cards.CardController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.ActionEnum;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.TurnState;
import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;
import it.polimi.ingsw.cg15.utils.MapLoader;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author LMR - MMP
 */
public class GameController  {

    private static final int MAX_TURN_NUMBER = 39;
    private GameState gameState;
    private FieldController fieldController;
    private BlockingQueue<Event> queue;
    private Map<String, Player> players;
    private CardController cardController;
    private String gameToken;


    public GameController(GameBox gameBox) {
        this.gameState = gameBox.getGameState();
        this.queue = gameBox.getQueue();
        this.players = gameBox.getPlayers();
        this.fieldController = new FieldController(gameState);
        this.cardController = new CardController(gameState);
        this.gameToken = gameBox.getGameToken();
    }

    public void popolateField(){
        int numPlayer = players.size();
        int numHumans = numPlayer/2;
        int numAliens = numPlayer-numHumans;
        Field field = gameState.getField();

        Random ran = new Random();
        int playerNumber =1;
        for (Player player: players.values()) {
            player.setPlayerNumber(playerNumber);
            int randomNum = ran.nextInt(2);
            if(randomNum==0 && numHumans>0){
                player.setPosition(field.getHumanStartingPosition());
                field.getHumanStartingPosition().addPlayer(player);
                player.setPlayerType(PlayerType.HUMAN);
                numHumans--;
            }else{
                if(numAliens>0){
                    player.setPosition(field.getAlienStartingPosition());
                    field.getAlienStartingPosition().addPlayer(player);

                    player.setPlayerType(PlayerType.ALIEN);
                    numAliens--;
                }
                else{
                    player.setPosition(field.getHumanStartingPosition());
                    field.getHumanStartingPosition().addPlayer(player);
                    player.setPlayerType(PlayerType.HUMAN);
                    numHumans--;

                }
            }
            playerNumber++;
        }
    }


    public void initGame(String mapName){

        fieldController.loadMap(mapName);
        popolateField();
        PlayerController pc = new PlayerController(gameState);   
        gameState.newTurnState(pc.getPlayerById(PlayerController.FIRST_PLAYER));
        cardController.generateDecks();
        gameState.setInit();

        /*
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("currentplayer", Integer.toString(PlayerController.FIRST_PLAYER));
        String json = NetworkProxy.eventToJSON(new Event(new ClientToken("", gameToken),"pub",null,retValues));
        Broker.publish(gameToken, json);
         */
        nextTurn();
    }

    public void nextTurn(){
        int turnNumber = gameState.getTurnNumber();
        if(turnNumber<=MAX_TURN_NUMBER){
            TurnState turnState = null;

            PlayerController pc = new PlayerController(gameState);
            if(turnNumber==1){
                turnState =   gameState.newTurnState(pc.getPlayerById(PlayerController.FIRST_PLAYER));

            }else{
                turnState = gameState.newTurnState(pc.getPlayerById(pc.getNextPlayer().getPlayerNumber()));
            }

            gameState.setTurnNumber(turnNumber+1);

            if(gameState.getTurnState().getCurrentPlayer().getPlayerType()==PlayerType.HUMAN){
                turnState.getActionList().add(ActionEnum.MOVE);
                turnState.getActionList().add(ActionEnum.ENDTURN);
                turnState.getActionList().add(ActionEnum.ASKSECTOR); 

            }else{
                turnState.getActionList().add(ActionEnum.MOVE);
                turnState.getActionList().add(ActionEnum.ATTACK);
                turnState.getActionList().add(ActionEnum.ENDTURN); 
                turnState.getActionList().add(ActionEnum.ASKSECTOR); 

            }




            Map<String,String> retValues = new HashMap<String, String>();
            retValues.put("currentplayer", Integer.toString(gameState.getTurnState().getCurrentPlayer().getPlayerNumber()));

            String json = NetworkProxy.eventToJSON(new Event(new ClientToken("", gameToken),"pub",null,retValues));
            Broker.publish(gameToken, json);


        }
        else{
            System.out.println("ENDGAME");
        }

    }

    public String getGameToken(){
        return gameToken;
    }



    public Event eventHandler(Event e) {
        Event response=null;

        synchronized (gameState) {

            String playerToken = e.getToken().getPlayerToken();

            if(playerToken!=null && (gameState.isStarted() && gameState.isInit())){
                String command = e.getCommand();
System.out.println("PORCODIOIMPESTATO "+command);
                switch(command){

                case "getmap": 
                    response =getMap(e);
                    break;


                case "getplayerinfo" :
                    response = getPlayerInfo(e);
                    break;

                case "getturninfo" :
                    response = getTurnInfo(e);
                    break;

                case "getactionlist" :
                    response = getActionList(e);
                    break;

                case "getcardlist" :
                    response = getCardList(e);
                    break;
                case "useitem":
                    System.out.println("DIOCANEEE");
                    e = useItemCard(e);
                    break;


                default:
                    System.out.println("DEFAULT");
                    if(players.containsKey(playerToken)){
                        Player thisPlayer = players.get(playerToken);
                        if(gameState.getTurnState().getCurrentPlayer().equals(thisPlayer)){
                            response =  handleAction(e);
                        }
                    }
                    break;
                }                    
            }

        }

        return response;

    }


    private Event useItemCard(Event e) {
        System.out.println("DIOMAIALEEEE!!! "+e);

        String strCard = e.getArgs().get("itemcard");

        ItemCard card = ItemCard.fromString(strCard);
        Event response=null;
        switch (card) {
        case ITEM_TELEPORT:
            Action teleport = new Teleport(this, e);
            response= teleport.execute();
            break;

        case ITEM_ADRENALINE:
            Action adrenaline = new Adrenaline(this, e);
            response= adrenaline.execute();
            break;
        case ITEM_SEDATIVES:
            Action sedatives = new Sedatives(this, e);
            response= sedatives.execute();
            break;
        case ITEM_ATTACK:
            Action attack = new Attack(this, e);
            response= attack.execute();
            break;

        default:
            break;
        }

        return response;
    }

    private Event getCardList(Event e) {
        String playerToken = e.getToken().getPlayerToken();
        Player thisPlayer = players.get(playerToken);

        List<ItemCard> list = thisPlayer.getCardList();
        int cardsSize = thisPlayer.getCardListSize();

        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("return", "true");
        retValues.put("cardssize", Integer.toString(cardsSize));

        for (ItemCard actionEnum : list) {
            retValues.put(actionEnum.toString(),"");
        }


        return new Event(e, retValues);    
    }

    private Event getActionList(Event e) {

        List<ActionEnum> listAction = gameState.getTurnState().getActionList();

        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("return", "true");
        for (ActionEnum actionEnum : listAction) {
            retValues.put(actionEnum.toString(),"");
        }


        return new Event(e, retValues);
    }

    private Event handleAction(Event e) {

        if(gameState.getTurnState().isActionInActionList(e.getCommand())){
System.out.println("DIOMADONNAPUTTANA!!! "+e.getCommand());
            switch(e.getCommand()){

            case "move":
                Action move = new Move(this, e);
                e= move.execute();
                break;
            case "attack":
                Action attack = new Attack(this,e);
                e = attack.execute();
                break;


            case "discarditem":
                //     e = discarditem(e);

            case "escape":
                //     Action escape = new Escape(this,e);
                //     e = escape.execute();

            case "asksector":
                Action asksector = new AskSector(this,e);
                e= asksector.execute();
                break;
                
            case "endturn":
                System.out.println("ENDTURN");
                e = endTurn(e);
                break;
            }

        }

        return e;

    }

    private Event endTurn(Event e) {
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("endturn", "true");        
        retValues.put("return", "true");        

        Event response = new Event(e, retValues);

        Event toPublish = new Event(new ClientToken("", gameToken),"pub",retValues);
        Broker.publish(gameToken,NetworkProxy.eventToJSON(toPublish));

        nextTurn();


        return response;
    }

    private Event getPlayerInfo(Event e) {
        String playerToken = e.getToken().getPlayerToken();

        Player thisPlayer = players.get(playerToken);


        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("playernumber",Integer.toString(thisPlayer.getPlayerNumber()));
        retValues.put("currentposition",thisPlayer.getPosition().getCoordinate().toString());
        retValues.put("cardnumber",Integer.toString(thisPlayer.getCardListSize()));
        retValues.put("playertype",thisPlayer.getPlayerType().toClassName());


        Event response = new Event(e,retValues);

        return response;
    }


    private Event getTurnInfo(Event e) {

        int currentPlayer = gameState.getTurnState().getCurrentPlayer().getPlayerNumber();
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("currentplayer",Integer.toString(currentPlayer) );

        Event response = new Event(e,retValues);   

        return response;
    }


    public void removeAction(ActionEnum action){
        gameState.getTurnState().getActionList().remove(action);

    }




    private Event getMap(Event e) {

        Field field = gameState.getField();

        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("map", field.getPrintableMap());
        Event result = new Event(e,retValues);
        return result;
    }

    public FieldController getFieldController() {
        return this.fieldController;
    }

    // return a new instance of a specific subtype of PlayerController from an
    // Enumeration Value
    public PlayerController getPlayerInstance(Player player) {
        String className = ((new PlayerController(gameState)).getClass().getPackage() + "."
                + player.getPlayerType().toClassName() + "PlayerController").substring("package "
                        .length());

        Class<?> classe;

        Object object = null;
        try {
            System.out.println(className);
            classe = Class.forName(className);
            Constructor<?> costruttore = classe.getConstructor(GameState.class);
            object = costruttore.newInstance(gameState);

        } catch (ClassNotFoundException e) {

            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "ClassNotFoundException", e);

        } catch (NoSuchMethodException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "NoSuchMethodException", e);

        } catch (SecurityException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "SecurityException", e);
        } catch (InstantiationException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "InstantiationException", e);

        } catch (IllegalAccessException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "IllegalAccessException", e);

        } catch (IllegalArgumentException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE,
                    "IllegalArgumentException", e);

        } catch (InvocationTargetException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE,
                    "InvocationTargetException", e);

        }
        return objectToPlayerController(object);
    }

    private PlayerController objectToPlayerController(Object myObject) {
        return (PlayerController) myObject;
    }

    public Player getCurrentPlayer() {
        return gameState.getTurnState().getCurrentPlayer();
    }

}
