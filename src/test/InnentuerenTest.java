package test;
import business.sonderwunsch.*;
import static org.junit.Assert.*;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class InnentuerenTest {
	ObjectId kategorieId;
	Sonderwunsch sonderwunsch;
	@Before
	public void setUp() throws Exception {
	 kategorieId = new ObjectId("60c7261b6a4c07e6d651b402");
	 sonderwunsch = new Sonderwunsch(kategorieId, "Test Beschreibung", 100);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateSonderwunsch() {
		assertEquals(kategorieId, sonderwunsch.getKategorieId());
		assertEquals("Test Beschreibung", sonderwunsch.getBeschreibung());
		assert(100== sonderwunsch.getPreis());

	}

}
