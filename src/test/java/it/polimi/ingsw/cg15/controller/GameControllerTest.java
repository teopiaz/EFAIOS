package it.polimi.ingsw.cg15.controller;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.GameInstance;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.player.Player;
import it.polimi.ingsw.cg15.model.player.PlayerType;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

public class GameControllerTest {

    GameState gs = GameInstance.getInstance().addGameInstance();
    Map<String,Player> players = new HashMap<String, Player>();
    GameBox gb = new GameBox(gs, null, null, players);
    GameController gc = new GameController(gb);
    

    @Test
    public final void testInitGame() {
        for(int i=0;i<7;i++){
       // gb.getPlayers().put(Integer.toString(i), new Player());
        gb.getPlayers().put(Integer.toString(i), gb.getGameState().addPlayer(new Player()));

        }
        //MapLoader.loadMap(gs.getField(), "fermi");

        
        gc.initGame("fermi");
        int numAlien = 0;
        int numHuman = 0;
        for (Entry<String,Player> player : gb.getPlayers().entrySet()) {
                System.out.println(player.getKey()+" "+player.getValue().getPlayerType());
                if(player.getValue().getPlayerType()==PlayerType.ALIEN){
                    numAlien++;
                }
                if(player.getValue().getPlayerType()==PlayerType.HUMAN){
                    numHuman++;
                }              
        }
        System.out.println("A:"+numAlien+" H:"+numHuman);
        assertEquals(4,numAlien);
        assertEquals(3,numHuman);

        
    }

}
