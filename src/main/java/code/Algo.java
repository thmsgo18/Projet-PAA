package code;

import code.exception.ColonNonPresentDansColonieException;
import code.exception.MemeColonException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

public class Algo {


    private static LinkedHashMap<Colon,Ressource> meilleurSolution;
    private static int meilleurCout;

    public static void SolutionNaif(Colonie c){
        System.out.println("salut");
        System.out.println("***********************Debut de l'algorithme naïf***********************");
        int i = 0;
        int fin = c.getColons().get(i).getPreferencesRessource().size();
        while(i<fin){
            for(Colon colon : c.getColons()){
                for(Ressource r : c.getRessources()){
                    if((r.equals(colon.getPreferencesRessource().get(i)) && r.getDisponibilite() && !(colon.isAttribue()))){
                        r.setDisponibilite(false);
                        colon.setRessource(r);
                        colon.setAttribue(true);
                        colon.setPosRessource(i);
                        System.out.println("Position de la ressource attribuée à "+colon.getNom()+" : "+colon.getPosRessource());
                    }
                }
            }
            i++;
        }
        System.out.println("***********************Fin de l'algorihtme naïf***********************");
    }

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

    public static void resolutionAutomatique2(Colonie colonie){
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
                    if (c1.getRessource()!=null && c1.getRessource().equals(c.getPreferencesRessource().get(i))) {
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

    public static void RecuitSimule(Colonie colonie, int k) throws ColonNonPresentDansColonieException, MemeColonException {
        SolutionNaif(colonie);
        int cout = calculAffectation(colonie);
        LinkedHashMap<Colon, Ressource> solution = affectationCourant(colonie);
        double temperature = 100.0;
        double temperatureMin = 0.01;
        double alpha = 0.95;
        Random random = new Random();

        meilleurCout = cout;
        meilleurSolution = solution;

        System.out.println("**************************Voici la meilleur affectation pour l'instant**************************");
        afficheDico();
        System.out.println("Le nombre de jaloux dans la colonie est de : "+meilleurCout);

        while (temperature > temperatureMin) {
            for (int i = 0; i < k; i++) {

                int indColon1 = random.nextInt(colonie.getColons().size());
                Colon c1 = colonie.getColons().get(indColon1);
                List<Colon> pasAmis = c1.getPasAmis();
                if (pasAmis.isEmpty()) continue;
                Colon c2 = pasAmis.get(random.nextInt(pasAmis.size()));
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
        System.out.println("Affichage avant affectation final :");
        afficheDico();
        System.out.println("Le nombre de jaloux : "+meilleurCout);
        System.out.println("Le nombre de jaloux : "+calculAffectation(colonie));
        colonie.afficherObjets();

        reaffectationNouvSolution(colonie);

        System.out.println("Voici l'affectation final : ");
        colonie.afficherObjets();
        System.out.println("Le nombre de jaloux : "+meilleurCout);


    }


    private static LinkedHashMap<Colon,Ressource> affectationCourant(Colonie colonie){
        LinkedHashMap<Colon,Ressource> affectCourant = new LinkedHashMap<>();
        for(Colon c : colonie.getColons()){
            affectCourant.put(c,c.getRessource());
        }
        return affectCourant;
    }


    private static void afficheDico(){
        for(Colon c : meilleurSolution.keySet()){
            System.out.println(c.getNom()+" : "+meilleurSolution.get(c).getNomRessource());
        }
    }

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

    public static void reaffectationNouvSolution(Colonie colonie) {
        for(Ressource r : colonie.getRessources()){
            r.setDisponibilite(true);
        }
        for (Colon c : colonie.getColons()) {
            c.setRessource(null);
            c.setAttribue(false);

        }
    }


}
