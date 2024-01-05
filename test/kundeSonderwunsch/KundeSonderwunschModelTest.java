package kundeSonderwunsch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import business.DatabaseConnector;
import business.haustyp.Haustyp;
import business.haustyp.HaustypModel;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;
import business.sonderwunsch.Sonderwunsch;
import business.sonderwunsch.SonderwunschModel;
import business.sonderwunschKategorie.SonderwunschKategorie;
import business.sonderwunschKategorie.SonderwunschKategorieModel;

public class KundeSonderwunschModelTest {

    private DatabaseConnector dbConnector;
    private KundeSonderwunschModel kundeSonderwunschModel;
    private SonderwunschModel sonderwunschModel;
    private SonderwunschKategorieModel sonderwunschKategorieModel;
    private HaustypModel haustypModel;
    private KundeModel kundeModel;
    private SonderwunschKategorie sonderwunschKategorie;
    private Sonderwunsch sonderwunsch;
    private Kunde kunde;
    private Haustyp haustyp;
    private ObjectId kundeId;
    private ObjectId sonderwunschKategorieId;
    private ObjectId sonderwunschId;
    private ObjectId kundeSonderwunschId;

    @Before
    public void setUp() throws Exception {
        dbConnector = DatabaseConnector.getInstance();
        kundeSonderwunschModel = KundeSonderwunschModel.getInstance(dbConnector);
        sonderwunschModel = SonderwunschModel.getInstance(dbConnector);
        sonderwunschKategorieModel = SonderwunschKategorieModel.getInstance(dbConnector);
        haustypModel = HaustypModel.getInstance(dbConnector);
        kundeModel = KundeModel.getInstance(dbConnector);
        haustyp = new Haustyp(999, false);
		haustyp.setId(haustypModel.addHaustyp(haustyp));
		kunde = new Kunde("123", haustyp.getId(), "Hans", "Peter",
				"1234", "Müller@Mustermann.com");
		kundeId = kundeModel.addKunde(kunde);
		sonderwunschKategorie = new SonderwunschKategorie("Test");
		sonderwunschKategorieId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);
		sonderwunsch = new Sonderwunsch(sonderwunschKategorieId, "Dies ist ein Test Produkt", 123);
		sonderwunschId = sonderwunschModel.addSonderwunsch(sonderwunsch);
    }

    @After
    public void tearDown() {
    	kundeModel.deleteKunde(kundeId);
    	haustypModel.deleteHaustyp(haustyp.getId());
    	sonderwunschKategorieModel.deleteSonderwunschKategorie(sonderwunschKategorieId);
    	sonderwunschModel.deleteSonderwunsch(sonderwunschId);
    	kundeSonderwunschModel.deleteKundeSonderwunsch(kundeSonderwunschId);
    }

    @Test
    public void testAddAndGetKundeSonderwunsch() throws Exception {
        int anzahl = 3;

        kundeSonderwunschId = kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId, anzahl);

        assertNotNull(kundeSonderwunschId);

        KundeSonderwunsch retrievedKundeSonderwunsch = kundeSonderwunschModel.getKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId);
        assertNotNull(retrievedKundeSonderwunsch);
        assertEquals(kundeId, retrievedKundeSonderwunsch.getKundeId());
        assertEquals(sonderwunschId, retrievedKundeSonderwunsch.getSonderwunschId());
        assertEquals(anzahl, retrievedKundeSonderwunsch.getAnzahl());
        
        kundeSonderwunschModel.deleteKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId);
    }

    @Test
    public void testUpdateAndDeleteKundeSonderwunsch() throws Exception {
        int anzahl = 3;

        ObjectId kundeSonderwunschId = kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId, anzahl);

        assertNotNull(kundeSonderwunschId);

        // Update
        int updatedAnzahl = 5;
        assertTrue(kundeSonderwunschModel.updateKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId, updatedAnzahl));

        // Verify update
        KundeSonderwunsch retrievedKundeSonderwunsch = kundeSonderwunschModel.getKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId);
        assertNotNull(retrievedKundeSonderwunsch);
        assertEquals(updatedAnzahl, retrievedKundeSonderwunsch.getAnzahl());

        // Delete
        assertTrue(kundeSonderwunschModel.deleteKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId));

        // Verify deletion
        assertNull(kundeSonderwunschModel.getKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId));
    }

    @Test
    public void testGetKundeSonderwuenscheByKunde() throws Exception {
        Sonderwunsch sonderwunsch2 = new Sonderwunsch(sonderwunschKategorieId, "Zweites Testprodukt", 321);
        ObjectId sonderwunschId2 = sonderwunschModel.addSonderwunsch(sonderwunsch2);
        int anzahl1 = 2;
        int anzahl2 = 3;

        kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId, anzahl1);
        kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId2, anzahl2);

        // Get Sonderwünsche for the customer
        List<KundeSonderwunsch> kundeSonderwuensche = kundeSonderwunschModel.getKundeSonderwuensche(kundeId);
        assertNotNull(kundeSonderwuensche);
        assertEquals(2, kundeSonderwuensche.size());

        // Verify the retrieved Sonderwünsche
        KundeSonderwunsch retrievedSonderwunsch1 = kundeSonderwuensche.get(0);
        assertEquals(kundeId, retrievedSonderwunsch1.getKundeId());
        assertEquals(sonderwunschId, retrievedSonderwunsch1.getSonderwunschId());
        assertEquals(anzahl1, retrievedSonderwunsch1.getAnzahl());

        KundeSonderwunsch retrievedSonderwunsch2 = kundeSonderwuensche.get(1);
        assertEquals(kundeId, retrievedSonderwunsch2.getKundeId());
        assertEquals(sonderwunschId2, retrievedSonderwunsch2.getSonderwunschId());
        assertEquals(anzahl2, retrievedSonderwunsch2.getAnzahl());
        
        sonderwunschModel.deleteSonderwunsch(sonderwunschId2);
    }

}
