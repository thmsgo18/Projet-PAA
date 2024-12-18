package exception;
/**
 * Exception dans le cas où un Colon n'est pas présent dans la Colonie.
 */
public class ColonNonPresentDansColonieException extends ColonException {
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public ColonNonPresentDansColonieException(String message) {
        super(message);
    }

}
