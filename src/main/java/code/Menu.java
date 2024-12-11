package code;

import code.exception.*;
import java.util.InputMismatchException;

/**
 * Cette classe, contenant que des méthodes static, répertorie les différents menus du programme.
 */
public class Menu {
    /**
     * Cette méthode static permet de :
     *      1 : Ajouter une relation 'ne s'aiment pas' entre deux colons.
     *      2 : Ajouter une liste de préférences à un colon.
     *      3 : Passer au menu 2.
     *
     * @param colonie de type Colonie.
     */
    public static void menu1(Colonie colonie) {
        colonie.init();

        int choix = -1;

        System.out.println("\n*******************************************  MENU 1  *****************************************");
        while(choix!=3) {
            System.out.println("\nQue voulez-vous faire : ");
            System.out.println("        1 : Ajouter une relation 'ne s'aiment pas' entre deux colons ");
            System.out.println("        2 : Ajouter une liste de préférences à un colon ");
            System.out.println("        3 : Fin ");
            try {
                choix = colonie.getSc().nextInt();
                switch (choix) {
                    case 1:
                        System.out.println("Entrez le nom du colon 1 ?");
                        String nomColon1 = colonie.getSc().next();
                        System.out.println("Entrez le nom du colon 2 ?");
                        String nomColon2 = colonie.getSc().next();
                        try{
                            colonie.ajoutRelation(nomColon1, nomColon2);
                        } catch (MemeColonException | ColonNonPresentDansColonieException |
                                 ColonDejaDansLaRelationException e) {
                            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
                        }

                        colonie.afficherColonsPasAmis();
                        break;

                    case 2:
                        colonie.getSc().nextLine();
                        System.out.println("Entrez le nom du colon :");
                        String nom = colonie.getSc().nextLine();
                        System.out.println("Entrez le nom des ressources, espacés entre eux :");
                        String ressource = colonie.getSc().nextLine();
                        try{
                            colonie.ajoutListePref(nom, ressource);
                        } catch (ColonNonPresentDansColonieException | RessourceManquanteException |
                                 RessourceDoubleException | RessourcePasDansColonieException e) {
                            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
                        }

                        colonie.afficherListePrefColons();
                        break;

                    case 3:
                        if (colonie.verificationListePref()) {
                            Algo.SolutionNaif(colonie);
                            Menu.menu2(colonie);
                        }else{
                            choix=-1;
                        }
                        break;

                    default:
                        System.err.println("ERREUR : Le choix est invalide !");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("ERREUR : Ce type d'entrée n'est pas accepté !");
                colonie.getSc().next();
            }
        }
    }

    /**
     * Cette méthode static permet de :
     *      1 : Échanger les ressources de 2 colons.
     *      2 : Afficher le nombre de colons jaloux.
     *      3 : Terminer le programme.
     *
     * @param colonie de type Colonie.
     */
    public static void menu2(Colonie colonie) {
        System.out.println("\n*******************************************  MENU 2  *******************************************");
        colonie.afficherObjets();

        int choix = -1;
        while(choix!=3){
            System.out.println("\nQue voulez-vous faire : ");
            System.out.println("        1 : Échanger les ressources de deux colons ");
            System.out.println("        2 : Afficher le nombre de colons jaloux ");
            System.out.println("        3 : Fin ");
            try{
                choix = colonie.getSc().nextInt();
                colonie.getSc().nextLine();
                switch(choix) {
                    case 1 :
                        System.out.println("Entrez le nom du colon 1 : ");
                        String nomColon1 = colonie.getSc().nextLine();
                        System.out.println("Entrez le nom du colon 2 : ");
                        String nomColon2 = colonie.getSc().nextLine();
                        try{
                            colonie.echangeRessource(nomColon1, nomColon2);
                        }catch(MemeColonException | ColonNonPresentDansColonieException e){
                            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
                        }
                        colonie.afficherObjets();
                        break;

                    case 2 :
                        colonie.afficherJaloux();
                        break;

                    case 3 :
                        System.out.println("Fin du programme !");
                        break;

                    default:
                        System.err.println("ERREUR : Le choix est invalide !");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("ERREUR : Ce type d'entrée n'est pas accepté !");
                colonie.getSc().next();
            }

        }
    }

    /**
     * (Dans le cas de l'utilisation d'un fichier de configuration de la colonie)
     * Cette méthode static permet de :
     *      1 : Réaliser une affectation automatique des ressources aux colons.
     *      2 : Sauvegarder la colonie actuelle.
     *      3 : Terminer le programme.
     *
     * @param colonie de type Colonie
     */
    public static void menuSolutionAuto(Colonie colonie){
        System.out.println("\n*********************************  MENU SOLUTION AUTOMATIQUE  *********************************");
        colonie.afficherListePrefColons();
        colonie.afficherColonsPasAmis();

        int choix = -1;
        while(choix!=3){
            System.out.println("\nQue voulez-vous faire : ");
            System.out.println("        1 : Résolution automatique");
            System.out.println("        2 : Sauvegarder la solution actuelle");
            System.out.println("        3 : Fin");
            try{
                choix = colonie.getSc().nextInt();
                colonie.getSc().nextLine();
                switch(choix){
                    case 1:
                        System.out.println("Affectation des ressources de colonie :");
                        int k =7;
                        try{
                            Algo.RecuitSimule(colonie,k);
                        }catch(Exception e){
                            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
                        }
                        colonie.afficherJaloux();
                        break;

                    case 2 :
                        System.out.println("\n**********  Sauvegarde de la solution actuelle  **********");
                        System.out.println("\nEntrez le chemin du fichier de sauvegarde : ");
                        String chemin = colonie.getSc().nextLine();
                        try{
                            Fichier.sauvegarde(chemin, colonie);
                        }catch(FichierException e){
                            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
                        }
                        break;

                    case 3 :
                        System.out.println("Fin du programme !");
                        break;

                    default:
                        System.err.println("ERREUR : Le choix est invalide !");
                        break;
                }

            }catch(InputMismatchException e){
                System.err.println("ERREUR : Ce type d'entrée n'est pas accepté !");
                colonie.getSc().nextLine();

            }
        }

    }

}
