package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Field;
import java.util.ArrayList;
import java.util.List;

//TODO: singleton
/**
 * @author MMP - LMR
 * The class that contains the varius game state. It allows to group information on the various games.
 */
public class GameInstance {
    
    /**
     * The singleton instance of game instance.
     */
    static GameInstance singletonInstance = new GameInstance();
    
    /**
     * The list of instances of game state.
     */
    private List<GameState> instanceList = new ArrayList<GameState>();
    
    /**
     * The constructor.
     */
    private GameInstance() {
    }
    
    /**
     * @return the current instance of game.
     */
    public static GameInstance getInstance(){
        return singletonInstance;
    }
    
    /**
     * Add a match into the instance of game.
     * @return the game instance created.
     */
    public GameState addGameInstance( ){
        Field field = new Field();
        DeckContainer deckContainer = new DeckContainer();
        GameState gameState = new GameState(field, deckContainer);
        instanceList.add(gameState);
        return gameState;
    }
    
    /**
     * @return the size of the instance list.
     */
    public int getInstanceSize(){
        return instanceList.size();

    }

    /**
     * Remove a game state from the instance list.
     * @param gameState The game state to remove.
     * @return a boolean if tha game state has been removed.
     */
    public boolean removeGameInstace(GameState gameState){
        if(instanceList.remove(gameState)){
            return true;
        }
        return false;
    }

}
