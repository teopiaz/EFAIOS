package it.polimi.ingsw.cg15.action;

import it.polimi.ingsw.cg15.controller.GameController;

/**
 * @author MMP - LMR
 * When a player lands in a dangerous game and draw a card sector of green type is called this class and run the execute method that allows the player to select a cell of the game in which pretend to disclose its position.
 */
public class NoiseGreen extends Action {

    /**
     * The icon of the item present in the sector card.
     */
    boolean hasItem=false;

    /**
     * @param gc the game controller
     * @param item the icon of the item present in the sector card
     */
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
