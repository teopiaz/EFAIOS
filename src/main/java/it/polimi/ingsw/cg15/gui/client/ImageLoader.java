package it.polimi.ingsw.cg15.gui.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

/**
 * @author MMP - LMR
 * The class for loading the images.
 */
public class ImageLoader {
    
    /**
     * A buffer with the immages.
     */
    private static BufferedImage nullImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    
    /**
     * The image cache.
     */
    private static ConcurrentMap<String, BufferedImage> imageCache = new ConcurrentHashMap<String, BufferedImage>();

    
    private ImageLoader(){
        
    }
    
    
    /**
     * @param name The name of the image.
     * @return The image.
     */
    public static BufferedImage load(String name) {
        BufferedImage image = nullImage;
        if (name == null) {
            return nullImage;
        }
        image = imageCache.get(name);
        if (image == null) {
            String fName = "/" + name + ".png";
            InputStream input = ImageLoader.class.getResourceAsStream(fName);
            if (input != null) {
                try {
                    image = ImageIO.read(input);
                    imageCache.putIfAbsent(name, image);
                } catch (IOException e) {
                    Logger.getLogger(ImageLoader.class.getName()).log(Level.SEVERE, "IO exception", e);
                    image = nullImage;
                }
            }
        }
        return image;
    }
    
}
