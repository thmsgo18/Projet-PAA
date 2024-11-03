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

    public boolean Jaloux(Colon colon){
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

    public void calculeJaloux(){
        for(int i=0; i<colons.size(); i++){
            if(this.Jaloux(colons.get(i))){
                this.affectation++;
            }
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

        for(Colon colon : colons) {
            System.out.print(colon.getNom() + " : ");
            for(int i=0; i<colon.getPasAmis().size(); i++) {
                System.out.print(colon.getPasAmis().get(i).getNom()+" ");
            }
            System.out.println();
        }
    }

    public void ajoutListePref() {
        sc.nextLine();
        System.out.println("Entrez le nom du colon");
        char nom = sc.nextLine().charAt(0);
        System.out.println("Entrez les nom des ressources et espacé entre eux : ");
        String ressource = sc.nextLine();
        StringTokenizer tok = new StringTokenizer(ressource," ");
        List<Ressource> listePref = new ArrayList<Ressource>(ressourcesColonie.size());
        while(tok.hasMoreTokens()){
            String chaine = tok.nextToken();
            int nb = Integer.parseInt(chaine);
            Ressource r = new Ressource(nb);
            listePref.add(r);
        }



        for(Colon colon : colons) {
            if(colon.getNom() == nom) {
                colon.setPreferencesRessource(listePref);
            } else {
                System.out.println("Le colon n'existe pas.");
            }
        }


        System.out.print("liste de préférences de " + nom + " : ");
        for(Colon colon : colons) {
            if(colon.getNom() == nom) {
                for (Ressource ress : colon.getPreferencesRessource()) {
                    System.out.print(ress.getNomRessource() + " ");
                }
            }
        }
    }

    public boolean verification() {
        boolean complet = true;

//        if(colons.size() != NBCOLONS){
//            System.out.println("La colonie n'est pas complète !");
//            return false;
//        }
//
//        for(Colon colon : colons) {
//            if(colon.getPreferencesRessource().size() != ressourcesColonie.size()) {
//                complet = false;
//                System.out.print(colon.getNom() + " ");
//            }
//        }
        return complet;
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
                    System.out.println("Le nombre que vous avez tapez est invalide !\nQue voulez-vous faire ? : ");
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
                        System.out.print("Liste des colons avec des listes de préférences vides ou incomplètes : ");
                        menu1();
                    } else {
                        //menu2();
                    }
                    System.out.println("Fin du programme ! ");
            }
        }
    }

    public void menu2(){
        System.out.println("Bienvenue au menue 2 que voulez faire ?");
        Scanner sc = new Scanner(System.in);
        int choix = 0;
        while(choix!=3){
            System.out.println("1 : echanger les ressources de 2 colons ");
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
                    //AfficheJaloux();
                    break;

                case 3 :
                    System.out.println("Fin du programme ! ");
                    return;
            }

        }
    }

    public void echangeRessource(){
        System.out.println("Bienvenue dans l'echange de ressource entre 2 colons !");
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom du premier colon : ");
        char prenomColon1 = sc.nextLine().charAt(0);
        System.out.println("Entrez le nom du deuxieme colon : ");
        char prenomColon2 = sc.nextLine().charAt(0);
        Ressource perm1 = new Ressource(0);
        Ressource perm2 = new Ressource(0);
        for(Colon c : colons) {
            if(c.getNom() == prenomColon1){
                perm1 = c.getRessource();
            }
            if(c.getNom() == prenomColon2){
                perm2 = c.getRessource();
            }
        }

        for(Colon c : colons){
            if(c.getNom() == prenomColon1){
                c.setRessource(perm2);
            }
            if(c.getNom() == prenomColon2){
                c.setRessource(perm1);
            }
        }

    }

    public void calculAffectation(){
        for(Colon c  : colons) {
            int jaloux = 0;
            for(Colon c1 : c.getPasAmis()){
                for(int i= 0; i<c.getPosRessource();i++){
                    if(c1.getRessource().equals(c.getPreferencesRessource().get(i))){
                        jaloux++;
                    }
                }
                if(jaloux>0){
                    this.affectation++;
                }
            }
        }
        return;
    }

    public void afficherJaloux(){
        System.out.println("Bienvenue dans le menu de l'affichage des jaloux !\n");
        calculAffectation();
        System.out.println("Le nombre de colon jaloux dans la colonie est de "+affectation+"\n");
        System.out.println("Voici un récapitulatif du partage de ressource dans la colonie : \n");
        for(Colon c : colons){
            System.out.println(c.getNom()+" : "+c.getRessource().getNomRessource()+"\n");
        }
        return;
    }
}
