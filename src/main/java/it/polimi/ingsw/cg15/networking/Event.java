package it.polimi.ingsw.cg15.networking;

import java.util.List;

public class Event {
    ClientToken token;
    String command;
    List<String> args; 
    List<String>  retValues;
    Class event;
    
    public void setCommand(String command){
        this.command=command;
        /*   event.getClass().
     Action pippo =  event.class.newInstance();
        pippo.
        */
    }

    public Event(ClientToken token, String command, List<String> args) {
        this.token = token;
        this.command = command;
        this.args = args;

    }
    
    public Event(ClientToken token, String command, List<String> args, List<String> retValues) {
        this.token = token;
        this.command = command;
        this.args = args;
        this.retValues = retValues;
    }
    
    public Event(Event e, List<String> retValues ) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;
        this.retValues = retValues;

    }

    public ClientToken getToken() {
        return token;
    }

    public void setPlayerToken(ClientToken token) {
        this.token = token;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setArgs(List<String> args) {
        this.args = args;
    }

    public List<String> getRetValues() {
        return retValues;
    }

    public void setRetValues(List<String> retValues) {
        this.retValues = retValues;
    }

    public String getCommand() {
        return command;
    }
    
    
}
