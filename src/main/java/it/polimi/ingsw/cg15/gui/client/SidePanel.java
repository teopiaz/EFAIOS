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

public class SidePanel extends JPanel {

    //MP3Player player;
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private DefaultListModel<String> selections = new DefaultListModel<>();

    {
        selections.addElement("Giocatore 1: attacca in F11 nessuno colpito");
        selections.addElement("Giocatore 4: rumore in C05 ");
        selections.addElement("Giocatore 3: si sposta in zona sicura"); 
        selections.addElement("Giocatore 2: si sposta in zona sicura" );
        selections.addElement("Giocatore 1: attacca in F11 nessuno colpito");
        selections.addElement("Giocatore 4: rumore in C05 ");
        selections.addElement("Giocatore 3: si sposta in zona sicura"); 
        selections.addElement("iocatore 2:  rumore in L09" );
        selections.addElement("Giocatore 1: attacca in F11 nessuno colpito");
    }
    public SidePanel(){
        super();
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        setBackground(Color.black);

        JPanel logPanel = new LogPanel();
        JPanel chatPanel = new ChatPanel();
        JPanel actionPanel = new ActionPanel();
        JPanel cardPanel = new CardPanel();
        //   MusicPlayer musicPlayer = new MusicPlayer();
        //	Thread mp3player = new Thread(musicPlayer);
        //	mp3player.start();
        //	add(musicPlayer);
        add(logPanel);
        add(cardPanel);
        add(actionPanel);
        add(chatPanel);


    }


    private class LogPanel extends JPanel{

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public LogPanel(){

            setPreferredSize(new Dimension(350,250));
            setMaximumSize(getPreferredSize());
            setMinimumSize(getPreferredSize());

            JList<String> list = new JList<String>(selections);
            list.setBackground(Color.black);
            list.setCellRenderer(new RendererCelleLog());
            list.setSelectedIndex(0);
            list.setBorder(BorderFactory.createLineBorder(Color.black));
            JScrollPane scrollPane = new JScrollPane(list);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            list.setFont(CFont.getFont("TopazPlus"));

            Border emptyBorder = BorderFactory.createEmptyBorder();
            scrollPane.setBorder(emptyBorder);
            
/*
            scrollPane.setPreferredSize(new Dimension(300,200));
            scrollPane.setMaximumSize(getPreferredSize());
            scrollPane.setMinimumSize(getPreferredSize());
            */
            setBorder(BorderFactory.createLineBorder(Color.red));
            add(scrollPane,BorderLayout.EAST);
            setBackground(Color.black);
        }
    }


    private class ChatPanel extends JPanel {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private NetworkHelper netHelper;
        final JTextArea textArea;
        public ChatPanel(){
            netHelper = NetworkHelper.getInstance();
            textArea = new JTextArea(5, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);
            // scrollPane.setPreferredSize(new Dimension(380, 100));
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            textArea.setEditable(false);
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            textArea.setFont(CFont.getFont("TopazPlus"));


            textArea.setText("Giocatore1: secondo me si trova nella cella H12!!");

            final JTextField userInputField = new JTextField(30);
            userInputField.setFont(CFont.getFont("TopazPlus"));
            userInputField.setMaximumSize(new Dimension(350, 50));

            userInputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selections.insertElementAt(userInputField.getText(), 0);

                }
            });

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            this.add(userInputField, SwingConstants.CENTER);
            this.add(scrollPane, SwingConstants.CENTER);

        }

        public void addToChat(String msg){
            textArea.append(msg+"\n");
        }


    }
    /*

    private class MusicPlayer extends JPanel  implements Runnable{


		public MusicPlayer(){
			player = new MP3Player();
			player.setRepeat(true);

			player.addToPlayList(new File("./music/SnD_-_TuneUp_Utilities_2006_5.xcrk.mp3"));
			player.addToPlayList(new File("./music/BLeH_-_Juiced_cheat_codes_enabler.mp3"));
			player.addToPlayList(new File("./music/keygensong.mp3"));
			player.addToPlayList(new File("./music/AiR_-_Truepianoskg.mp3"));
			player.addToPlayList(new File("./music/AAOCG_-_XnView_1.xxkg.mp3"));
			player.addToPlayList(new File("./music/keygensong.mp3"));
			player.addToPlayList(new File("./music/SnD_-_Zealot_Productskgs.mp3"));
			//player.setShuffle(true);
			//player.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
			setPreferredSize(new Dimension(350,30) );
			setMaximumSize(getPreferredSize());
			setBackground(Color.BLACK);
			this.add(player);
		}

		@Override
		public void run() {
			player.play();
		//	player.pause();
		}




    }
     */

}
