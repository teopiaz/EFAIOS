package it.polimi.ingsw.cg15.gui.client;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * @author MMP - LMR
 * Graphic implementation of the map.
 */
public class MapPanel extends JPanel {


    private int selectedSectorX;
    private int selectedSectorY;
    private boolean isSelected = false;

    private int posY;
    private int posX;

    /**
     * The test game map.
     */
    transient GameMap test = new GameMap(15,23);

    /**
     * New board.
     */
    private transient int[][] board = new int[23][15];

    /**
     * Editor mode that lets you edit or create new maps.
     */
    private boolean editorMode = false;
    private boolean isStarted = false;





    /**
     * The serial UID version.
     */
    private static final long serialVersionUID = 1L;


    /**
     * The map panel.
     * @param doublebuffer 
     * @param board The board.
     */
    public MapPanel(boolean doublebuffer,int[][] board){
        super(doublebuffer);
        this.board=board;
        setPreferredSize(new Dimension(850, 650));
        setMinimumSize(getPreferredSize());
        setBorder(BorderFactory.createBevelBorder(10));
        setBorder(BorderFactory.createLineBorder(Color.white));
        setBackground(Color.black);
        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        HexSprite.setHeight(40);
        repaint();
        MyMouseListener ml = new MyMouseListener();            
        addMouseListener(ml);
    }



    /**
     * Paint the various component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.drawImage(ImageLoader.load("background_map"), 0, 0, null, null);
        g2.setColor(Color.DARK_GRAY);
        Point position = null;
        Point selectedPosition =null;
        if(isStarted){
         position= new Point(posX, posY);
        }
        if(isSelected){
         selectedPosition = new Point(selectedSectorX,selectedSectorY);
        }
        
        for (int i=0;i<23;i++) {
            for (int j=0;j<14;j++) {
                HexSprite.drawHex(i,j,g2);
                HexSprite.fillHex(i,j,test.getCell(j,i).getLabel(),g2,board[i][j],position,selectedPosition);
            }
        }
    }



    class MyMouseListener extends MouseAdapter  {   //inner class inside DrawingPanel 
        @Override
        public void mouseClicked(MouseEvent e) { 

            Point p = new Point( HexSprite.pxtoHex(e.getX(),e.getY()) );
            if (p.x < 0 || p.y < 0 || p.x >= 23 || p.y >= 15) {
                return;
            }


            if(editorMode){
                //What do you want to do when a hexagon is clicked?
                if(board[p.x][p.y]+1==6){
                    board[p.x][p.y] = 0;
                }
                else{
                    board[p.x][p.y] +=1;

                }
            }else{
                if(board[p.x][p.y]!=0){
                    isSelected=true;
                    selectedSectorX = p.x;
                    selectedSectorY = p.y;
                }else{
                    isSelected=false;
                }
            }
            repaint();
        }        
    } //end of MyMouseListener class 

    public boolean getEditorMode(){
        return editorMode;
    }
    public void setEditorMode(boolean mode){
        this.editorMode = mode;
        if(mode){
            board = new int[23][15];
        }
    }

    public int[][] getBoard() {
        return this.board;
    }

    public void setBoard(int[][] board) {
        this.board=board;
    }

    /**
     * @return the isSelected
     */
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * @param isSelected the isSelected to set
     */
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        repaint();
    }

    /**
     * @return the selectedSectorX
     */
    public int getSelectedSectorX() {
        return selectedSectorX;
    }

    /**
     * @return the selectedSectorY
     */
    public int getSelectedSectorY() {
        return selectedSectorY;
    }

    public String getSelectedSectorLabel(){
        if(isSelected)
            return test.getCell(selectedSectorY,selectedSectorX).getLabel();
        return "";
    }

    public void setPosition(String s){
        isStarted =true;
        this.posY = Cell.getRowByLabel(s);
        this.posX = Cell.getColByLabel(s);
    }


}
