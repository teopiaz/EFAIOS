package it.polimi.ingsw.cg15.gui.server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLogger {
    private static ServerGUI gui;
     

    public ServerLogger(ServerGUI gui){
        ServerLogger.gui=gui;
    }
    
    public static synchronized void log(String message){
      
        String str = message+"\n";
        gui.appendLog(str);
        Logger.getLogger("LOG").log(Level.INFO, str );

    }



}
