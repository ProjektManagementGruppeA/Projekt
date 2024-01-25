package kundeSonderwunsch;

import static org.junit.jupiter.api.Assertions.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import business.kundeSonderwunsch.KundeSonderwunsch;

public class KundeSonderwunschTest {

    @Test
    public void testConstructorAndGetters() {
        ObjectId kundeId = new ObjectId();
        ObjectId sonderwunschId = new ObjectId();
        int anzahl = 3;

        KundeSonderwunsch kundeSonderwunsch = new KundeSonderwunsch(kundeId, sonderwunschId, anzahl);

        assertEquals(kundeId, kundeSonderwunsch.getKundeId());
        assertEquals(sonderwunschId, kundeSonderwunsch.getSonderwunschId());
        assertEquals(anzahl, kundeSonderwunsch.getAnzahl());
    }

    @Test
    public void testSetters() {
        KundeSonderwunsch kundeSonderwunsch = new KundeSonderwunsch(null, null, 0);

        ObjectId newKundeId = new ObjectId();
        kundeSonderwunsch.setKundeId(newKundeId);
        assertEquals(newKundeId, kundeSonderwunsch.getKundeId());

        ObjectId newSonderwunschId = new ObjectId();
        kundeSonderwunsch.setSonderwunschId(newSonderwunschId);
        assertEquals(newSonderwunschId, kundeSonderwunsch.getSonderwunschId());

        int newAnzahl = 5;
        kundeSonderwunsch.setAnzahl(newAnzahl);
        assertEquals(newAnzahl, kundeSonderwunsch.getAnzahl());
    }
}
