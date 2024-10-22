import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Colonie {
    private String nom;
    private List<Ressource> ressourceDeLaColonie;
    private List<Colon> Lescolons;
    public Colonie(String nom, List<Ressource> ressourceDeLaColonie) {
        this.nom = nom;
        this.ressourceDeLaColonie = ressourceDeLaColonie;
        this.Lescolons = new ArrayList<Colon>(26);
    }
    public String getNom() {
        return nom;
    }

    public List<Ressource> getRessourceDeLaColonie() {
        return ressourceDeLaColonie;
    }

    public List<Colon> getLescolons() {
        return Lescolons;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setRessourceDeLaColonie(List<Ressource> ressourceDeLaColonie) {
        this.ressourceDeLaColonie = ressourceDeLaColonie;
    }

    public void setLescolons(List<Colon> Lescolons) {
        this.Lescolons = Lescolons;
    }

}
