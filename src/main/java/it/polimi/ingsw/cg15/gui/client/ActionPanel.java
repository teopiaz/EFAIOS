package it.polimi.ingsw.cg15.gui.client;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ActionPanel extends JPanel{

    public ActionPanel(){
        List<JButton> buttonList = new ArrayList<JButton>();
        setBackground(Color.BLACK);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        JButton btnMove = new JButton("MOVE");
        JButton btnAttack = new JButton("ATTACK");
        JButton btnCard = new JButton("USE CARD");
        btnCard.setEnabled(false);

        buttonList.add(btnMove);
        buttonList.add(btnAttack);
        buttonList.add(btnCard);





        for (JButton btn : buttonList) {
            btn.setFont(CFont.getFont("TopazPlus"));
            buttonPanel.add(btn);
        }
        add(buttonPanel);


        JPanel actionLabelPanel = new JPanel();
        JLabel actionLabel = new JLabel();
        actionLabelPanel.setBackground(Color.BLACK);
        actionLabel.setFont(CFont.getFont("TopazPlus"));
        actionLabel.setText("Seleziona una cella dove muoversi");
        actionLabel.setForeground(Color.WHITE);
        actionLabelPanel.add(actionLabel);


        add(actionLabelPanel);
    }


}
