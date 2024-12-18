package exception;
/**
 * Exception dans le cas où le Colon est déjà dans la Colonie.
 */
public class RessourceDejaDansColonieException extends RessourceException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public RessourceDejaDansColonieException(String message) {
        super(message);
    }
}
