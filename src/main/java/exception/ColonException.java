package exception;
/**
 * Exception, parente des exceptions, portant sur les Colons.
 */
public class ColonException extends Exception {
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public ColonException(String message) {
        super(message);
    }
}
