import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();
        try{
            String cheminFichier = "src/main/java/config.txt";
            Colonie colonie = new Colonie(nom);
            colonie.init2(cheminFichier);
        } catch (Exception e) {
           System.out.println(e.getMessage());
        }
    }
}
