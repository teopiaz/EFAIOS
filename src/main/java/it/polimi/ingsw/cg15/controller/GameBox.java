package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.concurrent.BlockingQueue;

public class GameBox {
    
    
    GameState gameState;
    String gameToken;
    BlockingQueue<Event> queue;

    
    public GameBox(GameState gs, BlockingQueue<Event> queue, String token) {
        this.gameState=gs;
        this.gameToken=token;
        this.queue=queue;
    }

}
