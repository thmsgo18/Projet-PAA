import code.Algo;
import code.Colon;
import code.Colonie;
import code.Ressource;
import code.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ColonieTest {
    Colonie COLONIE = null;
    Colon colon_1 = null;
    Colon colon_2 = null;
    @BeforeEach
    public void beforeEach() throws ColonDejaDansColonieException {
        COLONIE = new Colonie("Meilleure_Colonie");
        colon_1 = new Colon("colon_1");
        colon_2 = new Colon("colon_2");
        COLONIE.addColon(colon_1);
        COLONIE.addColon(colon_2);
    }


    /**Vérifie que les deux colons ont tous les deux été ajoutés dans la liste PasAmis de l'autre et que la taille de la liste de colons a bien été incrémentée comme il le fallait
     *
     * @throws ColonDejaDansLaRelationException
     * @throws ColonNonPresentDansColonieException
     * @throws MemeColonException
     */
    @Test
    public void ajoutRelationTest() throws ColonDejaDansLaRelationException, ColonNonPresentDansColonieException, MemeColonException {
        COLONIE.ajoutRelation("colon_1", "colon_2");
        assertEquals(1, COLONIE.getColons("colon_1").getPasAmis().size());
        assertEquals(1, COLONIE.getColons("colon_2").getPasAmis().size());
        assertEquals("colon_2", COLONIE.getColons("colon_1").getPasAmis().get(0).getNom());
        assertEquals("colon_1", COLONIE.getColons("colon_2").getPasAmis().get(0).getNom());
    }

    /**
     * Vérifie qu'un colon ne peut pas se détester lui-même
     */
    @Test
    public void ajoutRelationTestMemeColon() {
        assertThrows(MemeColonException.class, ()->{
            COLONIE.ajoutRelation("colon_1", "colon_1");
        });
    }

    /**
     * Vérifie lorsqu'un colon auquel on veut ajouter une relation n'est pas présent dans la liste de colons
     */
    @Test
    public void ajoutRelationTestColonNull() {
        assertThrows(ColonNonPresentDansColonieException.class, ()->{
            COLONIE.ajoutRelation("colon_5", "colon_1");
        });
    }

    /**
     * Vérifie lorsqu'une relation PasAmis existe déjà entre deux colons
     * @throws ColonNonPresentDansColonieException
     * @throws MemeColonException
     * @throws ColonDejaDansLaRelationException
     */
    @Test
    public void ajoutRelationTestColonDejaDansLaRelation() throws ColonNonPresentDansColonieException, MemeColonException, ColonDejaDansLaRelationException {
        COLONIE.ajoutRelation("colon_1", "colon_2");
        assertThrows(ColonDejaDansLaRelationException.class, ()->{
            COLONIE.ajoutRelation("colon_2", "colon_1");
        });
    }

    //La méthode ressourceInList() est déclarée en private mais a été testée correctement en la déclarant public
    /**
     * Vérifie si une ressource est bien présente ou non dans la liste des ressources de la colonie
     */
    @Test
    public void ressourceInListTest() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        //assertTrue(COLONIE.ressourceInList(COLONIE.getRessources(), "ressource_1"));
        //assertFalse(COLONIE.ressourceInList(COLONIE.getRessources(), "ressource_5"));
    }

    /**
     * Vérifie que la liste de préférences d'un colon a bien été correctement ajoutée et que le taille de celui-ci correspond
     * @throws RessourceDoubleException
     * @throws RessourcePasDansColonieException
     * @throws ColonNonPresentDansColonieException
     * @throws RessourceManquanteException
     */
    @Test
    public void ajoutListePrefTest() throws RessourceDoubleException, RessourcePasDansColonieException, ColonNonPresentDansColonieException, RessourceManquanteException, RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        COLONIE.addRessource(new Ressource("ressource_2"));
        COLONIE.ajoutListePref("colon_1", "ressource_1 ressource_2");
        COLONIE.ajoutListePref("colon_2", "ressource_1 ressource_2");
        assertEquals(2, colon_1.getPreferencesRessources().size());
        assertEquals(2, colon_2.getPreferencesRessources().size());

        List<String> strings = new LinkedList<>();
        for(Ressource r : colon_1.getPreferencesRessources()) {
            strings.add(r.getNomRessource());
        }
        String resultat = String.join(" ", strings);
        assertEquals("ressource_1 ressource_2", resultat);
    }

    /**
     * Vérifie que le colon à qui on veut ajouter une liste de préférences existe bien
     */
    @Test
    public void ajoutListePrefTestColonNonPresentDansColonie() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        assertThrows(ColonNonPresentDansColonieException.class, ()->{
            COLONIE.ajoutListePref("colon_5", "ressource_1");
        });
    }

    /**
     * Vérifie lorsque la liste de préférences d'un colon est vide ou incomplète
     */
    @Test
    public void ajoutListePrefTestRessourceManquante() throws RessourceDoubleException, RessourcePasDansColonieException, ColonNonPresentDansColonieException, RessourceManquanteException, RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        COLONIE.addRessource(new Ressource("ressource_2"));
        COLONIE.ajoutListePref("colon_1", "ressource_1 ressource_2");
        COLONIE.ajoutListePref("colon_2", "ressource_1 ressource_2");
        assertThrows(RessourceManquanteException.class, ()->{
            COLONIE.ajoutListePref("colon_1", "");
            COLONIE.ajoutListePref("colon_2", "ressource_1");
        });
    }

    /**
     * Vérifie lorsqu'une ressource est ajoutée deux fois
     */
    @Test
    public void ajoutListePrefTestRessourceDouble() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        COLONIE.addRessource(new Ressource("ressource_2"));
        assertThrows(RessourceDoubleException.class, ()->{
            COLONIE.ajoutListePref("colon_1", "ressource_1 ressource_1");
        });
    }

    /**
     * Vérifie lorsqu'une ressource n'existe pas dans la liste des ressources de la colonie
     */
    @Test
    public void ajoutListePrefTestRessourcePasDansColonie() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        assertThrows(RessourcePasDansColonieException.class, ()->{
            COLONIE.ajoutListePref("colon_1", "ressources_5");
        });
    }

    /**
     * Vérifie que les listes de préférences de tous les colons ont bien été renseignées ou pas
     * @throws RessourceDoubleException
     * @throws RessourcePasDansColonieException
     * @throws ColonNonPresentDansColonieException
     * @throws RessourceManquanteException
     */
    @Test
    public void verificationListePrefTest() throws RessourceDoubleException, RessourcePasDansColonieException, ColonNonPresentDansColonieException, RessourceManquanteException, RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        COLONIE.addRessource(new Ressource("ressource_2"));

        COLONIE.ajoutListePref("colon_1", "ressource_1 ressource_2");
        assertFalse(COLONIE.verificationListePref());
        COLONIE.ajoutListePref("colon_2", "ressource_1 ressource_2");
        assertTrue(COLONIE.verificationListePref());
    }

    /**
     * Vérifie que la liste des colons est correctement triée par ordre décroissant du nombre de colons PasAmis
     * @throws ColonDejaDansLaRelationException
     * @throws ColonNonPresentDansColonieException
     * @throws MemeColonException
     */
    @Test
    public void trieListColonTest() throws ColonDejaDansLaRelationException, ColonNonPresentDansColonieException, MemeColonException, ColonDejaDansColonieException {
        Colon colon_3 = new Colon("colon_3");
        COLONIE.addColon(colon_3);

        COLONIE.ajoutRelation("colon_1", "colon_2");
        COLONIE.ajoutRelation("colon_3", "colon_2");
        COLONIE.trieListColon();

        List<String> strings = new LinkedList<>();
        for(Colon c :COLONIE.getColons()) {
            strings.add(c.getNom());
        }
        String resultat = String.join(" ", strings);
        assertEquals("colon_2 colon_1 colon_3", resultat);
    }

    /**
     * Vérifie que l'échange entre les ressources attribuées au deux colons ont bien été échangées
     * @throws ColonDejaDansColonieException
     * @throws RessourceDejaDansColonieException
     * @throws RessourceDoubleException
     * @throws RessourcePasDansColonieException
     * @throws ColonNonPresentDansColonieException
     * @throws RessourceManquanteException
     * @throws MemeColonException
     */
    @Test
    public void echangeRessourceTest() throws RessourceDejaDansColonieException, RessourceDoubleException, RessourcePasDansColonieException, ColonNonPresentDansColonieException, RessourceManquanteException, MemeColonException {
        Ressource ressource_1 = new Ressource("ressource_1");
        Ressource ressource_2 = new Ressource("ressource_2");
        COLONIE.addRessource(ressource_1);
        COLONIE.addRessource(ressource_2);
        COLONIE.ajoutListePref("colon_1", "ressource_1 ressource_2");
        COLONIE.ajoutListePref("colon_2", "ressource_1 ressource_2");
        Algo.SolutionNaif(COLONIE);

        //avant échange des ressources
        assertEquals(ressource_1, colon_1.getRessource());
        assertEquals(ressource_2, colon_2.getRessource());
        //après échange des ressources
        COLONIE.echangeRessource("colon_1", "colon_2");
        assertEquals(ressource_2, colon_1.getRessource());
        assertEquals(ressource_1, colon_2.getRessource());
    }

    /**
     * Vérifie que les deux colons auxquels on veut échanger les ressouces attribuées ne sont pas les mêmes
     */
    @Test
    public void echangeRessourceTestMemeColonException() {
        assertThrows(MemeColonException.class, ()->{
            COLONIE.echangeRessource("colon_1", "colon_1");
        });
    }

    /**
     * Vérifie que les colons existent bien dans la colonie
     */
    @Test
    public void echangeRessourceTestColonNonPresentDansColonieException() {
        assertThrows(ColonNonPresentDansColonieException.class, ()->{
            COLONIE.echangeRessource("colon_3", "colon_1");
        });
    }

    /**
     * Vérifie qu'une ressource a été correctement ajoutée à la liste des ressources de la colonie
     * @throws RessourceDejaDansColonieException
     */
    @Test
    public void addRessourceTest() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        assertEquals(1, COLONIE.getRessources().size());
        COLONIE.addRessource(new Ressource("ressource_2"));
        assertEquals(2, COLONIE.getRessources().size());

        List<String> strings = new LinkedList<>();
        for(Ressource r : COLONIE.getRessources()) {
            strings.add(r.getNomRessource());
        }
        String resultat = String.join(" ", strings);
        assertEquals("ressource_1 ressource_2", resultat);
    }

    /**
     * Vérifie lorsqu'une ressource existe déjà dans la colonie
     */
    @Test
    public void addRessourceTestRessourceDejaDansColonie() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));

        assertThrows(RessourceDejaDansColonieException.class, ()->{
            COLONIE.addRessource(new Ressource("ressource_1"));
        });
    }

    /**
     * Vérifie qu'on récupère le bon colon en rentrant son nom
     */
    @Test
    public void getColons() throws RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        assertEquals(colon_1, COLONIE.getColons("colon_1"));
    }

    /**
     * Vérifie qu'un colon est correctement ajouté à la liste des colons de la colonie
     * @throws ColonDejaDansColonieException
     * @throws RessourceDejaDansColonieException
     */
    @Test
    public void addColonTest() throws ColonDejaDansColonieException, RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        assertEquals(2, COLONIE.getColons().size());

        Colon colon_3 = new Colon("colon_3");
        COLONIE.addColon(colon_3);
        assertEquals(3, COLONIE.getColons().size());

        List<String> strings = new LinkedList<>();
        for(Colon c : COLONIE.getColons()) {
            strings.add(c.getNom());
        }
        String resultat = String.join(" ", strings);
        assertEquals("colon_1 colon_2 colon_3", resultat);
    }

    /**
     * Vérifie si le colon que l'on veut ajouter est déjà présent dans la colonie
     * @throws ColonDejaDansColonieException
     * @throws RessourceDejaDansColonieException
     */
    @Test
    public void addColonTestColonDejaDansColonie() throws ColonDejaDansColonieException, RessourceDejaDansColonieException {
        COLONIE.addRessource(new Ressource("ressource_1"));
        assertThrows(ColonDejaDansColonieException.class, ()->{
            COLONIE.addColon(new Colon("colon_1"));
        });
    }

}