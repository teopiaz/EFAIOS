package it.polimi.ingsw.cg15.model.field;

import java.util.HashMap;
import java.util.Map;

/**
 * The class that contains the enumeration of possible types of sector inside the map.
 * @author MMP - LMR
 */
public enum Sector {
    GREY(1), WHITE(2), HATCH(3), HUMAN(4), ALIEN(5);
    
    /**
     * Each type of sector is represented for convenience also by a number.
     */
    private int sectorNum;
    
    private static Map<Integer, Sector> map = new HashMap<Integer, Sector>();

    static {
        for (Sector sectorElement : Sector.values()) {
            map.put(sectorElement.sectorNum, sectorElement);
        }
    }

    private Sector(final int num) { sectorNum = num; }
    
    /**
     * @param the number of the sector type.
     * @return 
     */
    public static Sector valueOf(int sectorNum) {
        return map.get(sectorNum);
    }
    
    /**
     * @return the number of the sector type
     */
    public int getValue() {
        return sectorNum;
    }

}