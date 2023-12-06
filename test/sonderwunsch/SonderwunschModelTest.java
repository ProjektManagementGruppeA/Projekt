package sonderwunsch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import business.DatabaseConnector;
import business.sonderwunsch.Sonderwunsch;
import business.sonderwunsch.SonderwunschModel;

class SonderwunschModelTest {
	
	private DatabaseConnector dbConnector;
	private SonderwunschModel sonderwunschModel;

	@BeforeEach
	void setUp() throws Exception {
		dbConnector = DatabaseConnector.getInstance();
		sonderwunschModel = SonderwunschModel.getInstance(dbConnector);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
    public void testAddAndGetSonderwunsch() {
        ObjectId kategorieId = new ObjectId();
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(kategorieId, beschreibung, preis);
        ObjectId sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        assertNotNull(sonderwunschId);

        Sonderwunsch retrievedSonderwunsch = sonderwunschModel.getSonderwunschById(sonderwunschId);
        assertNotNull(retrievedSonderwunsch);
        assertEquals(kategorieId, retrievedSonderwunsch.getKategorieId());
        assertEquals(beschreibung, retrievedSonderwunsch.getBeschreibung());
        assertEquals(preis, retrievedSonderwunsch.getPreis());
    }
	
	@Test
    public void testUpdateAndDeleteSonderwunsch() {
        ObjectId kategorieId = new ObjectId();
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(kategorieId, beschreibung, preis);
        ObjectId sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        assertNotNull(sonderwunschId);

        // Update
        String updatedBeschreibung = "Updated Testbeschreibung";
        sonderwunsch.setBeschreibung(updatedBeschreibung);
        assertTrue(sonderwunschModel.updateSonderwunsch(sonderwunschId, sonderwunsch));

        // Verify update
        Sonderwunsch retrievedSonderwunsch = sonderwunschModel.getSonderwunschById(sonderwunschId);
        assertNotNull(retrievedSonderwunsch);
        assertEquals(updatedBeschreibung, retrievedSonderwunsch.getBeschreibung());

        // Delete
        assertTrue(sonderwunschModel.deleteSonderwunsch(sonderwunschId));

        // Verify deletion
        assertNull(sonderwunschModel.getSonderwunschById(sonderwunschId));
    }

    @Test
    public void testGetSonderwunschByKategorie() {
        ObjectId kategorieId = new ObjectId();
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(kategorieId, beschreibung, preis);
        sonderwunschModel.addSonderwunsch(sonderwunsch);

        List<Sonderwunsch> sonderwunsche = sonderwunschModel.getSonderwunschByKategorie("TestCategory");
        assertNotNull(sonderwunsche);
        assertEquals(0, sonderwunsche.size());

        sonderwunsche = sonderwunschModel.getSonderwunschByKategorie(kategorieId.toHexString());
        assertNotNull(sonderwunsche);
        assertEquals(1, sonderwunsche.size());

        Sonderwunsch retrievedSonderwunsch = sonderwunsche.get(0);
        assertEquals(kategorieId, retrievedSonderwunsch.getKategorieId());
        assertEquals(beschreibung, retrievedSonderwunsch.getBeschreibung());
        assertEquals(preis, retrievedSonderwunsch.getPreis());
    }

    @Test
    public void testGetAllSonderwunsch() {
        List<Sonderwunsch> sonderwunsche = sonderwunschModel.getAllSonderwunsch();
        assertNotNull(sonderwunsche);
        int initialSize = sonderwunsche.size();

        ObjectId kategorieId = new ObjectId();
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(kategorieId, beschreibung, preis);
        sonderwunschModel.addSonderwunsch(sonderwunsch);

        sonderwunsche = sonderwunschModel.getAllSonderwunsch();
        assertNotNull(sonderwunsche);
        assertEquals(initialSize + 1, sonderwunsche.size());
    }

    @Test
    public void testGetSonderwunschIdsByKategorieId() {
        ObjectId kategorieId = new ObjectId();
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(kategorieId, beschreibung, preis);
        ObjectId sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        List<ObjectId> sonderwunschIds = sonderwunschModel.getSonderwunschIdsByKategorieId(kategorieId);
        assertNotNull(sonderwunschIds);
        assertEquals(1, sonderwunschIds.size());
        assertEquals(sonderwunschId, sonderwunschIds.get(0));
    }

}
