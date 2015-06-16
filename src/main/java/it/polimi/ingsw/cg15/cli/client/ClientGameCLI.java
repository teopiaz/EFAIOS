package it.polimi.ingsw.cg15.cli.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;

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
	 * The client token.
	 */
	private static ClientToken ctoken;
	
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
	 * @param ctoken The client token.
	 * @param netHelper The network helper.
	 * @param server The Socket communicator.
	 * @param gmRemote The Remote Game Manager.
	 */
	public ClientGameCLI(ClientToken ctoken, NetworkHelper netHelper, SocketCommunicator server,GameManagerRemote gmRemote) {
		ClientGameCLI.ctoken = ctoken;
		ClientGameCLI.networkHelper = netHelper;
		netHelper.registerGui(this);
	}

	/**
	 * Notification that a game is started.
	 */
	public static void notifyStart() {
		isStarted = true;
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
				if (init) {
					getMap();
					getPlayerInfo();
					getTurnInfo();
					printToScreen("Game Started");
					printToScreen("E' il mio turno? " + myTurn());
					init = false;
				}
				if (myTurn()) {
					printToScreen("E' il tuo turno");
					getPlayerInfo();
					debugPrintPlayerInfo();
					getAvailableActionsList();
					debugPrintActionList();
					getAvailableCardList();
					debugPrintCardList();
					printToScreen("SELEZIONA UN AZIONE");
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
					case "ask":
						askSector();
						break;
					default:
						printToScreen("Azione Non Valida");
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Logger.getLogger(ClientGameCLI.class.getName()).log(Level.SEVERE, "Timer interrupted", e);
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
			Event result = networkHelper.askSector(position);
			if (result.actionResult()) {
				validSector = true;
			} else {
				printToScreen(result.getRetValues().get("error"));
			}
		}
	}

	/**
	 * The menu that allows you to use an item card.
	 */
	private void useCardMenu() {
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
			spotlight();
		}
	}

	/**
	 * The method to perform the item card spotlight.
	 */
	private void spotlight() {
		if (cardList.contains("spotlight")) {
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
			Event result = networkHelper.useCard(card);
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
			Event e = new Event(ctoken, "attack", null);
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
				printToScreen("ERRORE: " + result.getRetValues().get("error"));
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
				+ "num cards: " + cardList.size() + "\n" + "current position: " + currentPosition
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
		String map = networkHelper.getMap();
	}

	/**
	 * Method to make the action move.
	 */
	private void actionMove() {
		if (!hasMove) {
			printToScreen("POSIZIONE ATTUALE: " + currentPosition);
			printToScreen("Enter your destination:");
			String destination = "";
			destination = scanner.nextLine();
			Event result = networkHelper.move(destination);
			if (result.actionResult()) {
				currentPosition = result.getRetValues().get("destination");
				printToScreen("New position: " + currentPosition);
				hasMove = true;
				if (result.getRetValues().containsKey("asksector")) {
					askSector();
				}
				if (result.getRetValues().containsKey("item")) {
					if (result.getRetValues().get("item").equals("true")) {
						printToScreen("You draw the card: " + (result.getRetValues().get("card")));
					}
				}
			} else {
				printToScreen("ERRORE: " + result.getRetValues().get("error"));
				hasMove = false;
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
	public void stampa(String msg) {
		printToScreen(msg);
	}

	/** 
	 * The logger. It contains information and things to show to various users.
	 * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#log(it.polimi.ingsw.cg15.networking.Event)
	 */
	public void log(Event e) {
		if (e.getRetValues().containsKey("move")) {
			String player = e.getRetValues().get("player");
			String sector = e.getRetValues().get("move");
			printToScreen("Player " + player + " has moved in: " + sector);
		}
		
	      if (e.getRetValues().containsKey("card")) {
	            String player = e.getRetValues().get("player");
	            String card = e.getRetValues().get("card");
	            printToScreen("Giocatore " + player + " ha usato la carta " + card);
	        }
		if (e.getRetValues().containsKey("attack")) {
			String playerNum = e.getRetValues().get("player");
			String position = e.getRetValues().get("attack");
			printToScreen("Player " + playerNum + ": attacks in the sector: " + position);
			int count = 0;
			for (Entry<String, String> ret : e.getRetValues().entrySet()) {
				if (ret.getValue().equals("killed")) {
					printToScreen("Player " + ret.getKey() + " was killed by the player " + playerNum);
					count++;
				}
			}
			if (count == 0) {
				printToScreen("No one was killed.");
			}
		}
		if (e.getRetValues().containsKey("noise")) {
			if (e.getRetValues().get("noise").equals("true")) {
				String playerNum = e.getRetValues().get("player");
				String position = e.getRetValues().get("position");
				printToScreen("Player " + playerNum + ": noise in the sector " + position);
			}
		}
		if (e.getRetValues().containsKey("hatch")) {
			if (e.getRetValues().get("hatch").equals("false")) {
				printToScreen(e.getRetValues().get("message"));
			} else {
				String player = e.getRetValues().get("player");
				printToScreen("Player" + player + " has drawn an hatch card " + e.getRetValues().get("hatchcard"));
			}
		}
	}

	public void chat(Event e) {
		printToScreen("Chat :" + "[Player " + e.getRetValues().get("player") + "]" + " "+ e.getRetValues().get("message"));
	}

	/** 
	 * Set the game as started.
	 * @see it.polimi.ingsw.cg15.gui.ViewClientInterface#setStarted()
	 */
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
		this.currentPlayerId=currentPlayer;
	}

}
