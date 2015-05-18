package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;

public class NoiseGreen extends Action {

    boolean hasItem=false;

    public NoiseGreen(GameController gc, boolean item) {
        // TODO Auto-generated constructor stub
        super(gc);
        this.hasItem=item;

    }

    @Override
    public boolean execute() {

        if(hasItem){
            Action draw = new DrawItemCard(getGameController());
            draw.execute();
            Action noise =new MakeNoise(getGameController());
            noise.execute();
        }
        
        return false;
    }

}
