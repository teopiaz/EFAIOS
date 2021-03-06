package it.polimi.ingsw.cg15.model.field;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MMP - LMR The class that contains the cubic coordinates.
 */
public class Coordinate {

    /**
     * The cubic coordinate.
     */
    private final int x, y, z;

    /**
     * The values of the cubics coordinate.
     * 
     * @param x
     * @param y
     * @param z
     */
    public Coordinate(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Turns the values of row and column into a cubic coordinates.
     * 
     * @param r
     *            The row.
     * @param c
     *            The column.
     */

    public Coordinate(int r, int c) {
        this(c, // x
                -c - (r - (c + (c & 1)) / 2), // y
                r - (c + (c & 1)) / 2 // z
        );
    }

    /**
     * It allows to obtain the coordinates from a string that identifies the.
     * 
     * @param label The string.
     * @return coordinate by row and column.
     */
    public static Coordinate getByLabel(String label) {
        int r = 1, c = 1;

        String str = label;
        str = str.toUpperCase();
        if (str.matches("^[A-Z][0-9]?[0-9]$")) {
            char[] charStr = str.toCharArray();
            if (charStr.length < 3) {
                c = (int) charStr[0] - 64;
                r = charStr[1] - 48;
            }
            if (charStr.length == 3) {
                c = (int) charStr[0] - 64;
                r = (charStr[1] - 48) * 10 + (charStr[2] - 48);
            }
            return new Coordinate(r, c);
        } else {
            return null;
        }
    }

    /**
     * @return the x cubic coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * @return the y cubic coordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * @return the z cubic coordinate.
     */
    public int getZ() {
        return z;
    }

    /**
     * @return The column.
     */
    public int getCol() {
        return x;
    }

    /**
     * @return The row.
     */
    public int getRow() {
        return z + (x + (x & 1)) / 2;
    }

    /**
     * Turns into string.
     */
    @Override
    public String toString() {
        String tmp;
        tmp = new String("" + ((char) (getCol() + 64)));
        if ((getRow()) < 10)
            tmp = new String(tmp + "0");
        return new String(tmp + (getRow()));
    }

    /**
     * hash function
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    /**
     * Check equality.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (x != other.x || y != other.y || z != other.z)
            return false;

        return true;
    }

    /**
     * It allows you to get a list of the coordinates adjacent given a certain
     * direction.
     * 
     * @param direction The direction.
     * @return list of the coordinates adjacent.
     */
    public Coordinate getNeighbor(Direction direction) {
        switch (direction) {
        case N:
            return new Coordinate(x, y + 1, z - 1);
        case NE:
            return new Coordinate(x + 1, y, z - 1);
        case NW:
            return new Coordinate(x - 1, y + 1, z);
        case S:
            return new Coordinate(x, y - 1, z + 1);
        case SE:
            return new Coordinate(x + 1, y - 1, z);
        case SW:
            return new Coordinate(x - 1, y, z + 1);
        default:
            return null;
        }
    }

    /**
     * It allows you to get a list of the coordinates adjacent.
     * 
     * @return list of the coordinates adjacent.
     */
    public List<Coordinate> getNeighborsList() {
        List<Coordinate> list = new LinkedList<Coordinate>();
        for (Direction direction : Direction.values()) {
            list.add(getNeighbor(direction));
        }
        return list;
    }

}
