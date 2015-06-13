package it.polimi.ingsw.cg15.gui.client;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.imageio.ImageIO;

public class ImageLoader {
    private static BufferedImage nullImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static ConcurrentMap<String, BufferedImage> imageCache = new ConcurrentHashMap<String, BufferedImage>();

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
                    // TODO Auto-generated catch block
                    image = nullImage;
                }
            }
        }
        return image;
    }
}
