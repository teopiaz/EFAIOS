package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.networking.Event;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;


/**
 * @author MMP - LMR
 * The client lobby GUI.
 */
public class ClientLobbyGUI implements Runnable{


    JFrame frame;

    JTable table;

    MyTableModel tableModel = new MyTableModel();

    /**
     * The network helper.
     */
    NetworkHelper netHelper;

    /**
     * The client game GUI.
     */
    private ClientGameGUI gui;

    /**
     * The table wit the games.
     */
    private Map<String, String> gameMap;

    /**
     * The IP address for communication.
     */
    private String host = "localhost";

    /**
     * The port for communication.
     */
    private int port = 1337;

    /**
     * The Client Lobby GUI.
     * @param networkHelper The network helper.
     * @param clientTaskGUI The tasks for the client GUI.
     */
    public ClientLobbyGUI(final NetworkHelper networkHelper, Runnable clientTaskGUI){

        this.netHelper=networkHelper;
        this.gui = (ClientGameGUI)clientTaskGUI;
        frame = new JFrame("Escape from Aliens in Outer Space - LOBBY");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        JPanel lobbyPanel = new JPanel(new BorderLayout());
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        table = new JTable(tableModel);
        JButton btnCreate = new JButton("Crea Partita");
        JButton btnJoin = new JButton("Entra in una Partita");
        JButton btnRefreshList = new JButton("Aggiorna La Lista");

        btnCreate.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String gameName = JOptionPane.showInputDialog(frame, "Insert Game Name","1");
                String mapName = JOptionPane.showInputDialog(frame, "Insert Map Name","test123");
                networkHelper.createGame(gameName,mapName.toLowerCase());
                updateGameList();
            }

        });

        btnRefreshList.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(networkHelper.getType());
                updateGameList();
            }

        });

        btnJoin.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                int rowNum =  table.getSelectedRow();
                String selectedGame = (String) table.getModel().getValueAt(rowNum, 0);
                String gameToken="";
                gameToken = gameMap.get(selectedGame);
                networkHelper.setGameToken(gameToken);
                Event response =  networkHelper.joinGame(gameToken);
                if(response.getRetValues().containsValue("joined")){
                    gui.showGUI();
                    frame.setVisible(false);
                    frame.dispose();
                }else{
                    JOptionPane.showMessageDialog(frame, response.getRetValues().get(Event.ERROR));
                }
            }

        });

        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButton radioSock = new JRadioButton("Socket");
        JRadioButton radioRMI = new JRadioButton("RMI");
        radioSock.setSelected(true);   

        radioSock.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                NetworkHelper.getClientSocket(host, port);
            }

        });

        radioRMI.addActionListener(new ActionListener() {

            /**
             * @param e The action event.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    NetworkHelper.getClientRMI();
                } catch (RemoteException e1) {
                    Logger.getLogger(ClientLobbyGUI.class.getName()).log(Level.SEVERE, "Error in the remote comunication", e1);
                }
            }

        });

        radioGroup.add(radioSock);
        radioGroup.add(radioRMI);
        sidePanel.add(radioRMI,  BoxLayout.X_AXIS);
        sidePanel.add(radioSock,  BoxLayout.X_AXIS);
        sidePanel.add(btnRefreshList, BoxLayout.X_AXIS);
        sidePanel.add(btnJoin, BoxLayout.X_AXIS);
        sidePanel.add(btnCreate, BoxLayout.X_AXIS);
        lobbyPanel.add(sidePanel,BorderLayout.EAST);
        lobbyPanel.add(table,BorderLayout.CENTER);
        frame.add(lobbyPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(false);
    }

    /**
     * Method for show he GUI.
     */
    public void showGUI(){
        this.frame.setVisible(true);
    }

    /**
     * Run the Client Lobby GUI.
     */
    @Override
    public void run() {
        Logger.getLogger(ClientLobbyGUI.class.getName()).log(Level.INFO, "Client Lobby Gui");

    }

    /**
     * Update the game list.
     */
    private void updateGameList(){
        tableModel.cleanTable();
        gameMap = netHelper.getGamesList();
        for (Entry<String,String> gameToken : gameMap.entrySet()) {
            Map<String,String> game = netHelper.getGameInfo(gameToken.getValue());
            tableModel.addRow(game.get("name"),game.get("mapName").toUpperCase(),game.get("playercount")+"/8","STATUS");
        }
    }

    /**
     * @author MMP - LMR
     * Table with the list of games.
     */
    public class MyTableModel extends AbstractTableModel {

        /**
         * 
         */
        private static final long serialVersionUID = 341537634814025241L;

        /**
         * Name of columns.
         */
        private String[] columnNames = { "NOME PARTITA", "MAPPA", "GIOCATORI","STATO" };

        /**
         * Rows of table.
         */
        private transient List<String[]> rows;

        /**
         * The constructor.
         */
        public MyTableModel() {
            rows = new ArrayList<>();
        }

        /**
         * Get number of row.
         */
        @Override
        public int getRowCount() {
            return rows.size();
        }

        /**
         * Get number of column.
         */
        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        /**
         * Get name of column.
         */
        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }


        @Override
        public Class<String> getColumnClass(int columnIndex) {
            return String.class;
        }

        /**
         * Get a specific value.
         * @param rowIndex The index of the row.
         * @param columnIndex The index for the column.
         */
        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String[] row = rows.get(rowIndex);
            return row[columnIndex];
        }

        /**
         * Add a row.
         * @param gameName The name of the game.
         * @param mapName The name of the map.
         * @param playerCount The number of players.
         * @param status The status of the match.
         */
        public void addRow(String gameName, String mapName,String playerCount,String status) {
            int rowCount = getRowCount();
            String[] row = new String[getColumnCount()];
            row[0]=gameName;
            row[1]=mapName;
            row[2]=playerCount;
            row[3]=status;
            rows.add(row);
            fireTableRowsInserted(rowCount, rowCount);
        }      

        /**
         * Remove all content in the table.
         */
        public void cleanTable(){
            fireTableRowsDeleted(0, getRowCount());
            rows = new ArrayList<>();
        }
    }       

}
