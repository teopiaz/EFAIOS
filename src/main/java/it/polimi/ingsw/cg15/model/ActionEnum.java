package it.polimi.ingsw.cg15.model;

/**
 * @author MMP - LMR
 * The class that contains the enumeration for the various type of action.
 * They are move, use item, discard, escape, attack, ask sector and end turn.
 */
public enum ActionEnum {
    MOVE,USEITEM,DISCARD,ESCAPE,ATTACK,ASKSECTOR,ENDTURN;
    
    /**
     * @see java.lang.Enum#toString()
     * Transform the enumeration into a string.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }

}
