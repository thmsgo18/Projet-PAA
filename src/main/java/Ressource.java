public class Ressource{
    private int nomRessource;
    private boolean disponible;

    public Ressource(int nomRessource){
        this.nomRessource = nomRessource;
        this.disponible = true;
    }

    public int getNomRessource(){
        return nomRessource;
    }
    public void setNomRessource(int nomRessource){
        this.nomRessource = nomRessource;
    }

    public boolean getDisponibilite(){
        return disponible;
    }
    public void setDisponibilite(boolean disponible){
        this.disponible = disponible;
    }

    public boolean equals(Ressource ressource){
        return this.nomRessource == ressource.getNomRessource();
    }
}
