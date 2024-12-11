package code;

import java.util.List;
import java.util.ArrayList;

/**
 * Cette classe permet de représenter un colon de la colonie.
 *
 */
public class Colon {
    private String nom;
    private List<Ressource> preferencesRessources;
    private List<Colon> pasAmis;
    private Ressource ressource;
    private int posRessource;
    private boolean attribue;


    /**
     * Le constructeur de l'objet Colon.
     *
     * @param nom de type String du Colon.
     */
    public Colon(String nom) {
        this.nom = nom;
        this.preferencesRessources = new ArrayList<>();
        this.pasAmis = new ArrayList<>();
        this.ressource = null;
        this.posRessource = -1;
        this.attribue = false;
    }

    /**
     * Getter du nom du Colon.
     *
     * @return de type String.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de la liste de préférence des Ressources du Colon.
     *
     * @return de type List de Ressource.
     */
    public List<Ressource> getPreferencesRessources() {
        return preferencesRessources;
    }

    /**
     * Setter de la liste de préférence des Ressources du Colon.
     *
     * @param preferencesRessources de type List de Ressource.
     */
    public void setPreferencesRessources(List<Ressource> preferencesRessources) {
        this.preferencesRessources = preferencesRessources;
    }

//    /**
//     * Getter de la Ressource présente à une position donnée dans la liste de préférences du Colon.
//     *
//     * @param i de type int indiquant la position dans la liste de préférences du Colon.
//     * @return de type Ressource.
//     */
//    public Ressource getUnePreferenceRessource(int i) {
//        return preferencesRessources.get(i);
//    }

    /**
     * Getter de la liste de 'pas amis' du Colon.
     *
     * @return de type List de Colon.
     */
    public List<Colon> getPasAmis() {
        return pasAmis;
    }

    /**
     * Cette méthode permet d'ajouter un Colon à la liste de 'pas amis' du Colon.
     *
     * @param colon de type Colon.
     */
    public void addPasAmis(Colon colon) {
        this.pasAmis.add(colon);
    }

    /**
     * Cette méthode permet de vérifier si un Colon est dans la liste de 'pas amis' du Colon.
     *
     * @param c de type Colon.
     * @return de type booléen.
     */
    public boolean recherchePasAmis(Colon c){
        for(Colon i : this.pasAmis){
            if(i.equals(c)){
                // Présence du colon c dans la liste des 'pas amis' du colon
                return true;
            }
        }
        return false;
    }

    /**
     * Getter de la Ressource attribuée au Colon.
     *
     * @return de type Ressource.
     */
    public Ressource getRessource() {
        return this.ressource;
    }

    /**
     * Setter de la Ressource au Colon.
     *
     * @param ressource de type Ressource.
     */
    public void setRessource(Ressource ressource) {
        this.ressource = ressource;
    }

    /**
     * Getter de la position de la Ressource, attribuée au Colon, dans la liste de préférences du Colon.
     *
     * @return de type int.
     */
    public int getPosRessource() {
        for(int i=0; i<preferencesRessources.size(); i++){
            if(preferencesRessources.get(i).equals(this.ressource)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Setter de la position de la Ressource, attribuée au Colon, dans la liste de préférence du Colon.
     *
     * @param posRessource de type int.
     */
    public void setPosRessource(int posRessource) {
        this.posRessource = posRessource;
    }

    /**
     * Getter de l'état d'attribution de la Ressource au Colon.
     *
     * @return de type booléen.
     */
    public boolean isAttribue() {
        return attribue;
    }

    /**
     * Setter de l'état d'attribution de la Ressource au Colon.
     *
     * @param attribue de type booléen.
     */
    public void setAttribue(boolean attribue) {
        this.attribue = attribue;
    }

    /**
     * Cette méthode permet de vérifier si deux Colons sont égaux.
     *
     * @param colon de type Colon.
     * @return de type booléen.
     */
    public boolean equals(Colon colon) {
        return this.nom.equals(colon.getNom());
    }
}
