package it.polimi.ingsw.cg15.cli.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author MMP - LMR
 * The class that contains method for the Client CLI Game.
 */
public class ClientGameCLI implements ViewClientInterface {


    /**
     * Variable that says if the game has started or not.
     */
    private static boolean isStarted = false;

    /**
     * The ID for the current player.
     */
    private static int currentPlayerId;

    /**
     * Variable that says if the game has ended or not.
     */
    private static boolean isEnded = false;

    /**
     * The network helper for communications.
     */
    private static NetworkHelper networkHelper;

    /**
     * The type of the player.
     */
    String playerType;

    /**
     * The current player position.
     */
    String currentPosition;

    /**
     * The player number.
     */
    int playerNumber;

    /**
     * The number of cards.
     */
    int cardNumber;

    /**
     * Variable that says if the player has moved or not.
     */
    private boolean hasMove = false;

    /**
     * Variable that says if the game has initialized or not.
     */
    private boolean init = true;

    /**
     * Scanner for IO.
     */
    private Scanner scanner = new Scanner(System.in);

    /**
     * A list of actions.
     */
    private List<String> actionList = new ArrayList<String>();

    /**
     * Variable that says if the player has attacked or not.
     */
    private boolean hasAttacked;

    /**
     * A list of cards.
     */
    private List<String> cardList = new ArrayList<String>();

    /**
     * Player constant string
     */
    public static final String PLAYER = "Player ";

    /**
     * @param ctoken The client token.
     * @param netHelper The network helper.
     * @param server The Socket communicator.
     * @param gmRemote The Remote Game Manager.
     */
    public ClientGameCLI(NetworkHelper netHelper) {
        ClientGameCLI.networkHelper = netHelper;
        netHelper.registerGui(this);
    }

    /**
     * Notification that a game is started.
     */
    public static void notifyStart() {
        isStarted = true;
        printToScreen("Game Started");
    }

    /**
     * Notification that a game is ended.
     */
    public static void notifyEnd() {
        isStarted = false;
        isEnded = true;
    }

    /**
     * Method to start the game. 
     * It contains the choice of the type of action to be performed.
     */
    public void start() {
        while (!isEnded) {
            if (isStarted) {
                gameInit();
                myTurnMenu();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Logger.getLogger(ClientGameCLI.class.getName()).log(Level.SEVERE, "Timer interrupted", e);
            }
        }
    }

    private void gameInit(){
        if (init) {
            getMap();
            getPlayerInfo();
            debugPrintPlayerInfo();
            getTurnInfo();
            init = false;
        }
    }

    private void myTurnMenu(){
        if (myTurn()) {
            printToScreen("Now is your turn!");
            getPlayerInfo();
            debugPrintPlayerInfo();
            getAvailableActionsList();
            debugPrintActionList();
            getAvailableCardList();
            debugPrintCardList();
            printToScreen("SELECT AN ACTION\n(m: move/a: attack/e: end turn/c: use card)\n");
            String choice = scanner.nextLine();
            switch (choice) {
            case "m":
                actionMove();
                break;
            case "a":
                attack();
                break;
            case "e":
                endTurn();
                break;
            case "c":
                useCardMenu();
                break;
            default:
                printToScreen("Invalid Action");
            }
        }
    }

    /**
     * The method for making noise in a given sector.
     */
    private void askSector() {
        boolean validSector = false;
        while (!validSector) {
            printToScreen("In which sector do you want to make noise?");
            String position = scanner.nextLine();
            if (position.matches("^[a-zA-Z][0-9]?[0-9]$")) {
                Event result = networkHelper.askSector(position);
                if (result.actionResult()) {
                    validSector = true;
                } else {
                    printToScreen(result.getRetValues().get(Event.ERROR));
                }
            }
        }
    }

    /**
     * The menu that allows you to use an item card.
     */
    private void useCardMenu() {
        if(!cardList.isEmpty()){
            getAvailableCardList();
            debugPrintCardList();
            String choice = scanner.nextLine();
            switch (choice) {
            case "teleport":
                actionUseCard("teleport");
                break;
            case "adrenaline":
                actionUseCard("adrenaline");
                break;
            case "sedatives":
                actionUseCard("sedatives");
                break;
            case "attack":
                actionUseCard("attack");
                break;
            case "spotlight":
                spotlight(choice);
                break;
            default: 
                printToScreen("No card Selected");
            }
        }
    }

    /**
     * The method to perform the item card spotlight.
     */
    private void spotlight(String spotlight) {
        
        if (cardList.contains(spotlight)) {
            printToScreen("Enter a sector where you want to make light:");
            String target = scanner.nextLine();
            Event response = networkHelper.spotlight(target);
            if(response.actionResult()){
                response.getRetValues().remove("return");
                if(response.getRetValues().isEmpty()){
                    printToScreen("No player in the sectors illuminated");
                }else{
                    for (Entry<String,String> player : response.getRetValues().entrySet()) {
                        printToScreen("The player "+player.getKey()+" is located in sector "+player.getValue() );
                    }
                }
            }
        } else {
            printToScreen("You do not have this card.");
        }
    }

    /**
     * @param card The card to use.
     */
    private void actionUseCard(String card) {
        if (cardList.contains(card)) {
            networkHelper.useCard(card);
        } else {
            printToScreen("You do not have this card.");
        }
        getAvailableCardList();
    }

    /**
     * A debug method for print the available cards.
     */
    private void debugPrintCardList() {
        printToScreen("CARTE DISPONIBILI " + cardList.size());
        for (String string : cardList) {
            printToScreen(string);
        }
    }

    /**
     * The method of attack.
     */
    private void attack() {
        if (!hasAttacked) {
            Event result;
            result = networkHelper.attack();
            if (result.actionResult()) {
                int killedPlayer = Integer.parseInt(result.getRetValues().get("killcount"));
                if (killedPlayer == 0) {
                    printToScreen("No player killed.");
                } else {
                    printToScreen("You killed " + killedPlayer + " player.");
                }
                hasAttacked = true;
            } else {
                printToScreen("ERRORE: " + result.getRetValues().get(Event.ERROR));
                hasAttacked = false;
            }
        }
    }

    /**
     * A debug method for print the available actions.
     */
    private void debugPrintActionList() {
        printToScreen("AZIONI DISPONIBILI");
        for (String string : actionList) {
            printToScreen(string);
        }
    }

    /**
     * Get the available actions.
     */
    private void getAvailableActionsList() {
        actionList = networkHelper.getAvailableActionsList();
    }

    /**
     * Get the available cards.
     */
    private void getAvailableCardList() {
        cardList = networkHelper.getAvailableCardsList();
    }

    /**
     * A debug method for print the player's information.
     */
    private void debugPrintPlayerInfo() {
        printToScreen("player number: " + playerNumber + "\n" + "player type: " + playerType + "\n"
                + "current position: " + currentPosition
                + "\n");
    }

    /**
     * @param s The strinf to print for debug.
     */
    public static void debugPrint(String s) {
        printToScreen(s);
    }

    /**
     * Method to end your turn.
     */
    private void endTurn() {
        Event result = networkHelper.endTurn();
        if (result.actionResult()) {
            printToScreen("FINE TURNO");
            hasMove = false;
            hasAttacked = false;
        }
    }

    /**
     * Get the information about the turn.
     */
    private void getTurnInfo() {
        ClientGameCLI.currentPlayerId = networkHelper.getTurnInfo();
    }

    /**
     * @return The player ID for the current player.
     */
    private boolean myTurn() {
        return currentPlayerId == playerNumber;
    }

    /**
     * Return the player's information.
     */
    private void getPlayerInfo() {
        Event result = networkHelper.getPlayerInfo();
        this.playerNumber = Integer.parseInt(result.getRetValues().get("playernumber"));
        this.currentPosition = result.getRetValues().get("currentposition");
        this.cardNumber = Integer.parseInt(result.getRetValues().get("cardnumber"));
        this.playerType = result.getRetValues().get("playertype");
        //numero 1 tipo Alien/Human/Superalien currentposition A03 numcard = 2
    }

    /**
     * Return the current map used.
     */
    private void getMap() {
        networkHelper.getMap();
    }

    /**
     * Method to make the action move.
     */
    private void actionMove() {
        if (!hasMove) {
            printToScreen("CURRENT POSITION: " + currentPosition);
            printToScreen("Enter your destination:");
            String destination = "";
            destination = scanner.nextLine();
            if (destination.matches("^[a-zA-Z][0-9]?[0-9]$")) {
                Event result = networkHelper.move(destination);
                if (result.actionResult()) {
                    currentPosition = result.getRetValues().get("destination");
                    printToScreen("New position: " + currentPosition);
                    hasMove = true;
                    if (result.getRetValues().containsKey("asksector")) {
                        askSector();
                    }

                    if ( result.getRetValues().containsKey(Event.ITEM) &&  Event.TRUE.equals(result.getRetValues().get(Event.ITEM)) ) {
                        printToScreen("You draw the card: " + (result.getRetValues().get("card")));
                    }

                } else {
                    printToScreen("ERROR: " + result.getRetValues().get(Event.ERROR));
                    hasMove = false;
                }
            }
        }
    }

    /**
     * @param currentPlayer The current player to set.
     */
    public static void setCurrentPlayer(int currentPlayer) {
        currentPlayerId = currentPlayer;
    }

    /**
     * Print to screen informations.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#stampa(java.lang.String)
     */
    @Override
    public void stampa(String msg) {
        printToScreen(msg);
    }

    /** 
     * The logger. It contains information and things to show to various users.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#log(it.polimi.ingsw.cg15.networking.Event)
     */
    @Override
    public void log(Event e) {
        if (e.getRetValues().containsKey("move")) {
            String player = e.getRetValues().get(Event.PLAYER);
            String sector = e.getRetValues().get("move");
            printToScreen(PLAYER + player + " has moved in: " + sector);
        }

        if(e.getRetValues().containsKey(Event.MESSAGE)) {
            printToScreen(e.getRetValues().get(Event.MESSAGE));
        }

        logCard(e);
        logAttack(e);
        if ( e.getRetValues().containsKey("noise") &&  Event.TRUE.equals(e.getRetValues().get("noise"))   ) {
            String playerNum = e.getRetValues().get(Event.PLAYER);
            String position = e.getRetValues().get("position");
            printToScreen(PLAYER + playerNum + ": noise in the sector " + position);
        }

        if (e.getRetValues().containsKey("hatch")) {
            if (e.getRetValues().get("hatch").equals(Event.FALSE)) {
                printToScreen(e.getRetValues().get(Event.MESSAGE));
            } else {
                String player = e.getRetValues().get(Event.PLAYER);
                printToScreen("Player" + player + " has drawn an hatch card " + e.getRetValues().get("hatchcard"));
            }
        }
    }
    
    private void logCard(Event e) {
        if (e.getRetValues().containsKey("card")) {
            String player = e.getRetValues().get(Event.PLAYER);
            String card = e.getRetValues().get("card");
            printToScreen(PLAYER + player + " used " + card+"card");
            
            if("spotlight".equals(card)){
                e.getRetValues().remove("card");
                e.getRetValues().remove(Event.PLAYER);
                for (Entry<String,String> ret : e.getRetValues().entrySet()) {
                    printToScreen(PLAYER + ret.getKey() + " spotted in sector: " + ret.getValue());

                }
            }
        }
        
    }

    private void logAttack(Event e) {
        if (e.getRetValues().containsKey(Event.ATTACK)) {
            String playerNum = e.getRetValues().get(Event.PLAYER);
            String position = e.getRetValues().get(Event.ATTACK);
            printToScreen(PLAYER + playerNum + ": attacks in the sector: " + position);
            int count = 0;
            for (Entry<String, String> ret : e.getRetValues().entrySet()) {
                if ( "killed".equals(ret.getValue())) {
                    printToScreen(PLAYER + ret.getKey() + " was killed by the player " + playerNum);
                    count++;
                }
            }

            printToScreen(count +" killed.");
        }
        
    }

    @Override
    public void chat(Event e) {
        printToScreen("Chat :" + "[Player " + e.getRetValues().get(Event.PLAYER) + "]" + " "+ e.getRetValues().get(Event.MESSAGE));
    }

    /** 
     * Set the game as started.
     * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#setStarted()
     */
    @Override
    public void setStarted() {
        notifyStart();
    }

    /**
     * @param s The string to print.
     */
    public static void printToScreen(String s) {
        System.out.println(s);
    }

    /**
     * currentPlayer The current player.
     */
    @Override
    public void currentPlayer(int currentPlayer) {
        ClientGameCLI.currentPlayerId=currentPlayer;
    }

    @Override
    public void endGame(Event e) {

        boolean winner = false;
        for (Entry<String, String> ele : e.getRetValues().entrySet()) {
            printToScreen(PLAYER + ele.getKey() + ": " + ele.getValue());
            if(ele.getKey().equals(Integer.toString(playerNumber)) && "win".equals(ele.getValue())){
                winner=true;
            }
        }
        if(winner){
            printToScreen("You Win the match");
        }
        else{
            printToScreen("You Lose the match");
        }

        notifyEnd();


    }

}
