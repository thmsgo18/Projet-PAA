package exception;
/**
 * Exception dans le cas où une ressource est manquante.
 */
public class RessourceManquanteException extends RessourceException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public RessourceManquanteException(String message) {
        super(message);
    }
}
