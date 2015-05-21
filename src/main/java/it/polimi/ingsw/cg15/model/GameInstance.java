package it.polimi.ingsw.cg15.model;

import it.polimi.ingsw.cg15.model.cards.DeckContainer;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.player.Player;

import java.util.ArrayList;
import java.util.List;

//TODO: singleton
public class GameInstance {
    static GameInstance singletonInstance = new GameInstance();
    private List<GameState> instanceList = new ArrayList<GameState>();
    

    private GameInstance() {
    }
    public static GameInstance getInstance(){
        return singletonInstance;
    }
    
    

    public GameState addGameInstance(Field field, DeckContainer deckContainer,List<Player> players ){
        GameState gameState = new GameState(field, deckContainer, players);
        instanceList.add(gameState);
        return gameState;
    }
    
    public GameState getGameInstance(int k){
        return	instanceList.get(k);

    }

    public int getInstanceSize(){
        return instanceList.size();

    }

    public boolean removeGameInstace(int k){
        if(instanceList.remove(k)!=null){

            return true;
        }
        return false;
    }

}
