package exception;
/**
 * Exception, parente des exceptions, portant sur les Ressources.
 */
public class RessourceException extends Exception{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public RessourceException(String message) {
        super(message);
    }
}
