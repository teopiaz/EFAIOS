package it.polimi.ingsw.cg15.model.cards;

/**
 * @author MMP - LMR
 * This are the hatch cards. They are of 6 types.
 */
public enum ItemCard implements Card {
    ITEM_DEFENSE("defense"), ITEM_ATTACK("attack"), ITEM_TELEPORT("teleport"), 
    ITEM_SEDATIVES("sedatives"), ITEM_SPOTLIGHT("spotlight"), ITEM_ADRENALINE("adrenaline");
    
    public String toString() {
        return name().substring(5).toLowerCase();
    }
    
    private String text;

    ItemCard(String text) {
        this.text = text;
      }

      public String getText() {
        return this.text;
      }

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