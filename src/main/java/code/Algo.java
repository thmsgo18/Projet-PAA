package code;

import java.util.List;

public class Algo {
    public static void algoNaif(Colonie c){
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

    }

    public static void resolutionAutomatique(Colonie colonie,int k){
        algoNaif(colonie);
        int i = 0;
        while(i<k){

        }


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
        algoNaif(colonie);
        colonie.trieListColon();
        calculAffectation(colonie);
        for(Colon colon :colonie.getColons()){
            int affectation = colonie.getAffectation();
            System.out.println("Le nombre d'affectation au départ est de :"+ affectation);
            testEchange(colon,colonie,affectation);
        }
        System.out.println("Le nombre d'affectation à la fin est de :"+ colonie.getAffectation());
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
