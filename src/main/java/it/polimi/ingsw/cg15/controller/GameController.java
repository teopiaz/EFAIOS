package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.controller.player.PlayerController;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Field;
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


    
	public GameController(String gameToken,BlockingQueue<Event> queue) {
	    this.queue = queue;
	    //TODO: modificare la sequenza di creazione della partita
	
	    
	    this.fieldController = new FieldController(gameState);

	}


	

    public void run() {
       //TODO da cambiare
        while(true){
         
            if(queue.isEmpty()){
            try {
                this.wait();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            }
            else{
                eventHandler(queue.poll());
            }
        }
        
    }
        
        
   public void eventHandler(Event e){
       System.out.println(e.getCommand());
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
            // TODO Auto-generated catch block
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
        }
        return (PlayerController) object;
    }
    
    
    public Player getCurrentPlayer(){
        return gameState.getTurnState().getCurrentPlayer();
    }






    
  

}
