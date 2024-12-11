package code;

/**
 * Cette classe permet de représenter un object ressource de la colonie.
 */
public class Ressource{
    private final String nomRessource;
    private boolean disponible;
    private int popularite;

    /**
     * Constructeur de l'object Ressource.
     *
     * @param nomRessource de type String.
     */
    public Ressource(String nomRessource){
        this.nomRessource = nomRessource;
        this.disponible = true;
        this.popularite = 0;
    }

    /**
     * Getter du nom de la Ressource.
     *
     * @return le nom de la Ressource de type String.
     */
    public String getNomRessource(){
        return nomRessource;
    }

    /**
     * Getter de la disponibilité de la Ressource
     *
     * @return de type booléen.
     */
    public boolean getDisponibilite(){
        return disponible;
    }

    /**
     * Setter de la disponibilité de la Ressource.
     *
     * @param disponible de type booléen.
     */
    public void setDisponibilite(boolean disponible){
        this.disponible = disponible;
    }

    /**
     * Cette méthode permet de tester l'égalité entre deux Ressources.
     * Le teste d'égalité entre deux Ressources se fait sur le nom des Ressources.
     *
     * @param ressource de type Ressource.
     * @return de type booléen.
     */
    public boolean equals(Ressource ressource){
        return this.nomRessource.equals(ressource.getNomRessource());
    }

    /**
     * Getter de la popularité de la Ressource.
     * La popularité de la Ressource permet de savoir la préférence global des Ressources de la Colonie.
     * Cela permet d'avoir par exemple la Ressource la plus convoité de la Colonie.
     *
     * @return de type int.
     */
    public int getPopularite(){
        return popularite;
    }

    /**
     * Setter de la popularité de la Ressource.
     *
     * @param popularite de type int.
     */
    public void setPopularite(int popularite){
        this.popularite = popularite;
    }

    /**
     * Cette méthode permet de tester si une Ressource est préférée à une autre en fonction de sa popularité.
     *
     * @param ressource de type Ressource.
     * @return de type booléen.
     */
    public boolean superieur(Ressource ressource){
        return this.popularite >=ressource.getPopularite();
    }
}
