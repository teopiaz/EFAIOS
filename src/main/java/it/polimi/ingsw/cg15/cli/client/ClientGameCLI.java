package it.polimi.ingsw.cg15.cli.client;

import it.polimi.ingsw.cg15.NetworkHelper;
import it.polimi.ingsw.cg15.gui.ViewClientInterface;
import it.polimi.ingsw.cg15.networking.ClientToken;
import it.polimi.ingsw.cg15.networking.Event;
import it.polimi.ingsw.cg15.networking.GameManagerRemote;
import it.polimi.ingsw.cg15.networking.NetworkProxy;
import it.polimi.ingsw.cg15.networking.SocketCommunicator;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

public class ClientGameCLI implements ViewClientInterface{

	private static SocketCommunicator server;
	private static GameManagerRemote gmRemote;
	private static ClientToken ctoken;
	private static boolean isStarted = false;
	private String ip="127.0.0.1";
	private int port = 1337;


	private static int currentPlayerId;
	private static boolean isEnded=false;
	private static NetworkHelper networkHelper;

	String playerType;
	String currentPosition;
	int playerNumber;
	int cardNumber;
	private boolean myTurn;
	private boolean hasMove=false;
	private boolean init = true;
	private Scanner scanner = new Scanner(System.in);
	private List<String> actionList=new ArrayList<String>();
	private boolean hasAttacked;
	private List<String> cardList=new ArrayList<String>();
	private int cardsSize;



	public ClientGameCLI(ClientToken ctoken,NetworkHelper netHelper, SocketCommunicator server, GameManagerRemote gmRemote) {
		ClientGameCLI.ctoken = ctoken;
		ClientGameCLI.server = server;
		ClientGameCLI.gmRemote = gmRemote;
		ClientGameCLI.networkHelper = netHelper;
		netHelper.registerGui(this);


	}

	public static void notifyStart(){
		isStarted=true;
	}
	public static void notifyEnd(){
		isStarted=false;
		isEnded=true;
	}


	public void start(){
		while(!isEnded){

			if(isStarted){
				if(init){
					getMap();
					getPlayerInfo();
					getTurnInfo();
					System.out.println("Game Started");
					System.out.println("E' il mio turno? "+myTurn());
					init=false;
				}
				if(myTurn()){
					System.out.println("E' il tuo turno");
					getPlayerInfo();
					debugPrintPlayerInfo();
					getAvailableActionsList();
					debugPrintActionList();
					getAvailableCardList();
					debugPrintCardList();
					System.out.println("SELEZIONA UN AZIONE");
					String choice = scanner.nextLine();

					switch(choice){

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
						System.out.println("Azione Non Valida");
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
		boolean validSector=false;
		while(!validSector){
			System.out.println("In quale settore vuoi fare rumore?");
			String position = scanner.nextLine();

			Event result = networkHelper.askSector(position);

			System.out.println(result);
			if(result.actionResult()){
				validSector=true;
			}else{
				System.out.println(result.getRetValues().get("error"));
			}
		}
	}

	private void useCardMenu() {
		getAvailableCardList();
		debugPrintCardList();
		String choice = scanner.nextLine();

		switch(choice){

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
		if(cardList.contains("spotlight")){
			System.out.println("Inserisci un settore da illuminare");
			String target = scanner.nextLine();
			networkHelper.spotlight(target);
		}
		else{
			System.out.println("Non possiedi questa carta");
		}

	}

	private void actionUseCard(String card) {
		if(cardList.contains(card)){

			Event result = networkHelper.useCard(card);

		}
		else{
			System.out.println("Non possiedi questa carta");
		}
		getAvailableCardList();


	}

	private void debugPrintCardList() {
		System.out.println("CARTE DISPONIBILI "+cardNumber);
		for (String string : cardList) {
			System.out.println(string);
		}

	}

	private void attack() {
		if(!hasAttacked){


			Event e = new Event(ctoken,"attack",null);
			Event result;

			result = networkHelper.attack();
			System.out.println(result);
			if(result.actionResult()){
				int killedPlayer =Integer.parseInt(result.getRetValues().get("killcount"));
				if(killedPlayer==0){
					System.out.println("Nessuna Vittima");
				}
				else{
					System.out.println("Hai ucciso "+killedPlayer+" giocatori");
				}
				hasAttacked=true;

			}else{
				System.out.println("ERRORE: "+result.getRetValues().get("error"));
				hasAttacked=false;
			}
		}
	}

	private void debugPrintActionList() {
		System.out.println("AZIONI DISPONIBILI");
		for (String string : actionList) {
			System.out.println(string);
		}

	}

	private void getAvailableActionsList() {
		actionList =networkHelper.getAvailableActionsList();
	}

	private void getAvailableCardList() {

		cardList =networkHelper.getAvailableCardsList();
		cardsSize = cardList.size();
	}


	private void debugPrintPlayerInfo(){
		System.out.println("player number: "+playerNumber+"\n"+
				"player type: "+playerType+"\n"+
				"num cards: "+cardNumber+"\n"+
				"current position: "+currentPosition+"\n");

	}

	public static void debugPrint(String s){
		System.out.println(s);
	}

	private void endTurn() {
		System.out.println("ENDTURN");
		Event result = networkHelper.endTurn();

		if(result.actionResult()){
			System.out.println("FINTE TURNO");
			hasMove = false;
			hasAttacked=false;
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
		this.playerNumber =Integer.parseInt( result.getRetValues().get("playernumber"));
		this.currentPosition = result.getRetValues().get("currentposition");
		this.cardNumber = Integer.parseInt( result.getRetValues().get("cardnumber"));
		this.playerType = result.getRetValues().get("playertype");


		/*
		 * numero 1
		 * tipo Alien/Human/Superalien
		 * currentposition A03
		 * numcard = 2
		 */

	}

	private void getMap() {

		String map = networkHelper.getMap();

	}

	private void actionMove() {
		if(!hasMove){
			System.out.println("CURRENT POSITION: "+currentPosition);
			System.out.println("DESTINATION:");
			String destination="";

			destination = scanner.nextLine();

			Event result = networkHelper.move(destination);

			if(result.actionResult()){
				currentPosition=result.getRetValues().get("destination");
				System.out.println("DEST: "+currentPosition);
				hasMove=true;

				if(result.getRetValues().containsKey("asksector")){
					askSector();
				}
				if(result.getRetValues().containsKey("item")){
					if(result.getRetValues().get("item").equals("true")){
						System.out.println("hai pescato la carta "+(result.getRetValues().get("card")));
					}
				}

			}else{
				System.out.println("ERRORE: "+result.getRetValues().get("error"));
				hasMove=false;
			}
		}
	}





	public static void setCurrentPlayer(int currentPlayer) {
		currentPlayerId = currentPlayer;
	}

	public void stampa(String msg){
		System.out.println("DIOCANEEE  "+msg);
	}

	public void log(Event e){
	
			if(  e.getRetValues().containsKey("move")){    
				String player = e.getRetValues().get("player");
				String sector = e.getRetValues().get("move");
				System.out.println("Giocatore "+player+" si è mosso in "+sector);
			}
			if(  e.getRetValues().containsKey("attack")){ 
				String playerNum = e.getRetValues().get("player");
				String position = e.getRetValues().get("attack");
				System.out.println("Giocatore "+playerNum+": attacca nel settore "+position);
				int count =0;
				for (Entry<String,String> ret : e.getRetValues().entrySet()) {
					if(ret.getValue().equals("killed")){
						System.out.println("Giocatore "+ret.getKey()+" ucciso dal giocatore "+ playerNum);
						count++;
					}
				}
				if(count==0){
					System.out.println("Nessuna Vittima");
				}

			}
			if(  e.getRetValues().containsKey("noise")){ 
				if(e.getRetValues().get("noise").equals("true")){
					String playerNum = e.getRetValues().get("player");
					String position = e.getRetValues().get("position");
					System.out.println("Giocatore "+playerNum+": rumore in settore "+position);
				}
			}
			if(  e.getRetValues().containsKey("hatch")){ 
				if(e.getRetValues().get("hatch").equals("false")){
					System.out.println(e.getRetValues().get("message"));
				}
				else{
					String player = e.getRetValues().get("player");

					System.out.println("il giocatore" + player+" ha pescato una hatch card "+e.getRetValues().get("hatchcard"));
				}
			}
		}
	public void chat(Event e){
		System.out.println("Chat :"+"[Giocatore "+e.getRetValues().get("player")+"]"+" "+e.getRetValues().get("message"));
	}

}
