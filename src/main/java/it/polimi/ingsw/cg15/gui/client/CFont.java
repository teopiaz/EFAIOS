package it.polimi.ingsw.cg15.gui.client;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

//TODO dobbiamo mettere per forza San Francisco come font!

/**
 * @author MMP - LMR
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
     * The font cache.
     */
    private static ConcurrentMap<String, Font> fontCache = new ConcurrentHashMap<String, Font>();

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
                    fontCache.putIfAbsent(name, font);
                } catch (Exception ex) {
                    font = SERIF_FONT;
                }
            }
        }
        return font;
    }
    
}
