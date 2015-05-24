package it.polimi.ingsw.cg15.gui.server;



import it.polimi.ingsw.cg15.MainServer;
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

public class ServerGUI implements Runnable {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private JFrame frame; 
    private JTextArea logTextArea;
    private Server server;
    
    
    public ServerGUI(Server server){
        this.server = server;
        prepareFrame();
    }
    
    private void prepareFrame() {
        
        frame= new JFrame("Escape From Alien - Server");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.setResizable(true);
        JPanel panel = new JPanel(new BorderLayout());
        
        logTextArea = new JTextArea(5,50);
        JScrollPane logTextPanel = new JScrollPane(logTextArea);
        logTextArea.setLineWrap(true);
        logTextArea.setWrapStyleWord(true);
        logTextArea.setEditable(false);
        logTextPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);


        
        
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
            public void actionPerformed(ActionEvent e) {
                server.startServer();
                 logTextArea.append("Server Started\n");
                 logTextArea.setCaretPosition(logTextArea.getDocument().getLength());

            } });
        
        btnStop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                server.stopServer();
                 logTextArea.append("Server Stopped\n");
                 logTextArea.setCaretPosition(logTextArea.getDocument().getLength());

            } });
        
    }

    public void run() {
        frame.setLocationRelativeTo(null);  

        frame.setVisible(true);
    }
    
    

}
