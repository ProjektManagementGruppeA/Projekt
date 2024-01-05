package sonderwunsch;

import static org.junit.jupiter.api.Assertions.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.sonderwunsch.Sonderwunsch;

class SonderwunschTest {
	
	private Sonderwunsch sonderwunsch;

	@BeforeEach
	void setUp() throws Exception {
		ObjectId kategorieId = new ObjectId();
		String beschreibung = "Testbeschreibung";
		Integer preis = 100;
		
		sonderwunsch = new Sonderwunsch(kategorieId, beschreibung, preis);
	}

	@Test
    public void testGetId() {
        assertNull(sonderwunsch.getId());
    }

    @Test
    public void testSetId() {
        ObjectId id = new ObjectId();
        sonderwunsch.setId(id);
        assertEquals(id, sonderwunsch.getId());
    }

    @Test
    public void testGetKategorieId() {
        ObjectId kategorieId = sonderwunsch.getKategorieId();
        assertNotNull(kategorieId);
    }

    @Test
    public void testSetKategorieId() {
        ObjectId newKategorieId = new ObjectId();
        sonderwunsch.setKategorieId(newKategorieId);
        assertEquals(newKategorieId, sonderwunsch.getKategorieId());
    }

    @Test
    public void testGetBeschreibung() {
        assertEquals("Testbeschreibung", sonderwunsch.getBeschreibung());
    }

    @Test
    public void testSetBeschreibung() {
        String newBeschreibung = "Neue Testbeschreibung";
        sonderwunsch.setBeschreibung(newBeschreibung);
        assertEquals(newBeschreibung, sonderwunsch.getBeschreibung());
    }

    @Test
    public void testGetPreis() {
        assertEquals(Integer.valueOf(100), sonderwunsch.getPreis());
    }

    @Test
    public void testSetPreis() {
        Integer newPreis = 200;
        sonderwunsch.setPreis(newPreis);
        assertEquals(newPreis, sonderwunsch.getPreis());
    }
	

}
