package it.polimi.ingsw.cg15.gui.client;

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
public class Cell {
    private final int x, y, z;
    private final String label;

    public Cell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        String tmp;
        tmp = new String("" + ((char) (x + 65 + GameMap.r / 2)));
        if ((z + 1) < 10)
            tmp = new String(tmp + "0");
        label = new String(tmp + (z + 1));
    }

    public Cell(int r, int q) {
        this(q, -q - r, r);
    }

    public int distance(Cell b) {
        return (Math.abs(this.x - b.getX()) + Math.abs(this.y - b.getY()) + Math.abs(this.z
                - b.getZ())) / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getLabel() {
        return new String(this.label);
    }

    @Override
    public String toString() {
        return "{" + this.label + "} " + "[x=" + x + ", z=" + z + " y=" + y + "]";
    }

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

    @Override
    public boolean equals(Object obj) {
        Cell b = (Cell) obj;
        if (this.x == b.getX() && this.y == b.getY() && this.z == b.getZ())
            return true;
        else
            return false;
    }

}
