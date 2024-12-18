package code;

import exception.ColonNonPresentDansColonieException;
import exception.MemeColonException;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

/**
 * Cette classe, contenant que des méthodes statics, permet de gérer les différents algorithmes de repartition des Ressources aux Colons.
 */
public class Algo {
    private static LinkedHashMap<Colon,Ressource> meilleurSolution;
    private static int meilleurCout;

    /**
     * Cette classe étant static, nous n'avons pas de constructeur.
     */
    public Algo(){}

    /**
     * Cette méthode permet de faire une première affectation des Ressources aux Colons.
     * Cet algorithme permet d'affecter la première Ressource d'un Colon, si cette dernière et disponible. Si cela n'est pas possible l'algorithme passe à la Ressource préférée suivante et ainsi de suite.
     *
     * @param c de type Colonie.
     */
    public static void SolutionNaif(Colonie c){
        int i = 0;
        int fin = c.getColons().get(i).getPreferencesRessources().size();
        while(i<fin){
            for(Colon colon : c.getColons()){
                for(Ressource r : c.getRessources()){
                    if((r.equals(colon.getPreferencesRessources().get(i)) && r.getDisponibilite() && !(colon.isAttribue()))){
                        r.setDisponibilite(false);
                        colon.setRessource(r);
                        colon.setAttribue(true);
                        colon.setPosRessource(i);
                    }
                }
            }
            i++;
        }
    }

    /**
     * Cette méthode permet de tester tous les échanges possibles entre un Colon passé en paramètre et ses voisins. Si le nombre de jaloux diminue, nous conservons cette attribution des Ressources.
     *
     * @param colon de type Colon indiquant le Colon central sur lequel nous allons tester les échanges.
     * @param colonie de type Colonie.
     * @param affectation de type int indiquant le nombre d'affectations avant de l'appel de cette méthode.
     */
    public static void testEchange(Colon colon, Colonie colonie, int affectation){
        int affect = affectation;
        for(Colon c : colon.getPasAmis()){
            try{
                colonie.echangeRessource(c.getNom(), colon.getNom());
                calculAffectation(colonie);
                if(colonie.getAffectation()>affect){
                    colonie.echangeRessource(colon.getNom(), c.getNom());
                    calculAffectation(colonie);
                }else{
                    affect=colonie.getAffectation();
                }
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Cette méthode permet de réduire le nombre de jaloux dans la Colonie.
     * L'algorithme utilisé ici se base sur une heuristique du plus contraint d’abord. Nous allons trier la liste des Colons par ordre décroissant par rapport à leur nombre de relations 'pas amis'.
     * Une fois la liste triée, nous allons boucler sur cette liste et essayer de trouver une meilleure affectation.
     *
     * @param colonie de type Colonie.
     */
    public static void resolutionAutomatique(Colonie colonie){
        SolutionNaif(colonie);
        colonie.trieListColon();
        calculAffectation(colonie);
        for(Colon colon :colonie.getColons()){
            int affectation = colonie.getAffectation();
            System.out.println("Le nombre d'affectation au départ est de :"+ affectation);
            testEchange(colon,colonie,affectation);
        }
        System.out.println("Le nombre d'affectation à la fin est de :"+ colonie.getAffectation());
    }

    /**
     * Cette méthode permet de savoir le nombre de jaloux dans la Colonie.
     *
     * @param colonie de type Colonie.
     * @return de type int indiquant le nombre de jaloux dans la Colonie.
     */
    public static int calculAffectation(Colonie colonie){
        int res = 0;
        boolean jaloux;
        for(Colon c  : colonie.getColons()) {
            jaloux = false;
            List<Colon> pasAmis = c.getPasAmis();
            for(Colon c1 : pasAmis){
                // On vérifie si c préfère la ressource de c1 à la sienne
                // Ici, vous utilisiez getPosRessource() et la liste de préférences.
                // Assurez-vous que c.getPosRessource() est correctement mis à jour.
                // On peut vérifier la préférence en utilisant directement l'ordre dans preferences.
                for(int i = 0; i<c.getPosRessource();i++) {
                    if (c1.getRessource()!=null && c1.getRessource().equals(c.getPreferencesRessources().get(i))) {
                        jaloux = true;
                    }
                }
            }
            if(jaloux){
                res++;
            }
        }
        colonie.setAffectation(res);
        return res;
    }

    /**
     * Cette méthode applique l'algorithme du recuit simulé pour améliorer la répartition des Ressources aux Colons et réduire le nombre de Colons jaloux.
     *
     * @param colonie de type Colonie.
     * @param k de type int indiquant le nombre l'essaie que la méthode à pour trouver une meilleure affectation.
     * @throws ColonNonPresentDansColonieException dans le cas où un colon n'est pas présent dans la Colonie.
     * @throws MemeColonException dans le cas où Colon1 et Colon2 sont le même Colon.
     */
    public static void RecuitSimule(Colonie colonie, int k) throws ColonNonPresentDansColonieException, MemeColonException {
        SolutionNaif(colonie);
        int cout = calculAffectation(colonie);
        LinkedHashMap<Colon, Ressource> solution = affectationCourant(colonie);
        double temperature = 100.0;
        double temperatureMin = 0.0001;
        double alpha = 0.99;
        Random random = new Random();

        meilleurCout = cout;
        meilleurSolution = solution;

        System.out.println("**************************  Voici l'affectation en utilisant l'algorithme naïf  **************************");
        afficheDico();
        System.out.println("Le nombre de jaloux dans la colonie est de : "+meilleurCout);

        while (temperature > temperatureMin) {
            for (int i = 0; i < k; i++) {

                int indColon1 = random.nextInt(colonie.getColons().size());
                int indColon2 = random.nextInt(colonie.getColons().size());
                while(indColon2 == indColon1){
                    indColon2 = random.nextInt(colonie.getColons().size());
                }
                Colon c1 = colonie.getColons().get(indColon1);
                Colon c2 = colonie.getColons().get(indColon2);
                colonie.echangeRessource(c1.getNom(), c2.getNom());
                int nouveauCout = calculAffectation(colonie);

                if (nouveauCout <= cout) {
                    // Accepter la nouvelle solution
                    cout = nouveauCout;
                    solution = affectationCourant(colonie);

                    if (nouveauCout <= meilleurCout) {
                        meilleurCout = nouveauCout;
                        meilleurSolution = solution;

                    }
                } else {
                    // Accepter avec une probabilité
                    int delta = nouveauCout - cout;
                    double p = Math.exp(-delta / temperature);
                    if (random.nextDouble() < p) {
                        cout = nouveauCout;
                        solution = affectationCourant(colonie);

                    } else {
                        colonie.echangeRessource(c2.getNom(), c1.getNom());
                    }
                }
            }
            // Réduire la température
            temperature *= alpha;
        }

        reaffectationNouvSolution(colonie);

        System.out.println("\nVoici l'affectation finale : ");
        colonie.afficherObjets();
        System.out.println("\n-> Le nombre de colons jaloux dans la colonie est de " + meilleurCout);

    }

    /**
     * Cette méthode permet d'avoir l'affectation des Ressources aux Colons sous forme d'une LinkedHashMap.
     *
     * @param colonie de type Colonie.
     * @return de type LinkedHashMap.
     */
    private static LinkedHashMap<Colon,Ressource> affectationCourant(Colonie colonie){
        LinkedHashMap<Colon,Ressource> affectCourant = new LinkedHashMap<>();
        for(Colon c : colonie.getColons()){
            affectCourant.put(c,c.getRessource());
        }
        return affectCourant;
    }

    /**
     * Cette méthode permet d'afficher l'affectation des Ressources aux Colons de la LinkedHashMap de la meilleure solution.
     */
    private static void afficheDico(){
        for(Colon c : meilleurSolution.keySet()){
            System.out.println(c.getNom()+" : "+meilleurSolution.get(c).getNomRessource());
        }
    }

    /**
     * Cette méthode permet d'afficher l'affectation des Ressources aux Colons de la LinkedHashMap passée en paramètre.
     *
     * @param sol de type LinkedHashMap.
     */
    private static void afficheDico(LinkedHashMap<Colon,Ressource> sol){
        for(Colon c :sol.keySet()){
            System.out.println(c.getNom()+" : "+c.getRessource().getNomRessource());
        }

        for (Colon c : meilleurSolution.keySet()) {
            Ressource r = meilleurSolution.get(c);
            c.setRessource(r);
            c.setAttribue(true);
            if (r != null) {
                r.setDisponibilite(false);
            }
        }
    }

    /**
     * Cette méthode permet d'affecter les Ressources aux Colons en fonction de l'affectation définie dans la meilleure affectation sous forme de LinkedHashMap.
     *
     * @param colonie de type Colonie.
     */
    public static void reaffectationNouvSolution(Colonie colonie) {
        for(Ressource r : colonie.getRessources()){
            r.setDisponibilite(true);
        }
        for (Colon c : colonie.getColons()) {
            c.setRessource(null);
            c.setAttribue(false);

        }
        for(Colon c : colonie.getColons()){
            c.setRessource(meilleurSolution.get(c));
            c.setAttribue(true);
            c.getRessource().setDisponibilite(false);

        }
    }


}
