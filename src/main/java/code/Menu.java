package code;

import code.exception.*;

import java.util.InputMismatchException;

public class Menu {

    public static void menu1(Colonie colonie) {
        colonie.init();

        int choix = -1;

        while(choix!=3) {
            System.out.println("Que voulez-vous faire : ");
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
                            colonie.partageRessources();
                            Menu.menu2(colonie);
                        }else{

                        }
                        break;

                    default:
                        System.out.println("Le nombre est incorrect !");
                        break;
                }
            } catch (InputMismatchException e) {
                // La valeur saisie n'est pas un int
                e.getMessage();
                colonie.getSc().next();
            }
        }
    }

    public static void menu2(Colonie colonie) {
        System.out.println("Récapitulatif des Colons et de leur code.Ressource:");
        colonie.afficherObjets();
        int choix = -1;
        while(choix!=3){
            System.out.println("Menu 2 que voulez-vous faire : ");
            System.out.println("        1 : Échanger les ressources de 2 colons ");
            System.out.println("        2 : Afficher le nombre de jaloux ");
            System.out.println("        3 : Fin ");
            try{
                choix = colonie.getSc().nextInt();
                colonie.getSc().nextLine();
                switch(choix) {
                    case 1 :
                        System.out.println("Entrez le nom du premier colon : ");
                        String nomColon1 = colonie.getSc().nextLine();
                        System.out.println("Entrez le nom du deuxième colon : ");
                        String nomColon2 = colonie.getSc().nextLine();
                        try{
                            colonie.echangeRessource(nomColon1, nomColon2);
                        }catch(MemeColonException | ColonNonPresentDansColonieException e){
                            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
                        }
                        System.out.println("Récapitulatif des Colons et de leur code.Ressource:");
                        colonie.afficherObjets();
                        break;

                    case 2 :
                        colonie.afficherJaloux();
                        break;

                    case 3 :
                        System.out.println("Fin du programme ! ");
                        break;

                    default:
                        break;
                }
            } catch (InputMismatchException e) {
                // La valeur saisie n'est pas un int
                System.out.println("La valeur entrée n'est pas un entier !");
                colonie.getSc().next();
            }

        }
    }

    public static void menuSolutionAuto(Colonie colonie) throws InputMismatchException {
        int choix = -1;
        colonie.afficherColonsPasAmis();
        colonie.afficherListePrefColons();
        while(choix!=3){
            System.out.println("Menu solution auto que voulez-vous faire : ");
            System.out.println("        1 : Résolution automatique");
            System.out.println("        2 : sauvegarder la solution actuelle");
            System.out.println("        3 : Fin");
            try{
                System.out.println("Quelle est votre choix ?");
                choix = colonie.getSc().nextInt();
                colonie.getSc().nextLine();
                switch(choix){
                    case 1:
                        System.out.println("Affectation des ressources de colonie :");
                        colonie.resolutionAutomatique();
                        colonie.afficherJaloux();
                        break;
                    case 2 :
                        System.out.println("Vous avez choisit la sauvegarde ! ");
                        System.out.println("Entrez le nom du fichier où sera sauvegarder la colonie : ");
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

                }

            }catch(InputMismatchException e){
                System.out.println("Vous avez taper un mauvais caractère !");
                colonie.getSc().nextLine();

            }
        }

    }

}
