package exception;
/**
 * Exception dans le cas où une Ressource n'est pas présente dans la Colonie.
 */
public class RessourcePasDansColonieException extends RessourceException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public RessourcePasDansColonieException(String message) {
        super(message);
    }
}
