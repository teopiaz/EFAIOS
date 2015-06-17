package it.polimi.ingsw.cg15.gui.client;

//import jaco.mp3.player.MP3Player;

import it.polimi.ingsw.cg15.NetworkHelper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * The class that contains the graphics for the side panel.
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
    
    // MP3Player player;
    
    /**
     * The UID of serial version.
     */
    private static final long serialVersionUID = 1L;
    
    private DefaultListModel<String> selections = new DefaultListModel<>();

    {
        selections.addElement("In attesa per l'inizio del match...");
    }

    /**
     * The constructor of the side panel.
     */
    public SidePanel() {
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBackground(Color.black);
        logPanel = new LogPanel();
        chatPanel = new ChatPanel();
        actionPanel = new ActionPanel();
        cardPanel = new CardPanel();
        // MusicPlayer musicPlayer = new MusicPlayer();
        // Thread mp3player = new Thread(musicPlayer);
        // mp3player.start();
        // add(musicPlayer);
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
         * The serial UID version.
         */
        private static final long serialVersionUID = 1L;

        /**
         * A list.
         */
        JList<String> list;

        /**
         * The log panel.
         */
        public LogPanel() {

            setPreferredSize(new Dimension(350, 250));
            setMaximumSize(getPreferredSize());
            setMinimumSize(getPreferredSize());
            list = new JList<String>(selections);
            list.setBackground(Color.LIGHT_GRAY);
            list.setCellRenderer(new RendererCelleLog());
            list.setSelectedIndex(0);
            list.setBorder(BorderFactory.createLineBorder(Color.black));
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            list.setFont(CFont.getFont("TopazPlus"));
            Border emptyBorder = BorderFactory.createEmptyBorder();
            scrollPane.setBorder(emptyBorder);
            /*
             * scrollPane.setPreferredSize(new Dimension(300,200));
             * scrollPane.setMaximumSize(getPreferredSize());
             * scrollPane.setMinimumSize(getPreferredSize());
             */
            setBorder(BorderFactory.createLineBorder(Color.red));
            add(scrollPane, BorderLayout.EAST);
            setBackground(Color.black);
        }
        
        /**
         * Add a string to the logger.
         * @param s The string to add.
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
        private static final long serialVersionUID = 1L;
        private NetworkHelper netHelper;
        final JTextArea textArea;
        
        /**
         * The chat panel.
         */
        public ChatPanel() {
            netHelper = NetworkHelper.getInstance();
            textArea = new JTextArea(5, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);
            // scrollPane.setPreferredSize(new Dimension(380, 100));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textArea.setFont(CFont.getFont("TopazPlus"));
            textArea.setText("");
            final JTextField userInputField = new JTextField(30);
            userInputField.setFont(CFont.getFont("TopazPlus"));
            userInputField.setMaximumSize(new Dimension(350, 50));
            userInputField.addActionListener(new ActionListener() {
                
                /**
                 * @param e The event to perform.
                 */
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
         * @param msg The message to add as chat message.
         */
        public void addToChat(String msg) {
            textArea.append(msg + "\n");
            textArea.updateUI();
            textArea.setCaretPosition(textArea.getDocument().getLength());
        }
    }

    /*
     * private class MusicPlayer extends JPanel implements Runnable{
     * 
     * public MusicPlayer(){ player = new MP3Player(); player.setRepeat(true);
     * 
     * player.addToPlayList(new
     * File("./music/SnD_-_TuneUp_Utilities_2006_5.xcrk.mp3"));
     * player.addToPlayList(new
     * File("./music/BLeH_-_Juiced_cheat_codes_enabler.mp3"));
     * player.addToPlayList(new File("./music/keygensong.mp3"));
     * player.addToPlayList(new File("./music/AiR_-_Truepianoskg.mp3"));
     * player.addToPlayList(new File("./music/AAOCG_-_XnView_1.xxkg.mp3"));
     * player.addToPlayList(new File("./music/keygensong.mp3"));
     * player.addToPlayList(new File("./music/SnD_-_Zealot_Productskgs.mp3"));
     * //player.setShuffle(true);
     * //player.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
     * setPreferredSize(new Dimension(350,30) );
     * setMaximumSize(getPreferredSize()); setBackground(Color.BLACK);
     * this.add(player); }
     * 
     * @Override public void run() { player.play(); // player.pause(); }
     * }
     */

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

}
