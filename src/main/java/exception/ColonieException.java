package exception;
/**
 * Exception, parente des exceptions, portant sur la Colonie.
 */
public class ColonieException extends Exception{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public ColonieException(String message) {
        super(message);
    }
}
