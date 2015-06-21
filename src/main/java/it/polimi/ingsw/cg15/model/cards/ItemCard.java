package it.polimi.ingsw.cg15.model.cards;

/**
 * @author MMP - LMR
 * This are the hatch cards. They are of 6 types. Defense, Attack, Teleport, Sedatives, Spotlight, Adrenaline.
 */
public enum ItemCard implements Card {
    ITEM_DEFENSE("defense"), ITEM_ATTACK("attack"), ITEM_TELEPORT("teleport"), 
    ITEM_SEDATIVES("sedatives"), ITEM_SPOTLIGHT("spotlight"), ITEM_ADRENALINE("adrenaline");

    /** 
     * @see java.lang.Enum#toString()
     * Convert into a string.
     */
    @Override
    public String toString() {
        return name().substring(5).toLowerCase();
    }

    /**
     * string rappresentation of ItemCard enumeration value
     */
    private String text;

    /**
     * The constructor.
     * @param text The text.
     */
    ItemCard(String text) {
        this.text = text;
    }

    /**
     * @return The text.
     */
    public String getText() {
        return this.text;
    }

    /**
     * @param text The text to convert.
     * @return A card from a text.
     */
    public static ItemCard fromString(String text) {
        if (text != null) {
            for (ItemCard b : ItemCard.values()) {
                if (text.equalsIgnoreCase(b.text)) {
                    return b;
                }
            }
        }
        return null;
    }

}