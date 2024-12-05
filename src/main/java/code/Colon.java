package code;

import java.util.List;
import java.util.ArrayList;

public class Colon {
    private final String nom;
    private List<Ressource> preferencesRessource;
    private List<Colon> pasAmis;
    private Ressource ressource;
    private int posRessource;
    private boolean attribue;

    public Colon(String nom) {
        this.nom = nom;
        this.preferencesRessource = new ArrayList<>();
        this.pasAmis = new ArrayList<>();
        this.ressource = null;
        this.posRessource = -1;
        this.attribue = false;
    }

    public String getNom() {
        return nom;
    }

    public List<Ressource> getPreferencesRessource() {
        return preferencesRessource;
    }
    public void setPreferencesRessource(List<Ressource> preferencesRessource) {
        this.preferencesRessource = preferencesRessource;
    }

    public Ressource getUnePreferenceRessource(int i) {
        return preferencesRessource.get(i);
    }

    public List<Colon> getPasAmis() {
        return pasAmis;
    }
    public void addPasAmis(Colon colon) {
        this.pasAmis.add(colon);
    }

    public boolean recherchePasAmis(Colon c){
        for(Colon i : this.pasAmis){
            if(i.equals(c)){
                // Présence du colon c dans la liste des 'pas amis' du colon
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
        return this.nom.equals(colon.getNom());
    }
}
