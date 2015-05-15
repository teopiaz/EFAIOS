package it.polimi.ingsw.cg15.model.player;

import it.polimi.ingsw.cg15.model.cards.ItemCard;
import it.polimi.ingsw.cg15.model.field.Cell;

import java.util.ArrayList;
import java.util.List;


/*
 * Questa è l'istanza del model "Player", contiene le informazioni sul giocatore 
 * come: posizione, la lista delle carte oggetto che possiede e il tipo del giocatore 
 * (umano, alieno). Inoltre è presente status che indica se il giocatore è ancora vivo 
 * oppure è stato eliminato da un alieno, in quel caso la variabile status è false.
 */

public class Player {

   
	protected Cell position;
	private List<ItemCard> cards;
	private PlayerType type;
	private boolean status = true;

/*
 * Il costruttore di questa classe prende in oggetto la cella di origine da dove 
 * partono i due tipi di giocatore e il tipo di giocatore che è stato creato. Inoltre
 * assegna la possibilità di assegnargli alcune carte di tipo oggetto, massimo 3.
 */
	public Player(Cell origin,PlayerType type) {
		this.position = origin;
		this.type = type;
		cards = new ArrayList<ItemCard>(3);
	}

/*
 * Questo metodo ritorna la posizione corrente del giocatore.
 */
	public Cell getPosition(){
		return this.position;
	}

/*
 * Questo metodo ritorna il tipo del giocatore in oggetto.
 */
	public PlayerType getPlayerType(){
		return this.type;
	}

/*
 * Questo metodo assegna al giocatore una nuova posizione.
 */
	public void setPosition(Cell dest){
		this.position = dest;
	}


	/*
	 * Questo metodo ritorna lo stato del giocatore ossia se è ancora vivo oppure no.
	 */
	public boolean isAlive(){
		return status;
	}

/*
 * Questo metodo uccide il giocatore su cui viene chiamato. Serve quando un giocatore viene eliminato.
 */
	public void killPlayer(){
	    this.status=false;}
	}