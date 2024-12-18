package exception;
/**
 * Exception dans le cas où le même Colon est entré deux fois en paramètre.
 */
public class MemeColonException extends ColonException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public MemeColonException(String message) {
        super(message);
    }
}
