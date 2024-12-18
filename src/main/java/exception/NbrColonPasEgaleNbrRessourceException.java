package exception;
/**
 * Exception dans le cas où il n'y a pas le même nombre de Colons que de Ressources.
 */
public class NbrColonPasEgaleNbrRessourceException extends ColonieException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public NbrColonPasEgaleNbrRessourceException(String message) {
        super(message);
    }
}
