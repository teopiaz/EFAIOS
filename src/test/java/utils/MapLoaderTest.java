package utils;

import static org.junit.Assert.*;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;
import org.junit.Test;

public class MapLoaderTest {


    @Test
    public void testMapLoadingFromFile() {
        Field field = new Field();
        assertFalse(MapLoader.loadMap(field, "nonesiste"));
        assertFalse(MapLoader.loadMap(field, null));

        MapLoader.loadMap(field, "galilei");
        field.printMap();
        
    }

}
