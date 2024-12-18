package exception;
/**
 * Exception, parente des exceptions, portant sur les fichiers.
 */
public class FichierException extends RuntimeException {
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public FichierException(String message) {
        super(message);
    }
}
