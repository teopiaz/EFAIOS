package it.polimi.ingsw.cg15.gui.client;

import java.awt.AWTEvent;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.JLayer;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.LayerUI;


/**
 * @author MMP - LMR
 * A visual effect that illuminates the map in a certain way when you use the card spotlight.
 */
public class SpotlightLayerUI extends LayerUI<JPanel> {
    
    /**
     * 
     */
    private static final long serialVersionUID = -4020458722057607364L;

    private boolean mActive;
    
    private int mX, mY;
    
    private boolean enabled = false;

    @Override
    public void installUI(JComponent c) {
      super.installUI(c);
      JLayer jlayer = (JLayer)c;
      jlayer.setLayerEventMask(
        AWTEvent.MOUSE_EVENT_MASK |
        AWTEvent.MOUSE_MOTION_EVENT_MASK
      );
    }

    @Override
    public void uninstallUI(JComponent c) {
      JLayer jlayer = (JLayer)c;
      jlayer.setLayerEventMask(0);
      super.uninstallUI(c);
    }

    /**
     * Paint the frame.
     */
    @Override
    public void paint (Graphics g, JComponent c) {
      Graphics2D g2 = (Graphics2D)g;
      // Paint the view.
      super.paint (g2, c);
      if (mActive && enabled) {
        // Create a radial gradient, transparent in the middle.
        java.awt.geom.Point2D center = new java.awt.geom.Point2D.Float(mX, mY);
        float radius = 72;
        float[] dist = {0.0f, 1.0f};
        Color[] colors = {new Color(0.0f, 0.0f, 0.0f, 0.0f), Color.BLACK};
        RadialGradientPaint p =
            new RadialGradientPaint(center, radius, dist, colors);
        g2.setPaint(p);
        g2.setComposite(AlphaComposite.getInstance(
            AlphaComposite.SRC_OVER, .85f));
        g2.fillRect(0, 0, c.getWidth(), c.getHeight());
      }
      g2.dispose();
    }

    /**
     * Process the mouse event.
     */
    @Override
    protected void processMouseEvent(MouseEvent e, JLayer l) {
       if(enabled){
      if (e.getID() == MouseEvent.MOUSE_ENTERED) mActive = true;
      if (e.getID() == MouseEvent.MOUSE_EXITED) mActive = false;
      l.repaint();
       }
    }

    /**
     * Process the mouse motion.
     */
    @Override
    protected void processMouseMotionEvent(MouseEvent e, JLayer l) {
      Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), l);
      mX = p.x;
      mY = p.y;
      l.repaint();
    }
    
    /**
     * Enable the spotlight visual effect.
     */
    public void enableSpot(){
        this.enabled=true;
    }
    
    /**
     * Disable the spotlight visual effect.
     */
    public void disableSpot(){
        this.enabled=false;
    }
    
  }