package code;

import code.exception.*;

import java.io.*;
import java.util.StringTokenizer;

/**
 * Cette classe, contenant que des méthodes static, permet de gérer la partie fichier du programme
 */
public class Fichier {
    /**
     * Cette classe étant static, nous n'avons pas de constructeur.
     */
    public Fichier() {}

    /**
     * Cette méthode static permet de créer une colonie à partir d'un fichier texte.
     *
     * @param cheminFichier de type String permettant d'avoir le chemin du fichier texte à partir duquel nous allons construire la colonie.
     * @param colonie de type Colonie.
     * @throws IOException pour les erreurs d'accès au fichier.
     * @throws ParamException pour les erreurs au niveau des paramètres des commandes dans le fichier.
     * @throws SyntaxeException pour les erreurs de syntaxe dans le fichier.
     * @throws ColonDejaDansColonieException pour le cas où nous voulons ajouter un colon déjà présent dans la colonie.
     * @throws RessourceDejaDansColonieException pour le cas où nous voulons ajouter une ressource déjà présente dans la colonie.
     * @throws MemeColonException pour le cas où nous voulons créer une relation 'se déteste' entre deux fois le même colon.
     * @throws ColonNonPresentDansColonieException pour le cas où le colon n'est pas présent dans la colonie.
     * @throws ColonDejaDansLaRelationException pour le cas où la relation existe déjà entre deux colons.
     * @throws RessourceManquanteException pour le cas où il manque une ou plusieurs ressources dans la liste de préférence d'un colon.
     * @throws RessourceDoubleException pour le cas où une ressource est écrite plusieurs fois dans la liste de préférence d'un colon.
     * @throws RessourcePasDansColonieException pour le cas où la ressource n'est pas dans la colonie.
     * @throws NbrColonPasEgaleNbrRessourceException pour le cas où il n'y a pas le même nombre de colons que de ressources.
     * @throws EnsembleListPreferenceColonieIncompleteException pour le cas où un ou plusieurs colons n'ont pas leur liste de préférences renseignée.
     */
    public static void init2(String cheminFichier, Colonie colonie)throws IOException,
            ParamException,
            SyntaxeException,
            ColonDejaDansColonieException,
            RessourceDejaDansColonieException,
            MemeColonException,
            ColonNonPresentDansColonieException,
            ColonDejaDansLaRelationException,
            RessourceManquanteException,
            RessourceDoubleException,
            RessourcePasDansColonieException,
            NbrColonPasEgaleNbrRessourceException,
            EnsembleListPreferenceColonieIncompleteException
    {
        boolean c =true;
        boolean r =true;
        boolean d = true ;
        int nbrColons = 0;
        int nbrRessources = 0;
        int nbrLigne = 1;
        File file = new File(cheminFichier);
        if(!file.exists()){
            throw new IOException();
        }else {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                if(line.charAt(line.length()-1)=='.'){
                    line = line.replace(".","");
                    StringTokenizer tok = new StringTokenizer(line, "( ),");
                    String nomCommande = tok.nextToken();
                    switch (nomCommande) {
                        case "colon":
                            if (c) {
                                if (tok.hasMoreTokens()) {
                                    String nomColon = tok.nextToken();
                                    Colon colon = new Colon(nomColon);
                                    try{
                                        colonie.addColon(colon);
                                        nbrColons++;
                                    }catch(ColonDejaDansColonieException e) {
                                        throw new ColonDejaDansColonieException(e.getMessage()+" Erreur à la ligne : "+ nbrLigne + ".");
                                    }
                                } else {
                                    throw new ParamException("ERREUR : Le paramètre de colon n'est pas définit à la ligne : " + nbrLigne + ".");
                                }
                            } else {
                                throw new SyntaxeException("ERREUR : Le fichier ne respecte pas l'ordre (colon; ressource; déteste; preference) à la ligne :" + nbrLigne + ".");
                            }
                            break;

                        case "ressource":
                            if (r) {
                                c = false;
                                if (tok.hasMoreTokens()) {
                                    String nomRessource = tok.nextToken();
                                    Ressource ressource = new Ressource(nomRessource);
                                    try{
                                        colonie.addRessource(ressource);
                                        nbrRessources++;
                                    }catch(RessourceDejaDansColonieException e) {
                                        throw new RessourceDejaDansColonieException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                    }
                                } else {
                                    throw new ParamException("ERREUR : Le paramètre de ressource n'est pas définit à la ligne :" + nbrLigne + ".");
                                }
                            } else {
                                throw new SyntaxeException("ERREUR : Le fichier ne respecte pas l'ordre (colon; ressource; déteste; preference) à la ligne :" + nbrLigne + ".");
                            }
                            break;

                        case "deteste":
                            if (d) {
                                c = false;
                                r = false;
                                if (tok.hasMoreTokens()) {
                                    String nomColon1 = tok.nextToken();
                                    if(tok.hasMoreTokens()){
                                        String nomColon2 = tok.nextToken();
                                        try{
                                            colonie.ajoutRelation(nomColon1, nomColon2);
                                        }catch(MemeColonException e){
                                            throw new MemeColonException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                        }catch(ColonNonPresentDansColonieException e){
                                            throw new ColonNonPresentDansColonieException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                        }catch(ColonDejaDansLaRelationException e){
                                            throw new ColonDejaDansLaRelationException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                        }
                                    }else{
                                        throw new ParamException("ERREUR : Un paramètre de deteste n'est pas définit à la ligne :" + nbrLigne + ".");
                                    }
                                }else{
                                    throw new ParamException("ERREUR : Un paramètre de deteste n'est pas définit à la ligne :" + nbrLigne + ".");
                                }
                            } else {
                                throw new SyntaxeException("ERREUR : Le fichier ne respecte pas l'ordre (colon; ressource; déteste; preference) à la ligne :" + nbrLigne + ".");
                            }
                            break;

                        case "preferences":
                            c = false;
                            r = false;
                            d = false;
                            String nomColonL = tok.nextToken();
                            StringBuilder stringBuilder = new StringBuilder();
                            int i = 0;
                            while (tok.hasMoreTokens()) {
                                String nomPref = tok.nextToken();
                                stringBuilder.append(nomPref).append(" ");
                                i++;
                            }
                            if (i == nbrRessources) {
                                try{
                                    colonie.ajoutListePref(nomColonL, stringBuilder.toString());
                                }catch(RessourceManquanteException e){
                                    throw new RessourceManquanteException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                }catch(ColonNonPresentDansColonieException e){
                                    throw new ColonNonPresentDansColonieException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                }catch(RessourceDoubleException e){
                                    throw new RessourceDoubleException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                }catch(RessourcePasDansColonieException e){
                                    throw new RessourcePasDansColonieException(e.getMessage()+" Erreur à la ligne : " + nbrLigne + ".");
                                }

                            } else {
                                throw new ParamException("ERREUR : Il n'y a pas le bon nombre de paramètre à la ligne :" + nbrLigne + ".");
                            }
                            break;

                        default:
                            throw new SyntaxeException("ERREUR DANS LA RECONNAISSANCE DE LA COMMANDE, à la ligne :" + nbrLigne + ".");
                    }
                    nbrLigne++;

                }else{
                    throw new SyntaxeException("ERREUR : La ligne " + nbrLigne + " ne se finit pas par un point.");
                }
            }
            if (nbrColons != nbrRessources) {
                throw new NbrColonPasEgaleNbrRessourceException("ERREUR : Il n'y a pas le même nombre de ressources que de colons.");
            }
            if (!colonie.verificationListePref()) {
                throw new EnsembleListPreferenceColonieIncompleteException("ERREUR : La liste de préférences pour chaque colons ne sont pas toutes définies");
            }
            colonie.setCheminFichierConf(cheminFichier);
        }
    }

    /**
     * Cette méthode static permet de sauvegarder l'état de la colonie.
     *
     * @param chemin de type String indiquant le chemin et donc le nom du fichier texte dans lequel nous sauvegarderons.
     * @param colonie de type Colonie.
     * @throws FichierException pour le cas où le fichier de sauvegarde et de configuration est le même
     */
    public static void sauvegarde(String chemin, Colonie colonie)throws FichierException{
        if(chemin.equals(colonie.getCheminFichierConf())){
            throw new FichierException("ERREUR : Le fichier est identique au fichier de configuration.");
        }else{
            try(PrintWriter writer = new PrintWriter(chemin)){
                for(Colon colon : colonie.getColons()){
                    writer.println(colon.getNom() + ":" + colon.getRessource().getNomRessource());
                }
            }catch(IOException e){
                System.out.println(e.getClass().getName()+ "\n" + e.getMessage());
            }
        }
    }

}
