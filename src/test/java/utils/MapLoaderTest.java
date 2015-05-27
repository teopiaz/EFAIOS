package utils;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.utils.MapLoader;

import org.junit.Test;

public class MapLoaderTest {


    @Test
    public void testMapLoadingFromLocalFile() {
        Field field = new Field();
        assertFalse(MapLoader.loadMap(field, null));
        assertTrue(MapLoader.loadMap(field, "galilei"));
        
    }
    @Test
    public void testMapLoadingFromExternalFile() {
        Field field = new Field();
        MapLoader.loadMap(field, "galilei");
        MapLoader.saveMap(field, "pippo");
        assertTrue(MapLoader.loadMap(field, "pippo"));
        assertFalse(MapLoader.loadMap(field, "pluto"));

    }

}
