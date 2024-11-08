import java.util.*;



public class Colonie {
    private String nom;
    private List<Ressource> ressourcesColonie;
    private List<Colon> colons;
    private int affectation;
    private final Scanner sc;
    private static int nbrMaxColons;

    public Colonie(String nom) {
        this.nom = nom;
        this.ressourcesColonie = new ArrayList<Ressource>(26);
        this.colons = new ArrayList<Colon>(26);
        this.sc = new Scanner(System.in);
        this.affectation = 0;
        this.nbrMaxColons=26;
        init();
    }

    public void init() throws InputMismatchException{
        System.out.println("Entrez le nombre de colons dans la colonie " + this.nom);
        Integer n = sc.nextInt();
        if(n instanceof Integer && n<=this.nbrMaxColons){
            char nomColon = 'A';
            // Création des colons et des ressources
            for (int i = 0; i < n; i++) {
                Colon colon = new Colon(nomColon);
                this.colons.add(colon);
                nomColon++;

                Ressource ressource = new Ressource(i+1);
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
        System.out.println();
        System.out.println("Liste des ressources de la colonie : ");
        for(Ressource ressource : ressourcesColonie) {
            System.out.print(ressource.getNomRessource() + " ");
        }
        System.out.println();
    }

    public void menu1() {
        int choix = -1;

        while(choix!=3) {
            System.out.println("Que voulez-vous faire : ");
            System.out.println("1 : Ajouter une relation 'ne s'aiment pas' entre deux colons ");
            System.out.println("2 : Ajouter une liste de préférences à un colon ");
            System.out.println("3 : Fin ");
            try {
                choix = sc.nextInt();
                if (choix < 1 || choix > 3) {
                    while (choix < 1 || choix > 3) {
                        System.out.println("Le nombre que vous avez tapé est invalide !\nQue voulez-vous faire ? : ");
                        System.out.println("1 : Ajouter une relation 'ne s'aiment pas' entre deux colons ");
                        System.out.println("2 : Ajouter une liste de préférences à un colon ");
                        System.out.println("3 : Fin ");
                        choix = sc.nextInt();
                    }
                }

                switch (choix) {
                    case 1:
                        ajoutRelation();
                        break;

                    case 2:
                        ajoutListePref();
                        break;

                    case 3:
                        if (!verificationListePref()) {
                            menu1();
                        } else {
                            partageRessources();
                            menu2();
                        }
                        System.out.println("Fin du programme ! ");
                        break;


                }
            } catch (InputMismatchException e) {
                System.out.println("Le nombre est incorect !");
                sc.next();
            }
        }
    }

    public void menu2(){
        System.out.println("Bienvenue au menu 2 que voulez-vous faire ?");
        afficherObjets();
        int choix = 0;
        while(choix!=3){
            System.out.println("1 : Echanger les ressources de 2 colons ");
            System.out.println("2 : Afficher le nombre de jaloux ");
            System.out.println("3 : Fin ");
            choix = sc.nextInt();
            if(choix<1 || choix>3) {
                while(choix<1 || choix>3) {
                    System.out.println("Le nombre que vous avez tapez est invalide !\nQue voulez-vous faire ? : ");
                    System.out.println("1 : Echanger les ressources de 2 colons ");
                    System.out.println("2 : Afficher le nombre de jaloux ");
                    System.out.println("3 : Fin ");
                    choix = sc.nextInt();
                }
            }

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
            }
        }
    }

    public void ajoutRelation() {
        System.out.println("Entrez le nom du colon 1 ?");
        char nomColon1 = sc.next().charAt(0);
        System.out.println("Entrez le nom du colon 2 ?");
        char nomColon2 = sc.next().charAt(0);

        if(nomColon1 == nomColon2){
            System.out.println("Vous ne pouvez pas lier le même colons.\nRedirection au menu !");
        }
        else{
            Colon colon1 = null;
            Colon colon2 = null;
            for(Colon colon : colons) {
                if(colon.getNom() == nomColon1) {
                    colon1 = colon;
                }
                if(colon.getNom() == nomColon2) {
                    colon2 = colon;
                }
            }

            if (colon1==null || colon2==null){
                System.out.println("Un ou deux colons séléctionnés ne sont pas présent dans la liste des colons");
            }else {
                if (colon1.recherchePasAmis(colon2) || colon2.recherchePasAmis(colon1)) {
                    System.out.println("Le colon est déjà dans la liste des pas amis ");
                } else {
                    colon1.addPasAmis(colon2);
                    colon2.addPasAmis(colon1);
                }
            }

            this.afficheRecap();

        }


    }

    public void ajoutListePref() {
        sc.nextLine();
        System.out.println("Entrez le nom du colon : ");
        char nom = sc.nextLine().charAt(0);
        System.out.println("Entrez les noms des ressources, espacés entre eux : ");
        String ressource = sc.nextLine();
        String[] res = ressource.split(" ");

        if(res.length != ressourcesColonie.size() ){
            System.out.println("Le nombre de resource tappé n'est pas respecté !");
            System.out.println("Vous avez rentré "+res.length+" ressources");
            System.out.println("Redirection vers le menu");
            return;

        }else{
            StringTokenizer tok = new StringTokenizer(ressource," ");
            List<Ressource> listePref = new ArrayList<Ressource>(ressourcesColonie.size());

            while(tok.hasMoreTokens()) {
                String chaine = tok.nextToken();
                int nb = Integer.parseInt(chaine);
                boolean trouve = false;
                for(Ressource ressourceColonie : ressourcesColonie){

                    if(ressourceColonie.getNomRessource() == nb){
                        trouve = true;
                    }

                }

                if(trouve){

                    Ressource r = new Ressource(nb);
                    listePref.add(r);
                }
                else{

                    System.out.println("La ressource ne peut pas être ajouté !");

                }

            }

            boolean contient = false;
            for(Colon colon : colons) {
                if(colon.getNom() == nom) {
                    colon.setPreferencesRessource(listePref);
                    contient = true;
                }
            }
            if(!contient) {
                System.out.println("Le colon n'existe pas !");
            }

            System.out.println("Voici un récapitulatif de préférence de chaque Colon : ");
            for(Colon c : colons){
                System.out.print(c.getNom()+" : ");
                for(Ressource r : c.getPreferencesRessource()){
                    System.out.print(r.getNomRessource()+" ");
                }
                System.out.print("\n");
            }
        }

    }

    public boolean verificationListePref() {
        List<Colon> colonsIncomplets = new ArrayList<Colon>();

        //On vérifie que la liste de préférences de chaque colon est complète
        for(Colon colon : colons) {
            //Si non, on ajoute le colon à la liste des colons incomplets
            if(colon.getPreferencesRessource().size() != colons.size()) {
                colonsIncomplets.add(colon);
            }
        }

        //On affiche la liste des colons incomplets
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
            int j=0;
            while(!colons.get(i).isAttribue()){
                int pos = getPositionRessource(colons.get(i).getUnePreferenceRessource(j));
                if(this.ressourcesColonie.get(pos).getDisponibilite()){
                    colons.get(i).setRessource(this.ressourcesColonie.get(pos));
                    this.ressourcesColonie.get(pos).setDisponibilite(false);
                    this.ressourcesColonie.get(pos).setProrio(colons.get(i));
                    colons.get(i).setAttribue(true);
                    colons.get(i).setPosRessource(j);
                }else{
                    j++;
                }
            }
        }
    }

    public void echangeRessource(){
        System.out.println("Entrez le nom du premier colon : ");
        char prenomColon1 = sc.next().charAt(0);
        System.out.println("Entrez le nom du deuxième colon : ");
        char prenomColon2 = sc.next().charAt(0);
        if(prenomColon2 == prenomColon1) {

            System.out.println("On peut pas échanger les ressources d'un même colon");
            System.out.println(" Vous allez être redirigé au menu !");
            return;
        }
        else{
            Colon colon1 = null;
            Colon colon2 = null;

            for(Colon c : colons) {
                if(c.getNom() == prenomColon1) {
                    colon1 = c;
                }
                if(c.getNom() == prenomColon2){
                    colon2 = c;
                }
            }

            if(colon1 == null && colon2 == null) {
                System.out.println("Le colon 1 n'existe pas ! ");
                System.out.println("Le colon 2 n'existe pas ! ");

            } else if(colon1 == null) {
                System.out.println("Le colon 1 n'existe pas ! ");

            } else if(colon2 == null) {
                System.out.println("Le colon 2 n'existe pas ! ");

            } else {
                Ressource aux = colon1.getRessource();
                colon1.setRessource(colon2.getRessource());
                colon2.setRessource(aux);
                colon1.setPosRessource(colon1.getPosRessource());
                colon2.setPosRessource(colon2.getPosRessource());
            }
            System.out.println("Les ressources entre le colon "+ prenomColon1+" et le colon "+prenomColon2+" on était échange");

            System.out.println("Voici un récapitulatif des ressources de partages : ");
            afficherObjets();

        }

    }

    public void calculAffectation(){
        int res = 0;
        boolean jaloux;
        for(Colon c  : colons) {
            jaloux = false;
            List<Colon> pasAmis = c.getPasAmis();
            for(Colon c1 : pasAmis){
                for(int i = 0; i<c.getPosRessource();i++) {
                    if (c1.getRessource().equals(c.getPreferencesRessource().get(i)) ) {
                        jaloux = true;

                    }
                }
            }
            if(jaloux){
                res++;
            }
        }
        this.affectation = res;
    }

    public void afficherJaloux(){
        System.out.println("Voici la liste des préférences de chaque colon : ");
        for(Colon c : colons){
            System.out.print(c.getNom()+" : ");
            for(Ressource r : c.getPreferencesRessource()){
                System.out.print(r.getNomRessource()+" ");
            }
            System.out.print("\n");
        }
        calculAffectation();
        System.out.println("Le nombre de colons jaloux dans la colonie est de " + affectation);
        System.out.println("Voici un récapitulatif du partage de ressources dans la colonie : ");
        afficherObjets();

    }

    public void afficheRecap(){
        System.out.println("Voici un récapitulatif de chaque colon pas amis : ");
        for(Colon c : colons){
            System.out.print(c.getNom()+" : ");
            for(Colon cp : c.getPasAmis()){
                System.out.print(cp.getNom()+" ");
            }
            System.out.print("\n");
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

    public List<Colon> getColons() {
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
    public void setNom(){
        this.nom = nom;
    }

}
