package it.polimi.ingsw.cg15.gui.client;


import it.polimi.ingsw.cg15.NetworkHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * @author MMP - LMR
 * The side panel GUI.
 */
public class SidePanel extends JPanel {

    /**
     * The action panel.
     */
    private static ActionPanel actionPanel;
    
    /**
     * The log panel.
     */
    private static LogPanel logPanel;
    
    /**
     * The chat panel.
     */
    private static ChatPanel chatPanel;
    
    /**
     * The card panel.
     */
    private static CardPanel cardPanel;
    
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * Default list.
     */
    private DefaultListModel<String> selections = new DefaultListModel<>();
    
    /**
     * Client Game Gui.
     */
    private static ClientGameGUI mainPanel;


    /**
     * Side Panel
     * @param clientGameGUI The client game GUI.
     */
    public SidePanel(ClientGameGUI clientGameGUI) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        mainPanel = clientGameGUI;

        setBackground(Color.black);
        selections.addElement("Waiting for the beginning of the match...");


        logPanel = new LogPanel();
        chatPanel = new ChatPanel();
        actionPanel = new ActionPanel();
        cardPanel = new CardPanel();

        add(logPanel);
        add(cardPanel);
        add(actionPanel);
        add(chatPanel);

    }

    /**
     * @author MMP - LMR
     * The log panel.
     */
    class LogPanel extends JPanel {

        /**
         * Serial Version UID.
         */
        private static final long serialVersionUID = 1L;

        /**
         * List.
         */
        JList<String> list;

        /**
         * The log panel.
         */
        public LogPanel() {

            setPreferredSize(new Dimension(310, 205));
            setMaximumSize(getPreferredSize());
            setMinimumSize(getPreferredSize());

            list = new JList<String>(selections);
            list.setBackground(Color.LIGHT_GRAY);

            list.setCellRenderer(new RendererCelleLog());
            list.setSelectedIndex(0);
            list.setBorder(BorderFactory.createLineBorder(Color.black));
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            list.setFont(CFont.getFont(CFont.TOPAZPLUS));

            Border emptyBorder = BorderFactory.createEmptyBorder();
            scrollPane.setBorder(emptyBorder);


            scrollPane.setPreferredSize(new Dimension(300,200));
            scrollPane.setMaximumSize(getPreferredSize());
            scrollPane.setMinimumSize(getPreferredSize());

            setBorder(BorderFactory.createLineBorder(Color.red));
            add(scrollPane, BorderLayout.EAST);
            setBackground(Color.black);
        }

        /**
         * @param s The string to add as log.
         */
        public void addToLog(String s) {
            selections.insertElementAt(s, 0);

        }
    }

    /**
     * @author MMP - LMR
     * The chat panel.
     */
    class ChatPanel extends JPanel {

        /**
         * Serial Version UID.
         */
        private static final long serialVersionUID = 1L;
        
        /**
         * The network helper.
         */
        private transient NetworkHelper netHelper;
        
        /**
         * Text area.
         */
        final JTextArea textArea;

        /**
         * The chat panel.
         */
        public ChatPanel() {
            netHelper = NetworkHelper.getInstance();
            textArea = new JTextArea(5, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textArea.setFont(CFont.getFont(CFont.TOPAZPLUS));

            textArea.setBackground(Color.BLACK);
            textArea.setForeground(Color.GREEN);


            textArea.setText("");

            final JTextField userInputField = new JTextField(30);
            userInputField.setFont(CFont.getFont(CFont.TOPAZPLUS));
            userInputField.setMaximumSize(new Dimension(350, 50));

            userInputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String msgToSend = userInputField.getText();
                    netHelper.sendChat(msgToSend);
                    userInputField.setText("");

                }
            });

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(userInputField, SwingConstants.CENTER);
            this.add(scrollPane, SwingConstants.CENTER);

        }

        /**
         * Add a message to the chat.
         * @param msg Message to add as chat.
         */
        public void addToChat(String msg) {
            char[] msgArray = msg.toCharArray();
            for(int i=0;i<msgArray.length;i++){
                textArea.append(msgArray[i]+"");
                textArea.updateUI();
                textArea.setCaretPosition(textArea.getDocument().getLength());
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    Logger.getLogger(SidePanel.class.getName()).log(Level.SEVERE, "InterruptedException", e);

                }
            }
            textArea.append("\n");
            textArea.updateUI();
            textArea.setCaretPosition(textArea.getDocument().getLength());

        }

    }

    
    /**
     * @return The action panel.
     */
    public static ActionPanel getActionPanel() {
        return actionPanel;

    }

    /**
     * @return The log panel.
     */
    public static LogPanel getLogPanel() {
        return logPanel;

    }

    /**
     * @return The chat panel.
     */
    public static ChatPanel getChatPanel() {
        return chatPanel;
    }

    /**
     * @return The card panel.
     */
    public static CardPanel getCardPanel() {
        return cardPanel;

    }
    /**
     * @return The main panel of the client game GUI.
     */
    public static ClientGameGUI getMainPanel() {
        return mainPanel;

    }


}
