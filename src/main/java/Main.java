import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();
        try{
            Colonie colonie = new Colonie(nom);
            colonie.menu1();

        } catch (InputMismatchException e) {
           System.out.println(e.getMessage());
        }
    }
}
