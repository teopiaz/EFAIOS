package it.polimi.ingsw.cg15.gui.client;

//TODO INUTILE DA ELIMINARE

public class GameMap {
    public static int r;
    public static int q;

    public Cell[][] map;

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

    public Cell getCell(int r, int q) {
        return this.map[r][q];

    }
}
