package it.polimi.ingsw.cg15.controller;

import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Field;

import org.junit.Before;


public class FieldControllerTest {

    FieldController fc;
    Field field = new Field();

    @Before
    public void setUp() throws Exception {
        
        GameState gs = new GameState(field, null);
        fc = new FieldController(gs);
    }




}
