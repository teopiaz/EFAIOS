package it.polimi.ingsw.cg15.networking;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author matteo
 *
 */
public class Event implements Serializable {
    private final ClientToken token;
    private final String command;
    private final Map<String, String> args;
    private final Map<String, String> retValues;
    
    public final static String FALSE = "false";
    public final static String TRUE = "true";
    public final static String ERROR = "error";



    public Event(ClientToken token, String command, Map<String, String>  args) {
        if(token==null){
            this.token = new ClientToken(null, null);
        }
        else{
        this.token = token;
        }
        this.command = command;
        this.args = args;
        retValues=null;
    }
    
    public Event(ClientToken token, String retValue) {
        this.token = token;
        this.command = null;
        this.args = null;
        
        Map<String, String> retmap = new HashMap<String, String>();
        retmap.put("return", retValue);
        this.retValues = retmap;
    }



    public Event(ClientToken token, String command, Map<String, String>  args, Map<String, String> retValues) {
        if(token==null){
            this.token = new ClientToken(null, null);
        }
        else{
        this.token = token;
        }
        this.command = command;
        this.args = args;
        this.retValues = retValues;
    }


    public Event(Event e,  Map<String, String>retValues ) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;
        this.retValues = retValues;

    }

    public Event(Event e, String value ) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;

        Map<String, String> map = new HashMap<String, String>();
        map.put("return", value);
        this.retValues = map;
    }
    
    
    public Event(Event e, String retKey, String retValue) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;

        Map<String, String> map = new HashMap<String, String>();
        map.put(retKey, retValue);
        this.retValues = map;
    }
    


    public ClientToken getToken() {
        return token;
    }


    public Map<String, String> getArgs() {
        return args;
    }


    public Map<String, String> getRetValues() {
        return retValues;
    }


    public String getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return "Event [tokenPlayer=" + token.getPlayerToken()+" tokenGame="+ token.getGameToken() + ", command=" + command + ", args=" + args + ", retValues="
                + retValues + "]";
    }
    
    public boolean actionResult(){
        if(this.retValues.containsKey("return")){
            if(this.retValues.get("return").equals("true")){
                return true;
            }
        }
        return false;
    }


}
