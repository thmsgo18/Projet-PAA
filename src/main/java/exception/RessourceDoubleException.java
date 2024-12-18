package exception;
/**
 * La Ressource est en double dans la liste de préférence.
 */
public class RessourceDoubleException extends RessourceException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public RessourceDoubleException(String message) {
        super(message);
    }
}
