package it.polimi.ingsw.cg15.model;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActionEnumTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void test() {
        
        // Testo l'enumerazione.
        
        assertEquals(ActionEnum.MOVE, ActionEnum.valueOf("MOVE"));

    }

}
