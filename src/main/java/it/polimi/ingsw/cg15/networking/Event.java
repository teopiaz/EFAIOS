package it.polimi.ingsw.cg15.networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author matteo
 *
 */
public class Event {
    private final ClientToken token;
    private final String command;
    private final Map<String, String> args;

    private final Map<String, String> retValues;



    public Event(ClientToken token, String command, Map<String, String>  args) {
        this.token = token;
        this.command = command;
        this.args = args;
        retValues=null;
    }



    public Event(ClientToken token, String command, Map<String, String>  args, Map<String, String> retValues) {
        this.token = token;
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


}
