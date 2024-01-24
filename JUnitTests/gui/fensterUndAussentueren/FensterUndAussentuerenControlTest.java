package gui.fensterUndAussenentueren;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import business.DatabaseConnector;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;
import business.sonderwunsch.Sonderwunsch;

class FensterUndAussentuerenControlTest {
	static DatabaseConnector dbc;
	static FensterUndAussentuerenControl fuaControl;
	
	static Kunde testKunde;
	static KundeModel kundeModel;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		
		dbc = DatabaseConnector.getInstance();
		kundeModel = KundeModel.getInstance(dbc);
		
		fuaControl = new FensterUndAussentuerenControl(kundeModel);
		
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@Test
	void testAddKundenSonderwunsch() {
		
		// Model ist bereits getestet, hier wird eigentlich nur ein Funktionsaufruf getestet...
		ObjectId objIdsw1 = new ObjectId("999999999999");
		Sonderwunsch sw1 = new Sonderwunsch(objIdsw1, "beschreibung", 500);
		ObjectId objIdkunde = new ObjectId("999999999999");
		
		fuaControl.addKundenSonderwunsch(objIdkunde, objIdsw1, 0);
		List<KundeSonderwunsch> kundenWuensche = fuaControl.getKundeSonderwunsch(objIdkunde);
		
		// Kontrolliere Liste
		Boolean containsItem=false;
		for(KundeSonderwunsch ksw:kundenWuensche) {
			if(ksw.getSonderwunschId() == sw1.getId()) {
				containsItem=true;
				break;
			}
		}
		assertTrue(containsItem,"KundenSonderwunsch muss neuen Wunsch enthalten");
			
	}

	@Test
	void testLoadKundenSonderwunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testPruefeKonstellationSonderwuensche() {
		fail("Not yet implemented");
	}

	@Test
	void testGetKundeSonderwunsch() {
		fail("Not yet implemented");
	}

	@Test
	void testLeseFensterUndAussentuerenSonderwuenschePreise() {
		fail("Not yet implemented");
	}

}
