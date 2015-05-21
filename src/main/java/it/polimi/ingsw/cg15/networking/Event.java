package it.polimi.ingsw.cg15.networking;

public class Event {
    ClientToken token;
    String command;
    String args; 
    
    public void setCommand(String command){
        this.command=command;
    }
}
