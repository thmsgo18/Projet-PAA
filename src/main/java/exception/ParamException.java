package exception;
/**
 * Exception dans le cas où il y'a un problème dans les paramètres renseignés dans le fichier de configuration.
 */
public class ParamException extends FichierException {
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public ParamException(String message) {
        super(message);
    }
}
