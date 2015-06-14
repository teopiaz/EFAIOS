package it.polimi.ingsw.cg15.gui.client;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel{
    ImageIcon icon;
    List<JLabel> cardList = new ArrayList<JLabel>();
    JLabel label2;
    JLabel labelDefenseItemCard;
    
    Map<String,JLabel> cardMap = new HashMap<String, JLabel>();
    
    public CardPanel(){
        
        
       
        
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        
        BufferedImage spotLightCard = ImageLoader.load("defenseItemCard");
        spotLightCard = getScaledImage(spotLightCard, 70, 95);
             icon = new ImageIcon(spotLightCard);
             JLabel label = new JLabel(icon);
              label2 = new JLabel(icon);
             label.setPreferredSize(new Dimension(60,80));
            cardList.add(label);
            label.addMouseListener(new MouseAdapter() {
              @Override
            public void mouseClicked(MouseEvent me) {
                System.out.println("CLICKED"+cardList.size());
                JLabel label = new JLabel(icon);
                cardList.add(label);
                add(label2);
                revalidate();
                
              }
            });
            
            
            
          /*  BufferedImage defenseItemCard = ImageLoader.load("defenseItemCard");
            defenseItemCard = getScaledImage(defenseItemCard, 60, 80);
                 icon = new ImageIcon(defenseItemCard);
                 labelDefenseItemCard.setPreferredSize(new Dimension(60,80));
                cardList.add(label);
                labelDefenseItemCard.addMouseListener(new MouseAdapter() {
                  public void mouseClicked(MouseEvent me) {
                    System.out.println("CLICKED"+cardList.size());
                    JLabel label = new JLabel(icon);
                    cardList.add(label);
                    revalidate();
                    
                  }
                });
            
            */
            add(label);
            
            for (JLabel jLabel : cardList) {
                add(jLabel);

            }
            

    
    }
    
    
    
    
    
    private BufferedImage getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

}






