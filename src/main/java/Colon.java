import java.util.List;
import java.util.ArrayList;

public class Colon {
    private char nom;
    private String id;
    private List<Ressource> preferencesRessource;
    private Ressource ressource;
    private List<Colon> pasAmis;

    public Colon(char nom,String id, List<Ressource> preferencesRessource, List<Colon> pasAmis) {
        this.nom = nom;
        this.id = id;
        this.preferencesRessource = preferencesRessource;
        this.pasAmis = pasAmis;
        this.ressource = null;
    }
    public Colon(char nom, String prenom, String id) {
        this.nom = nom;
        this.id = id;
        this.preferencesRessource = new ArrayList<>();
        this.pasAmis = new ArrayList<>();
        this.ressource = null;
    }



    public void setPasAmis(List<Colon> pasAmis) {

        this.pasAmis = pasAmis;
    }
    public void addPasAmis(Colon colon) {

        this.pasAmis.add(colon);
    }
    public void removePasAmis(Colon colon) {

        this.pasAmis.remove(colon);
    }

    public void setPreferencesRessource(List<Ressource> preferencesRessource) {
        this.preferencesRessource = preferencesRessource;
    }

    public void addPreference(Ressource preference) {

        this.preferencesRessource.add(preference);
    }
    public void removePreference(Ressource preference) {

        this.preferencesRessource.remove(preference);
    }

    public List<Colon> getPasAmis() {

        return pasAmis;
    }
    public char getNom() {

        return nom;
    }
    public String getId() {

        return id;
    }
    public List<Ressource> getPreferencesRessource() {

        return preferencesRessource;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNom(char nom) {
        this.nom = nom;
    }

}
