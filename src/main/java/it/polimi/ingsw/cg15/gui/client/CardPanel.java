package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author MMP - LMR
 * The card panel GUI. It presents the user the three Item cards he has available.
 */
public class CardPanel extends JPanel{

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = -3787962420496021239L;

    /**
     * The image icon.
     */
    ImageIcon icon;


    /**
     * TODO completare
     */
    JLabel label2;

    /**
     * Defence item card label.
     */
    JLabel labelDefenseItemCard;

    /**
     * The network helper instance.
     */
    NetworkHelper networkHelper = NetworkHelper.getInstance();

    /**
     * A table with the item card.
     */
    Map<String,JLabel> cardMap = new HashMap<String, JLabel>();

	private final static int SPOT_STATE = 3;
	private final static int WAITING_STATE = 2;
	private final static int SELECT_STATE = 1;
	
	private static int state =WAITING_STATE;
    

    /**
     * The constructor for the Card Panel.
     */
    public CardPanel(){
        
        setPreferredSize(new Dimension(300, 110));
        setMaximumSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setBackground(Color.BLACK);
        
        setLayout(new FlowLayout());
     
        BufferedImage defenseCard = ImageLoader.load("defenseItemCard");
        defenseCard = getScaledImage(defenseCard, 70, 95);
        ImageIcon defenseIcon = new ImageIcon(defenseCard);
        JLabel defenseLabel = new JLabel(defenseIcon);
        defenseLabel.setPreferredSize(new Dimension(70,95));
        defenseLabel.setToolTipText("Difesa");
        cardMap.put("defense",defenseLabel);
        add(defenseLabel);



        BufferedImage spotLightCard = ImageLoader.load("spotlightItemCard");
        spotLightCard = getScaledImage(spotLightCard, 70, 95);
        ImageIcon spotLightIcon = new ImageIcon(spotLightCard);
        JLabel spotLightLabel = new JLabel(spotLightIcon);
        spotLightLabel.setPreferredSize(new Dimension(70,95));
        spotLightLabel.setToolTipText("Spotlight");
        cardMap.put("spotlight",spotLightLabel);
        add(spotLightLabel);

        
        
        BufferedImage adrenalineCard = ImageLoader.load("adrenalineItemCard");
        adrenalineCard = getScaledImage(adrenalineCard, 70, 95);
        ImageIcon adrenalineIcon = new ImageIcon(adrenalineCard);
        JLabel adrenalineLabel = new JLabel(adrenalineIcon);
        adrenalineLabel.setPreferredSize(new Dimension(70,95));
        adrenalineLabel.setToolTipText("Adrenalina");
        cardMap.put("adrenaline",adrenalineLabel);
        add(adrenalineLabel);
        
        
        BufferedImage sedativesCard = ImageLoader.load("sedativesItemCard");
        sedativesCard = getScaledImage(sedativesCard, 70, 95);
        ImageIcon sedativesIcon = new ImageIcon(sedativesCard);
        JLabel sedativesLabel = new JLabel(sedativesIcon);
        sedativesLabel.setPreferredSize(new Dimension(70,95));
        sedativesLabel.setToolTipText("Sedativi");
        cardMap.put("sedatives",sedativesLabel);
        add(sedativesLabel);

        
        BufferedImage attackCard = ImageLoader.load("attackItemCard");
        attackCard = getScaledImage(attackCard, 70, 95);
        ImageIcon attackIcon = new ImageIcon(attackCard);
        JLabel attackLabel = new JLabel(attackIcon);
        attackLabel.setPreferredSize(new Dimension(70,95));
        attackLabel.setToolTipText("Attacco");
        cardMap.put("attack",attackLabel);
        add(attackLabel);


        
        for (JLabel cardlabel : cardMap.values()) {
        	cardlabel.setVisible(false);
    }
	

        adrenalineLabel.addMouseListener(new MouseAdapter() {

            @Override
          public void mouseClicked(MouseEvent me) {
             
              networkHelper.useCard("adrenaline");
          	SidePanel.getActionPanel().printMsg("Hai usato la carta Adrenalina");
              revalidate();
              
            }
          });
        
        attackLabel.addMouseListener(new MouseAdapter() {

            @Override
          public void mouseClicked(MouseEvent me) {
             
              networkHelper.useCard("attack");
          	SidePanel.getActionPanel().printMsg("Hai usato la carta Attacco");
              revalidate();
              
            }
          });
        
        sedativesLabel.addMouseListener(new MouseAdapter() {

            @Override
          public void mouseClicked(MouseEvent me) {
             
              networkHelper.useCard("sedatives");
            	SidePanel.getActionPanel().printMsg("Hai usato la carta Sedativi");

              revalidate();
              
            }
          });



        spotLightLabel.addMouseListener(new MouseAdapter() {


			@Override
            public void mouseClicked(MouseEvent me) {
            

			switch(state){
				
			case WAITING_STATE:	
				state=SELECT_STATE;
            
            case SELECT_STATE:
            	
            	SidePanel.getMainPanel().getSpotLayer().enableSpot();
            	state=SPOT_STATE;
            	break;
          
            case SPOT_STATE:
            	
              	if(SidePanel.getMainPanel().getMapPanel().isSelected()){
					String target = SidePanel.getMainPanel().getMapPanel().getSelectedSectorLabel();
                  networkHelper.spotlight(target);
              	SidePanel.getActionPanel().printMsg("Hai usato la carta SpotLight nel settore "+target);
              	state =WAITING_STATE;
            	SidePanel.getMainPanel().getSpotLayer().disableSpot();

              	}else{
				 	SidePanel.getActionPanel().printMsg("Seleziona un settore");
              	}
                  revalidate();
                
              }
        }
            });
            
    
    }

    /**
     * Return the card list.
     */
    public void getCardsList() {
        List<String> cardList = new ArrayList<String>();

       
        if(networkHelper.isMyTurn()){  
        	
        	 for (JLabel cardlabel : cardMap.values()) {
             	cardlabel.setVisible(false);
             }
             revalidate();


            System.out.println("è il mio turno prendo la lista delle carte");
            cardList = networkHelper.getAvailableCardsList();
            for (String card : cardList) {
                System.out.println(card);
                if(cardMap.containsKey(card))
                	System.out.println(cardMap.get(card));
                cardMap.get(card).setVisible(true);

            }
            revalidate();
        }
    }

    /**
     * Return a scaled card image.
     * @param srcImg The source image
     * @param w Width
     * @param h Height
     * @return The image resized.
     */
    private BufferedImage getScaledImage(Image srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

}
