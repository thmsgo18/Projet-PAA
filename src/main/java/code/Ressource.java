package code;

/**
 * Cette classe permet de représenter un object ressource.
 */
public class Ressource{
    private final String nomRessource;
    private boolean disponible;
    private int point;

    /**
     *
     * @param nomRessource de type String
     */
    public Ressource(String nomRessource){
        this.nomRessource = nomRessource;
        this.disponible = true;
        this.point = 0;
    }

    public String getNomRessource(){
        return nomRessource;
    }


    public boolean getDisponibilite(){
        return disponible;
    }
    public void setDisponibilite(boolean disponible){
        this.disponible = disponible;
    }

    public boolean equals(Ressource ressource){
        return this.nomRessource.equals(ressource.getNomRessource());
    }

    public int getPoint(){
        return point;
    }
    public void setPoint(int point){
        this.point = point;
    }
    public boolean superieur(Ressource ressource){
        return this.point>=ressource.getPoint();
    }
}
