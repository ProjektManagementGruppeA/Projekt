package validierung.kunde;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KundeValidierungTest {

	@Test
	void testIsValidEmail() {
		assertAll("email",
				() -> assertTrue(KundeValidierung.isValidEmail("Hans@Peter.com")),
				() -> assertFalse(KundeValidierung.isValidEmail("")),
				() -> assertFalse(KundeValidierung.isValidEmail("@.")),
				() -> assertFalse(KundeValidierung.isValidEmail("@Peter.com")),
				() -> assertFalse(KundeValidierung.isValidEmail("Hans@peter.")),
				() -> assertFalse(KundeValidierung.isValidEmail("Hans@.de")),
				() -> assertFalse(KundeValidierung.isValidEmail("Hans.Joseph.c"))
		);
				
	}

	@Test
	void testIsValidName() {
		assertAll("name",
				() -> assertTrue(KundeValidierung.isValidName("Hans")),
				() -> assertFalse(KundeValidierung.isValidName("")),
				() -> assertFalse(KundeValidierung.isValidName(".")),
				() -> assertFalse(KundeValidierung.isValidName("1")),
				() -> assertFalse(KundeValidierung.isValidName("Hans1")),
				() -> assertFalse(KundeValidierung.isValidName("Hans Peter")),
				() -> assertFalse(KundeValidierung.isValidName("!"))
		);
	}

	@Test
	void testIsValidPhoneNumber() {
		assertAll("phonenumber",
				() -> assertTrue(KundeValidierung.isValidPhoneNumber("4900756")),
				() -> assertTrue(KundeValidierung.isValidPhoneNumber("07566768")),
				() -> assertTrue(KundeValidierung.isValidPhoneNumber("+4910756")),
				() -> assertFalse(KundeValidierung.isValidPhoneNumber("")),
				() -> assertFalse(KundeValidierung.isValidPhoneNumber(".")),
				() -> assertFalse(KundeValidierung.isValidPhoneNumber("11238257923527954235235")),
				() -> assertFalse(KundeValidierung.isValidPhoneNumber("12345568z")),
				() -> assertFalse(KundeValidierung.isValidPhoneNumber("Hans Peter")),
				() -> assertFalse(KundeValidierung.isValidPhoneNumber("!"))
		);
	}

}
