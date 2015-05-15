package it.polimi.ingsw.cg15.exception;

public class InvalidAction extends RuntimeException {

    public InvalidAction() {
        super();
    }

    public InvalidAction(String message) {
        super(message);
    }
}
