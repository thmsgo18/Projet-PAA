package code;

import code.exception.*;


import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Classe permettant d'exécuter le programme.
 */
public class Main {

    /**
     * Cette classe étant static, nous n'avons pas de constructeur.
     */
    public Main(){}

    /**
     * Méthode permettant de lancer l'exécution du programme.
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

        String cheminFichier = "src/main/fichierTXT/config.txt";

        try{
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
            System.err.println("ERREUR : Le fichier n'existe pas");
            System.err.println("La colonie va être créer manuellement");

            //System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
            Menu.menu1(colonie);
        }
        System.out.println("**********************************************************************************************");



        sc.close();
    }
}
