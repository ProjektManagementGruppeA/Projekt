package sonderwunschKategorie;

import static org.junit.jupiter.api.Assertions.*;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;

import business.sonderwunschKategorie.SonderwunschKategorie;

public class SonderwunschKategorieTest {

    @Test
    public void testConstructorAndGetters() {
        String name = "TestKategorie";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(name);

        assertEquals(name, sonderwunschKategorie.getName());
    }

    @Test
    public void testSetters() {
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie("OldName");

        ObjectId newId = new ObjectId();
        sonderwunschKategorie.setId(newId);
        assertEquals(newId, sonderwunschKategorie.getId());

        String newName = "NewName";
        sonderwunschKategorie.setName(newName);
        assertEquals(newName, sonderwunschKategorie.getName());
    }

}
