package gui.fensterUndAussenentueren;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;

import business.DatabaseConnector;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;
import business.sonderwunsch.Sonderwunsch;
import business.sonderwunsch.SonderwunschModel;

import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu den Grundriss-Varianten
 * kontrolliert.
 */
public final class FensterUndAussentuerenControl {
	
	// das View-Objekt des Grundriss-Fensters
	private FensterUndAussentuerenView fensterUndAussentuerenView;

	

	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Fenster fuer die Sonderwuensche zum Grundriss.
	 * @param grundrissStage, Stage fuer das View-Objekt zu den Sonderwuenschen zum Grundriss
	 */
	public FensterUndAussentuerenControl(KundeModel kundeModel, Kunde kunde){  
	   	Stage stageFensterUndAussentueren= new Stage();
	   	stageFensterUndAussentueren.initModality(Modality.APPLICATION_MODAL);
    	this.fensterUndAussentuerenView = new FensterUndAussentuerenView(this, stageFensterUndAussentueren, kunde); // ObjectId kunde
	}
	    
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.fensterUndAussentuerenView.oeffneFensterUndAussentuerenView();
	}
	
	
	public void addKundenSonderwunsch(ObjectId kunde, ObjectId sonderwunsch, int anz) {
		
	}
	
	public List<KundeSonderwunsch> loadKundenSonderwunsch(ObjectId kundeId, String kategorieName){
		DatabaseConnector connector = DatabaseConnector.getInstance();
		return KundeSonderwunschModel.getInstance(connector).getKundeSonderwuenscheByKategorie(kundeId, kategorieName);
	}
	
	public boolean pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw){
		return true;
	}
	
	public List<KundeSonderwunsch> getKundeSonderwunsch(ObjectId kunde)
	
	{
		DatabaseConnector connector = DatabaseConnector.getInstance();
		List<KundeSonderwunsch> list = KundeSonderwunschModel.getInstance(connector).getKundeSonderwuenscheByKategorie(kunde, "Fenster und Außentüren");
		
		return list; 
	}
	
	public int[] leseFensterUndAussentuerenSonderwuenschePreise() {
		
		DatabaseConnector connector = DatabaseConnector.getInstance();
		
		
	    List<Sonderwunsch> sonderwunsch = SonderwunschModel.getInstance(connector).getSonderwunschByKategorie("Fenster und Außentüren");
	    
	    //System.out.println(sonderwunsch);
	    int[] preise = new int[9];
	    for(int i = 0; i<sonderwunsch.size(); i++) {
	    	preise[i] = sonderwunsch.get(i).getPreis();
	    	//System.out.println(sonderwunsch.get(i).getPreis());
	    	
	    }
	    
	    return preise; 
	    

	    // Speichern Sie die Preise im GrundrissView
	    //grundrissView.setPreise(preise);

	    // Aktualisieren Sie die GUI-Elemente mit den gelesenen Preisen
	    //grundrissView.aktualisierePreiseGUI();
	
		}
	}

