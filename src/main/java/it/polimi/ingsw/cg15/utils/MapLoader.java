package it.polimi.ingsw.cg15.utils;

import it.polimi.ingsw.cg15.model.field.Cell;
import it.polimi.ingsw.cg15.model.field.Coordinate;
import it.polimi.ingsw.cg15.model.field.Field;
import it.polimi.ingsw.cg15.model.field.Sector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapLoader {

    private static final ArrayList<String> localMapList = new ArrayList<String>() ;
    
    static{
        localMapList.add("galvani"); 
        localMapList.add("galilei"); 
        localMapList.add("fermi"); 
    }

    
    
    private MapLoader() {

    }

    public static boolean loadMap(Field field, String mapName) {
        InputStream fin = null;

        if (mapName == null) {
            return false;
        }

        String fName = mapName + ".txt";

        if(localMapList.contains(mapName)){
            fin = MapLoader.class.getClassLoader().getResourceAsStream(fName);

            if (fin == null) {
                return false;
            }
        }
        else{
            File file = new File(fName);
            try {
                fin=new FileInputStream(file);
            } catch (FileNotFoundException e1) {
                Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "File "+ fName+" not found", e1);
                return false;
            }
            
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
                Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "MapLoad readline IOException", e1);

            }
        }

        try {
            reader.close();
        } catch (IOException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "MapLoad close IOException", e);

        }
        return true;

    }

    public static void saveMap(Field field,String mapName) {


        FileOutputStream fop = null;
        File file;

        if(mapName==""){
            mapName="map";
        }
        try {

            file = new File(mapName+".txt");
            fop = new FileOutputStream(file);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            String content="";
            // get the content in bytes
            for(int i=0;i<15;i++){
                for(int j=0; j<23; j++){
                    Cell cell = field.getCell(new Coordinate(j, i));
                    if(cell!=null){
                        content=content+(i)+","+(j)+","+ cell.getSectorType().getValue() +"\n"; 

                    }
                }
            }
            byte[] contentInBytes = content.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "Mapsave IOException", e);
        } finally {
            try {
                if (fop != null) {
                    fop.close();
                }
            } catch (IOException e) {
                Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "Mapsave file close IOException", e);
            }
        }
    }

}