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

public class ClientGameCLI implements ViewClientInterface {

	private static ClientToken ctoken;
	private static boolean isStarted = false;

	private static int currentPlayerId;
	private static boolean isEnded = false;
	private static NetworkHelper networkHelper;

	String playerType;
	String currentPosition;
	int playerNumber;
	int cardNumber;
	private boolean hasMove = false;
	private boolean init = true;
	private Scanner scanner = new Scanner(System.in);
	private List<String> actionList = new ArrayList<String>();
	private boolean hasAttacked;
	private List<String> cardList = new ArrayList<String>();

	public ClientGameCLI(ClientToken ctoken, NetworkHelper netHelper, SocketCommunicator server,GameManagerRemote gmRemote) {
		ClientGameCLI.ctoken = ctoken;

		ClientGameCLI.networkHelper = netHelper;
		netHelper.registerGui(this);

	}

	public static void notifyStart() {
		isStarted = true;
	}

	public static void notifyEnd() {
		isStarted = false;
		isEnded = true;
	}

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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void askSector() {
		boolean validSector = false;
		while (!validSector) {
			printToScreen("In quale settore vuoi fare rumore?");
			String position = scanner.nextLine();

			Event result = networkHelper.askSector(position);

			if (result.actionResult()) {
				validSector = true;
			} else {
				printToScreen(result.getRetValues().get("error"));
			}
		}
	}

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

	private void spotlight() {
		if (cardList.contains("spotlight")) {
			printToScreen("Inserisci un settore da illuminare");
			String target = scanner.nextLine();
			Event response = networkHelper.spotlight(target);
			if(response.actionResult()){
				response.getRetValues().remove("return");
				if(response.getRetValues().isEmpty()){
					printToScreen("Nessun giocatore nei settori illuminati");
				}else{
					for (Entry<String,String> player : response.getRetValues().entrySet()) {
						printToScreen("Il giocatore "+player.getKey()+" si trova nel settore "+player.getValue() );
					}
				}
			}
		} else {
			printToScreen("Non possiedi questa carta");
		}

	}

	private void actionUseCard(String card) {
		if (cardList.contains(card)) {

			Event result = networkHelper.useCard(card);

		} else {
			printToScreen("Non possiedi questa carta");
		}
		getAvailableCardList();

	}

	private void debugPrintCardList() {
		printToScreen("CARTE DISPONIBILI " + cardList.size());
		for (String string : cardList) {
			printToScreen(string);
		}

	}

	private void attack() {
		if (!hasAttacked) {

			Event e = new Event(ctoken, "attack", null);
			Event result;

			result = networkHelper.attack();
			if (result.actionResult()) {
				int killedPlayer = Integer.parseInt(result.getRetValues().get("killcount"));
				if (killedPlayer == 0) {
					printToScreen("Nessuna Vittima");
				} else {
					printToScreen("Hai ucciso " + killedPlayer + " giocatori");
				}
				hasAttacked = true;

			} else {
				printToScreen("ERRORE: " + result.getRetValues().get("error"));
				hasAttacked = false;
			}
		}
	}

	private void debugPrintActionList() {
		printToScreen("AZIONI DISPONIBILI");
		for (String string : actionList) {
			printToScreen(string);
		}

	}

	private void getAvailableActionsList() {
		actionList = networkHelper.getAvailableActionsList();
	}

	private void getAvailableCardList() {

		cardList = networkHelper.getAvailableCardsList();
	}

	private void debugPrintPlayerInfo() {
		printToScreen("player number: " + playerNumber + "\n" + "player type: " + playerType + "\n"
				+ "num cards: " + cardList.size() + "\n" + "current position: " + currentPosition
				+ "\n");

	}

	public static void debugPrint(String s) {
		printToScreen(s);
	}

	private void endTurn() {
		Event result = networkHelper.endTurn();

		if (result.actionResult()) {
			printToScreen("FINE TURNO");
			hasMove = false;
			hasAttacked = false;
		}

	}

	private void getTurnInfo() {
		ClientGameCLI.currentPlayerId = networkHelper.getTurnInfo();
	}

	private boolean myTurn() {
		return currentPlayerId == playerNumber;
	}

	private void getPlayerInfo() {

		Event result = networkHelper.getPlayerInfo();
		this.playerNumber = Integer.parseInt(result.getRetValues().get("playernumber"));
		this.currentPosition = result.getRetValues().get("currentposition");
		this.cardNumber = Integer.parseInt(result.getRetValues().get("cardnumber"));
		this.playerType = result.getRetValues().get("playertype");

		/*
		 * numero 1 tipo Alien/Human/Superalien currentposition A03 numcard = 2
		 */

	}

	private void getMap() {

		String map = networkHelper.getMap();

	}

	private void actionMove() {
		if (!hasMove) {
			printToScreen("POSIZIONE ATTUALE: " + currentPosition);
			printToScreen("inserisci la destinazione:");
			String destination = "";

			destination = scanner.nextLine();

			Event result = networkHelper.move(destination);

			if (result.actionResult()) {
				currentPosition = result.getRetValues().get("destination");
				printToScreen("Nuova Posizione: " + currentPosition);
				hasMove = true;

				if (result.getRetValues().containsKey("asksector")) {
					askSector();
				}
				if (result.getRetValues().containsKey("item")) {
					if (result.getRetValues().get("item").equals("true")) {
						printToScreen("hai pescato la carta " + (result.getRetValues().get("card")));
					}
				}

			} else {
				printToScreen("ERRORE: " + result.getRetValues().get("error"));
				hasMove = false;
			}
		}
	}

	public static void setCurrentPlayer(int currentPlayer) {
		currentPlayerId = currentPlayer;
	}

	public void stampa(String msg) {
		printToScreen(msg);
	}

	public void log(Event e) {
		if (e.getRetValues().containsKey("move")) {
			String player = e.getRetValues().get("player");
			String sector = e.getRetValues().get("move");
			printToScreen("Giocatore " + player + " si è mosso in " + sector);
		}
		
	      if (e.getRetValues().containsKey("card")) {
	            String player = e.getRetValues().get("player");
	            String card = e.getRetValues().get("card");
	            printToScreen("Giocatore " + player + " ha usato la carta " + card);
	        }
		if (e.getRetValues().containsKey("attack")) {
			String playerNum = e.getRetValues().get("player");
			String position = e.getRetValues().get("attack");
			printToScreen("Giocatore " + playerNum + ": attacca nel settore " + position);
			int count = 0;
			for (Entry<String, String> ret : e.getRetValues().entrySet()) {
				if (ret.getValue().equals("killed")) {
					printToScreen("Giocatore " + ret.getKey() + " ucciso dal giocatore " + playerNum);
					count++;
				}
			}
			if (count == 0) {
				printToScreen("Nessuna Vittima");
			}

		}
		if (e.getRetValues().containsKey("noise")) {
			if (e.getRetValues().get("noise").equals("true")) {
				String playerNum = e.getRetValues().get("player");
				String position = e.getRetValues().get("position");
				printToScreen("Giocatore " + playerNum + ": rumore in settore " + position);
			}
		}
		if (e.getRetValues().containsKey("hatch")) {
			if (e.getRetValues().get("hatch").equals("false")) {
				printToScreen(e.getRetValues().get("message"));
			} else {
				String player = e.getRetValues().get("player");

				printToScreen("il giocatore" + player + " ha pescato una hatch card " + e.getRetValues().get("hatchcard"));
			}
		}
	}

	public void chat(Event e) {
		printToScreen("Chat :" + "[Giocatore " + e.getRetValues().get("player") + "]" + " "+ e.getRetValues().get("message"));
	}

	public void setStarted() {
		notifyStart();
	}

	public static void printToScreen(String s) {
		System.out.println(s);
	}

	@Override
	public void currentPlayer(int currentPlayer) {
		this.currentPlayerId=currentPlayer;
	}

}
