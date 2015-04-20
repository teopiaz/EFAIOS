package it.polimi.ingsw.cg10;

public class Logger {

	static Logger istance=null;
	
	private Logger(){
		
	}
	
	public static Logger getIstance(){
		if(istance!=null){
			return istance;
			
		}
		else{
			istance = new Logger();
		}
		return istance;
	}
	
	
	public void debug(String s){
		
		System.out.println("Debug:"+s);
		
	}
	
}
