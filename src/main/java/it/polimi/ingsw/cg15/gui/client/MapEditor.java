package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author MMP - LMR
 * The GUI of the client game.
 */
public class MapEditor implements Runnable {

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
    private JFrame frameEditor;


    /**
     * A string with the map.
     */
    String strmap;

    /**
     * The Network Helper.
     */
    NetworkHelper netHelper;
    
 
    String savedMap;

    
    
    /**
     * the number of the player
     */
    int playerNumber;

    /**
     * The constructor.
     * @param netHelper The network helper.
     */
    public MapEditor(NetworkHelper netHelper){
        this.netHelper = netHelper;
        prepareFrame();
    }
    
    /**
     * Run the Client Game GUI.
     */
    @Override
    public void run() {
        
        frameEditor.setLocationRelativeTo(null);
    }

    /**
     * Prepare the frame for the Editor
     */
    public void prepareFrame() {
        frameEditor = new JFrame("MAP EDITOR");
        frameEditor.setLocationRelativeTo(null);
        frameEditor.setResizable(false);
        JPanel panel = new JPanel(true);
        panel.setLayout(new BorderLayout());
        map = new MapPanel(true, board);
        

        frameEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setBackground(Color.BLACK);

        frameEditor.add(map, BorderLayout.WEST);
        frameEditor.add(panel);
        
        frameEditor.setSize(1280, 720);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");
        JMenu debugMenu = new JMenu("Debug");
        JMenuItem editorDebugMenu = new JMenuItem("Send To Server");
        map.setEditorMode(true);
        frameEditor.setVisible(true);
        

        editorDebugMenu.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String mapName =JOptionPane.showInputDialog("Insert Map Name");
                if(netHelper.saveMapToServer(mapName,savedMap)){
                    JOptionPane.showMessageDialog(frameEditor, "Map Saved");
                }else{
                    JOptionPane.showMessageDialog(frameEditor, "Error, please retry");

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
                String mapName = JOptionPane.showInputDialog(frameEditor, "Insert Map name to load");
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
                String content = "";
                String mapName = JOptionPane.showInputDialog(frameEditor, "Insert Map Name to save");
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
                    savedMap = content;
                    fop.write(contentInBytes);
                    fop.flush();
                    fop.close();
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

        debugMenu.add(editorDebugMenu);
        menu.add(saveMenu);
        menu.add(loadMenu);
        menuBar.add(menu);
        menuBar.add(debugMenu);
        frameEditor.setJMenuBar(menuBar);
        frameEditor.pack();
    }

    /**
     * Show the Client GUI.
     */
    public void showGUI() {
        this.frameEditor.setVisible(true);
    }

    



    
    /**
     * @return The map panel.
     */
    public static MapPanel getMapPanel() {
        return map;

    }





}
