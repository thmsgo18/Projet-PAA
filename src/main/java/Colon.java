import java.util.List;
import java.util.ArrayList;

public class Colon {
    private String nom;
    private String prenom;
    private String id;
    private List<String> preferencesRessource;
    private List<Colon> pasAmis;

    public Colon(String nom, String prenom, String id, List<String> preferencesRessource, List<Colon> pasAmis) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
        this.preferencesRessource = preferencesRessource;
        this.pasAmis = pasAmis;
    }
    public Colon(String nom, String prenom, String id) {
        this.nom = nom;
        this.prenom = prenom;
        this.id = id;
        this.preferencesRessource = new ArrayList<>();
        this.pasAmis = new ArrayList<>();
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

    public void setPreferencesRessource(List<String> preferencesRessource) {
        this.preferencesRessource = preferencesRessource;
    }
    public void addPreference(String preference) {
        this.preferencesRessource.add(preference);
    }
    public void removePreference(String preference) {
        this.preferencesRessource.remove(preference);
    }

    public List<Colon> getPasAmis() {
        return pasAmis;
    }
    public String getNom() {
        return nom;
    }
    public String getPrenom() {
        return prenom;
    }
    public String getId() {
        return id;
    }
    public List<String> getPreferencesRessource() {
        return preferencesRessource;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
