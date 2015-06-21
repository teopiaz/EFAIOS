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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author MMP - LMR
 * The class that is responsible for loading and saving the map.
 * Galilei, Galvani and Fermi are already available.
 */
public class MapLoader {

    /**
     * List of maps already created.
     */
    private static final List<String> LOCALMAPLIST = new ArrayList<String>() ;
    

    /**
     * The constructor.
     */
    private MapLoader() {
        LOCALMAPLIST.add("galvani"); 
        LOCALMAPLIST.add("galilei"); 
        LOCALMAPLIST.add("fermi"); 
    }
    
    /**
     * Load a map.
     * @param field The field.
     * @param mapName The name of the map.
     * @return true if the map has been created correctly.
     */
    public static boolean loadMap(Field field, String mapName) {
        InputStream fin = null;
        if (mapName == null) {
            return false;
        }
        String fName ="maps/"+ mapName + ".txt";
        if(LOCALMAPLIST.contains(mapName)){
            fName =mapName + ".txt";
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
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "IO exception", e1);
        }
        splitLineAndAdd(field,line, reader);
        try {
            reader.close();
        } catch (IOException e) {
            Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "MapLoad close IOException", e);
        }
        return true;
    }

    private static void splitLineAndAdd(Field field, String lineArg, BufferedReader reader ) {
        String line = lineArg;
        while (line != null) {
            String[] splitted = line.split(",");
            int c = Integer.valueOf(splitted[1]);
            int r = Integer.valueOf(splitted[0]);
            int type = Integer.valueOf(splitted[2]);
                addToField(field,r,c,type);
            try {
                line = reader.readLine();
            } catch (IOException e1) {
                Logger.getLogger(MapLoader.class.getName()).log(Level.SEVERE, "MapLoad readline IOException", e1);
            }
        }
        
    }

    private static void addToField(Field field, int r, int c, int type) {
        if (type!=0) {
            Coordinate coord = new Coordinate(r, c);
            field.addCell(coord, Sector.valueOf(type));
            if(type==3){
                field.addHatchToList(coord);
            }
            if(type==4){
                field.setHumanStartingPosition(field.getCell(coord));
            }
            if(type==5){
                field.setAlienStartingPosition(field.getCell(coord));
            }   
        }
        
    }

    /**
     * Save a map.
     * @param field The field.
     * @param mapName The name of the map.
     */
    public static void saveMap(Field field,String strMapName) {
        FileOutputStream fop = null;
        File file;
        String mapName = strMapName;
        if(mapName==""){
            mapName="map";
        }
        try {
            file = new File("maps/"+mapName+".txt");
            fop = new FileOutputStream(file);
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            String content="";
            // get the content in bytes
            for(int r=0;r<15;r++){
                for(int c=0; c<23; c++){
                    Cell cell = field.getCell(new Coordinate(c, r));
                    if(cell!=null){
                        content=content+(r)+","+(c)+","+ cell.getSectorType().getValue() +"\n"; 
                    }
                }
            }
            byte[] contentInBytes = content.getBytes();
            fop.write(contentInBytes);
            fop.flush();
            fop.close();
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
