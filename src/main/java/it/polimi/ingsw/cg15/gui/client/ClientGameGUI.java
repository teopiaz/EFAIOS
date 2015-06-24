package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.Event;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.util.Map.Entry;
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
    static MapPanel map;

    /**
     * The frame.
     */
    private JFrame frame;

    /**
     * The spotlight effect layer.
     */
    static SpotlightLayerUI spotlightLayerUI;

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
     * Player constant string
     */
    public static final String PLAYER = "Player ";
    
    /**
     * the number of the player
     */
    int playerNumber;

    /**
     * The constructor.
     * @param netHelper The network helper.
     */
    public ClientGameGUI(NetworkHelper netHelper){
        this.netHelper = netHelper;
        netHelper.registerGui(this);
        prepareFrame();
    }
    
    /**
     * Run the Client Game GUI.
     */
    @Override
    public void run() {
        frame.setLocationRelativeTo(null);
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

        JPanel sidepanel = new SidePanel(this);

        frame.add(sidepanel, BorderLayout.EAST);
        frame.add(map, BorderLayout.WEST);
        frame.add(panel);
        spotlightLayerUI = new SpotlightLayerUI();
        jlayerSpot = new JLayer<JPanel>(map, spotlightLayerUI);
        frame.add(jlayerSpot);
        JMenuBar menuBar = new JMenuBar();
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
                if (spotlightEnable) {
                    spotlightLayerUI.enableSpot();
                    spotlightEnable = false;
                } else {
                    spotlightLayerUI.disableSpot();
                    spotlightEnable = true;
                }
            }

        });

        debugMenu.add(spotDebugMenu);
        debugMenu.add(editorDebugMenu);
        debugMenu.add(loadmapDebugMenu);
        menuBar.add(debugMenu);
        frame.setJMenuBar(menuBar);
        frame.pack();
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
            String player = e.getRetValues().get(Event.PLAYER);
            String sector = e.getRetValues().get("move");
            addToLog(PLAYER + player + " has moved in: " + sector);

        }
        
        if(e.getRetValues().containsKey(Event.MESSAGE)) {
            addToLog(e.getRetValues().get(Event.MESSAGE));
        }

        if (e.getRetValues().containsKey("card")) {
            String player = e.getRetValues().get(Event.PLAYER);
            String card = e.getRetValues().get("card");
            addToLog(PLAYER + player + " use " + card + " card.");

            if("spotlight".equals(card)){
                e.getRetValues().remove("card");
                e.getRetValues().remove(Event.PLAYER);
                for (Entry<String,String> ret : e.getRetValues().entrySet()) {
                    addToLog(PLAYER + ret.getKey() + " spotted in sector: " + ret.getValue());

                }
            }
        }




        if (e.getRetValues().containsKey("attack")) {
            String playerNum = e.getRetValues().get(Event.PLAYER);
            String position = e.getRetValues().get("attack");
            addToLog(PLAYER + playerNum + ": attacks in the sector: " + position);
            int count = 0;
            for (Entry<String, String> ret : e.getRetValues().entrySet()) {
                if ("killed".equals(ret.getValue())) {
                    addToLog(PLAYER + ret.getKey() + " killed by the player: " + playerNum);
                    count++;
                }
            }
            if (count == 0) {
                addToLog("No player killed.");
            }
        }

        if (Event.TRUE.equals(e.getRetValues().get("noise")) && e.getRetValues().containsKey("noise")  ) {
            String playerNum = e.getRetValues().get(Event.PLAYER);
            String position = e.getRetValues().get("position");
            addToLog(PLAYER + playerNum + ": make noise in sector: " + position);
        }

        if (e.getRetValues().containsKey("hatch")) {
            if (Event.FALSE.equals(e.getRetValues().get("hatch"))) {
                addToLog(e.getRetValues().get(Event.MESSAGE));
            } else {
                String player = e.getRetValues().get(Event.PLAYER);
                addToLog("Player" + player + " has drawn a hatch card: "
                        + e.getRetValues().get("hatchcard"));
            }
        }
    }

    /**
     * @param e The event to add as chat.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#chat(it.polimi.ingsw.cg15.networking.Event)
     */
    @Override
    public void chat(Event e) {
        String message = "[Player " + e.getRetValues().get(Event.PLAYER) + "]" + " "+ e.getRetValues().get(Event.MESSAGE);
        SidePanel.getChatPanel().addToChat(message);
    }

    /**
     * Set the game as started.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#setStarted()
     */
    @Override
    public void setStarted() {
        loadMap();
        addToLog("The game started now!");
        if(netHelper.isHuman()){
            addToLog("You are Human and you have to escape");

        }else{
            addToLog("You are an Alien. Good hunting!");

        }
        playerNumber = netHelper.getPlayerNumber();

        String position = netHelper.getPlayerPosition();
        SidePanel.getMainPanel();
        ClientGameGUI.getMapPanel().setPosition(position);

        SidePanel.getActionPanel().getActionsList();
    }

    /**
     * The current player turn.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#currentPlayer(int)
     */
    @Override
    public void currentPlayer(int currentPlayer) {

        SidePanel.getActionPanel().printMsg(PLAYER+ currentPlayer +" turn.");

        addToLog(PLAYER+ currentPlayer +" turn.");
        SidePanel.getActionPanel().getActionsList();
        SidePanel.getCardPanel().getCardsList();
        String position = netHelper.getPlayerPosition();
        SidePanel.getMainPanel();
        ClientGameGUI.getMapPanel().setPosition(position);

    }


    /**
     * @return The map panel.
     */
    public static MapPanel getMapPanel() {
        return map;

    }

    /**
     * @return The spotlight layer.
     */
    public static SpotlightLayerUI getSpotLayer() {
        return spotlightLayerUI;

    }

    @Override
    public void endGame(Event e) {
        boolean winner = false;
        for (Entry<String, String> ele : e.getRetValues().entrySet()) {
            addToLog(PLAYER + ele.getKey() + ": " + ele.getValue());
            if(ele.getKey().equals(Integer.toString(playerNumber)) && "win".equals(ele.getValue())){
                winner=true;
            }
        }
        if(winner){
            JOptionPane.showMessageDialog(frame,  "You Win the match",  "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(frame,  "You Lose the match",  "Game Over", JOptionPane.INFORMATION_MESSAGE);
        }

    }




}
