package it.polimi.ingsw.cg15.model.field;

import java.util.LinkedList;
import java.util.List;


public class Coordinate {


    private final int x,y,z;


    //cubic coordinate
    public Coordinate(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public Coordinate(int r, int c){

        this(
                c-(r+(r&1))/2,                  //x
                -(c-(r+(r&1))/2) - r,           //y
                r                               //z
                );
    }

    //TODO: handle null parameter ???
    public int getDistance(Coordinate b){
        if(b==null){
            throw new IllegalArgumentException("destination parameter cannot be null");
        }
        return (Math.abs(this.x - b.getX()) + Math.abs(this.y - b.getY()) + Math.abs(this.z - b.getZ())) / 2;
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

    public int getCol(){
        return x + (z + (z&1)) / 2;
    }
    public int getRow(){
        return z;

    }


    @Override
    public String toString() {
        String tmp;
        tmp = new String(""+((char)(getCol()+65)));
        if((getRow()+1)<10)
            tmp = new String(tmp+"0");

        return new String(tmp+(getRow()));
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Coordinate other = (Coordinate) obj;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    public Coordinate getNeighbor(Direction direction){

        switch (direction) {        
        case N: return new Coordinate(x,y+1,z-1);
        case NE:return new Coordinate(x+1,y,z-1);
        case NW:return new Coordinate(x-1,y+1,z);
        case S: return new Coordinate(x,y-1,z+1);
        case SE:return new Coordinate(x+1,y-1,z);
        case SW:return new Coordinate(x-1,y,z+1);
        default:
            return null;
        }

    }

    public List<Coordinate> getNeighborsList(){
        List<Coordinate> list = new LinkedList<Coordinate>();
        for (Direction direction : Direction.values()) {
            list.add(getNeighbor(direction));
        }
        return list;

    }

    




}
