package it.polimi.ingsw.cg15.model.field;

import java.util.HashMap;
import java.util.Map;

public enum Sector {
    GREY(1), WHITE(2), HATCH(3), HUMAN(4), ALIEN(5);
    
    private int sectorNum;
    private static Map<Integer, Sector> map = new HashMap<Integer, Sector>();


    static {
        for (Sector sectorElement : Sector.values()) {
            map.put(sectorElement.sectorNum, sectorElement);
        }
    }

    private Sector(final int num) { sectorNum = num; }
    
    public static Sector valueOf(int sectorNum) {
        return map.get(sectorNum);
    }

    
}