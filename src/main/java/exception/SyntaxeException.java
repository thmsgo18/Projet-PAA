package exception;
/**
 * Exception dans le cas où la syntaxe du fichier de configuration n'est pas respectée.
 */
public class SyntaxeException extends FichierException {
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public SyntaxeException(String message) {
        super(message);
    }
}
