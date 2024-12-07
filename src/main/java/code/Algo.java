package code;

import java.util.List;

public class Algo {
    public static void algoNaif(Colonie c){
        System.out.println("salut");

        System.out.println("***********************Debut de l'algorihtme naïf***********************");
        int i = 0;
        int fin = c.getColons().get(i).getPreferencesRessource().size();
        while(i<fin){
            for(Colon colon :c.getColons()){
                for(Ressource r : c.getRessources()){
                    if((r.equals(colon.getPreferencesRessource().get(i))&&r.getDisponibilite()&& !(colon.isAttribue()))){
                        r.setDisponibilite(false);
                        colon.setRessource(r);
                        colon.setAttribue(true);
                        colon.setPosRessource(i);
                        System.out.println("Position de la ressource attribué à "+colon.getNom()+" : "+colon.getPosRessource());
                    }
                }
            }
            i++;
        }

        System.out.println("***********************Fin de l'algorihtme naïf***********************");

    }

    public static void resolutionAutomatique(Colonie colonie,int k){
        algoNaif(colonie);
        int i = 0;
        while(i<k){

        }


    }


    public static void algoNaif2(Colonie colonie){
        colonie.trieListRessource();
        colonie.trieListColon();
        for(int i=0;i<colonie.getColons().size();i++){
            colonie.getColons().get(i).setRessource(colonie.getRessources().get(colonie.getRessources().size() - (1+i)));
        }
    }

    public static void calculAffectation(Colonie colonie){
        int res = 0;
        boolean jaloux;
        for(Colon c  : colonie.getColons()) {
            // Parcours de tous les colons de la colonie
            jaloux = false;
            List<Colon> pasAmis = c.getPasAmis();
            for(Colon c1 : pasAmis){
                for(int i = 0; i<c.getPosRessource();i++) {
                    if (c1.getRessource().equals(c.getPreferencesRessource().get(i))) {
                        // Jalousie de c1
                        jaloux = true;
                    }
                }
            }
            if(jaloux){
                // Incrémentation du nombre de jaloux
                res++;
            }
        }
        colonie.setAffectation(res);
    }
}
