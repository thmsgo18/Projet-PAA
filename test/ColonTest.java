import code.Colon;
import code.Ressource;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.discovery.SelectorResolver;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ColonTest {
    @Test
    public void addPasAmisTest() {
        Colon colon_1 = new Colon("colon_1");
        Colon colon_2 = new Colon("colon_2");
        assertEquals(0, colon_1.getPasAmis().size());
        colon_1.addPasAmis(colon_2);
        assertEquals(1, colon_1.getPasAmis().size());
        assertEquals(colon_2, colon_1.getPasAmis().get(0));
    }

    @Test
    public void recherchePasAmisTest() {
        Colon colon_1 = new Colon("colon_1");
        Colon colon_2 = new Colon("colon_2");
        assertFalse(colon_1.recherchePasAmis(colon_2));
        colon_1.addPasAmis(colon_2);
        assertTrue(colon_1.recherchePasAmis(colon_2));
    }

    @Test
    public void getPosRessourceTest() {
        Colon colon_1 = new Colon("colon_1");
        Colon colon_2 = new Colon("colon_2");
        Ressource ressource_1 = new Ressource("ressource_1");
        Ressource ressource_2 = new Ressource("ressource_2");
        ArrayList<Ressource> listePref = new ArrayList<Ressource>();
        assertEquals(0, colon_1.getPosRessource());
        listePref.add(ressource_1);
        listePref.add(ressource_2);
        colon_1.setPreferencesRessources(listePref);
        colon_1.setRessource(ressource_2);

        assertEquals(1, colon_1.getPosRessource());
    }
}
