package it.polimi.ingsw.cg15.model.player;

/**
 * This class of model contains information on its type, it can be of three types namely human, alien and superalien. 
 * An alien becomes superalien after successfully attacked at least one human. A superalien can move to three cells within the same turn.
 * @author MMP - LMR
 *
 */
public enum PlayerType {

    HUMAN, ALIEN, SUPERALIEN;
    /**
     * This method returns the name of the type of player where the first letter is a capital letter and the rest do not.
     * @return
     */
    public String toClassName() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }

}
