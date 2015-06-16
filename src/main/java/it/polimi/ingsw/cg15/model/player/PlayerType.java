package it.polimi.ingsw.cg15.model.player;

/**
 * @author MMP - LMR
 * This class of model contains information on its type, it can be of three types namely human, alien and super alien. 
 * An alien becomes super alien after successfully attacked at least one human. A super alien can move to three cells within the same turn.
 */
public enum PlayerType {

    HUMAN, ALIEN, SUPERALIEN;
    /**
     * This method returns the name of the type of player where the first letter is a capital letter and the rest do not.
     * @return the name of the player.
     */
    public String toClassName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

}
