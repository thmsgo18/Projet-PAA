package code;

public class Ressource{
    private final String nomRessource;
    private boolean disponible;

    public Ressource(String nomRessource){
        this.nomRessource = nomRessource;
        this.disponible = true;
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
}
