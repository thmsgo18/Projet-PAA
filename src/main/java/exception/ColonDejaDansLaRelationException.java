package exception;
/**
 * Exception dans le cas un Colon est déjà dans la liste de relation 'pas amis' du Colon.
 */
public class ColonDejaDansLaRelationException extends ColonException{
    /**
     * Constructeur de l'expection avec un message.
     * @param message de type String indiquant le messag à rajouter à l'exception.
     */
    public ColonDejaDansLaRelationException(String message) {
        super(message);
    }
}
