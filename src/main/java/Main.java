import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Entrez le nom de la colonie : ");
        String nom = sc.nextLine();
        Colonie colonie = new Colonie(nom);
        colonie.init();
        colonie.menu1();
        System.out.println("Commençons la répartition des ressources entre colons !");
        colonie.partageRessources();
        colonie.menu2();

    }
}
