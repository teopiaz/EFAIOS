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
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;



public class MapPanel extends JPanel {

    /**
     * 
     */
    GameMap test = new GameMap(15,23);
    private int[][] board;
    private boolean editorMode = false;

    private static final long serialVersionUID = 1L;


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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        g.drawImage(ImageLoader.load("background_map"), 0, 0, null, null);
        g2.setColor(Color.DARK_GRAY);
        for (int i=0;i<23;i++) {
            for (int j=0;j<14;j++) {
                HexSprite.drawHex(i,j,g2);
                HexSprite.fillHex(i,j,test.getCell(j,i).getLabel(),g2,board[i][j]);
            }
        }
    }


    class MyMouseListener extends MouseAdapter  {   //inner class inside DrawingPanel 
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
            }
            System.out.println("board["+p.x+"]["+p.y+"] = "+board[p.x][p.y]);

            repaint();
        }        
    } //end of MyMouseListener class 

}