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

        String cheminFichier = args[0];
        Colonie colonie = new Colonie(nom);

        try{
            colonie.init2(cheminFichier);
            colonie.menuSolutionAuto();
        }catch(FichierException | ColonieException e){
            System.out.println(e.getMessage());
        }catch(IOException e){
            System.out.println(e.getMessage());
            colonie.menu1();
        }
    }
}
