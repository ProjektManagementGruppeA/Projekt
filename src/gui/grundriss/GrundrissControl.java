package gui.grundriss;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;

import business.DatabaseConnector;
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
public final class GrundrissControl {
	
	// das View-Objekt des Grundriss-Fensters
	private GrundrissView grundrissView;

	

	/**
	 * erzeugt ein ControlObjekt inklusive View-Objekt und Model-Objekt zum 
	 * Fenster fuer die Sonderwuensche zum Grundriss.
	 * @param grundrissStage, Stage fuer das View-Objekt zu den Sonderwuenschen zum Grundriss
	 */
	public GrundrissControl(KundeModel kundeModel){  
	   	Stage stageGrundriss = new Stage();
    	stageGrundriss.initModality(Modality.APPLICATION_MODAL);
    	this.grundrissView = new GrundrissView(this, stageGrundriss);
	}
	    
	/**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		this.grundrissView.oeffneGrundrissView();
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
		List<KundeSonderwunsch> list = KundeSonderwunschModel.getInstance(connector).getKundeSonderwuenscheByKategorie(kunde, "Grundriss");
		
		return list; 
	}
	
	public int[] leseGrundrissSonderwuenschePreise() {
		
		DatabaseConnector connector = DatabaseConnector.getInstance();
		
		
	    List<Sonderwunsch> sonderwunsch = SonderwunschModel.getInstance(connector).getSonderwunschByKategorie("Grundriss");
	    
	    System.out.println(sonderwunsch);
	    int[] preise = new int[6];
	    for(int i = 0; i<sonderwunsch.size(); i++) {
	    	preise[i] = sonderwunsch.get(i).getPreis();
	    	System.out.println(sonderwunsch.get(i).getPreis());
	    	
	    }
	    
	    return preise; 
	    

	    // Speichern Sie die Preise im GrundrissView
	    //grundrissView.setPreise(preise);

	    // Aktualisieren Sie die GUI-Elemente mit den gelesenen Preisen
	    //grundrissView.aktualisierePreiseGUI();
	
		}
	}

