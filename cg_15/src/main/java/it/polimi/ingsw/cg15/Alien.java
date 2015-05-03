package it.polimi.ingsw.cg15;

/**
 * 
 */
public class Alien extends Player {

    /**
     * 
     */
    public Alien() {
    }

    /**
     * @return
     */
	@Override
    public void move(Cell dest) {
    	if(this.position.distance(dest)<=2){
    		this.position=dest;
    	}
    }

    /**
     * @return
     */
    private void pesca() {
    }



}