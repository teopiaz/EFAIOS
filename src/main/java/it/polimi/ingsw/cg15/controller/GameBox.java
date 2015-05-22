package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.concurrent.BlockingQueue;

public class GameBox {


    private GameState gameState;
    private String gameToken;
    private BlockingQueue<Event> queue;


    public GameBox(GameState gs, BlockingQueue<Event> queue, String token) {
        this.gameState=gs;
        this.gameToken=token;
        this.queue=queue;
    }


    public GameState getGameState() {
        return gameState;
    }


    public String getGameToken() {
        return gameToken;
    }


    public BlockingQueue<Event> getQueue() {
        return queue;
    }

}
