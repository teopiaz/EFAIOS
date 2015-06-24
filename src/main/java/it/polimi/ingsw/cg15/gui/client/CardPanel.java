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
     * Defence item card label.
     */
    JLabel labelDefenseItemCard;

    /**
     * The network helper instance.
     */
    transient NetworkHelper networkHelper = NetworkHelper.getInstance();

    /**
     * A table with the item card.
     */
    transient Map<String,JLabel> cardMap = new HashMap<String, JLabel>();

    /**
     * Constant for the spotlight state.
     */
    private static final int SPOT_STATE = 3;
    
    /**
     * Constant for the waiting state.
     */
    private static final int WAITING_STATE = 2;
    
    /**
     * Constant for the select state.
     */
    private static final int SELECT_STATE = 1;

    /**
     * Value for the spotlight finite state machine.
     */
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
                SidePanel.getActionPanel().printMsg("Used Adrenaline card");
                revalidate();

            }
        });

        attackLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {

                networkHelper.useCard("attack");
                SidePanel.getActionPanel().printMsg("Used Attack card");
                revalidate();

            }
        });

        sedativesLabel.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {

                networkHelper.useCard("sedatives");
                SidePanel.getActionPanel().printMsg("Used Sedatives card");

                revalidate();

            }
        });



        spotLightLabel.addMouseListener(new MouseAdapter() {


            @Override
            public void mouseClicked(MouseEvent me) {

                if(state==WAITING_STATE){ 
                    state=SELECT_STATE;
                }

                switch(state){

                case SELECT_STATE:
                    SidePanel.getActionPanel().printMsg("Select a sector to spot players");
                    SidePanel.getMainPanel().getSpotLayer().enableSpot();
                    state=SPOT_STATE;
                    break;

                case SPOT_STATE:
                    spotStateSwitchCase();
                    break;
                    
                default:
                    revalidate();


                }
            }

            private void spotStateSwitchCase() {

                if(SidePanel.getMainPanel().getMapPanel().isSelected()){
                    String target = SidePanel.getMainPanel().getMapPanel().getSelectedSectorLabel();
                    networkHelper.spotlight(target);
                    SidePanel.getActionPanel().printMsg("Used Spotlight card in sector "+target);
                    state =WAITING_STATE;
                    SidePanel.getMainPanel().getSpotLayer().disableSpot();

                }else{
                    SidePanel.getActionPanel().printMsg("Select a sector to spot players");
                }
                revalidate();
                
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

            cardList = networkHelper.getAvailableCardsList();
            for (String card : cardList) {
                if(cardMap.containsKey(card))
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
