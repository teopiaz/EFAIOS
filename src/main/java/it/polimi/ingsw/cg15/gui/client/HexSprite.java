package it.polimi.ingsw.cg15.gui.client;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.TexturePaint;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class HexSprite {
    private static int h;
    private static int r;
    private static int s;
    private static int t;
    private static int BORDERS = 30; // default number of pixels for the border.
    private static BufferedImage sectorAlienImage = null;
    private static BufferedImage sectorHumanImage = null;
    private static BufferedImage sectorHatchImage = null;

    private HexSprite(){
        
    }
    
    public static void setHeight(int height) {
        h = height; // h = basic dimension: height (distance between two adj
        // centresr aka size)
        r = h / 2; // r = radius of inscribed circle
        s = (int) (h / 1.73205); // s = (h/2)/cos(30)= (h/2) / (sqrt(3)/2) = h /
        // sqrt(3)
        t = (int) (r / 1.73205); // t = (h/2) tan30 = (h/2) 1/sqrt(3) = h / (2
        // sqrt(3)) = r / sqrt(3)

    }

    public static void drawHex(int i, int j, Graphics2D g2) {

        int x = i * (s + t);
        int y = j * h + (i % 2) * h / 2;
        Polygon poly = hex(x, y);
        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillPolygon(poly);
        g2.drawPolygon(poly);

    }

    public static Polygon hex(int x0, int y0) {

        int y = y0 + BORDERS;
        int x = x0 + BORDERS;
        if (s == 0 || h == 0) {
            return new Polygon();
        }

        int[] cx, cy;

        cx = new int[] { x + t, x + s + t, x + s + t + t, x + s + t, x + t, x };

        cy = new int[] { y, y, y + r, y + r + r, y + r + r, y + r };
        return new Polygon(cx, cy, 6);
    }
    


    public static void fillHex(int i, int j, String label, Graphics2D g2, int n,Point position, Point selectedPosition){

            
        int posX = 0;
        int posY = 0;
        boolean isStarted=false;
        if(position!=null){
            isStarted=true;
            posX=position.x;
            posY=position.y;
        }
        boolean isSelected=false;
        int selectedSectorX = 0;
        int selectedSectorY =0;
        if(selectedPosition!=null){
            isSelected=true;
            selectedSectorX = selectedPosition.x;
            selectedSectorY = selectedPosition.y;
        }
        
        int x = i * (s + t);
        int y = j * h + (i % 2) * h / 2;
        g2.setFont(CFont.getFont("TopazPlus"));

        if (n == 1) {
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillPolygon(hex(x, y));
            g2.setColor(Color.WHITE);
            g2.drawString("" + label, x + r + BORDERS - label.length() * 3, y + r + BORDERS + 4);

        }

        if (n == 2) {
            g2.setColor(new Color(192, 192, 192, 200));

            g2.fillPolygon(hex(x, y));
            g2.setColor(Color.WHITE);
            g2.drawString("" + label, x + r + BORDERS - label.length() * 3, y + r + BORDERS + 4);

        }
        if (n == 3) {
            // hatch
            if (sectorHatchImage == null) {
                sectorHatchImage = ImageLoader.load("24");
            }
            Polygon poly = hex(x, y);

            Rectangle2D rect = new Rectangle2D.Double(0, 0, poly.getBounds2D().getWidth(), poly
                    .getBounds2D().getHeight());
            rect.setFrame(x - 15.0, y - 10.0, poly.getBounds2D().getWidth(), poly.getBounds2D()
                    .getHeight());
            TexturePaint tex = new TexturePaint(sectorHatchImage, rect);

            g2.setPaint(tex);

            g2.fill(poly);

        }

        if (n == 4) {
            if (sectorHumanImage == null) {
                sectorHumanImage = ImageLoader.load("18");
            }
            Polygon poly = hex(x, y);

            Rectangle2D rect = new Rectangle2D.Double(0, 0, poly.getBounds2D().getWidth(), poly
                    .getBounds2D().getHeight());
            rect.setFrame(x - 15.0, y - 10.0, poly.getBounds2D().getWidth(), poly.getBounds2D()
                    .getHeight());
            TexturePaint tex = new TexturePaint(sectorHumanImage, rect);

            g2.setPaint(tex);

            g2.fill(poly);

        }
        if (n == 5) {
            if (sectorAlienImage == null) {
                sectorAlienImage = ImageLoader.load("21");
            }
            Polygon poly = hex(x, y);

            Rectangle2D rect = new Rectangle2D.Double(0, 0, poly.getBounds2D().getWidth(), poly
                    .getBounds2D().getHeight());
            rect.setFrame(x - 15.0, y - 10.0, poly.getBounds2D().getWidth(), poly.getBounds2D()
                    .getHeight());
            TexturePaint tex = new TexturePaint(sectorAlienImage, rect);

            g2.setPaint(tex);

            g2.fill(poly);

        }

        if (isSelected && selectedSectorX == i && selectedSectorY == j) {
            g2.setColor(new Color(0, 255, 0, 128));
            g2.fillPolygon(hex(x, y));
            g2.setColor(Color.DARK_GRAY);
            g2.drawString("" + label, x + r + BORDERS - label.length() * 3, y + r + BORDERS + 4);

        }

        if (isStarted && posX == i && posY == j) {
            g2.setColor(new Color(255, 178, 0, 128));
            g2.fillPolygon(hex(x, y));
        }

    }

    public static Point pxtoHex(int mxArg, int myArg) {
        Point p = new Point(-1, -1);
        int mx = mxArg;
        int my = myArg;
        // correction for BORDERS and XYVertex
        mx -= BORDERS;
        my -= BORDERS;

        int x = mx / (s + t); // this gives a quick value for x. It
        // works only on odd cols and doesn't
        // handle the triangle sections. It
        // assumes that the hexagon is a rectangle
        // with width s+t (=1.5*s).
        int y = (my - (x % 2) * r) / h; // this gives the row easily. It
        // needs to be offset by h/2
        // (=r)if it is in an even
        // column

        int dx = mx - x * (s + t);
        int dy = my - y * h;

        if (my - (x % 2) * r < 0)
            return p; // prevent clicking in the open halfhexes at the top of
        // the screen

        // even columns
        if (x % 2 == 0) {
            if (dy > r && (dx * r / t < dy - r)) {
                x--;
            }

            if (dy < r && ((t - dx) * r / t > dy)) {
                x--;
                y--;
            }

        } else { // odd columns
            if (dy > h && dx * r / t < dy - h) {
                x--;
                y++;
            }
            if (dy < h && (t - dx) * r / t > dy - r) {
                x--;
            }
        }
        p.x = x;
        p.y = y;
        return p;
    }

}
