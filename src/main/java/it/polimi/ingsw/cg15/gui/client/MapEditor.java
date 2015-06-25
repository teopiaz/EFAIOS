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
import java.util.Random;
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


        panel.setBackground(Color.BLACK);

        frameEditor.add(map, BorderLayout.WEST);
        frameEditor.add(panel);

        frameEditor.setSize(1280, 720);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem saveMenu = new JMenuItem("Save");
        JMenuItem loadMenu = new JMenuItem("Load");
        JMenuItem randomMenu = new JMenuItem("Generate a Random Map");
        JMenu debugMenu = new JMenu("Server");
        JMenuItem editorDebugMenu = new JMenuItem("Send To Server");
        map.setEditorMode(true);
        frameEditor.setVisible(true);


        randomMenu.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                board = map.getBoard();

                Random ran = new Random();
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 23; j++) {
                        int randomInt = ran.nextInt(4);
                        if(randomInt==1 || randomInt==3){
                            board[j][i] = 1;
                        }else{
                            board[j][i] = randomInt;	
                        }
                    }
                }
                for (int i = 1; i < 14; i++) {
                    for (int j = 1; j < 22; j++) {
                        if( board[j][i+1]==0 && board[j+1][i]==0 && board[j][i-1]==0 && board[j-1][i]==0){
                            board[j][i]=0;
                        }
                    }
                }
                generateRandomCell(5,5,0,0,3);

                generateRandomCell(4,5,0,15,3);

                generateRandomCell(4,5,10,15,3);

                generateRandomCell(4,5,10,0,3);

                generateRandomCell(6,6,5, 9,4);

                generateRandomCell(6,6,5, 9,5);


                map.repaint();

            }
        });

        editorDebugMenu.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                savedMap="";
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < 23; j++) {
                        savedMap = savedMap + (i + 1) + "," + (j + 1) + "," + board[j][i] + "\n";
                    }
                }

                String mapName =JOptionPane.showInputDialog("Insert Map Name");
                if(mapName!=""){
                    if(netHelper.saveMapToServer(mapName,savedMap)){
                        JOptionPane.showMessageDialog(frameEditor, "Map Saved");
                    }else{
                        JOptionPane.showMessageDialog(frameEditor, "Error, please retry");

                    }
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

        menu.add(randomMenu);
        menu.add(saveMenu);
        menu.add(loadMenu);
        menuBar.add(menu);
        menuBar.add(debugMenu);
        frameEditor.setJMenuBar(menuBar);
        frameEditor.pack();
    }
    /**
     *  Generate desired sector in map matrix randomly between bounds
     * @param rRand row bound for random
     * @param cRand column bound for random
     * @param rBound row bound
     * @param cBound column bound
     * @param sector integer identifying a sector type
     */
    public void generateRandomCell(int rRand,int cRand,int rBound, int cBound,int sector){
        boolean done=false;
        int timeout=0;
        Random ran = new Random();
        while(!done || timeout==50){
            int firstR=ran.nextInt(rRand)+rBound;
            int firstC = ran.nextInt(cRand)+cBound;
            if(board[firstC][firstR]==2){
                board[firstC][firstR]=sector;
                done=true;
            }
            timeout++;
        }


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
