package code;

import code.exception.ColonieException;
import code.exception.FichierException;


import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();

        String cheminFichier = "src/main/fichierTXT/config_error2.txt";
        Colonie colonie = new Colonie(nom);

        try{
            colonie.init2(cheminFichier);
            colonie.menuSolutionAuto();
        }catch(FichierException | ColonieException e){
            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
        }catch(IOException e){
            System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
            colonie.menu1();
        }
    }
}
