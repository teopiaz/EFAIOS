package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.action.Action;
import it.polimi.ingsw.cg15.action.Adrenaline;
import it.polimi.ingsw.cg15.action.AskSector;
import it.polimi.ingsw.cg15.action.Attack;
import it.polimi.ingsw.cg15.action.AttackCard;
import it.polimi.ingsw.cg15.action.Move;
import it.polimi.ingsw.cg15.action.Sedatives;
import it.polimi.ingsw.cg15.action.Spotlight;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author LMR - MMP
 * The Game Controller class. It contains many methods to manage the game. 
 * The most important are the ones that make the game start, end, change the current turn.
 * It also allow to obtain information on various aspects of the current game.
 */
public class GameController  {

    /**
     * The maximum number of turn in a match. At the 39th round win aliens. 
     */
    private static final int MAX_TURN_NUMBER = 39;

    /**
     * A reference to the GameState class of the model that contains lots of information about the current state of the game round.
     */
    private GameState gameState;

    /**
     * A reference to the FieldController class of the controller that contains the field methods to manage the field of the game.
     */
    private FieldController fieldController;

    /**
     * A reference to the CardController class of the controller that contains the methods to manipulate the cards.
     */
    private CardController cardController;

    /**
     * A tables that contains a reference to the players of the game.
     */
    private Map<String, Player> players;

    /**
     * This is a string that uniquely identifies the match.
     */
    private String gameToken;

    /**
     * The constructor of the class.
     * @param gameBox A container which has the information required to the game controller
     */
    public GameController(GameBox gameBox) {
        this.gameState = gameBox.getGameState();
        this.players = gameBox.getPlayers();
        this.fieldController = new FieldController(gameState);
        this.cardController = new CardController(gameState);
        this.gameToken = gameBox.getGameToken();
    }

    /**
     * It is the method that allows you to populate the field with players who are added to the current game. 
     * He takes care of assigning each player a starting position from which to begin the match.
     */
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

    /**
     * This is the method that is called when I want to start a game. 
     * Call the method that assigns to players a starting position. 
     * It generates the decks of cards required for each game.
     * @param mapName the name of the map in which I want to play.
     */
    public void initGame(String mapName){

        fieldController.loadMap(mapName);
        popolateField(); // Give each player a starting position.
        PlayerController pc = new PlayerController(gameState);   
        gameState.newTurnState(pc.getPlayerById(PlayerController.FIRST_PLAYER));
        cardController.generateDecks();
        gameState.setInit();

        /*
         * TODO controllare se questa parte commentata è necessaria.
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("currentplayer", Integer.toString(PlayerController.FIRST_PLAYER));
        String json = NetworkProxy.eventToJSON(new Event(new ClientToken("", gameToken),"pub",null,retValues));
        Broker.publish(gameToken, json);
         */
        nextTurn();
    }

    /**
     * The manager of the turn of the game. Go to the next player who has to play.
     */
    public void nextTurn(){

        int turnNumber = gameState.getTurnNumber();
        if(turnNumber<=MAX_TURN_NUMBER || !isGameEnded()){
            TurnState turnState = null;
            PlayerController pc = new PlayerController(gameState);
            if(turnNumber==1){
                turnState =   gameState.newTurnState(pc.getPlayerById(PlayerController.FIRST_PLAYER));

            }else{
                turnState = gameState.newTurnState(pc.getPlayerById(pc.getNextPlayer().getPlayerNumber()));
            }
            if(turnState.getCurrentPlayer().isAlive()){
                gameState.setTurnNumber(turnNumber+1);
            }else{
                nextTurn();
            }
            if(gameState.getTurnState().getCurrentPlayer().getPlayerType()==PlayerType.HUMAN){
                turnState.getActionList().add(ActionEnum.MOVE);
                turnState.getActionList().add(ActionEnum.ENDTURN);
                turnState.getActionList().add(ActionEnum.USEITEM);
            }else{
                turnState.getActionList().add(ActionEnum.MOVE);
                turnState.getActionList().add(ActionEnum.ATTACK);
                turnState.getActionList().add(ActionEnum.ENDTURN); 
            }
            Map<String,String> retValues = new HashMap<String, String>();
            retValues.put("currentplayer", Integer.toString(gameState.getTurnState().getCurrentPlayer().getPlayerNumber()));
            String json = NetworkProxy.eventToJSON(new Event(new ClientToken("", gameToken),"pub",null,retValues));
            Broker.publish(gameToken, json);
        }
        else{
            System.out.println("ENDGAME");
            endGame();
        }
    }

    /**
     * @return the Game Token, a string that uniquely identifies the match.
     */
    public String getGameToken(){
        return gameToken;
    }

    /**
     * This event handler is concerned to translate the message received in some specific action to perform.
     * @param e The event that I received and that I have to worry about managing.
     * @return The response to the event that has the information to handle the fact that action is successful or not.
     */
    public Event eventHandler(Event e) {
        Event response=null;

        synchronized (gameState) {
            String playerToken = e.getToken().getPlayerToken();
            if(playerToken!=null && (gameState.isStarted() && gameState.isInit())){
                String command = e.getCommand().toLowerCase();
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
        System.out.println("ACTION RESPONSE"+response);
        return response;
    }

    /**
     * The method that allows you to use one of the object card available.
     * @param e The event that I received and that I have to worry about managing.
     * @return The response to the event that has the information to handle the fact that action is successful or not.
     */
    private Event useItemCard(Event e) {

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
            Action attack = new AttackCard(this, e);
            response= attack.execute();
            break;
        case ITEM_SPOTLIGHT:
            Action spotlight = new Spotlight(this, e);
            response= spotlight.execute();
            break;
        default:
            break;
        }
        System.out.println("CARD RESPONSE"+response);
        return response;
    }

    /**
     * Method that allows to obtain the list of cards owned by a certain player.
     * @param e The event that I received and that I have to worry about managing.
     * @return The response to the event that has the information to handle the fact that action is successful or not.
     */
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

    /**
     * Method that allows to obtain the list of actions available for a particular player.
     * @param e The event that I received and that I have to worry about managing.
     * @return The response to the event that has the information to handle the fact that action is successful or not.
     */
    private Event getActionList(Event e) {
        List<ActionEnum> listAction = gameState.getTurnState().getActionList();
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("return", "true");
        for (ActionEnum actionEnum : listAction) {
            retValues.put(actionEnum.toString(),"");
        }
        return new Event(e, retValues);
    }

    /**
     * The method that calls the respective actions to be performed depending on which is requested.
     * @param e The event that I received and that I have to worry about managing.
     * @return The response to the event that has the information to handle the fact that action is successful or not.
     */
    private Event handleAction(Event e) {
        if(gameState.getTurnState().isActionInActionList(e.getCommand())){
            switch(e.getCommand()){
            case "move":
                Action move = new Move(this, e);
                e= move.execute();
                break;
            case "attack":
                Action attack = new Attack(this,e);
                e = attack.execute();
                break;
            case "useitem":
                System.out.println("DIOCANEEE");
                e = useItemCard(e);
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
            default:
                Map<String,String> retValues = new HashMap<String, String>();
                retValues.put("return", "false");
                retValues.put("errore", "azione non valida");
                e=new Event(e, retValues);
            }
        }
        return e;
    }

    /**
     * It allows you to end the current turn.
     * @param e The event that I received and that I have to worry about managing.
     * @return The response to the event that has the information to handle the fact that action is successful or not.
     */
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
    
    /**
     * A method that allows to receive various player information.
     * @param e The event that I received and that I have to worry about managing.
     * @return various information about the players: the unique number, the current position, the number of card owned 
     * by a player and the type of the player.
     */
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

    /**
     * A method that lets you know what is the current player.
     * @param e The event that I received and that I have to worry about managing.
     * @return the current player.
     */
    private Event getTurnInfo(Event e) {
        int currentPlayer = gameState.getTurnState().getCurrentPlayer().getPlayerNumber();
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("currentplayer",Integer.toString(currentPlayer) );
        Event response = new Event(e,retValues);   
        return response;
    }

    /**
     * Method that allows to delete an action from the list of those available.
     * @param action The action to be removed.
     */
    public void removeAction(ActionEnum action){
        gameState.getTurnState().getActionList().remove(action);
    }

    /**
     * Method that allows to end the game.
     */
    public void endGame(){
        gameState.setEnded();
        Map<String,String> retValues = new HashMap<String, String>();
        Event endEvent = new Event(new ClientToken("", getGameToken()),"endgame",null,retValues);
        Broker.publish(gameToken,NetworkProxy.eventToJSON(endEvent));
        GameManager.getInstance().removeGame(gameToken);
    }

    /**
     * Method that lets you know if the game is over or not.
     * @return yes or no
     */
    public boolean isGameEnded(){
        if(gameState.isEnded()){
            endGame();
            return true;
        }
        else
            return false;
    }

    /**
     * Method for returning the current map on which you are playing.
     * @param e The event that I received and that I have to worry about managing.
     * @return the map itself.
     */
    private Event getMap(Event e) {
        Field field = gameState.getField();
        Map<String,String> retValues = new HashMap<String, String>();
        retValues.put("map", field.getPrintableMap());
        Event result = new Event(e,retValues);
        return result;
    }

    /**
     * Method for returning the field controller.
     * @return the field controller.
     */
    public FieldController getFieldController() {
        return this.fieldController;
    }

    /**
     * Return a new instance of a specific Player Controller from an enumeration value.
     * @param player the player of the 
     * @return Return a new instance of a specific Player Controller from an enumeration value.
     */
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
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "IllegalArgumentException", e);
        } catch (InvocationTargetException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "InvocationTargetException", e);
        }
        return objectToPlayerController(object);
    }

    /**
     * TODO che cosa fa?
     * @param myObject
     * @return
     */
    private PlayerController objectToPlayerController(Object myObject) {
        return (PlayerController) myObject;
    }

    /**
     * @return the current player of a specific turn.
     */
    public Player getCurrentPlayer() {
        return gameState.getTurnState().getCurrentPlayer();
    }

    /**
     * Removes all the actions available to the user and adds only ask sector. 
     * This particular action allows to obtain an area in which say "noise".
     * It needs for green cards allowing you to lie about your current position.
     * @return true or false based on the success of the action list swap.
     */
    public boolean askForSector(){
        TurnState ts = gameState.getTurnState();
        if(!ts.areListSwapped()){
            ts.swapActionsList();        
            ts.addAskSectorAction();
            return true;
        }
        return false;
    }

    /**
     * Restore the list of actions available to the user.
     */
    public void restoreActionList() {
        TurnState ts = gameState.getTurnState();
        if(ts.areListSwapped()){
            ts.swapActionsList();                    
        }
    }
}
