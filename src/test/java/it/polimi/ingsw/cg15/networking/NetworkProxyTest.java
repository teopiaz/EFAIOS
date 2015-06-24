package it.polimi.ingsw.cg15.networking;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class NetworkProxyTest {

    String s;
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        s = "{\"playerToken\": \"d23d2ed223faf3f3a3\",\"gameToken\":\n \"91ca29jd933d2ed2f3f33\",\"command\": \"move\",\"args\": {\"destination\": \"A02\", \"asdasd\": \"B99\"},\"retValues\": { \"result\": \"true\",\"sectorCard\":\"cardRed\", \"itemCard\":\"itemTeleport\" }}";
    }

    @Test
    public final void testParseString() {
        
        // Testo le stringhe che passano come messaggi.

        Event e = NetworkProxy.jsonToEvent(s);
        assertEquals(e.getToken().getPlayerToken(),"d23d2ed223faf3f3a3");
        assertEquals(e.getToken().getGameToken(),"91ca29jd933d2ed2f3f33");
        assertEquals(e.getCommand(),"move");
        assertEquals(e.getArgs().get("destination"),"A02");
        assertEquals(e.getRetValues().get("result"),"true");
        assertEquals(e.getRetValues().get("itemCard"),"itemTeleport");

        String json = NetworkProxy.eventToJSON(e);
        
        e = NetworkProxy.jsonToEvent(json);

        assertEquals(e.getToken().getPlayerToken(),"d23d2ed223faf3f3a3");
        assertEquals(e.getToken().getGameToken(),"91ca29jd933d2ed2f3f33");
        assertEquals(e.getCommand(),"move");
        assertEquals(e.getArgs().get("destination"),"A02");
        assertEquals(e.getRetValues().get("result"),"true");
        assertEquals(e.getRetValues().get("itemCard"),"itemTeleport");

        

    
    
    }

}
