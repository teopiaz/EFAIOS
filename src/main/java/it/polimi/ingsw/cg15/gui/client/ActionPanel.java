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

    private static final int ASKSECTOR_STATE = 2;
    private static final int WAITING_STATE = 1;

    private int state =WAITING_STATE;


    /**
     * 
     */
    private static final long serialVersionUID = -8240130326507614510L;
    JLabel actionLabel;
    NetworkHelper networkHelper = NetworkHelper.getInstance();
    Map<String,JButton> buttonMap = new HashMap<String,JButton>();
    static int a = 0;


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

                if(state==WAITING_STATE){
                    actionMove();
                    SidePanel.getMainPanel();
                    ClientGameGUI.getMapPanel().setSelected(false);
                    String position = networkHelper.getPlayerPosition();
                    SidePanel.getMainPanel();
                    ClientGameGUI.getMapPanel().setPosition(position);
                }

                if(state==ASKSECTOR_STATE){
                    actionAsk();
                    SidePanel.getMainPanel();
                    ClientGameGUI.getMapPanel().setSelected(false);
                }



            }

            private void actionAsk() {
                Event response=null;

                SidePanel.getMainPanel();
                String target = ClientGameGUI.getMapPanel().getSelectedSectorLabel();
                SidePanel.getMainPanel();
                if(ClientGameGUI.getMapPanel().isSelected()){


                    response = networkHelper.askSector(target);
                    if (response.actionResult()) {
                        actionLabel.setText("Hai fatto rumore nel settore "+ target);
                        state=WAITING_STATE;
                    }else{
                        actionLabel.setText("Errore:"+response.getRetValues().get(Event.ERROR));
                    }

                }else{
                    actionLabel.setText("Seleziona un settore");

                }

            }

            private void actionMove() {
                Event response=null;
                SidePanel.getMainPanel();
                if(ClientGameGUI.getMapPanel().isSelected()){
                    SidePanel.getMainPanel();
                    String target = ClientGameGUI.getMapPanel().getSelectedSectorLabel();
                    response = networkHelper.move(target);
                    if (response.actionResult()) {
                        actionLabel.setText("Ti sei spostato nel settore "+ networkHelper.getCurrentPosition());

                        SidePanel.getMainPanel();
                        ClientGameGUI.getMapPanel().setSelected(false);
                        if (response.getRetValues().containsKey("asksector")) {
                            state=ASKSECTOR_STATE;
                            actionLabel.setText("Seleziona un settore dove fare rumore");

                        }
                    }else{
                        actionLabel.setText("Errore:"+response.getRetValues().get(Event.ERROR));

                    }

                }
                else{
                    actionLabel.setText("Seleziona un settore");

                }

            }
        });

        btnEndTurn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        Event response = networkHelper.endTurn();
                        if (response.actionResult()) {
                            actionLabel.setText("Turno Terminato");
                        } else {
                            actionLabel.setText("Errore");
                        }


                    }
                }).start();


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
        List<String> actionList = new ArrayList<String>();

        for (JButton btn : buttonMap.values()) {
            btn.setEnabled(false);
        }

        if(networkHelper.isMyTurn()){       
            int player = networkHelper.getTurnInfo();
            actionLabel.setText("E' il turno del giocatore: " + player);
            actionList = networkHelper.getAvailableActionsList();

            for (String action : actionList) {
                if(buttonMap.containsKey(action))
                    buttonMap.get(action).setEnabled(true);
            }
            if(actionList.contains("asksector")){
                state=ASKSECTOR_STATE;
                for (JButton btn : buttonMap.values()) {
                    btn.setEnabled(false);
                }
                buttonMap.get("move").setEnabled(true);
            }
        }
    }

}