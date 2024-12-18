package exception;
/**
 * Exception dans ce cas où un ou plusieurs Colons n'ont pas leur liste de préférences renseignée.
 */
public class EnsembleListPreferenceColonieIncompleteException extends ColonieException {
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public EnsembleListPreferenceColonieIncompleteException(String message) {
        super(message);
    }
}
