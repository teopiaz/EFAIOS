package it.polimi.ingsw.cg15.networking;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MMP - LMR
 * The event class.
 */
public class Event implements Serializable {
    
    /**
     * The client token.
     */
    private final ClientToken token;
    
    /**
     * A new command.
     */
    private final String command;
    
    /**
     * Arguments.
     */
    private final Map<String, String> args;
    
    /**
     * The return values.
     */
    private final Map<String, String> retValues;
    
    /**
     * False.
     */
    public final static String FALSE = "false";
    
    /**
     * True.
     */
    public final static String TRUE = "true";
    
    /**
     * Error.
     */
    public final static String ERROR = "error";
    
    /**
     * Return.
     */
    public final static String RETURN = "return";

    /**
     * A constructor.
     * @param token The client token.
     * @param command The command.
     * @param args The arguments of the command.
     */
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
    
    /**
     * A constructor.
     * @param token The Client Token.
     * @param retValue Return values.
     */
    public Event(ClientToken token, String retValue) {
        this.token = token;
        this.command = null;
        this.args = null;
        Map<String, String> retmap = new HashMap<String, String>();
        retmap.put("return", retValue);
        this.retValues = retmap;
    }

    /**
     * A constructor.
     * @param token The Client Token.
     * @param command The command.
     * @param args The arguments of the command.
     * @param retValue Return values.
     */
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


    /**
     * A constructor.
     * @param e The event.
     * @param retValue Return values.
     */
    public Event(Event e,  Map<String, String>retValues ) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;
        this.retValues = retValues;
    }

    /**
     * A constructor.
     * @param e The event.
     * @param value A generic value.
     */
    public Event(Event e, String value ) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;
        Map<String, String> map = new HashMap<String, String>();
        map.put("return", value);
        this.retValues = map;
    }
    
    /**
     * A constructor.
     * @param e The event.
     * @param retKey Return key.
     * @param retValue Return values.
     */
    public Event(Event e, String retKey, String retValue) {
        this.token = e.token;
        this.command = e.command;
        this.args = e.args;
        Map<String, String> map = new HashMap<String, String>();
        map.put(retKey, retValue);
        this.retValues = map;
    }
    
    /**
     * @return the Client Token.
     */
    public ClientToken getToken() {
        return token;
    }

    /**
     * @return The arguments.
     */
    public Map<String, String> getArgs() {
        return args;
    }

    /**
     * @return Return values
     */
    public Map<String, String> getRetValues() {
        return retValues;
    }

    /**
     * @return The command.
     */
    public String getCommand() {
        return command;
    }

    /**
     * Transform into a string.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Event [tokenPlayer=" + token.getPlayerToken()+" tokenGame="+ token.getGameToken() + ", command=" + command + ", args=" + args + ", retValues="
                + retValues + "]";
    }
    
    /**
     * @return The result of the current action.
     */
    public boolean actionResult(){
        if(this.retValues.containsKey("return")){
            if(this.retValues.get("return").equals("true")){
                return true;
            }
        }
        return false;
    }

}
