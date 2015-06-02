package it.polimi.ingsw.cg15.model;

public enum ActionEnum {
    MOVE,USEITEM,DISCARD,ESCAPE,ATTACK,ASKSECTOR,ENDTURN;
    
    
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    
    
}
