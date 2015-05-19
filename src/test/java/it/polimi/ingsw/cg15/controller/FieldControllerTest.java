package it.polimi.ingsw.cg15.controller;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.GameState;
import it.polimi.ingsw.cg15.model.field.Field;

import org.junit.Before;
import org.junit.Test;


public class FieldControllerTest {

    FieldController fc;
    Field field = new Field();

    @Before
    public void setUp() throws Exception {
        
        GameState gs = new GameState(field, null, null);
        fc = new FieldController(gs);
    }




}
