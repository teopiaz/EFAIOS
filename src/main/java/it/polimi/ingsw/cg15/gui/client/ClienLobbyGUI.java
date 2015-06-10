package it.polimi.ingsw.cg15.gui.client;

import it.polimi.ingsw.cg15.NetworkHelper;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ClienLobbyGUI implements Runnable{

    JFrame frame;
    JTable table;
    MyTableModel tableModel = new MyTableModel();
    NetworkHelper netHelper;
    private ClientGameGUI gui;
    private Map<String, String> gameMap;


    public ClienLobbyGUI(final NetworkHelper networkHelper, Runnable clientTaskGUI){
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

            @Override
            public void actionPerformed(ActionEvent e) {
                String gameName = JOptionPane.showInputDialog(frame, "Insert Game Name");

                String mapName = JOptionPane.showInputDialog(frame, "Insert Map Name");

                networkHelper.createGame(gameName,mapName.toLowerCase());
                updateGameList();

            }
        });

        btnRefreshList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                updateGameList();

            }
        });
        
        btnJoin.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              int rowNum =  table.getSelectedRow();
             String selectedGame = (String) table.getModel().getValueAt(rowNum, 0);
             String gameToken="";

             gameToken = gameMap.get(selectedGame);
             networkHelper.setGameToken(gameToken);
            
                networkHelper.joinGame(gameToken);
                gui.showGUI();
                frame.dispose();
                
            }
        });

        JLabel lblSock = new JLabel("Socket");
        ButtonGroup radioGroup = new ButtonGroup();
        JRadioButton radioSock = new JRadioButton("Socket");
        JRadioButton radioRMI = new JRadioButton("RMI");
        radioSock.setSelected(true);   
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
        frame.setVisible(true);


    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }

    private void updateGameList(){
        tableModel.cleanTable();
        gameMap = netHelper.getGamesList();
        for (Entry<String,String> gameToken : gameMap.entrySet()) {
            Map<String,String> game = netHelper.getGameInfo(gameToken.getValue());
            tableModel.addRow(game.get("name"),game.get("mapName").toUpperCase(),game.get("playercount")+"/8","STATUS");

        }

    }

    public class MyTableModel extends AbstractTableModel {

        private String[] columnNames = { "NOME PARTITA", "MAPPA", "GIOCATORI","STATO" };
        private List<String[]> rows;

        public MyTableModel() {
            rows = new ArrayList<>();
        }

        @Override
        public int getRowCount() {
            return rows.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }


        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }


        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String[] row = rows.get(rowIndex);
            return row[columnIndex];
        }

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

        public void cleanTable(){
            fireTableRowsDeleted(0, getRowCount());
            rows = new ArrayList<>();

        }
    }       

}
