package gui.kunde;

// import java.sql.SQLException;

import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;
import business.sonderwunsch.SonderwunschModel;
import business.sonderwunschKategorie.SonderwunschKategorieModel;
import business.sonderwunschKategorie.SonderwunschKategorie;
import gui.fensterUndAussenentueren.FensterUndAussentuerenControl;
import gui.grundriss.GrundrissControl;
import gui.innentueren.InnentuerenControl;
import javafx.stage.Stage;
import validierung.kunde.KundeValidierung;
import business.DatabaseConnector;
import business.haustyp.HaustypModel;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten kontrolliert.
 */
public class KundeControl {
       
    // das View-Objekt des Grundfensters mit den Kundendaten
	private KundeView kundeView;
    // das Model-Objekt des Grundfensters mit den Kundendaten
    private KundeModel kundeModel;
    private HaustypModel haustypModel;
	// private SonderwunschModel swModel;
    /* das GrundrissControl-Objekt fuer die Sonderwuensche
       zum Grundriss zu dem Kunden */
    private GrundrissControl grundrissControl;
    private FensterUndAussentuerenControl fensterUndAussentuerenControl;
	private InnentuerenControl innentuerenControl;
    
    /**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Grundfenster mit den Kundendaten.
	 * @param primaryStage, Stage fuer das View-Objekt zu dem Grundfenster mit den Kundendaten
	 */
    public KundeControl(Stage primaryStage) {
		DatabaseConnector connector = DatabaseConnector.getInstance();
		this.kundeModel = KundeModel.getInstance(connector);
		this.haustypModel = HaustypModel.getInstance(connector);
		// this.swModel = SonderwunschModel.getInstance(connector);
        this.kundeView = new KundeView(this, primaryStage, kundeModel, haustypModel);
    }
    
    /*
     * erstellt, falls nicht vorhanden, ein Grundriss-Control-Objekt.
     * Das GrundrissView wird sichtbar gemacht.
     */
    public void oeffneGrundrissControl(){
    	if (this.grundrissControl == null){
    		this.grundrissControl = new GrundrissControl(kundeModel);
      	}
    	this.grundrissControl.oeffneGrundrissView();
    }
    
    
    public void oeffneFensterUndAussentuerenControl(){
    	if (this.fensterUndAussentuerenControl == null){
    		this.fensterUndAussentuerenControl = new FensterUndAussentuerenControl(kundeModel);
      	}
    	this.fensterUndAussentuerenControl.oeffneGrundrissView();
    }
    
    public void oeffneInnentuerenControl(Kunde kunde) {
    	if (this.innentuerenControl == null){
    		this.innentuerenControl = new InnentuerenControl(kundeModel, kunde);
      	}
    	this.innentuerenControl.oeffneInnentuerenView();
		
	}
    
	/**
	 * speichert ein Kunde-Objekt in die Datenbank
	 * @param kunde, Kunde-Objekt, welches zu speichern ist
	 */
    public void speichereKunden(Kunde kunde){
      	try{
      		if (!KundeValidierung.isValidName(kunde.getVorname())) {
      			this.kundeView.zeigeFehlermeldung("Speicherung Fehlgeschlagen", "Vorname ist Invalid");
      			return;
      		}
      		if (!KundeValidierung.isValidName(kunde.getNachname())) {
      			this.kundeView.zeigeFehlermeldung("Speicherung Fehlgeschlagen", "Nachname ist Invalid");
      			return;
      		}
      		if (!KundeValidierung.isValidEmail(kunde.getEmail())) {
      			this.kundeView.zeigeFehlermeldung("Speicherung Fehlgeschlagen", "Email ist Invalid");
      			return;
      		}
      		if (!KundeValidierung.isValidPhoneNumber(kunde.getTelefonnummer())) {
      			this.kundeView.zeigeFehlermeldung("Speicherung Fehlgeschlagen", "Telefonummer ist Invalid");
      			return;
      		}
    		kundeModel.addKunde(kunde);
    		this.kundeView.zeigeErfolg("Speicherung erfolgreich", "Der Kunde wurde in die Datenbank aufgenommen");
    	}
    
    	catch(Exception exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("Exception",
                exc.getMessage());
    	}
    }
    
    public Kunde leseKunde(int hausnummer) {
		try{
    		return kundeModel.getKundeByHausnummer(hausnummer);
    	}
//    	catch(SQLException exc){
//    		exc.printStackTrace();
//    		this.kundeView.zeigeFehlermeldung("SQLException",
//                "Fehler beim lesen aus der Datenbank");
//    	}
    	catch(Exception exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("Exception",
                "Unbekannter Fehler");
    		return null;
    	}
	}
    
    public void aendereKunden(Kunde kunde) {
		try{
			if (!KundeValidierung.isValidName(kunde.getVorname())) {
      			this.kundeView.zeigeFehlermeldung("Änderung Fehlgeschlagen", "Vorname ist Invalid");
      			return;
      		}
      		if (!KundeValidierung.isValidName(kunde.getNachname())) {
      			this.kundeView.zeigeFehlermeldung("Änderung Fehlgeschlagen", "Nachname ist Invalid");
      			return;
      		}
      		if (!KundeValidierung.isValidEmail(kunde.getEmail())) {
      			this.kundeView.zeigeFehlermeldung("Änderung Fehlgeschlagen", "Email ist Invalid");
      			return;
      		}
      		if (!KundeValidierung.isValidPhoneNumber(kunde.getTelefonnummer())) {
      			this.kundeView.zeigeFehlermeldung("Änderung Fehlgeschlagen", "Telefonummer ist Invalid");
      			return;
      		}
    		kundeModel.updateKunde(kunde);
    		this.kundeView.zeigeErfolg("Änderung Erfolgreich", "Die Änderungen wurden vorgenommen");
    	}
//    	catch(SQLException exc){
//    		exc.printStackTrace();
//    		this.kundeView.zeigeFehlermeldung("SQLException",
//                "Fehler beim Speichern in die Datenbank");
//    	}
    	catch(Exception exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("Exception",
                "Unbekannter Fehler");
    	}

	}
    
    public boolean loescheKunde(Kunde kunde) {
		try{
    		return kundeModel.deleteKunde(kunde.getId());
    	}
//    	catch(SQLException exc){
//    		exc.printStackTrace();
//    		this.kundeView.zeigeFehlermeldung("SQLException",
//                "Fehler beim Loeschen aus der Datenbank");
//    	}
    	catch(Exception exc){
    		exc.printStackTrace();
    		this.kundeView.zeigeFehlermeldung("Exception",
                "Unbekannter Fehler");
    		return false;
    	}
    }

	
}
