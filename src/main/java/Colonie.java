import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Colonie {
    private String nom;
    private List<Ressource> ressourcesColonie;
    private List<Colon> colons;
    private int affectation;
    private final Scanner sc;

    public Colonie(String nom) {
        this.nom = nom;
        this.ressourcesColonie = new ArrayList<Ressource>(26);
        this.colons = new ArrayList<Colon>(26);
        this.sc = new Scanner(System.in);
        this.affectation = 0;
        init();
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

    public boolean jaloux(Colon colon){
        for(int i=0; i<colon.getPosRessource(); i++){
            Ressource res = this.ressourcesColonie.get(this.getPositionRessource(colon.getUnePreferenceRessource(i)));
            for(int j=0; j<colon.getPasAmis().size(); j++){
                if (colon.getPasAmis().get(j).equals(res.getProrio())){
                    return true;
                }
            }
        }
        return false;
    }

    public void calculJaloux(){
        int res = 0;
        for(int i=0; i<colons.size(); i++){
            if(this.jaloux(colons.get(i))){
                res++;
            }
        }
        this.affectation = res;
    }

    public int getPositionRessource(Ressource ressource){
        for(int i=0; i<ressourcesColonie.size(); i++){
            if(ressourcesColonie.get(i).equals(ressource)){
                return i;
            }
        }
        return -1;
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

    public List<Ressource> getRessourcesColonie() {
        return ressourcesColonie;
    }
    public void setRessourcesColonie(List<Ressource> ressourceDeLaColonie) {
        this.ressourcesColonie = ressourceDeLaColonie;
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


    public void ajoutRelation() {
        System.out.println("Entrez le nom du colon 1 ?");
        char nomColon1 = sc.next().charAt(0);
        System.out.println("Entrez le nom du colon 2 ?");
        char nomColon2 = sc.next().charAt(0);

        Colon colon1 = null;
        Colon colon2 = null;
        for(Colon colon : colons) {
            if(colon.getNom() == nomColon1) {
                colon1 = colon;
            }
        }
        for(Colon colon : colons) {
            if(colon.getNom() == nomColon2) {
                colon2 = colon;
            }
        }

        if (colon1 != null) {
            colon1.addPasAmis(colon2);
        } else {
            System.out.println("L'action souhaitée n'a pas été réalisée.");
            System.out.println("Le colon 1 n'existe pas.");
        }
        if (colon2 != null) {
            colon2.addPasAmis(colon1);
        } else {
            System.out.println("L'action souhaitée n'a pas été réalisée.");
            System.out.println("Le colon 2 n'existe pas.");

        }
    }

    public void ajoutListePref() {
        sc.nextLine();
        System.out.println("Entrez le nom du colon : ");
        char nom = sc.nextLine().charAt(0);
        System.out.println("Entrez les noms des ressources, espacés entre eux : ");
        String ressource = sc.nextLine();
        StringTokenizer tok = new StringTokenizer(ressource," ");
        List<Ressource> listePref = new ArrayList<Ressource>(ressourcesColonie.size());
        while(tok.hasMoreTokens()){
            String chaine = tok.nextToken();
            int nb = Integer.parseInt(chaine);
            Ressource r = new Ressource(nb);
            listePref.add(r);
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
    }

    public boolean verification() {
        System.out.println("Liste des colons avec des listes de préférences vides ou incomplètes : ");
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
            for(Colon colon : colonsIncomplets) {
                System.out.println(colon.getNom());
            }
            return false;
        } else {
            return true;
        }
    }

    public void init(){
        System.out.println("Entrez le nombre de colons dans la colonie " + this.nom);
        int n = sc.nextInt();

        char nomColon = 'A';
        int nomRessource = 1;
        for(int i = 0; i < n; i++){
            Colon colon = new Colon(nomColon);
            this.colons.add(colon);
            nomColon++;

            Ressource ressource = new Ressource(nomRessource);
            this.ressourcesColonie.add(ressource);
            nomRessource++;
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
            choix = sc.nextInt();
            if(choix<1 || choix>3) {
                while(choix<1 || choix>3) {
                    System.out.println("Le nombre que vous avez tapé est invalide !\nQue voulez-vous faire ? : ");
                    System.out.println("1 : Ajouter une relation 'ne s'aiment pas' entre deux colons ");
                    System.out.println("2 : Ajouter une liste de préférences à un colon ");
                    System.out.println("3 : Fin ");
                    choix = sc.nextInt();
                }
            }

            switch(choix) {
                case 1:
                    ajoutRelation();
                    break;

                case 2:
                    ajoutListePref();
                    break;

                case 3:
                    if(!verification()) {
                        menu1();
                    } else {
                        partageRessources();
                        menu2();
                    }
                    System.out.println("Fin du programme ! ");
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

    public void echangeRessource(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du premier colon : ");
        char prenomColon1 = sc.nextLine().charAt(0);
        System.out.println("Entrez le nom du deuxième colon : ");
        char prenomColon2 = sc.nextLine().charAt(0);
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

        afficherObjets();
    }

    public void afficherJaloux(){
        calculJaloux();
        System.out.println("Le nombre de colons jaloux dans la colonie est de " + affectation);
        System.out.println("Voici un récapitulatif du partage de ressources dans la colonie : ");
        afficherObjets();
    }

    public void afficherObjets() {
        for(Colon c : colons) {
            System.out.println(c.getNom() + ":" + c.getRessource().getNomRessource());
        }
    }
}
