package it.polimi.ingsw.cg15.gui.client;

/**
 * @author MMP - LMR
 * The GUI information about the cell.
 */
public class Cell {

    /**
     * The cubic coordinate.
     */
    private final int x, y, z;

    /**
     * A label.
     */
    private final String label;

    /**
     * The constructor by cubic coordinate.
     * @param x
     * @param y
     * @param z
     */
    public Cell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        String tmp;
        tmp = new String("" + ((char) (x + 65 + GameMap.getR() / 2)));
        if ((z + 1) < 10)
            tmp = new String(tmp + "0");
        label = new String(tmp + (z + 1));
    }

    /**
     * The constructor by row and column.
     * @param r
     * @param q
     */
    public Cell(int r, int q) {
        this(q, -q - r, r);
    }

    /**
     * Distance
     * @param b The cell.
     * @return The distance between the cell.
     */
    public int distance(Cell b) {
        return (Math.abs(this.x - b.getX()) + Math.abs(this.y - b.getY()) + Math.abs(this.z
                - b.getZ())) / 2;
    }

    /**
     * @return The x cubic coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * @return The y cubic coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * @return The z cubic coordinate.
     */
    public int getZ() {
        return z;
    }

    /**
     * @return the label.
     */
    public String getLabel() {
        return new String(this.label);
    }

    /**
     * Transform into a string.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{" + this.label + "} " + "[x=" + x + ", z=" + z + " y=" + y + "]";
    }

    /**
     * Hashcode function
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((label == null) ? 0 : label.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    /**
     * Check equality.
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        Cell b = (Cell) obj;
        return this.x == b.getX() && this.y == b.getY() && this.z == b.getZ();
    }



    /**
     * Get a row by a label.
     * @param strArg The string.
     * @return The label.
     */
    public static int getRowByLabel(String strArg) {
        int r = 1;
        String str=strArg;
        str=str.toUpperCase();
        if(str.matches("^[A-Z][0-9]?[0-9]$")){
            char[] charStr = str.toCharArray();
            if (charStr.length < 3) {
                r = charStr[1]-48;
            }
            if (charStr.length == 3) {
                r = (charStr[1]-48 )* 10 + (charStr[2]-48);
            }

        }
        return r-1;

    }

    /**
     * Get a col by a label.
     * @param strArg The string.
     * @return Col.
     */
    public static int getColByLabel(String strArg) {
        int  c = 1;
        String str=strArg;
        str=str.toUpperCase();
        if(str.matches("^[A-Z][0-9]?[0-9]$")){
            char[] charStr = str.toCharArray();
            if (charStr.length < 3) {
                c = (int)charStr[0]-64;

            }
            if (charStr.length == 3) {
                c = (int)charStr[0]-64;

            }

        }
        return c-1;

    }



}

//hex cell memorizzata con coordinate cubiche classe immutabile
/*
 * 
# convert cube to axial
q = x
r = z

# convert axial to cube
x = q
z = r
y = -q-z
 */
