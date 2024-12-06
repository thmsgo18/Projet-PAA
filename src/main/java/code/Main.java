package code;

import code.exception.*;


import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();

        String cheminFichier = "/main/fichierTXT/config_error14.txt";
        Colonie colonie = new Colonie(nom);

        try{
            Fichier.init2(cheminFichier, colonie);
            Menu.menuSolutionAuto(colonie);
        }catch(ParamException |
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
            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
            Menu.menu1(colonie);
        }



    }
}
