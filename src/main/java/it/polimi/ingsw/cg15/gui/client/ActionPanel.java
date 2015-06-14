package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.Event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionPanel extends JPanel {

    /**
     * 
     */
    private static final long serialVersionUID = -8240130326507614510L;
    JLabel actionLabel;
    NetworkHelper networkHelper = NetworkHelper.getInstance();
    Map<String,JButton> buttonMap = new HashMap<String,JButton>();


    public ActionPanel() {

        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        JButton btnMove = new JButton("MOVE");
        JButton btnAttack = new JButton("ATTACK");
        JButton btnCard = new JButton("USE CARD");
        JButton btnEndTurn = new JButton("END TURN");

        btnCard.setEnabled(false);

        buttonMap.put("move", btnMove);
        buttonMap.put("attack", btnAttack);
        buttonMap.put("endturn", btnEndTurn);
        buttonMap.put("usecard", btnCard);



        
        btnAttack.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                
                Event response = networkHelper.attack();
                if(response.actionResult()){
                    actionLabel.setText("Hai attaccato nel settore "+ networkHelper.getCurrentPosition());
                    getActionsList();
                }else{
                    actionLabel.setText("Errore: "+ response.getRetValues().get(Event.ERROR));

                }
            }
        });
        
        btnMove.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int player = networkHelper.getTurnInfo();
                actionLabel.setText("è il turno del giocatore: " + player);

            }
        });

        btnEndTurn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Event response = networkHelper.endTurn();
                if (response.actionResult()) {
                    int player = networkHelper.getTurnInfo();

                    actionLabel.setText("è il turno del giocatore: " + player);
                } else {
                    actionLabel.setText("Errore");

                }
            }
        });

        for (JButton btn : buttonMap.values()) {
            btn.setFont(CFont.getFont("TopazPlus"));
            btn.setEnabled(false);
            buttonPanel.add(btn);

        }
        add(buttonPanel);

        JPanel actionLabelPanel = new JPanel();
        actionLabel = new JLabel();
        actionLabelPanel.setBackground(Color.BLACK);
        actionLabel.setFont(CFont.getFont("TopazPlus"));
        actionLabel.setForeground(Color.WHITE);
        actionLabelPanel.setPreferredSize(new Dimension(100, 40));
        actionLabelPanel.setMaximumSize(getPreferredSize());
        actionLabelPanel.add(actionLabel);

        add(actionLabelPanel);
    }

    public void printMsg(String msg) {
        actionLabel.setText(msg);
    }

    public void getActionsList() {
        System.out.println("SONO IL GIOCATORE: "+networkHelper.getPlayerNumber() );
        List<String> actionList = new ArrayList<String>();

        for (JButton btn : buttonMap.values()) {
            btn.setEnabled(false);
        }
     
        System.out.println(networkHelper.isMyTurn()+" ");
        if(networkHelper.isMyTurn()){       

            System.out.println("è il mio turno prendo la lista delle azioni");
            actionList = networkHelper.getAvailableActionsList();

            for (String action : actionList) {
                System.out.println(action);
                if(buttonMap.containsKey(action))
                buttonMap.get(action).setEnabled(true);
            }
        }
    }

}
