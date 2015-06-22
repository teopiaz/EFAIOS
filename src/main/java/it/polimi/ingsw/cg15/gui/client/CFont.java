package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 * @author Matteo Michele Piazzolla
 * The custom font.
 */
public class CFont {
    
    /**
     * The font.
     */
    Font customFont;
    
    /**
     * Serif font.
     */
    private static final Font SERIF_FONT = new Font("serif", Font.PLAIN, 14);
    
    /**
     * Topaz plus string constant
     */
    public static final String TOPAZPLUS = "TopazPlus";

    
    /**
     * The font cache.
     */
    private static Map<String, Font> fontCache = new ConcurrentHashMap<String, Font>();

    /**
     * Get the font.
     * @param name The name of the font.
     * @return The font.
     */
    public static Font getFont(String name) {
        Font font = null;
        if (name == null) {
            return SERIF_FONT;
        }
        font = fontCache.get(name);
        if (font == null) {
            String fName = "/fonts/" + name + ".ttf";
            InputStream input = ImageLoader.class.getResourceAsStream(fName);
            if (input != null) {
                try {
                    font = Font.createFont(Font.TRUETYPE_FONT, input).deriveFont(14f);
                    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                    ge.registerFont(font);
                    fontCache.put(name, font);
                } catch (Exception ex) {
                    Logger.getLogger(NetworkHelper.class.getName()).log(Level.SEVERE, "Exception", ex);

                    font = SERIF_FONT;
                }
            }
        }
        return font;
    }
    
}
