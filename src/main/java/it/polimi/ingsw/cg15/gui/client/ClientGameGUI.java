package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.Event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author MMP - LMR
 * The GUI of the client game.
 */
public class ClientGameGUI implements Runnable, ViewClientInterface {

    /**
     * The game board.
     */
    int[][] board = new int[23][15];

    /**
     * The map panel.
     */
    MapPanel map;

    /**
     * The frame.
     */
    private JFrame frame;

    /**
     * The spotlight effect layer.
     */
    SpotlightLayerUI spotlightLayerUI;

    /**
     * The panel for spotlight layer.
     */
    JLayer<JPanel> jlayerSpot;

    /**
     * Enable or disable spotlight effect.
     */
    boolean spotlightEnable = false;

    /**
     * A string with the map.
     */
    String strmap;

    /**
     * The Network Helper.
     */
    NetworkHelper netHelper;

    /**
     * Run the Client Game GUI.
     */
    @Override
    public void run() {
        frame.setLocationRelativeTo(null);
        // frame.setVisible(true);
    }

    /**
     * The constructor.
     * @param netHelper The network helper.
     */
    public ClientGameGUI(NetworkHelper netHelper) {
        this.netHelper = netHelper;
        netHelper.registerGui(this);
        prepareFrame();
        System.out.println("GUI creato");
    }

    /**
     * Prepare the frame for the Client GUI.
     */
    public void prepareFrame() {
        frame = new JFrame("Escape From Aliens in Outer Space");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        JPanel panel = new JPanel(true);
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BorderLayout());
        map = new MapPanel(true, board);
        frame.setSize(1280, 720);
        JPanel sidepanel = new SidePanel();
        frame.add(sidepanel, BorderLayout.EAST);
        frame.add(map, BorderLayout.WEST);
        frame.add(panel);
        spotlightLayerUI = new SpotlightLayerUI();
        jlayerSpot = new JLayer<JPanel>(map, spotlightLayerUI);
        frame.add(jlayerSpot);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");
        JMenu debugMenu = new JMenu("Debug");
        JMenuItem spotDebugMenu = new JMenuItem("Spotlight");
        JMenuItem editorDebugMenu = new JMenuItem("Editor Mode");
        JMenuItem loadmapDebugMenu = new JMenuItem("Load game map");
        
        loadmapDebugMenu.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                loadMap();
            }

        });

        editorDebugMenu.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                map.setEditorMode(!map.getEditorMode());
            }
            
        });

        spotDebugMenu.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if (spotlightEnable == true) {
                    spotlightLayerUI.enableSpot();
                    spotlightEnable = false;
                } else {
                    spotlightLayerUI.disableSpot();
                    spotlightEnable = true;
                }
            }
            
        });

        loadMenu.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                board = map.getBoard();
                FileInputStream fin = null;
                String mapName = JOptionPane.showInputDialog(frame, "Insert Map name to load");
                if (mapName == "") {
                    mapName = "map";
                }
                File file = new File("maps/" + mapName + ".txt");
                try {
                    fin = new FileInputStream(file);
                } catch (FileNotFoundException e1) {
                    Logger.getLogger(ClientGameGUI.class.getName()).log(Level.SEVERE, "File not found exception", e1);
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
                String line = null;
                try {
                    line = reader.readLine();
                } catch (IOException e1) {
                    Logger.getLogger(ClientGameGUI.class.getName()).log(Level.SEVERE, "IO exception", e1);
                }
                while (line != null) {
                    String[] splitted = line.split(",");
                    board[Integer.valueOf(splitted[1]) - 1][Integer.valueOf(splitted[0]) - 1] = Integer.valueOf(splitted[2]);
                    try {
                        line = reader.readLine();
                    } catch (IOException e1) {
                        Logger.getLogger(ClientGameGUI.class.getName()).log(Level.SEVERE, "IO exception", e1);
                    }
                }
                map.repaint();
            }
            
        });

        saveMenu.addActionListener(new ActionListener() {

            /**
             * @param event The action event.
             */
            @Override
            public void actionPerformed(ActionEvent event) {
                board = map.getBoard();
                FileOutputStream fop = null;
                File file;
                String content = "";// ="version,0.1"+"\n"+"mapname,prova"+"\n"+
                // "row,column,sector value,\n";
                String mapName = JOptionPane.showInputDialog(frame, "Insert Map Name to save");
                if (mapName == "") {
                    mapName = "map";
                }
                try {
                    file = new File("maps/" + mapName + ".txt");
                    fop = new FileOutputStream(file);
                    // if file doesnt exists, then create it
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    // get the content in bytes
                    for (int i = 0; i < 15; i++) {
                        for (int j = 0; j < 23; j++) {
                            content = content + (i + 1) + "," + (j + 1) + "," + board[j][i] + "\n";
                        }
                    }
                    byte[] contentInBytes = content.getBytes();
                    fop.write(contentInBytes);
                    fop.flush();
                    fop.close();
                    System.out.println("Done");
                } catch (IOException e) {
                    Logger.getLogger(ClientGameGUI.class.getName()).log(Level.SEVERE, "IO Exception", e);
                } finally {
                    try {
                        if (fop != null) {
                            fop.close();
                        }
                    } catch (IOException e) {
                        Logger.getLogger(ClientGameGUI.class.getName()).log(Level.SEVERE, "IO Exception", e);
                    }
                }
            }
            
        });
        
        debugMenu.add(spotDebugMenu);
        debugMenu.add(editorDebugMenu);
        debugMenu.add(loadmapDebugMenu);
        menu.add(saveMenu);
        menu.add(loadMenu);
        menuBar.add(menu);
        menuBar.add(debugMenu);
        frame.setJMenuBar(menuBar);
        frame.pack();
        // GraphicsDevice device =
        // GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
        // device.setFullScreenWindow(frame);
    }

    /**
     * Show the Client GUI.
     */
    public void showGUI() {
        this.frame.setVisible(true);
    }

    /**
     * Load the map.
     */
    public void loadMap() {
        board = new int[23][16];
        map.setBoard(board);
        strmap = netHelper.getMap();
        Scanner mapScanner = new Scanner(strmap);
        while (mapScanner.hasNextLine()) {
            String line = mapScanner.nextLine();
            String[] splitted = line.split(",");
            String strrr = splitted[0];
            String strc = splitted[1];
            String strtype = splitted[2];
            int r = Integer.parseInt(strrr) - 1;
            int c = Integer.parseInt(strc) - 1;
            int type = Integer.parseInt(strtype);
            board[c][r] = type;
        }
        mapScanner.close();
        map.repaint();
    }

    /**
     * @param msg The massage to print.
     */
    @Override
    public void stampa(String msg) {
        SidePanel.getActionPanel().printMsg(msg);
    }

    /**
     * @param s The string to add as log.
     */
    public void addToLog(String s) {
        SidePanel.getLogPanel().addToLog(s);
    }

    /**
     * Log an event e.
     * @param e The event to log.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#log(it.polimi.ingsw.cg15.networking.Event)
     */
    @Override
    public void log(Event e) {
        SidePanel.getActionPanel().getActionsList();
        SidePanel.getCardPanel().getCardsList();
        if (e.getRetValues().containsKey("move")) {
            String player = e.getRetValues().get("player");
            String sector = e.getRetValues().get("move");
            addToLog("Player " + player + " has moved in: " + sector);
        }
        if (e.getRetValues().containsKey("attack")) {
            String playerNum = e.getRetValues().get("player");
            String position = e.getRetValues().get("attack");
            addToLog("Player " + playerNum + ": attacks in the sector: " + position);
            int count = 0;
            for (Entry<String, String> ret : e.getRetValues().entrySet()) {
                if (ret.getValue().equals("killed")) {
                    addToLog("Player " + ret.getKey() + " killed by the player: " + playerNum);
                    count++;
                }
            }
            if (count == 0) {
                addToLog("No player killed.");
            }
        }
        if (e.getRetValues().containsKey("noise")) {
            if (e.getRetValues().get("noise").equals("true")) {
                String playerNum = e.getRetValues().get("player");
                String position = e.getRetValues().get("position");
                addToLog("Player " + playerNum + ": make noise in sector: " + position);
            }
        }
        if (e.getRetValues().containsKey("hatch")) {
            if (e.getRetValues().get("hatch").equals("false")) {
                addToLog(e.getRetValues().get("message"));
            } else {
                String player = e.getRetValues().get("player");
                addToLog("Player" + player + " has drawn a hatch card: "
                        + e.getRetValues().get("hatchcard"));
            }
        }
    }

    /**
     * @param e The event to add as chat.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#chat(it.polimi.ingsw.cg15.networking.Event)
     */
    public void chat(Event e) {
        String message = "[Player " + e.getRetValues().get("player") + "]" + " "+ e.getRetValues().get("message");
        SidePanel.getChatPanel().addToChat(message);
    }

    /**
     * Set the game as started.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#setStarted()
     */
    public void setStarted() {
        loadMap();
        addToLog("The game started now!");
        SidePanel.getActionPanel().getActionsList();
    }

    /**
     * The current player turn.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#currentPlayer(int)
     * TODO scrivere le println in inglese!
     */
    @Override
    public void currentPlayer(int currentPlayer) {
        System.out.println("È il turno del giocatore "+ currentPlayer);
        addToLog("È il turno del giocatore "+ currentPlayer);
        SidePanel.getActionPanel().getActionsList();
        SidePanel.getCardPanel().getCardsList();
    }

}
