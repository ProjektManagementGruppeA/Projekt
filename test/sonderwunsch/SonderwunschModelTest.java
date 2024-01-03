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
import business.sonderwunschKategorie.SonderwunschKategorie;
import business.sonderwunschKategorie.SonderwunschKategorieModel;

class SonderwunschModelTest {
	
	private DatabaseConnector dbConnector;
	private SonderwunschModel sonderwunschModel;
	private SonderwunschKategorieModel sonderwunschKategorieModel;
	private SonderwunschKategorie sonderwunschKategorie;
	private ObjectId sonderwunschKategorieId;
	private ObjectId sonderwunschId;
	

	@BeforeEach
	void setUp() throws Exception {
		dbConnector = DatabaseConnector.getInstance();
		sonderwunschModel = SonderwunschModel.getInstance(dbConnector);
		sonderwunschKategorieModel = SonderwunschKategorieModel.getInstance(dbConnector);
		sonderwunschKategorie = new SonderwunschKategorie("test");
		sonderwunschKategorieId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);
	}

	@AfterEach
	void tearDown() throws Exception {
		sonderwunschKategorieModel.deleteSonderwunschKategorie(sonderwunschKategorieId);
		sonderwunschModel.deleteSonderwunsch(sonderwunschId);
	}

	@Test
    public void testAddAndGetSonderwunsch() {
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(sonderwunschKategorieId, beschreibung, preis);
        sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        assertNotNull(sonderwunschId);

        Sonderwunsch retrievedSonderwunsch = sonderwunschModel.getSonderwunschById(sonderwunschId);
        assertNotNull(retrievedSonderwunsch);
        assertEquals(sonderwunschKategorieId, retrievedSonderwunsch.getKategorieId());
        assertEquals(beschreibung, retrievedSonderwunsch.getBeschreibung());
        assertEquals(preis, retrievedSonderwunsch.getPreis());
    }
	
	@Test
    public void testUpdateAndDeleteSonderwunsch() {
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(sonderwunschKategorieId, beschreibung, preis);
        sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

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
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(sonderwunschKategorieId, beschreibung, preis);
        sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        List<Sonderwunsch> sonderwunsche = sonderwunschModel.getSonderwunschByKategorie("test");
        assertNotNull(sonderwunsche);
        assertEquals(1, sonderwunsche.size());

        Sonderwunsch retrievedSonderwunsch = sonderwunsche.get(0);
        assertEquals(sonderwunschKategorieId, retrievedSonderwunsch.getKategorieId());
        assertEquals(beschreibung, retrievedSonderwunsch.getBeschreibung());
        assertEquals(preis, retrievedSonderwunsch.getPreis());
    }

    @Test
    public void testGetAllSonderwunsch() {
        List<Sonderwunsch> sonderwunsche = sonderwunschModel.getAllSonderwunsch();
        assertNotNull(sonderwunsche);
        int initialSize = sonderwunsche.size();

        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(sonderwunschKategorieId, beschreibung, preis);
        sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        sonderwunsche = sonderwunschModel.getAllSonderwunsch();
        assertNotNull(sonderwunsche);
        assertEquals(initialSize + 1, sonderwunsche.size());
    }

    @Test
    public void testGetSonderwunschIdsByKategorieId() {
        String beschreibung = "Testbeschreibung";
        Integer preis = 100;

        Sonderwunsch sonderwunsch = new Sonderwunsch(sonderwunschKategorieId, beschreibung, preis);
        sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);

        List<ObjectId> sonderwunschIds = sonderwunschModel.getSonderwunschIdsByKategorieId(sonderwunschKategorieId);
        assertNotNull(sonderwunschIds);
        assertEquals(1, sonderwunschIds.size());
        assertEquals(sonderwunschId, sonderwunschIds.get(0));
    }

}
