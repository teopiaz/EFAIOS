package it.polimi.ingsw.cg15.model.cards;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ItemCardTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testToString() {
        
        // Testo la trasposizione in stringa di una carta oggetto.

        ItemCard test = ItemCard.ITEM_TELEPORT;
        assertEquals("teleport", test.toString());
    }
    
    @Test
    public final void testFromString() {
        
        // Testo la trasposizione di una carta oggetto in stringa.

        ItemCard test = ItemCard.ITEM_TELEPORT;
      
        String text = "teleport";
        ItemCard test2 = ItemCard.fromString(text);
        assertEquals(test, test2); test2.getText();
        assertEquals(test, test2);
        ItemCard.valueOf("ITEM_TELEPORT");
        
        
        
        
        
    }
    
    

}
