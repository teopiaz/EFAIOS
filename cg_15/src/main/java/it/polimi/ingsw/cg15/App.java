package it.polimi.ingsw.cg15;

/**
 * Hello world!
 *
 */

public class App 
{
	int test;
	private static final Logger LOGGER = Logger.getIstance();
	public App(){
	test=10;
	
	
	}
	
	
    public static void main( String[] args )
    {
    	Field map = new Field(3,5);

    	int d = map.getCell(20, 2).distance(map.getCell(3, 4));
    	System.out.println(d);

    	map.printMap();
    	
    	LOGGER.debug("Hello World");
    	
    }
}
