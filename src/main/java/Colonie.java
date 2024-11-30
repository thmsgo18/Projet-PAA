import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class Colonie {
    private String nom;
    private List<Ressource> ressourcesColonie;
    private List<Colon> colons;
    private int affectation;
    private final Scanner sc;
    private final int nbrMaxColons;

    public Colonie(String nom) {
        this.nom = nom;
        this.ressourcesColonie = new ArrayList<Ressource>(26);
        this.colons = new ArrayList<Colon>(26);
        this.sc = new Scanner(System.in);
        this.affectation = 0;
        this.nbrMaxColons=26;
    }

    public void init() throws InputMismatchException{
        // Initialisation de la colonie
        System.out.println("Entrez le nombre de colons dans la colonie " + this.nom);
        Integer n = sc.nextInt();
        sc.nextLine();
        if(n instanceof Integer && n<=this.nbrMaxColons && n>=0){
            // Création des colons et des ressources
            for (int i = 0; i < n; i++) {
                System.out.println("Entrez le nom du colon"+i+" :");
                String nomColon = sc.nextLine();
                Colon colon = new Colon(nomColon);
                this.colons.add(colon);

                System.out.println("Entrez le nom de la ressource"+i+" :");
                String nomRessource = sc.nextLine();
                Ressource ressource = new Ressource(nomRessource);
                this.ressourcesColonie.add(ressource);
            }
        }
        else{
            throw new InputMismatchException("Nombre de colons dans la colonie incorrect");
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

    public void init2() throws IOException {
        // Initialisation de la colonie
        String cheminFichier = "src/main/java/config.txt";
        StringBuilder sb = new StringBuilder();
        try{
            FileReader fr = new FileReader(cheminFichier);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                sb.append(line);
            }
            StringTokenizer tok = new StringTokenizer(sb.toString(),".");
            while(tok.hasMoreTokens()){
                String commande = tok.nextToken();
                StringTokenizer tok2 = new StringTokenizer(commande,"( ),");
                String nomCommande = tok2.nextToken();
                switch(nomCommande){
                    case "colon":
                            String nomColon = tok2.nextToken();
                            Colon colon = new Colon(nomColon);
                            this.colons.add(colon);
                            break;
                    case "ressource":
                            String nomRessource = tok2.nextToken();
                            Ressource ressource = new Ressource(nomRessource);
                            this.ressourcesColonie.add(ressource);
                            break;
                    case "deteste":
                            String nomColon1 = tok2.nextToken();
                            String nomColon2 = tok2.nextToken();
                            this.ajoutRelation(nomColon1, nomColon2);
                            break;
                    case "preferences":
                            String nomColonL = tok2.nextToken();
                            StringBuilder stringBuilder = new StringBuilder();
                            while(tok2.hasMoreTokens()){
                                String nomPref = tok2.nextToken();
                                stringBuilder.append(nomPref+" ");
                            }
                            this.ajoutListePref(nomColonL, stringBuilder.toString());
                            break;
                    default :
                        System.out.println("ERREUR DANS LA RECONNAISSANCE DE LA COMMANDE");
                        break;
                }
            }
            this.afficherObjets();
            this.afficherJaloux();
            this.afficheColonsPasAmis();
        }catch (IOException e){
            System.out.println(e.getMessage());
            try{
                Colonie colonie = new Colonie(nom);
                colonie.menu1();
            } catch (InputMismatchException IME) {
                System.out.println(IME.getMessage());
            }
        }
    }

    public void menu1() {
        try{
            this.init();
        }catch (InputMismatchException e){
            e.printStackTrace();
        }
        int choix = -1;

        while(choix!=3) {
            System.out.println("Que voulez-vous faire : ");
            System.out.println("1 : Ajouter une relation 'ne s'aiment pas' entre deux colons ");
            System.out.println("2 : Ajouter une liste de préférences à un colon ");
            System.out.println("3 : Fin ");
            try {
                choix = sc.nextInt();
                switch (choix) {
                    case 1:
                        System.out.println("Entrez le nom du colon 1 ?");
                        String nomColon1 = sc.next();
                        System.out.println("Entrez le nom du colon 2 ?");
                        String nomColon2 = sc.next();
                        ajoutRelation(nomColon1, nomColon2);
                        break;

                    case 2:
                        sc.nextLine();
                        System.out.println("Entrez le nom du colon : ");
                        String nom = sc.nextLine();
                        System.out.println("Entrez les noms des ressources, espacés entre eux : ");
                        String ressource = sc.nextLine();
                        ajoutListePref(nom, ressource);
                        break;

                    case 3:
                        if (!verificationListePref()) {
                            menu1();
                        } else {
                            partageRessources();
                            menu2();
                        }
                        break;

                    default:
                        System.out.println("Le nombre est incorect !");
                        break;
                }
            } catch (InputMismatchException e) {
                // La valeure saisie n'est pas un int
                System.out.println("La valeure entrée n'est pas un entier !");
                sc.next();
            }
        }
    }

    public void menu2(){
        System.out.println("Bienvenue au menu 2 que voulez-vous faire ?");
        System.out.println("Récapitulatif des Colons et de leur Ressource:");
        afficherObjets();
        int choix = -1;
        while(choix!=3){
            System.out.println("1 : Echanger les ressources de 2 colons ");
            System.out.println("2 : Afficher le nombre de jaloux ");
            System.out.println("3 : Fin ");
            try{
                choix = sc.nextInt();

                switch(choix) {
                    case 1 :
                        echangeRessource();
                        break;

                    case 2 :
                        afficherJaloux();
                        break;

                    case 3 :
                        System.out.println("Fin du programme ! ");
                        break;

                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                // La valeure saisie n'est pas un int
                System.out.println("La valeure entrée n'est pas un entier !");
                sc.next();
            }

        }
    }

    public void ajoutRelation(String nomColon1, String nomColon2) {
        if(nomColon1.equals(nomColon2)){
            System.out.println("Vous ne pouvez pas lier le même colon.\nRedirection au menu !");
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
                System.out.println("Un ou deux colons séléctionnés ne sont pas présent dans la liste des colons");
            }else {
                if (colon1.recherchePasAmis(colon2) || colon2.recherchePasAmis(colon1)) {
                    System.out.println("Le colon "+ colon2.getNom() +" est déjà dans la liste des 'pas amis' du colon "+ colon1.getNom() +" et inversement");
                } else {
                    // Ajout dans colon1 & dans colon2 d'une relation 'pas amis'
                    colon1.addPasAmis(colon2);
                    colon2.addPasAmis(colon1);
                }
            }
            this.afficheColonsPasAmis();
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

    public void ajoutListePref(String nom, String ressources) {
        boolean contient = false;
        for(Colon colon : colons) {
            if(colon.getNom().equals(nom)){
                // Le colon est présent dans la colonie
                contient = true;
            }
        }
        if(!contient) {
            System.out.println("Le colon n'existe pas !");
            System.out.println("Redirection vers le menu");
            return;
        }else{
            String[] res = ressources.split(" ");

            // Vérification de si il y'a toute la liste de préférence des ressources
            if(res.length != ressourcesColonie.size() ){
                System.out.println("Le nombre de resource tapé n'est pas respecté !");
                System.out.println("Vous avez rentré "+res.length+" ressources, au lieu de "+ ressourcesColonie.size()+" ressources.");
                System.out.println("Redirection vers le menu");
                return;
            }else{

                StringTokenizer tok = new StringTokenizer(ressources," ");
                List<Ressource> listePref = new ArrayList<Ressource>(ressourcesColonie.size());

                while(tok.hasMoreTokens()) {
                    // Ajout des ressources à la liste des préférences ressources
                    String chaine = tok.nextToken();
                    if(this.ressourceInList(this.ressourcesColonie, chaine)){
                        Ressource r = new Ressource(chaine);
                        if(this.ressourceInList(listePref, chaine)){
                            System.out.println("La ressource ne peut pas être ajouté !");
                            System.out.println("Redirection vers le menu");
                            return;
                        }else{
                            listePref.add(r);
                        }
                    }
                    else{
                        System.out.println("La ressource ne peut pas être ajouté !");
                        System.out.println("Redirection vers le menu");
                        return;
                    }
                }
                this.getColon(nom).setPreferencesRessource(listePref);

                System.out.println("Récapitulatif de préférence de chaque Colon : ");
                for(Colon c : colons){
                    System.out.print(c.getNom()+" : ");
                    for(Ressource r : c.getPreferencesRessource()){
                        System.out.print(r.getNomRessource()+" ");
                    }
                    System.out.print("\n");
                }
            }

        }

    }

    public boolean verificationListePref() {
        List<Colon> colonsIncomplets = new ArrayList<Colon>();

        //Vérification que la liste de préférences de chaque colon est complète
        for(Colon colon : colons) {
            //Si non, on ajoute le colon à la liste des colons incomplets
            if(colon.getPreferencesRessource().size() != colons.size()) {
                colonsIncomplets.add(colon);
            }
        }

        // Affichage de la liste des colons incomplets
        if(!colonsIncomplets.isEmpty()) {
            System.out.println("Liste des colons avec des listes de préférences vides ou incomplètes : ");
            for(Colon colon : colonsIncomplets) {
                System.out.println(colon.getNom());
            }
            return false;
        } else {
            return true;
        }
    }

    public void partageRessources(){
        for(int i=0; i<colons.size(); i++){
            // Parcours de tout les colons de la colonie
            int j=0;
            while(!colons.get(i).isAttribue()){
                // Tant que le colon n'a pas de ressource attribuée
                int pos = getPositionRessource(colons.get(i).getUnePreferenceRessource(j));
                if(this.ressourcesColonie.get(pos).getDisponibilite()){
                    // La ressource est disponible
                    colons.get(i).setRessource(this.ressourcesColonie.get(pos));
                    this.ressourcesColonie.get(pos).setDisponibilite(false);
                    colons.get(i).setAttribue(true);
                    colons.get(i).setPosRessource(j);
                }else{
                    // La ressource n'est pas disponible
                    // Passage à la ressource préférée suivante
                    j++;
                }
            }
        }
    }

    public void echangeRessource(){
        System.out.println("Entrez le nom du premier colon : ");
        char nomColon1 = sc.next().charAt(0);
        System.out.println("Entrez le nom du deuxième colon : ");
        char nomColon2 = sc.next().charAt(0);
        if(nomColon2 == nomColon1) {
            System.out.println("On peut pas échanger les ressources d'un même colon.\nRedirection au menu !");
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
                System.out.println("Un ou deux colons séléctionnés ne sont pas présent dans la liste des colons");
            }
            else {
                // Échange des ressources entre les deux colons
                Ressource aux = colon1.getRessource();
                colon1.setRessource(colon2.getRessource());
                colon2.setRessource(aux);
                colon1.setPosRessource(colon1.getPosRessource());
                colon2.setPosRessource(colon2.getPosRessource());
                System.out.println("Les ressources entre le colon "+ nomColon1 +" et le colon "+ nomColon2 +" on était échange");

                System.out.println("Récapitulatif des Colons et de leur Ressource:");
                afficherObjets();
            }

        }

    }

    public void calculAffectation(){
        int res = 0;
        boolean jaloux;
        for(Colon c  : colons) {
            // Parcours de tous les colons de la colonie
            jaloux = false;
            List<Colon> pasAmis = c.getPasAmis();
            for(Colon c1 : pasAmis){
                for(int i = 0; i<c.getPosRessource();i++) {
                    if (c1.getRessource().equals(c.getPreferencesRessource().get(i))) {
                        // Jalousie de c1
                        jaloux = true;
                    }
                }
            }
            if(jaloux){
                // Incrémentation du nombre de jaloux
                res++;
            }
        }
        this.affectation = res;
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
        calculAffectation();
        System.out.println("Le nombre de colons jaloux dans la colonie est de " + affectation);
        System.out.println("Récapitulatif des Colons et de leur Ressource: ");
        afficherObjets();
    }

    public void afficheColonsPasAmis(){
        System.out.println("Récapitulatif de chaque colon pas amis : ");
        for(Colon c : colons){
            System.out.print(c.getNom()+" : ");
            for(Colon cp : c.getPasAmis()){
                System.out.print(cp.getNom()+" ");
            }
            System.out.println();
        }
    }

    public void afficherObjets() {
        for(Colon c : colons) {
            System.out.println(c.getNom() + ":" + c.getRessource().getNomRessource());
        }
    }

    public List<Ressource> getRessourcesColonie() {
        return ressourcesColonie;
    }
    public void setRessourcesColonie(List<Ressource> ressourceDeLaColonie) {
        this.ressourcesColonie = ressourceDeLaColonie;
    }
    public int getPositionRessource(Ressource ressource){
        for(int i=0; i<ressourcesColonie.size(); i++){
            if(ressourcesColonie.get(i).equals(ressource)){
                return i;
            }
        }
        return -1;
    }

    public Colon getColon(String nom){
        for(Colon c : this.colons){
            if(c.getNom().equals(nom)){
                return c;
            }
        }
        return null;
    }
    public List<Colon> getColon() {
        return colons;
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

    public Scanner getSc() {
        return sc;
    }

    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }

}
