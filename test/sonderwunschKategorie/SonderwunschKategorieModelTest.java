package sonderwunschKategorie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import business.DatabaseConnector;
import business.sonderwunschKategorie.SonderwunschKategorie;
import business.sonderwunschKategorie.SonderwunschKategorieModel;

public class SonderwunschKategorieModelTest {

    private DatabaseConnector dbConnector;
    private SonderwunschKategorieModel sonderwunschKategorieModel;
    private ObjectId sonderwunschKategorieId;

    @Before
    public void setUp() {
        dbConnector = DatabaseConnector.getInstance();
        sonderwunschKategorieModel = SonderwunschKategorieModel.getInstance(dbConnector);
    }

    @AfterEach
    public void tearDown() {
    	sonderwunschKategorieModel.deleteSonderwunschKategorie(sonderwunschKategorieId);
    }

    @Test
    public void testAddAndGetSonderwunschKategorie() {
        String kategorieName = "TestKategorie";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(kategorieName);

        sonderwunschKategorieId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);

        assertNotNull(sonderwunschKategorieId);

        SonderwunschKategorie retrievedCategory = sonderwunschKategorieModel.getSonderwunschKategorieById(sonderwunschKategorieId);
        assertNotNull(retrievedCategory);
        assertEquals(kategorieName, retrievedCategory.getName());
    }

    @Test
    public void testUpdateAndDeleteSonderwunschKategorie() {
        String oldKategorieName = "OldKategorie";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(oldKategorieName);

        sonderwunschKategorieId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);

        assertNotNull(sonderwunschKategorieId);

        // Update
        String newKategorieName = "NewKategorie";
        sonderwunschKategorie.setName(newKategorieName);
        assertTrue(sonderwunschKategorieModel.updateSonderwunschKategorie(sonderwunschKategorieId, sonderwunschKategorie));

        // Verify update
        SonderwunschKategorie updatedCategory = sonderwunschKategorieModel.getSonderwunschKategorieById(sonderwunschKategorieId);
        assertNotNull(updatedCategory);
        assertEquals(newKategorieName, updatedCategory.getName());

        // Delete
        assertTrue(sonderwunschKategorieModel.deleteSonderwunschKategorie(sonderwunschKategorieId));

        // Verify deletion
        assertNull(sonderwunschKategorieModel.getSonderwunschKategorieById(sonderwunschKategorieId));
    }

    @Test
    public void testGetAllSonderwunschKategorie() {
        String kategorieName1 = "Kategorie1";
        String kategorieName2 = "Kategorie2";

        SonderwunschKategorie kategorie1 = new SonderwunschKategorie(kategorieName1);
        SonderwunschKategorie kategorie2 = new SonderwunschKategorie(kategorieName2);

        sonderwunschKategorieId = sonderwunschKategorieModel.addSonderwunschKategorie(kategorie1);
        ObjectId sonderwunschKategorieId2 = sonderwunschKategorieModel.addSonderwunschKategorie(kategorie2);

        // Get all categories
        List<SonderwunschKategorie> categories = sonderwunschKategorieModel.getAllSonderwunschKategorie();
        assertNotNull(categories);
        assertEquals(2, categories.size());

        // Verify the retrieved categories
        SonderwunschKategorie retrievedCategory1 = categories.get(0);
        assertEquals(kategorieName1, retrievedCategory1.getName());

        SonderwunschKategorie retrievedCategory2 = categories.get(1);
        assertEquals(kategorieName2, retrievedCategory2.getName());
        
        sonderwunschKategorieModel.deleteSonderwunschKategorie(sonderwunschKategorieId2);
    }

    @Test
    public void testGetSonderwunschKategorieByName() {
        String kategorieName = "Testkategorie";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(kategorieName);

        sonderwunschKategorieId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);

        // Get category by name
        SonderwunschKategorie retrievedCategory = sonderwunschKategorieModel.getSonderwunschKategorieByName(kategorieName);
        assertNotNull(retrievedCategory);
        assertEquals(kategorieName, retrievedCategory.getName());
    }

}
