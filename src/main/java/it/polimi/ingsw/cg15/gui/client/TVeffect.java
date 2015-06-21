package it.polimi.ingsw.cg15.gui.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author MMP - LMR
 * Implements the effect of turning off the TV.
 */
public class TVeffect {

    /**
     * The width of the panel.
     */
    private static int Width = 1280;
    
    /**
     * The height of the panel.
     */
    private static int Height = Width * 9/16;

    /**
     * The lobby for client.
     */
    private final ClientLobbyGUI lobby;
    
    /**
     * The frame.
     */
    JFrame frame;

    /**
     * The method for the TV effect.
     * @param taskLobby The lobby of tasks.
     */
    public TVeffect(Runnable taskLobby){
        this.lobby =(ClientLobbyGUI) taskLobby;
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }
                GraphicsDevice device = GraphicsEnvironment
                        .getLocalGraphicsEnvironment().getScreenDevices()[0];
                DisplayMode dm = device.getDisplayMode();
                //device.setDisplayMode(dm); 
                /*device.setDisplayMode(new DisplayMode(1280, 800,dm.getBitDepth(),dm.getRefreshRate()));
                W = device.getDisplayMode().getWidth();
                H = device.getDisplayMode().getHeight();
                 */
                Width=1280;
                Height=720;
                //   System.out.println(device.getDisplayMode().getRefreshRate());
                frame = new JFrame("Test");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                //frame.setPreferredSize(new Dimension(W,H));
                Pannello pannello = new Pannello();
                frame.add(pannello);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                // device.setFullScreenWindow(frame);
            }
        });
    }
    
    /**
     * @author MMP - LMR
     * Another panel.
     */
    private class Pannello extends JPanel {
        private int x=0;
        float lerpAmount = 0;
        float lerpScale = 0;
        float lerpShrink = 0;
        int tvheight=0;
        int tvwidth=0;
        boolean tvstate = false;
        int width;
        Timer timer ;
        BufferedImage image,scanline,noise;
        Random ran = new Random();
        boolean endIntro=false;
        public Pannello(){
            setPreferredSize(new Dimension(Width,Height));
            setBackground(Color.BLACK);
            image = ImageLoader.load("logo720alpha");
            image = (BufferedImage) getScaledImage(image, Width/3,Height/3); 
            image = (BufferedImage) getScaledImage(image, Width,Height );    
            scanline = ImageLoader.load("scanlines");
            scanline = (BufferedImage) getScaledImage(scanline, Width, Height);
            image = (BufferedImage) getScaledImage(image, Width, Height);
            image = colorShift(image,4);
            final Timer timershift = new Timer(10,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    image = colorShift(image,ran.nextInt(2)-ran.nextInt(2));
                    noise = noise(image);
                    repaint();
                }
            });
            timershift.start();
            width = image.getWidth();
            tvheight=image.getHeight();
            tvwidth=image.getWidth();
            repaint();
            Timer timer1 = new Timer(5000,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    timer = new Timer(40, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            //ogni volta che termina il timer
                            if (tvstate)
                                scaleIt(0.02F,0.2F,156,0,2,image.getHeight());  // turning on
                            else
                                scaleIt(0.1F,0.2F,0,-400,image.getHeight(),1);   // turning off
                            image= getScaledImage(image,tvwidth,tvheight);
                            repaint();
                            if(image.getWidth() < 100 && image.getHeight()<10 && endIntro==false){
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e1) {
                                    Logger.getLogger(TVeffect.class.getName()).log(Level.SEVERE, "Interrupt Exception", e1);
                                }
                                lobby.showGUI();
                                frame.dispose();
                                endIntro=true;
                                timershift.stop();
                            }
                        }
                    });
                    timer.setRepeats(true);
                    timer.setCoalesce(true);
                    timer.start();
                }
            });
            timer1.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(image, (Width/2)-image.getWidth()/2, (Height/2)-image.getHeight()/2, this);
            g2d.drawImage((Image)noise, 0,0,null);
            g2d.drawImage((Image)scanline, 0,0,null);
        }

        public BufferedImage colorShift(BufferedImage image,int offset){
            BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int i,j;
            boolean negative = false;
            if(offset<0){
                offset = Math.abs(offset);
                negative = true;
            }
            for(i=0;i<image.getHeight();i++){
                for(j=offset;j<image.getWidth()-offset;j++){
                    int  pixel = image.getRGB( j,i);
                    int pixelred=image.getRGB(j-offset,i);
                    int pixelblu =image.getRGB(j+offset,i);
                    if(negative){
                        int tmppx = pixelred;
                        pixelred=pixelblu;
                        pixelblu=tmppx;
                    }
                    Color colore = new Color(pixel);
                    Color colorered = new Color(pixelred);
                    Color coloreblu = new Color(pixelblu);
                    tmp.setRGB(j, i,new Color( colorered.getRed(), coloreblu.getGreen(), colore.getBlue() , colore.getAlpha() ).getRGB() );
                }
            }
            return tmp;
        }

        public void scaleIt(float amountA, float amountB, int valueA, int valueB, int valueC, int valueD){
            if (lerpAmount < 1)
            {
                lerpAmount += amountA;
            }
            if (lerpScale < 1)
            {
                lerpScale += amountB;
                tvheight = (int)lerp(valueC,valueD,lerpScale);
            } 
            if (!tvstate && tvheight < 100 && lerpShrink < 1)
            {
                tvwidth = (int)lerp(width,1,lerpShrink);
                lerpShrink += .09;
            }
        }

        private BufferedImage noise(BufferedImage image){
            BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int i,j;
            Random ran = new Random();
            for(i=0;i<image.getHeight();i+=3){
                for(j=0;j<image.getWidth();j+=2){
                    tmp.setRGB(j, i, new Color( image.getRGB(j, i) + (ran.nextInt(30))).getRGB() <<ran.nextInt(25));
                }
            }
            return tmp;
        }
        
        private BufferedImage getScaledImage(Image srcImg, int w, int h){
            BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(srcImg, 0, 0, w, h, null);
            g2.dispose();
            return resizedImg;
        }
        
        float lerp(float a, float b, float f)
        {
            return a + f * (b - a);
        }
    }

}
