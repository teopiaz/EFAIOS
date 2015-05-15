package it.polimi.ingsw.cg15.model.player;


/*
 * Questa classe del model contiene le informazioni sul suo tipo, esso può essere di 3 tipi.
 */


public enum PlayerType {
HUMAN,
ALIEN,
SUPERALIEN;

/*
 * Questo metodo ritorna il tipo di giocatore sotto forma di stringa dove la prima lettera è maiuscola e le altre no.
 */
public String toClassName() {
    return name().charAt(0) + name().substring(1).toLowerCase();
}

}
