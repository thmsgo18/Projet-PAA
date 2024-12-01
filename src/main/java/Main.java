import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();

        String cheminFichier = "src/main/java/config.txt";
        Colonie colonie = new Colonie(nom);

        if(colonie.verifFichier(cheminFichier)){
            colonie.init2(cheminFichier);
            colonie.menuSolutionAuto();
        }else{
            System.out.println("Erreur de fichier");
            System.out.println("Redirection menu 1");
            colonie.menu1();
        }
    }
}
