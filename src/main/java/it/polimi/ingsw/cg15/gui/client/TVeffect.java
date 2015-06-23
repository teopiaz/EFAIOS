package it.polimi.ingsw.cg15.gui.client;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
                    Logger.getLogger(TVeffect.class.getName()).log(Level.SEVERE, "Exception", ex);

                }
                Width=1280;
                Height=720;
                frame = new JFrame("Escape From Alien In Outer Space");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new Pannello());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
    
    /**
     * @author MMP - LMR
     * Another panel.
     */
    private class Pannello extends JPanel {
        
        /**
         * The serial version UID.
         */
        private static final long serialVersionUID = -8454735757922056844L;
        
        /**
         * The linear interpolation amount.
         */
        float lerpAmount = 0;
        
        /**
         * The linear interpolation Scale.
         */
        float lerpScale = 0;
        
        /**
         * The linear interpolation shrink.
         */
        float lerpShrink = 0;
        
        /**
         * The Tv Height.
         */
        int tvheight=0;
        
        /**
         * The Tv Width.
         */
        int tvwidth=0;
        
        /**
         * The Tv activation.
         */
        boolean tvstate = false;
        
        /**
         * Width.
         */
        int width;
        
        /**
         * A timer.
         */
        Timer timer ;
        
        /**
         * Image, Scanline and Noise.
         */
        BufferedImage image,scanline,noise;
        
        /**
         * A random number.
         */
        Random ran = new Random();
        
        /**
         * Second random number.
         */
        Random ran2 = new Random();
        
        /**
         * End intro.
         */
        boolean endIntro=false;
        
        /**
         * The panel.
         */
        public Pannello(){
            setPreferredSize(new Dimension(Width,Height));
            setBackground(Color.BLACK);
            image = ImageLoader.load("logo720alpha");
            image = getScaledImage(image, Width/3,Height/3); 
            image = getScaledImage(image, Width,Height );    
            scanline = ImageLoader.load("scanlines");
            scanline = getScaledImage(scanline, Width, Height);
            image = getScaledImage(image, Width, Height);
            image = colorShift(image,4);
            final Timer timershift = new Timer(10,new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    image = colorShift(image,ran.nextInt(2)-ran2.nextInt(2));
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
                                scaleIt(0.02F,0.2F,2,image.getHeight());  // turning on
                            else
                                scaleIt(0.1F,0.2F,image.getHeight(),1);   // turning off
                            image= getScaledImage(image,tvwidth,tvheight);
                            repaint();
                            if(image.getWidth() < 100 && image.getHeight()<10 && !endIntro){
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

        /**
         * Paint the component.
         * @param g The graphics.
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D)g;
            g2d.drawImage(image, (Width/2)-image.getWidth()/2, (Height/2)-image.getHeight()/2, this);
            g2d.drawImage((Image)noise, 0,0,null);
            g2d.drawImage((Image)scanline, 0,0,null);
        }

        /**
         * @param image A image.
         * @param offsetArg A offset.
         * @return color shift on the image.
         */
        public BufferedImage colorShift(BufferedImage image,int offsetArg){
            int offset = offsetArg;
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

        /**
         * Scale the gui.
         * @param amountA Form factor parameter.
         * @param amountB Form factor parameter.
         * @param valueC Form factor parameter.
         * @param valueD Form factor parameter.
         */
        public void scaleIt(float amountA, float amountB, int valueC, int valueD){
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

        /**
         * @param image The image.
         * @return Noise.
         */
        private BufferedImage noise(BufferedImage image){
            BufferedImage tmp = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            int i,j;
            Random ran3 = new Random();
            for(i=0;i<image.getHeight();i+=3){
                for(j=0;j<image.getWidth();j+=2){
                    tmp.setRGB(j, i, new Color( image.getRGB(j, i) + (ran3.nextInt(30))).getRGB() <<ran3.nextInt(25));
                }
            }
            return tmp;
        }
        
        /**
         * Return scaled image.
         * @param srcImg The source image.
         * @param w Form factor parameter.
         * @param h Form factor parameter.
         * @return The scaled image.
         */
        private BufferedImage getScaledImage(Image srcImg, int w, int h){
            BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = resizedImg.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(srcImg, 0, 0, w, h, null);
            g2.dispose();
            return resizedImg;
        }
        
        /**
         * @param a Form factor parameter.
         * @param b Form factor parameter.
         * @param f Form factor parameter.
         * @return Lerp.
         */
        float lerp(float a, float b, float f)
        {
            return a + f * (b - a);
        }
        
        /**
         * @param stream The output stream.
         * @throws java.io.IOException
         */
        private void writeObject(java.io.ObjectOutputStream stream) throws java.io.IOException {
            throw new java.io.NotSerializableException( getClass().getName() );
        }

        /**
         * @param stream The input stream.
         * @throws java.io.IOException
         * @throws ClassNotFoundException
         */
        private void readObject(java.io.ObjectInputStream stream) throws java.io.IOException, ClassNotFoundException {
            throw new java.io.NotSerializableException( getClass().getName() );
        }

    }

}
