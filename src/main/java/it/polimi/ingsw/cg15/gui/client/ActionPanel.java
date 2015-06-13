package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.Event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    public ActionPanel() {

        List<JButton> buttonList = new ArrayList<JButton>();
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

        buttonList.add(btnMove);
        buttonList.add(btnAttack);
        buttonList.add(btnCard);
        buttonList.add(btnEndTurn);

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

        for (JButton btn : buttonList) {
            btn.setFont(CFont.getFont("TopazPlus"));
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

}
