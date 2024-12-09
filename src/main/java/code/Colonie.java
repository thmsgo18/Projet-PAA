package code;

import code.exception.*;

import java.util.*;


public class Colonie {
    private String nom;
    private List<Ressource> ressourcesColonie;
    private List<Colon> colons;
    private int affectation;
    private final Scanner sc;
    private String cheminFichierConf;

    public Colonie(String nom) {
        this.nom = nom;
        this.ressourcesColonie = new ArrayList<Ressource>(26);
        this.colons = new ArrayList<Colon>(26);
        this.sc = new Scanner(System.in);
        this.affectation = 0;
        this.cheminFichierConf="";
    }

    public void init() {
        // Initialisation de la colonie
        Integer n = null;
        while (n == null) {
            System.out.println("Entrez le nombre de colons dans la colonie " + this.nom);
            if (sc.hasNextInt()) {
                n = sc.nextInt();
                if (n <= 0) {
                    System.out.println("Le nombre doit être supérieur à zéro. Réessayez.");
                    n = null;
                }
            } else {
                System.out.println("Erreur : Veuillez entrer un nombre entier valide.");
                sc.next();
            }
        }
        sc.nextLine();

        // Création des colons et des ressources
        for (int i = 0; i < n; i++) {
            System.out.println("Entrez le nom du colon" + (i + 1) + " :");
            String nomColon = sc.nextLine();
            Colon colon = new Colon(nomColon);
            try {
                this.addColon(colon);
            } catch (ColonDejaDansColonieException e) {
                System.out.println(e.getClass().getName() + "\n" + e.getMessage());
                i--;
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println("Entrez le nom de la ressource" + (i + 1) + " :");
            String nomRessource = sc.nextLine();
            Ressource ressource = new Ressource(nomRessource);
            try {
                this.addRessource(ressource);
            } catch (RessourceDejaDansColonieException e) {
                System.out.println(e.getClass().getName() + "\n" + e.getMessage());
                i--;
            }
        }


        System.out.println("Liste des colons de la colonie : ");
        for(Colon colon : colons) {
            System.out.print(colon.getNom() + " ");
        }
        System.out.println("\nListe des ressources de la colonie : ");
        for(Ressource ressource : ressourcesColonie) {
            System.out.print(ressource.getNomRessource() + " ");
        }
        System.out.println();
    }


    public void ajoutRelation(String nomColon1, String nomColon2) throws MemeColonException,
            ColonNonPresentDansColonieException,
            ColonDejaDansLaRelationException {
        if(nomColon1.equals(nomColon2)){
            throw new MemeColonException("ERREUR : Vous ne pouvez pas lier le même colon.");
        }
        else{
            Colon colon1 = null;
            Colon colon2 = null;
            for(Colon colon : colons) {
                // Recherche dans la colonie de la présence de colon1 & colon2
                if(colon.getNom().equals(nomColon1)){
                    colon1 = colon;
                }
                if(colon.getNom().equals(nomColon2)){
                    colon2 = colon;
                }
            }
            if (colon1==null || colon2==null){
                throw new ColonNonPresentDansColonieException("ERREUR : Un ou deux colons sélectionné ne sont pas présent dans la liste des colons.");
            }else {
                if (colon1.recherchePasAmis(colon2) || colon2.recherchePasAmis(colon1)) {
                    throw new ColonDejaDansLaRelationException("ERREUR : Le colon "+ colon2.getNom() +" est déjà dans la liste des 'pas amis' du colon "+ colon1.getNom() +" et inversement.");
                } else {
                    // Ajout dans colon1 & dans colon2 d'une relation 'pas amie'
                    colon1.addPasAmis(colon2);
                    colon2.addPasAmis(colon1);
                }
            }
        }
    }

    private boolean ressourceInList(List<Ressource> l, String nomRessource) {
        for(Ressource ressource : l){
            // Parcours de la liste de ressources de la colonie
            if(ressource.getNomRessource().equals(nomRessource)){
                // Présence de la ressource dans la liste de ressources de la colonie
                return true;
            }
        }
        return false;
    }

    public void ajoutListePref(String nom, String ressources) throws ColonNonPresentDansColonieException,
            RessourceManquanteException,
            RessourceDoubleException,
            RessourcePasDansColonieException {
        boolean contient = false;
        for(Colon colon : colons) {
            if(colon.getNom().equals(nom)){
                // Le colon est présent dans la colonie.
                contient = true;
                break;
            }
        }
        if(!contient) {
            throw new ColonNonPresentDansColonieException("ERREUR : Le colon n'existe pas.");
        }else{
            String[] res = ressources.split(" ");

            // Vérification de s'il y a toute la liste de préférence des ressources
            if(res.length != ressourcesColonie.size() ){
                throw new RessourceManquanteException("ERREUR : Le nombre de resource tapé n'est pas respecté. Vous avez rentré "+res.length+" ressources. Nous avons besoin de "+ressourcesColonie.size()+" ressources.");
            }else{
                StringTokenizer tok = new StringTokenizer(ressources," ");
                List<Ressource> listePref = new ArrayList<Ressource>(ressourcesColonie.size());

                while(tok.hasMoreTokens()) {
                    // Ajout des ressources à la liste des préférences ressources
                    String chaine = tok.nextToken();
                    if(this.ressourceInList(this.ressourcesColonie, chaine)){
                        Ressource r = new Ressource(chaine);
                        if(this.ressourceInList(listePref, chaine)){
                            throw new RessourceDoubleException("ERREUR : La ressource est en double dans la liste de préférence.");
                        }else{
                            listePref.add(r);
                        }
                    }
                    else{
                        throw new RessourcePasDansColonieException("ERREUR : Une des ressources n'existe pas.");
                    }
                }
                this.getColons(nom).setPreferencesRessource(listePref);
            }
        }
    }

    public boolean verificationListePref() {
        List<Colon> colonsIncomplets = new ArrayList<Colon>();

        //Vérification que la liste de préférences de chaque colon est complète
        for(Colon colon : colons) {
            //Sinon, on ajoute le colon à la liste des colons incomplets
            if(colon.getPreferencesRessource().size() != colons.size()) {
                colonsIncomplets.add(colon);
            }
        }

        // Affichage de la liste des colons incomplets
        if(!colonsIncomplets.isEmpty()) {
            System.out.println("Liste des colons avec des listes de préférences vides ou incomplètes : ");
            for(Colon colon : colonsIncomplets) {
                System.out.println("    " + colon.getNom());
            }
            return false;
        } else {
            return true;
        }
    }

    public void trieListRessource(){
        boolean echange;
        this.calculePointRessource();
        do {
            echange = false;
            for (int i = 0; i < this.ressourcesColonie.size() - 1; i++) {
                Ressource r1 = this.ressourcesColonie.get(i);
                Ressource r2 = this.ressourcesColonie.get(i + 1);

                // Comparaison des priorités
                if (r2.getPopularite() > r1.getPopularite()) {
                    // Echange des ressources
                    this.ressourcesColonie.set(i, r2);
                    this.ressourcesColonie.set(i + 1, r1);
                    echange = true;
                }
            }
            // Tant qu'un échange a eu lieu, on continue
        } while (echange);
    }

    public void trieListColon(){
        boolean echange;
        do {
            echange = false;
            for (int i = 0; i < this.colons.size() - 1; i++) {
                Colon c1 = this.colons.get(i);
                Colon c2 = this.colons.get(i + 1);

                // Comparaison des priorités
                if (c2.getPasAmis().size() > c1.getPasAmis().size()) {
                    // Echange des colons
                    this.colons.set(i, c2);
                    this.colons.set(i + 1, c1);
                    echange = true;
                }
            }
            // Tant qu'un échange a eu lieu, on continue
        } while (echange);

    }

    private void calculePointRessource(){
        for (Colon colon : this.colons) {
            List<Ressource> listeRes = colon.getPreferencesRessource();
            for(int i = 0; i < listeRes.size(); i++){
                for(Ressource r : this.ressourcesColonie){
                    if(listeRes.get(i).equals(r)){
                        r.setPopularite(r.getPopularite()+(listeRes.size()-i));
                    }
                }
            }
        }
    }


    public void echangeRessource(String nomColon1, String nomColon2) throws MemeColonException, ColonNonPresentDansColonieException {
        if(nomColon2.equals(nomColon1)) {
            throw new MemeColonException("ERREUR : On peut pas échanger les ressources d'un même colon.");
        }
        else{
            Colon colon1 = null;
            Colon colon2 = null;

            // Vérification présence des colons dans la colonie
            for(Colon c : colons) {
                if(c.getNom().equals(nomColon1)){
                    colon1 = c;
                }
                if(c.getNom().equals(nomColon2)){
                    colon2 = c;
                }
            }

            if(colon1 == null || colon2 == null) {
                throw new ColonNonPresentDansColonieException("ERREUR : Un ou deux colons sélectionnés ne sont pas présent dans la liste des colons.");
            }
            else {
                // Échange des ressources entre les deux colons
                Ressource aux = colon1.getRessource();
                colon1.setRessource(colon2.getRessource());
                colon2.setRessource(aux);
                colon1.setPosRessource(colon1.getPosRessource());
                colon2.setPosRessource(colon2.getPosRessource());
            }

        }

    }

    public void afficherJaloux(){
        System.out.println("Liste des préférences de chaque colon : ");
        for(Colon c : colons){
            System.out.print(c.getNom()+" : ");
            for(Ressource r : c.getPreferencesRessource()){
                System.out.print(r.getNomRessource()+" ");
            }
            System.out.print("\n");
        }
        Algo.calculAffectation(this);
        System.out.println("Le nombre de colons jaloux dans la colonie est de " + affectation);
        System.out.println("Récapitulatif des Colons et de leur code.Ressource: ");
        afficherObjets();
    }

    public void afficherColonsPasAmis(){
        System.out.println("Récapitulatif de chaque colon pas amis : ");
        for(Colon c : colons){
            System.out.print("  "+c.getNom()+" : ");
            for(Colon cp : c.getPasAmis()){
                System.out.print(cp.getNom()+" ");
            }
            System.out.println();
        }
    }

    public void afficherListePrefColons(){
        System.out.println("Récapitulatif de préférence de chaque code.Colon : ");
        for(Colon c : colons){
            System.out.print("  "+c.getNom()+" : ");
            for(Ressource r : c.getPreferencesRessource()){
                System.out.print(r.getNomRessource()+" ");
            }
            System.out.print("\n");
        }
    }

    public void afficherObjets() {
        for(Colon c : colons) {
            System.out.println("    "+c.getNom() + ":" + c.getRessource().getNomRessource());
        }
    }


    public int getPositionRessource(Ressource ressource){
        for(int i=0; i<ressourcesColonie.size(); i++){
            if(ressourcesColonie.get(i).equals(ressource)){
                return i;
            }
        }
        return -1;
    }
    public void addRessource(Ressource ressource)throws RessourceDejaDansColonieException{
        for(Ressource r1 : this.ressourcesColonie){
            if(r1.getNomRessource().equals(ressource.getNomRessource())){
                throw new RessourceDejaDansColonieException("ERREUR : La ressource est déjà dans la colonie.");
            }
        }
        this.ressourcesColonie.add(ressource);
    }
    public List <Ressource> getRessources(){
        return this.ressourcesColonie;
    }

    public Colon getColons(String nom){
        for(Colon c : this.colons){
            if(c.getNom().equals(nom)){
                return c;
            }
        }
        return null;
    }
    public List<Colon> getColons() {
        return colons;
    }

    public void addColon(Colon c) throws ColonDejaDansColonieException {
        for(Colon c1 : this.colons){
            if(c1.equals(c)){
                throw new ColonDejaDansColonieException("ERREUR : Le colon est déjà dans la colonie.");
            }
        }
        this.colons.add(c);
    }

    public Scanner getSc(){
        return sc;
    }

    public String getCheminFichierConf() {
        return this.cheminFichierConf;
    }
    public void setCheminFichierConf(String cheminFichier) {
        this.cheminFichierConf = cheminFichier;
    }

    public void setAffectation(int affectation) {
        this.affectation = affectation;
    }
    public int getAffectation() {
        return affectation;
    }

}
