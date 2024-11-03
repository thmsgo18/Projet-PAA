public class Ressource{
    private int nomRessource;
    private boolean disponible;
    private Colon prorio;

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

    public Colon getProrio(){
        return prorio;
    }
    public void setProrio(Colon prorio){
        this.prorio = prorio;
    }

    public boolean equals(Ressource ressource){
        return this.nomRessource == ressource.getNomRessource();
    }
}
