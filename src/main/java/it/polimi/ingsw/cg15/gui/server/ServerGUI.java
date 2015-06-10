package it.polimi.ingsw.cg15.gui.server;



import it.polimi.ingsw.cg15.networking.Server;
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

public class ServerGUI implements Runnable {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 480;

    private JFrame frame; 
    private JTextArea logTextArea;
    private String strTitle = "Escape From Alien - Server";
    private Server serverRMI;
    private Server serverSocket;
    private Server broker;

    
    public ServerGUI(){
        prepareFrame();
    }
    public void setServer(Server serverSocket,Server serverRMI,Server broker){
        this.serverSocket=serverSocket;
        this.serverRMI = serverRMI;
        this.broker = broker;
    }
    
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
    
    public void appendLog(String msg){
        logTextArea.append(msg);
        logTextArea.updateUI();
        logTextArea.setCaretPosition(logTextArea.getDocument().getLength());
        
    }

    @Override
    public void run() {
        frame.setLocationRelativeTo(null);  

        frame.setVisible(true);
    }
    
    

}
