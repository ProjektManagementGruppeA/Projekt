package sonderwunschKategorie;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import business.DatabaseConnector;
import business.sonderwunschKategorie.SonderwunschKategorie;
import business.sonderwunschKategorie.SonderwunschKategorieModel;

public class SonderwunschKategorieModelTest {

    private DatabaseConnector dbConnector;
    private SonderwunschKategorieModel sonderwunschKategorieModel;

    @Before
    public void setUp() {
        dbConnector = DatabaseConnector.getInstance();
        sonderwunschKategorieModel = SonderwunschKategorieModel.getInstance(dbConnector);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddAndGetSonderwunschKategorie() {
        String categoryName = "TestKategorie";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(categoryName);

        ObjectId categoryId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);

        assertNotNull(categoryId);

        SonderwunschKategorie retrievedCategory = sonderwunschKategorieModel.getSonderwunschKategorieById(categoryId);
        assertNotNull(retrievedCategory);
        assertEquals(categoryName, retrievedCategory.getName());
    }

    @Test
    public void testUpdateAndDeleteSonderwunschKategorie() {
        String oldCategoryName = "OldCategory";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(oldCategoryName);

        ObjectId categoryId = sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);

        assertNotNull(categoryId);

        // Update
        String newCategoryName = "NewCategory";
        sonderwunschKategorie.setName(newCategoryName);
        assertTrue(sonderwunschKategorieModel.updateSonderwunschKategorie(categoryId, sonderwunschKategorie));

        // Verify update
        SonderwunschKategorie updatedCategory = sonderwunschKategorieModel.getSonderwunschKategorieById(categoryId);
        assertNotNull(updatedCategory);
        assertEquals(newCategoryName, updatedCategory.getName());

        // Delete
        assertTrue(sonderwunschKategorieModel.deleteSonderwunschKategorie(categoryId));

        // Verify deletion
        assertNull(sonderwunschKategorieModel.getSonderwunschKategorieById(categoryId));
    }

    @Test
    public void testGetAllSonderwunschKategorie() {
        String categoryName1 = "Category1";
        String categoryName2 = "Category2";

        SonderwunschKategorie category1 = new SonderwunschKategorie(categoryName1);
        SonderwunschKategorie category2 = new SonderwunschKategorie(categoryName2);

        sonderwunschKategorieModel.addSonderwunschKategorie(category1);
        sonderwunschKategorieModel.addSonderwunschKategorie(category2);

        // Get all categories
        List<SonderwunschKategorie> categories = sonderwunschKategorieModel.getAllSonderwunschKategorie();
        assertNotNull(categories);
        assertEquals(2, categories.size());

        // Verify the retrieved categories
        SonderwunschKategorie retrievedCategory1 = categories.get(0);
        assertEquals(categoryName1, retrievedCategory1.getName());

        SonderwunschKategorie retrievedCategory2 = categories.get(1);
        assertEquals(categoryName2, retrievedCategory2.getName());
    }

    @Test
    public void testGetSonderwunschKategorieByName() {
        String categoryName = "TestCategory";
        SonderwunschKategorie sonderwunschKategorie = new SonderwunschKategorie(categoryName);

        sonderwunschKategorieModel.addSonderwunschKategorie(sonderwunschKategorie);

        // Get category by name
        SonderwunschKategorie retrievedCategory = sonderwunschKategorieModel.getSonderwunschKategorieByName(categoryName);
        assertNotNull(retrievedCategory);
        assertEquals(categoryName, retrievedCategory.getName());
    }

}
