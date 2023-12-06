package kundeSonderwunsch;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import business.DatabaseConnector;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;

public class KundeSonderwunschModelTest {

    private DatabaseConnector dbConnector;
    private KundeSonderwunschModel kundeSonderwunschModel;

    @Before
    public void setUp() {
        dbConnector = DatabaseConnector.getInstance();
        kundeSonderwunschModel = KundeSonderwunschModel.getInstance(dbConnector);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetKundeSonderwunsch() throws Exception {
        ObjectId kundeId = new ObjectId();
        ObjectId sonderwunschId = new ObjectId();
        int anzahl = 3;

        ObjectId kundeSonderwunschId = kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId, anzahl);

        assertNotNull(kundeSonderwunschId);

        KundeSonderwunsch retrievedKundeSonderwunsch = kundeSonderwunschModel.getKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId);
        assertNotNull(retrievedKundeSonderwunsch);
        assertEquals(kundeId, retrievedKundeSonderwunsch.getKundeId());
        assertEquals(sonderwunschId, retrievedKundeSonderwunsch.getSonderwunschId());
        assertEquals(anzahl, retrievedKundeSonderwunsch.getAnzahl());
    }

    @Test
    public void testUpdateAndDeleteKundeSonderwunsch() throws Exception {
        ObjectId kundeId = new ObjectId();
        ObjectId sonderwunschId = new ObjectId();
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
        ObjectId kundeId = new ObjectId();
        ObjectId sonderwunschId1 = new ObjectId();
        ObjectId sonderwunschId2 = new ObjectId();
        int anzahl1 = 2;
        int anzahl2 = 3;

        kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId1, anzahl1);
        kundeSonderwunschModel.addKundeSonderwunsch(kundeId, sonderwunschId2, anzahl2);

        // Get Sonderwünsche for the customer
        List<KundeSonderwunsch> kundeSonderwuensche = kundeSonderwunschModel.getKundeSonderwuensche(kundeId);
        assertNotNull(kundeSonderwuensche);
        assertEquals(2, kundeSonderwuensche.size());

        // Verify the retrieved Sonderwünsche
        KundeSonderwunsch retrievedSonderwunsch1 = kundeSonderwuensche.get(0);
        assertEquals(kundeId, retrievedSonderwunsch1.getKundeId());
        assertEquals(sonderwunschId1, retrievedSonderwunsch1.getSonderwunschId());
        assertEquals(anzahl1, retrievedSonderwunsch1.getAnzahl());

        KundeSonderwunsch retrievedSonderwunsch2 = kundeSonderwuensche.get(1);
        assertEquals(kundeId, retrievedSonderwunsch2.getKundeId());
        assertEquals(sonderwunschId2, retrievedSonderwunsch2.getSonderwunschId());
        assertEquals(anzahl2, retrievedSonderwunsch2.getAnzahl());
    }

}
