package it.polimi.ingsw.cg15.gui.client;


/**
 * @author MMP - LMR
 * The game map.
 */
public class GameMap {
    
    private static int r;
    
    private static int q;

    /**
     * The map of cells.
     */
    private Cell[][] map;

    /**
     * The game map.
     * @param r
     * @param q
     */
    public GameMap(int r, int q) {
        GameMap.r = r;
        GameMap.q = q;
        map = new Cell[r][q + r / 2];
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < q + r / 2; j++) {
                map[i][j] = new Cell(i, j - r / 2);
            }
        }
    }

    /**
     * @param r
     * @param q
     * @return The cell.
     */
    public Cell getCell(int r, int q) {
        return this.map[r][q];
    }
    
    
    /**
     * 
     * @return r row number
     */
    public static int getR(){
        return r;
    }
    
    
    /**
     * 
     * @return q column number
     */
    public static int getQ(){
        return q;
    }
}
