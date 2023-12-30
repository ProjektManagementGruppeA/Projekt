package business.kunde;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;

import business.DatabaseConnector;
import business.haustyp.Haustyp;
import business.haustyp.HaustypModel;

@TestMethodOrder(OrderAnnotation.class)
class KundeModelTest {
	public static HaustypModel haustypModel;
	public static KundeModel kundeModel;
	public static Haustyp haustyp;
	public static Kunde kunde;
	
	
	@BeforeAll
	public static void setUp() {
		DatabaseConnector connector = DatabaseConnector.getInstance();
		haustypModel = HaustypModel.getInstance(connector);
		kundeModel = KundeModel.getInstance(connector);
		haustyp = new Haustyp(999, false);
		haustyp.setId(haustypModel.addHaustyp(haustyp));
		kunde = new Kunde("123", haustyp.getId(), "Hans", "Peter",
				"1234", "Müller@Mustermann.com");
		
	}
	
	@Order(1)
	@Test
	void testAddKunde() {
		assertDoesNotThrow(() -> kundeModel.addKunde(kunde));
	}
	
	@Order(2)
	@Test
	void testGetKundeByKundennummer() {
		Kunde testKunde = kundeModel.getKundeByKundennummer("123");
		assertAll("gleicher Kunde",
				() -> assertEquals(kunde.getKundennummer(),testKunde.getKundennummer(),"Kundenummer stimmt nicht überein"),
				() -> assertEquals(kunde.getHaustypId(),testKunde.getHaustypId(),"Haustypid stimmt nicht überein"),
				() -> assertEquals(kunde.getVorname(),testKunde.getVorname(),"Vorname stimmt nicht überein"),
				() -> assertEquals(kunde.getNachname(),testKunde.getNachname(),"Nachname stimmt nicht überein"),
				() -> assertEquals(kunde.getTelefonnummer(),testKunde.getTelefonnummer(),"Telefonnummer stimmt nicht überein"),
				() -> assertEquals(kunde.getEmail(),testKunde.getEmail(),"Email stimmt nicht überein")
				);
	}
	
	@Order(3)
	@Test
	void testGetKundeByHausnummer() {
		Kunde testKunde = kundeModel.getKundeByHausnummer(999);
		assertAll("gleicher Kunde",
				() -> assertEquals(kunde.getKundennummer(),testKunde.getKundennummer(),"Kundenummer stimmt nicht überein"),
				() -> assertEquals(kunde.getHaustypId(),testKunde.getHaustypId(),"Haustypid stimmt nicht überein"),
				() -> assertEquals(kunde.getVorname(),testKunde.getVorname(),"Vorname stimmt nicht überein"),
				() -> assertEquals(kunde.getNachname(),testKunde.getNachname(),"Nachname stimmt nicht überein"),
				() -> assertEquals(kunde.getTelefonnummer(),testKunde.getTelefonnummer(),"Telefonnummer stimmt nicht überein"),
				() -> assertEquals(kunde.getEmail(),testKunde.getEmail(),"Email stimmt nicht überein")
				);
	}

	@Order(4)
	@Test
	void testGetKundeByEmail() {
		Kunde testKunde = kundeModel.getKundeByEmail("Müller@Mustermann.com");
		assertAll("gleicher Kunde",
				() -> assertEquals(kunde.getKundennummer(),testKunde.getKundennummer(),"Kundenummer stimmt nicht überein"),
				() -> assertEquals(kunde.getHaustypId(),testKunde.getHaustypId(),"Haustypid stimmt nicht überein"),
				() -> assertEquals(kunde.getVorname(),testKunde.getVorname(),"Vorname stimmt nicht überein"),
				() -> assertEquals(kunde.getNachname(),testKunde.getNachname(),"Nachname stimmt nicht überein"),
				() -> assertEquals(kunde.getTelefonnummer(),testKunde.getTelefonnummer(),"Telefonnummer stimmt nicht überein"),
				() -> assertEquals(kunde.getEmail(),testKunde.getEmail(),"Email stimmt nicht überein")
				);
	}
	
	@Order(5)
	@Test
	void testUpdateKunde() {
		Kunde testkunde = kundeModel.getKundeByHausnummer(999);
		testkunde.setKundennummer("1");
		assertTrue(kundeModel.updateKunde(testkunde));
		assertNotNull(kundeModel.getKundeByKundennummer("1"));
		testkunde.setKundennummer("123");
		assertTrue(kundeModel.updateKunde(testkunde));
		
		
	}
	
	@Order(6)
	@Test
	void deleteKunde() {
		assertTrue(kundeModel.deleteKundeByHausnummer(999));
		assertFalse(kundeModel.deleteKundeByHausnummer(999));
		assertDoesNotThrow(() -> kundeModel.addKunde(kunde));
	}
	
	@AfterAll
	public static void tearDownAll() {
		kundeModel.deleteKundeByHausnummer(999);
		haustypModel.deleteHaustyp(haustyp.getId());
		
    }

}
