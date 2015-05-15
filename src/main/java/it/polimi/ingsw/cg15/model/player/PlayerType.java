package it.polimi.ingsw.cg15.model.player;

public enum PlayerType {
HUMAN,
ALIEN,
SUPERALIEN;

public String toClassName() {
    return name().charAt(0) + name().substring(1).toLowerCase();
}



}
