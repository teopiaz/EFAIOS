package it.polimi.ingsw.cg15.gui.server;

import it.polimi.ingsw.cg15.networking.Server;
import it.polimi.ingsw.cg15.networking.pubsub.Broker;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

/**
 * @author MMP - LMR
 * The GUI of the server.
 */
public class ServerGUI implements Runnable {

    /**
     * The width of the windows.
     */
    public static final int WIDTH = 600;
    
    /**
     * The height of the windows.
     */
    public static final int HEIGHT = 480;

    /**
     * The frame.
     */
    private JFrame frame; 
    
    /**
     * The text area with the log.
     */
    private JTextArea logTextArea;
    
    /**
     * Title of the panel.
     */
    private String strTitle = "Escape From Alien - Server";
    
    /**
     * RMI server.
     */
    private Server serverRMI;
    
    /**
     * Socket server.
     */
    private Server serverSocket;
    
    private Broker broker;

    /**
     * The constructor.
     */
    public ServerGUI(){
        prepareFrame();
    }
    
    /**
     * Set the current server used.
     * @param serverSocket The server socket.
     * @param serverRMI The RMI server.
     * @param broker The broker.
     */
    public void setServer(Server serverSocket,Server serverRMI,Broker broker){
        this.serverSocket=serverSocket;
        this.serverRMI = serverRMI;
        this.broker = broker;
    }
    
    /**
     * Instructions for preparing the panel gui server.
     */
    private void prepareFrame() {
        frame= new JFrame(strTitle);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(true);
        JPanel panel = new JPanel(new BorderLayout());
        logTextArea = new JTextArea(5,50);
        JScrollPane logTextPanel = new JScrollPane(logTextArea);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setEditable(false);
        logTextPanel.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        logTextArea.append("Escape From Alien In Outer Space\n");
        logTextArea.append("Matteo Michele Piazzolla\n");
        logTextArea.append("Luca Maria Ritmo\n");
        JButton btnStart = new JButton("Start Server");
        JButton btnStop = new JButton("Stop Server");
        JLabel buttonLabel = new JLabel("Server Action");
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.add(btnStop,BoxLayout.X_AXIS);
        sidePanel.add(btnStart,BoxLayout.X_AXIS);
        sidePanel.add(buttonLabel,BoxLayout.X_AXIS);
        panel.add(sidePanel,BorderLayout.EAST);
        panel.add(logTextPanel,BorderLayout.CENTER);
        frame.add(panel);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverSocket.startServer();
                serverRMI.startServer();
                broker.startServer();
               /*  logTextArea.append("Server Started\n");
                 logTextArea.setCaretPosition(logTextArea.getDocument().getLength());*/
                 frame.setTitle(strTitle+" STARTED");
            } });
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                serverSocket.stopServer();
                serverRMI.startServer();
                broker.stopServer();
                /* logTextArea.append("Server Stopped\n");
                 logTextArea.setCaretPosition(logTextArea.getDocument().getLength());*/
                 frame.setTitle(strTitle+" STOPPED");
            } });
    }
    
    /**
     * @param msg The message to append as log.
     */
    public void appendLog(String msg){
        logTextArea.append(msg);
        logTextArea.updateUI();
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
    }

    /**
     * Run the GUI server.
     */
    @Override
    public void run() {
        frame.setLocationRelativeTo(null);  
        frame.setVisible(true);
    }

}
