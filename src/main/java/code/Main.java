package code;
import java.io.IOException;
import java.util.Scanner;

import exception.*;

/**
 * Classe permettant d'exécuter le programme.
 */
public class Main {

    /**
     * Cette classe étant statique, nous n'avons pas de constructeur.
     */
    public Main(){}

    /**
     * Méthode statique permettant de lancer l'exécution du programme.
     *
     * @param args de type String indiquant les paramètres rentrés en ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("\n**********************************************************************************************");
        System.out.println("**********  Bienvenue dans le programme de partage de biens d'une colonie spatiale  **********\n");

        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();
        Colonie colonie = new Colonie(nom);

        if (args.length == 0) {
            Menu.menu1(colonie);
        }else{
            try{
                String cheminFichier = args[0];
                Fichier.init2(cheminFichier, colonie);
                Menu.menuSolutionAuto(colonie);
            } catch(ParamException |
                    SyntaxeException |
                    ColonDejaDansColonieException |
                    RessourceDejaDansColonieException |
                    MemeColonException |
                    ColonNonPresentDansColonieException |
                    ColonDejaDansLaRelationException |
                    RessourceManquanteException |
                    RessourceDoubleException |
                    RessourcePasDansColonieException |
                    NbrColonPasEgaleNbrRessourceException |
                    EnsembleListPreferenceColonieIncompleteException e)
            {
                System.out.println(e.getClass().getName()+ "\n" + e.getMessage());

            }catch(IOException e){
                System.err.println("ERREUR " + e.getClass().getName() + " : Le fichier n'existe pas. La colonie va être créer manuellement");
                Menu.menu1(colonie);
            }
        }

        System.out.println("**********************************************************************************************");

        sc.close();
    }
}
