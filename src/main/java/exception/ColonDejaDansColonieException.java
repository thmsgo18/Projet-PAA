package exception;
/**
 * Exception dans le cas où un Colon est déjà dans la Colonie.
 */
public class ColonDejaDansColonieException extends ColonException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public ColonDejaDansColonieException(String message) {
        super(message);
    }
}
