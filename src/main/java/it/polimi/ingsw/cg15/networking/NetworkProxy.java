package it.polimi.ingsw.cg15.networking;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NetworkProxy {

    /*
     * 
     */

    private NetworkProxy(){};


    public static Event JSONToEvent(String s){
        Logger.getLogger(NetworkProxy.class.getName()).log(Level.INFO, s);
        System.out.println(s);
        Event e = null ;

        Map<String, String> retValues=null;
        Map<String, String> args = null;

        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(s);



            String playerToken = (String) jsonObject.get("playerToken");
            String gameToken = (String) jsonObject.get("gameToken");
            String command = (String) jsonObject.get("command");

            JSONObject argsjson = (JSONObject) jsonObject.get("args");

            JSONObject retjson = (JSONObject) jsonObject.get("retValues");



            args = new HashMap<String, String>();

            if(argsjson!=null){
                Iterator<String> argkeysItr = argsjson.keySet().iterator();
                while(argkeysItr.hasNext()) {
                    String key = argkeysItr.next();
                    Object value = argsjson.get(key);

                    args.put(key, (String) value);
                }

            }
            retValues = new HashMap<String, String>();

            if(retjson!=null){

                Iterator<String> retkeysItr = retjson.keySet().iterator();
                while(retkeysItr.hasNext()) {
                    String key = retkeysItr.next();
                    Object value = retjson.get(key);

                    retValues.put(key, (String) value);
                }
            }




            ClientToken ctoken = new ClientToken(playerToken, gameToken);
            e = new Event(ctoken, command, args, retValues);

        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            //e1.printStackTrace();
            retValues = new HashMap<String, String>();
            retValues.put("return", "invalid request");
            e = new Event(new ClientToken("nullplyerToken", "nullgameToken"), "nullcommand",  args, retValues);
        }
        return e;

    }




    public static String eventToJSON(Event e){

        JSONObject jsonObject = new JSONObject();
        ClientToken ctoken = e.getToken();
        String playerToken = null;
        String gameToken= null;
        if(ctoken!=null){
            playerToken = e.getToken().getPlayerToken();
            gameToken = e.getToken().getGameToken();
        }
        String command = e.getCommand();
        if(playerToken!=null){
            jsonObject.put("playerToken", playerToken);
        }
        if(gameToken!=null){
            jsonObject.put("gameToken", e.getToken().getGameToken());
        }
        if(command!=null){
            jsonObject.put("command", e.getCommand());
        }

        if(e.getArgs()!=null){
            JSONObject jsonArgs = new JSONObject();
            for (Entry<String, String> elem : e.getArgs().entrySet()) {
                jsonArgs.put(elem.getKey(), elem.getValue());
            }
            jsonObject.put("args", jsonArgs);
        }
        if(e.getRetValues()!=null){
            JSONObject jsonRet = new JSONObject();
            for (Entry<String, String> elem : e.getRetValues().entrySet()) {
                jsonRet.put(elem.getKey(), elem.getValue());
            }
            jsonObject.put("retValues", jsonRet);
        }

        String result = new String(jsonObject.toString());

        return result;
    }
}

/*
{
"playerToken": "d23d2ed223faf3f3a3",
"gameToken": "91ca29jd933d2ed2f3f3a3",
"command": "move",
"args": 
  {
    "destination": "A02",
     "asd": "A03"

  }
,
"retValues": {
  "result": "true",
  "sectorCard":"cardRed",
  "itemCard":"itemTeleport"
}
}



{
"clientToken": "d23d2ed223faf3f3a3",
"gameToken": "91ca29jd933d2ed2f3f3a3",
"command": "listgame",
"args": {

  }
,
"retValues": {
  "result": "true",
  "91ca29jd933d2ed2f3f3a3":"prova_partita",
  "3d2ed2f33d2ed2f33d2ed2":"nome partita 2"
}
}*/