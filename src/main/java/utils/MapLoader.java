package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;

public class MapLoader {

    private MapLoader() {

    }

    public static boolean loadMap(Field field, String mapName) {
        InputStream fin = null;

        if (mapName == null) {
            return false;
        }
        String fName = mapName + ".txt";

        fin = MapLoader.class.getClassLoader().getResourceAsStream(fName);

        if (fin == null) {
            return false;
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(fin));
        String line = null;

        try {
            line = reader.readLine();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        while (line != null) {
            String[] splitted = line.split(",");
            int c = Integer.valueOf(splitted[1]);
            int r = Integer.valueOf(splitted[0]);
            int type = Integer.valueOf(splitted[2]);
            
            if (type!=0) {
                Coordinate coord = new Coordinate(r, c);
                field.addCell(coord, Sector.valueOf(type));
            }
            
            try {
                line = reader.readLine();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;

    }

    public static void saveMap(Field field) {

    }

}
