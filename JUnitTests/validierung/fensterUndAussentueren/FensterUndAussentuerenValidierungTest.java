package fensterUndAussentueren;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.scene.control.CheckBox;
import validierung.fensterUndAussentueren.FensterUndAussentuerenValidierung;

class FensterUndAussentuerenValidierungTest {
	
	static CheckBox cb1 = new CheckBox();
	static CheckBox cb2 = new CheckBox();
	
	@Test
	void testPruefeVorbereitungEG() {
		
		
		cb1.setSelected(true);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungEG(cb1,cb2)
		
		assertAll("pruefeVorbereitungEG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertFalse(cb2.isDisabled())
				);
	}
	
	@Test
	void unpreparedInstallationEGShouldReturnFalse() {
		cb1.setSelected(false);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungEG(cb1,cb2)
		
		assertAll("pruefeVorbereitungEG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertTrue(cb2.isDisabled()),
				() -> assertFalse(cb1.isSelected()),
				() -> assertFalse(cb2.isSelected())
				);
	}
	
	@Test
	void preparedInstallationEGShouldReturnTrue() {
		cb1.setSelected(true);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungEG(cb1,cb2)
		
		assertAll("pruefeVorbereitungEG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertFalse(cb2.isDisabled()),
				() -> assertTrue(cb1.isSelected()),
				() -> assertTrue(cb2.isSelected())
				);
	}
	
	@Test
	void unpreparedInstallationOGShouldReturnFalse() {
		cb1.setSelected(false);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungOG(cb1,cb2)
		
		assertAll("pruefeVorbereitungOG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertTrue(cb2.isDisabled()),
				() -> assertFalse(cb1.isSelected()),
				() -> assertFalse(cb2.isSelected())
				);
	}
	
	@Test
	void preparedInstallationOGShouldReturnTrue() {
		cb1.setSelected(true);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungOG(cb1,cb2)
		
		assertAll("pruefeVorbereitungOG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertFalse(cb2.isDisabled()),
				() -> assertTrue(cb1.isSelected()),
				() -> assertTrue(cb2.isSelected())
				);
	}
	
	
	@Test
	void unpreparedInstallationDGShouldReturnFalse() {
		cb1.setSelected(false);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungDG(cb1,cb2)
		
		assertAll("pruefeVorbereitungDG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertTrue(cb2.isDisabled()),
				() -> assertFalse(cb1.isSelected()),
				() -> assertFalse(cb2.isSelected())
				);
	}
	
	@Test
	void preparedInstallationDGShouldReturnTrue() {
		cb1.setSelected(true);
		cb2.setSelected(true);
		
		FensterUndAussentuerenValidierung.pruefeVorbereitungDG(cb1,cb2)
		
		assertAll("pruefeVorbereitungDG",
				() -> assertFalse(cb1.isDisabled()),
				() -> assertFalse(cb2.isDisabled()),
				() -> assertTrue(cb1.isSelected()),
				() -> assertTrue(cb2.isSelected())
				);
	}
	

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

}