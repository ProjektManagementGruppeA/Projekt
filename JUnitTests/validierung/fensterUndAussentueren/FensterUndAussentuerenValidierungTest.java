package fensterUndAussentueren;



import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import validierung.fensterUndAussentueren.FensterUndAussentuerenValidierung;

class FensterUndAussentuerenValidierungTest {
	
	@Test
	void testHasVorbereitungElektrischeAntriebeEG() {
		assertAll("HasVorbereitungElektrischeAntriebeEG",
				()-> assertFalse(FensterUndAussentuerenValidierung.hasVorbereitungElektrischeAntriebeEG(false)),
				()-> assertTrue(FensterUndAussentuerenValidierung.hasVorbereitungElektrischeAntriebeEG(true))
				);
	}

	@Test
	void testHasVorbereitungElektrischeAntriebeOG() {
		assertAll("HasVorbereitungElektrischeAntriebeOG",
				()-> assertFalse(FensterUndAussentuerenValidierung.hasVorbereitungElektrischeAntriebeOG(false)),
				()-> assertTrue(FensterUndAussentuerenValidierung.hasVorbereitungElektrischeAntriebeOG(true))
				);
	}

	@Test
	void testHasVorbereitungElektrischeAntriebeDG() {
		assertAll("HasVorbereitungElektrischeAntriebeDG",
				()-> assertFalse(FensterUndAussentuerenValidierung.hasVorbereitungElektrischeAntriebeDG(false)),
				()-> assertTrue(FensterUndAussentuerenValidierung.hasVorbereitungElektrischeAntriebeDG(true))
				);
	}

	@Test
	void testValidElektrischeRolladenEG() {
		assertAll("validEleketrischeRolladenEG",
				()-> assertFalse(FensterUndAussentuerenValidierung.validElektrischeRolladenEG(false,false)),
				()-> assertFalse(FensterUndAussentuerenValidierung.validElektrischeRolladenEG(false,true)),
				()-> assertTrue(FensterUndAussentuerenValidierung.validElektrischeRolladenEG(true,false)),
				()-> assertTrue(FensterUndAussentuerenValidierung.validElektrischeRolladenEG(true,true))
				);
	}

	@Test
	void testValidElektrischeRolladenOG() {
		assertAll("validEleketrischeRolladenOG",
				()-> assertFalse(FensterUndAussentuerenValidierung.validElektrischeRolladenOG(false,false)),
				()-> assertFalse(FensterUndAussentuerenValidierung.validElektrischeRolladenOG(false,true)),
				()-> assertTrue(FensterUndAussentuerenValidierung.validElektrischeRolladenOG(true,false)),
				()-> assertTrue(FensterUndAussentuerenValidierung.validElektrischeRolladenOG(true,true))
				);
	}

	@Test
	void testValidElektrischeRolladenDG() {
		assertAll("validEleketrischeRolladenDG",
				()-> assertFalse(FensterUndAussentuerenValidierung.validElektrischeRolladenDG(false,false)),
				()-> assertFalse(FensterUndAussentuerenValidierung.validElektrischeRolladenDG(false,true)),
				()-> assertTrue(FensterUndAussentuerenValidierung.validElektrischeRolladenDG(true,false)),
				()-> assertTrue(FensterUndAussentuerenValidierung.validElektrischeRolladenDG(true,true))
				);
	}
	

	
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}


}
