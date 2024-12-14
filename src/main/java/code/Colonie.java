package code;

import code.exception.*;

import java.util.*;

/**
 * Cette classe permet de représenter une Colonie
 */
public class Colonie {
    private String nom;
    private List<Ressource> ressourcesColonie;
    private List<Colon> colons;
    private int affectation;
    private final Scanner sc;
    private String cheminFichierConf;

    /**
     * Constructeur de l'object Colonie.
     *
     * @param nom de type String de la Colonie.
     */
    public Colonie(String nom) {
        this.nom = nom;
        this.ressourcesColonie = new ArrayList<Ressource>();
        this.colons = new ArrayList<Colon>();
        this.sc = new Scanner(System.in);
        this.affectation = 0;
        this.cheminFichierConf = "";
    }

    /**
     * Cette méthode permet d'initialiser la Colonie.
     * Création de n Colons et de n Ressources.
     */
    public void init() {
        System.out.println("\n********************  CREATION MANUELLE DE LA COLONIE " + nom + "  ********************");
        Integer n = null;
        while (n == null) {
            System.out.println("\nEntrez le nombre de colons dans la colonie " + this.nom + " : ");
            if (sc.hasNextInt()) {
                n = sc.nextInt();

                if (n <= 0) {
                    System.err.println("ERREUR : Le nombre doit être supérieur à zéro. Réessayez.");
                    n = null;
                }
            } else {
                System.err.println("ERREUR : Veuillez entrer un nombre entier valide.");
                sc.next();
            }
        }
        sc.nextLine();

        // Création des colons et des ressources
        for (int i = 0; i < n; i++) {
            System.out.println("Entrez le nom du colon " + (i + 1) + " :");
            String nomColon = sc.nextLine();
            Colon colon = new Colon(nomColon);
            try {
                this.addColon(colon);
            } catch (ColonDejaDansColonieException e) {
                System.err.println("ERREUR " + e.getClass().getName() + " : " + e.getMessage());
                i--;
            }
        }
        for (int i = 0; i < n; i++) {
            System.out.println("Entrez le nom de la ressource " + (i + 1) + " :");
            String nomRessource = sc.nextLine();
            Ressource ressource = new Ressource(nomRessource);
            try {
                this.addRessource(ressource);
            } catch (RessourceDejaDansColonieException e) {
                System.err.println("ERREUR " + e.getClass().getName() + " : " + e.getMessage());
                i--;
            }
        }


        System.out.println("\n**********  Liste des colons de la colonie  **********");
        for(Colon colon : colons) {
            System.out.print(colon.getNom() + " ");
        }
        System.out.println("\n\n**********  Liste des ressources de la colonie  **********");
        for(Ressource ressource : ressourcesColonie) {
            System.out.print(ressource.getNomRessource() + " ");
        }
        System.out.println();
    }

    /**
     * Cette méthode permet d'ajouter une relation 'pas amis' entre deux colons.
     *
     * @param nomColon1 de type String indiquant le nom du premier Colon.
     * @param nomColon2 de type String indiquant le nom du deuxième Colon.
     * @throws MemeColonException dans le cas où Colon1 est égale à Colon2.
     * @throws ColonNonPresentDansColonieException dans le cas un où les deux Colons n'est/sont pas dans la Colonie.
     * @throws ColonDejaDansLaRelationException dans le cas où il y a déjà une relation entre les deux Colons.
     */
    public void ajoutRelation(String nomColon1, String nomColon2) throws MemeColonException,
            ColonNonPresentDansColonieException,
            ColonDejaDansLaRelationException {
        if(nomColon1.equals(nomColon2)){
            throw new MemeColonException("Un même colon ne peut se détester lui-même !");
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
                throw new ColonNonPresentDansColonieException("Un ou deux colons sélectionné ne sont pas présent dans la liste des colons.");

            }else {
                if (colon1.recherchePasAmis(colon2) || colon2.recherchePasAmis(colon1)) {
                    throw new ColonDejaDansLaRelationException("Les colons " + colon1.getNom() + " et " + colon2.getNom() + " possède déjà une relation 'pas amis' entre eux.");
                } else {
                    // Ajout dans colon1 & dans colon2 d'une relation 'pas amie'
                    colon1.addPasAmis(colon2);
                    colon2.addPasAmis(colon1);
                }
            }
        }
    }

    /**
     * Cette méthode permet de tester si une ressource est présente dans la liste des Ressources.
     *
     * @param l de type Liste de Ressources.
     * @param nomRessource de type String indiquant le nom de la Ressource à chercher.
     * @return de type booléen.
     */
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

    /**
     * Cette méthode permet d'ajouter une liste de préférences à un Colon.
     *
     * @param nom de type String indiquant le nom du Colon.
     * @param ressources de type String indiquant sous forme de chaine de caractères la liste de préférences des Ressources. Les Ressources sont espacées entre elles par un espace.
     * @throws ColonNonPresentDansColonieException dans le cas un où les deux Colons n'est/sont pas dans la Colonie.
     * @throws RessourceManquanteException dans le cas une où plusieurs Ressources n'est/sont pas dans la liste de préférence.
     * @throws RessourceDoubleException dans le cas où une Ressource est présente deux fois dans la Colonie.
     * @throws RessourcePasDansColonieException dans le cas une où plusieurs Ressources n'est/sont pas dans la Colonie.
     */
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
            throw new ColonNonPresentDansColonieException("Le colon n'existe pas.");
        }else{
            String[] res = ressources.split(" ");

            // Vérification de s'il y a toute la liste de préférence des ressources
            if(res.length != ressourcesColonie.size() ){
                throw new RessourceManquanteException("Le nombre de resource tapé n'est pas respecté. Vous avez rentré " + res.length + " ressource(s), nous en avons besoin de " + ressourcesColonie.size() + ".");
            }else{
                StringTokenizer tok = new StringTokenizer(ressources," ");
                List<Ressource> listePref = new ArrayList<Ressource>(ressourcesColonie.size());

                while(tok.hasMoreTokens()) {
                    // Ajout des ressources à la liste des préférences ressources
                    String chaine = tok.nextToken();
                    if(this.ressourceInList(this.ressourcesColonie, chaine)){
                        Ressource r = new Ressource(chaine);
                        if(this.ressourceInList(listePref, chaine)){
                            throw new RessourceDoubleException("La ressource est en double dans la liste de préférence.");
                        }else{
                            listePref.add(r);
                        }
                    }
                    else{
                        throw new RessourcePasDansColonieException("Une des ressources n'existe pas.");
                    }
                }
                this.getColons(nom).setPreferencesRessources(listePref);
            }
        }
    }

    /**
     * Cette méthode permet de vérifier que les listes de préférences de tous les Colons sont renseignées.
     *
     * @return de type booléen.
     */
    public boolean verificationListePref() {
        List<Colon> colonsIncomplets = new ArrayList<Colon>();

        //Vérification que la liste de préférences de chaque colon est complète
        for(Colon colon : colons) {
            //Sinon, on ajoute le colon à la liste des colons incomplets
            if(colon.getPreferencesRessources().size() != colons.size()) {
                colonsIncomplets.add(colon);
            }
        }

        // Affichage de la liste des colons incomplets
        if(!colonsIncomplets.isEmpty()) {
            System.err.println("La liste de préférences de certains colons est incomplète !");
            System.out.println("\n**********  Liste des colons avec des listes de préférences vides ou incomplètes  **********");
            for(Colon colon : colonsIncomplets) {
                System.out.println("        " + colon.getNom());
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * Cette méthode permet de trier la liste des Ressources de la Colonie par ordre de popularité.
     */
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

    /**
     * Cette méthode permet de trier la liste des Colons de la Colonie dans l'ordre décroissant par rapport au nombre de Colons détestés de chaque Colon.
     */
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

    /**
     * Cette méthode permet de calculer la popularité de chaque Ressource.
     */
    private void calculePointRessource(){
        for (Colon colon : this.colons) {
            List<Ressource> listeRes = colon.getPreferencesRessources();
            for(int i = 0; i < listeRes.size(); i++){
                for(Ressource r : this.ressourcesColonie){
                    if(listeRes.get(i).equals(r)){
                        r.setPopularite(r.getPopularite()+(listeRes.size()-i));
                        System.out.println(r.getPopularite());
                    }
                }
            }
        }
    }

    /**
     * Cette méthode permet d'échanger les Ressources entre deux Colons.
     *
     * @param nomColon1 de type String indiquant le nom du premier Colon.
     * @param nomColon2 de type String indiquant le nom du deuxième Colon.
     * @throws MemeColonException dans le cas où Colon1 est égale à Colon2.
     * @throws ColonNonPresentDansColonieException dans le cas un où les deux Colons n'est/sont pas dans la Colonie.
     */
    public void echangeRessource(String nomColon1, String nomColon2) throws MemeColonException, ColonNonPresentDansColonieException {
        if(nomColon2.equals(nomColon1)) {
            throw new MemeColonException("On peut pas échanger les ressources d'un même colon.");
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
                throw new ColonNonPresentDansColonieException("Un ou deux colons sélectionnés ne sont pas présent dans la liste des colons.");
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

    /**
     * Cette méthode permet d'afficher la liste de préférences de chaque Colon, le nombre de jaloux et l'affectation des Ressources aux Colons.
     */
    public void afficherJaloux(){
        this.afficherListePrefColons();
        afficherColonsPasAmis();
        afficherObjets();
        Algo.calculAffectation(this);
        System.out.println("\n-> Le nombre de colons jaloux dans la colonie est de " + affectation);
    }

    /**
     * Cette méthode permet d'afficher la liste des colons et leur liste de Colons 'pas amis'.
     */
    public void afficherColonsPasAmis(){
        System.out.println("\n**********  Récapitulatif de tous les colons et de leur liste de colons qu'il déteste  **********");
        for(Colon c : colons){
            System.out.print("        " + c.getNom() + " : ");
            for(Colon cp : c.getPasAmis()){
                System.out.print(cp.getNom() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Cette méthode permet d'afficher la liste de préférences de chaque Colon.
     */
    public void afficherListePrefColons(){
        System.out.println("\n**********  Récapitulatif de  la liste de préférence de chaque colon  **********");
        for(Colon c : colons){
            System.out.print("        " + c.getNom() + " : ");
            for(Ressource r : c.getPreferencesRessources()){
                System.out.print(r.getNomRessource() + " ");
            }
            System.out.println();
        }
    }

    /**
     * Cette méthode affiche les Colons et leur Ressource.
     */
    public void afficherObjets() {
        System.out.println("\n**********  Récapitulatif des colons et de leur ressource attribuée  **********");
        for(Colon c : colons) {
            System.out.println("        " + c.getNom() + ":" + c.getRessource().getNomRessource());
        }
    }

    /**
     * Cettye méthode permet de retourner la position d'une Ressource dans la liste des Ressources de la Colonie.
     *
     * @param ressource de type Ressource indiquant la Ressource à chercher.
     * @return de type int indiquant la position de la liste.
     */
    public int getPositionRessource(Ressource ressource){
        for(int i=0; i<ressourcesColonie.size(); i++){
            if(ressourcesColonie.get(i).equals(ressource)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Cette méthode permet d'ajouter une Ressource à la liste des Ressources de la Colonie.
     *
     * @param ressource de type Ressource.
     * @throws RessourceDejaDansColonieException dans le cas où la Ressource est déjà présente dans la Colonie.
     */
    public void addRessource(Ressource ressource) throws RessourceDejaDansColonieException{
        for(Ressource r1 : this.ressourcesColonie){
            if(r1.getNomRessource().equals(ressource.getNomRessource())){
                throw new RessourceDejaDansColonieException("La ressource est déjà dans la colonie.");
            }
        }
        this.ressourcesColonie.add(ressource);
    }

    /**
     * Getter de la liste des Ressources de la Colonie.
     *
     * @return de type List de Ressource.
     */
    public List<Ressource> getRessources(){
        return this.ressourcesColonie;
    }

    /**
     * Getter d'un Colon de la Colonie.
     *
     * @param nom de tupe String indiquant le nom du Colon de la Colonie à retourner.
     * @return de type Colon.
     */
    public Colon getColons(String nom){
        for(Colon c : this.colons){
            if(c.getNom().equals(nom)){
                return c;
            }
        }
        return null;
    }

    /**
     * Getter de la liste de Colons de la Colonie.
     * @return de type List de Colon.
     */
    public List<Colon> getColons() {
        return colons;
    }

    /**
     * Cette méthode permet d'ajouter un Colon à la Colonie.
     *
     * @param c de type Colon indiquant le Colon à ajouter.
     * @throws ColonDejaDansColonieException dans le cas où le Colon est déjhà présent dans la Colonie.
     */
    public void addColon(Colon c) throws ColonDejaDansColonieException {
        for(Colon c1 : this.colons){
            if(c1.equals(c)){
                throw new ColonDejaDansColonieException("Le colon est déjà dans la colonie.");
            }
        }
        this.colons.add(c);
    }

    /**
     * Getter du scanner de l'entrée clavier de la Colonie.
     *
     * @return de type Scanner.
     */
    public Scanner getSc(){
        return sc;
    }

    /**
     * Getter du chemin du fichier de configuration de la Colonie.
     *
     * @return de type String.
     */
    public String getCheminFichierConf() {
        return this.cheminFichierConf;
    }

    /**
     * Setter du chemin du fichier de configuration de la Colonie.
     *
     * @param cheminFichier de type String.
     */
    public void setCheminFichierConf(String cheminFichier) {
        this.cheminFichierConf = cheminFichier;
    }

    /**
     * Setter du nombre de Jaloux dans la Colonie.
     *
     * @param affectation de type int.
     */
    public void setAffectation(int affectation) {
        this.affectation = affectation;
    }

    /**
     * Getter du nombre de Colons jaloux dans la Colonie.
     *
     * @return de type int.
     */
    public int getAffectation() {
        return affectation;
    }

}
