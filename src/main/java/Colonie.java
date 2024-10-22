import java.util.ArrayList;
import java.util.List;

public class Colonie {
    private String nom;
    private List<Ressource> ressourcesColonie;
    private List<Colon> colons;
    private int affectation;

    public Colonie(String nom) {
        this.nom = nom;
        this.ressourcesColonie = new ArrayList<Ressource>(26);
        this.colons = new ArrayList<Colon>(26);
    }
    public String getNom() {
        return nom;
    }

    public List<Ressource> getRessourcesColonie() {
        return ressourcesColonie;
    }

    public List<Colon> getColons() {
        return colons;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRessourcesColonie(List<Ressource> ressourceDeLaColonie) {
        this.ressourcesColonie = ressourceDeLaColonie;
    }

    public void setColons(List<Colon> colons) {
        this.colons = colons;
    }

    public int getAffectation() {
        return affectation;
    }
    public void setAffectation(int affectation) {
        this.affectation = affectation;
    }

}
