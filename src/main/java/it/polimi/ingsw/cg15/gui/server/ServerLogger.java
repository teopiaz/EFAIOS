package it.polimi.ingsw.cg15.gui.server;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The class with the server logger.
 */
public class ServerLogger {
    
    /**
     * GUI of server.
     */
    private static ServerGUI gui;
    

    
     
    /**
     * The constructor.
     * @param gui The server GUI.
     */
    private ServerLogger(ServerGUI gui){
        ServerLogger.gui=gui;
    }
    
    public static ServerLogger getServerLogger(ServerGUI gui){
        return new ServerLogger(gui);
    }
    
    
    
    /**
     * @param message The message to send as log.
     */
    public static synchronized void log(String message){
        String str = message+"\n";
        gui.appendLog(str);
        Logger.getLogger("LOG").log(Level.INFO, str );
    }

}
