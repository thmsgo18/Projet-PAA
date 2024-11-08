import java.util.List;
import java.util.ArrayList;

public class Colon {
    private char nom;
    private List<Ressource> preferencesRessource;
    private List<Colon> pasAmis;
    private Ressource ressource;
    private int posRessource;
    private boolean attribue;

    public Colon(char nom) {
        this.nom = nom;
        this.preferencesRessource = new ArrayList<>();
        this.pasAmis = new ArrayList<>();
        this.ressource = null;
        this.posRessource = -1;
        this.attribue = false;
    }
    public Colon(char nom, List<Colon> pasAmis) {
        this.nom = nom;
        this.preferencesRessource = new ArrayList<>();
        this.pasAmis = new ArrayList<>();
        this.ressource = null;
        this.posRessource = -1;
        this.attribue = false;
    }
    public Colon(char nom, List<Ressource> preferencesRessource, List<Colon> pasAmis) {
        this.nom = nom;
        this.preferencesRessource = preferencesRessource;
        this.pasAmis = pasAmis;
        this.ressource = null;
        this.posRessource = -1;
        this.attribue = false;
    }

    public boolean ressourceDejaUtilise(List<Colon> colons){
        for(int i=0;i<colons.size();i++){
            if(!(colons.get(i).equals(this))){
                if(colons.get(i).getRessource().equals(this.ressource)){
                    return true;
                }
            }
        }
        return false;
    }


    public char getNom() {
        return nom;
    }
    public void setNom(char nom) {
        this.nom = nom;
    }

    public List<Ressource> getPreferencesRessource() {
        return preferencesRessource;
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
    public Ressource getUnePreferenceRessource(int i) {
        return preferencesRessource.get(i);
    }

    public List<Colon> getPasAmis() {
        return pasAmis;
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

    public boolean recherchePasAmis(Colon c){
        for(Colon i : this.pasAmis){
            if(i.equals(c)){
                return true;
            }
        }
        return false;
    }

    public Ressource getRessource() {
        return this.ressource;
    }
    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    public int getPosRessource() {
        for(int i =0;i<preferencesRessource.size();i++){
            if(preferencesRessource.get(i).equals(this.ressource)) {
                return i;
            }
        }
        return 0;
    }
    public void setPosRessource(int posRessource) {
        this.posRessource = posRessource;
    }

    public boolean isAttribue() {
        return attribue;
    }
    public void setAttribue(boolean attribue) {
        this.attribue = attribue;
    }

    public boolean equals(Colon colon) {
        return this.nom == colon.getNom();
    }
}
