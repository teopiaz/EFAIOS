package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.controller.player.AlienPlayerController;
import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.networking.Event;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;

/**
 * @author LMR - MMP
 */
public class GameController implements Runnable{


    private GameState gameState;
    private FieldController fieldController;
    private BlockingQueue<Event> queue;



    public GameController(GameBox gameBox) {
        this.gameState=gameBox.getGameState();
        this.queue = gameBox.getQueue();
        //TODO: modificare la sequenza di creazione della partita


        this.fieldController = new FieldController(gameState);

    }




    public void run() {
        while(!queue.isEmpty()){
            eventHandler(queue.poll());
        }
    }



    public void eventHandler(Event e){
        
        System.out.println(this.toString()+" "+e.getCommand());
    }


    public FieldController getFieldController() {
        return this.fieldController;
    }





    // return a new specification instance of PlayerController from an
    // Enumeration Value
    public PlayerController getPlayerInstance(Player player) {
        String className = ((new PlayerController(gameState)).getClass()
                .getPackage()
                + "."
                + player.getPlayerType().toClassName() + "PlayerController")
                .substring("package ".length());

        Class<?> classe;

        Object object = null;
        try {
            System.out.println(className);
            classe = Class.forName(className);
            Constructor<?> costruttore = classe.getConstructor(GameState.class);
            object = costruttore.newInstance(gameState);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (SecurityException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (InstantiationException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            // TODO Auto-generated catch block
        }
        return objectToPlayerController(object);
    }


    private PlayerController objectToPlayerController(Object myObject) {
        return (PlayerController)myObject;
     }
    
    
    public Player getCurrentPlayer(){
        return gameState.getTurnState().getCurrentPlayer();
    }

    








}
