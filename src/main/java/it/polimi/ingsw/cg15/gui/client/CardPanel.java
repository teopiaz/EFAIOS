package it.polimi.ingsw.cg15.gui.client;


import it.polimi.ingsw.cg15.NetworkHelper;

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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CardPanel extends JPanel{
    ImageIcon icon;
  //  List<JLabel> cardList = new ArrayList<JLabel>();
    JLabel label2;
    JLabel labelDefenseItemCard;
    
    NetworkHelper networkHelper = NetworkHelper.getInstance();
    Map<String,JLabel> cardMap = new HashMap<String, JLabel>();
    
    public CardPanel(){
        

       
        
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        
        
        BufferedImage defenseCard = ImageLoader.load("defenseItemCard");
        defenseCard = getScaledImage(defenseCard, 70, 95);
        ImageIcon defenseIcon = new ImageIcon(defenseCard);
        JLabel defenseLabel = new JLabel(defenseIcon);
        defenseLabel.setPreferredSize(new Dimension(60,80));
        cardMap.put("defense",defenseLabel);


        BufferedImage spotLightCard = ImageLoader.load("spotlightItemCard");
        spotLightCard = getScaledImage(spotLightCard, 70, 95);
        ImageIcon spotLightIcon = new ImageIcon(spotLightCard);
        JLabel spotLightLabel = new JLabel(spotLightIcon);
        spotLightLabel.setPreferredSize(new Dimension(60,80));
        cardMap.put("spotlight",spotLightLabel);


        
        cardMap.put("defense",spotLightLabel);
        cardMap.put("adrenaline",spotLightLabel);
        cardMap.put("attack",spotLightLabel);
        cardMap.put("sedatives",spotLightLabel);
        
	

		



        spotLightLabel.addMouseListener(new MouseAdapter() {
              @Override
            public void mouseClicked(MouseEvent me) {
                
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
            
           
            

    
    }
    
    public void getCardsList() {
     
        List<String> cardList = new ArrayList<String>();

        for (JLabel	cardLabel : cardMap.values()) {
        	cardLabel.setVisible(true);
        }
     
        if(networkHelper.isMyTurn()){       

            System.out.println("è il mio turno prendo la lista delle carte");
            cardList = networkHelper.getAvailableCardsList();

            for (String card : cardList) {
                System.out.println(card);
                if(cardMap.containsKey(card))
            		add(cardMap.get(card));
            }
            revalidate();
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






