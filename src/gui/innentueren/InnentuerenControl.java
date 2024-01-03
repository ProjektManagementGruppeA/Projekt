package gui.innentueren;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

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

public class InnentuerenControl {
	
	private InnentuerenView innentuerenView;
	private KundeModel kundeModel;
	private SonderwunschModel sonderwunschModel;
	private KundeSonderwunschModel kundeSonderwunschModel;
	private Kunde kunde;
	private List<Sonderwunsch> sonderwuensche;

	public InnentuerenControl(KundeModel kundeModel, Kunde kunde) {
		DatabaseConnector connector = DatabaseConnector.getInstance();
    	this.sonderwunschModel = SonderwunschModel.getInstance(connector);
    	this.kundeSonderwunschModel = KundeSonderwunschModel.getInstance(connector);
		Stage stageInnentueren= new Stage();
	   	stageInnentueren.initModality(Modality.APPLICATION_MODAL);
    	this.innentuerenView = new InnentuerenView(this, stageInnentueren);
    	this.kundeModel = kundeModel;
    	this.kunde = this.kundeModel.getKundeByKundennummer(kunde.getKundennummer());
    	
    	
	}
	public void oeffneInnentuerenView(){ 
		this.innentuerenView.oeffneInnentuerenView();
	}

	public int[] leseInnentuerenSonderwuenschePreise() {
		if (sonderwuensche == null) {
			this.leseInnentuerenSonderwuensche();
		}
		int[] preise = new int[sonderwuensche.size()];
		for (int i = 0; i < sonderwuensche.size(); i++) {
			preise[i] = sonderwuensche.get(i).getPreis();
		}
		return preise;
	}
	private void leseInnentuerenSonderwuensche() {
		this.sonderwuensche = sonderwunschModel.getSonderwunschByKategorie("Innentüren");

	}

	public void speichereSonderwuensche(int[] anzahlarray){
		List<KundeSonderwunsch> kundeSonderwuensche = this.kundeSonderwunschModel.getKundeSonderwuenscheByKategorie(this.kunde.getId(), "Innentüren");
		List<Sonderwunsch> sonderwuensche = this.sonderwunschModel.getSonderwunschByKategorie("Innentüren");
			
		if (InnentürenValidierung.validiereGlasKlar(anzahlarray[0])) {
			speichernOderAendern(anzahlarray[0], kundeSonderwuensche, sonderwuensche.get(0));
		}
		else {
			innentuerenView.zeigeFehlermeldung("Fehler", "Die Anzahl der Glasausschnitte(Klar) ist falsch");
		}
		if (InnentürenValidierung.validiereGlasMilch(anzahlarray[1])) {
			speichernOderAendern(anzahlarray[1], kundeSonderwuensche, sonderwuensche.get(1));
		}
		else {
			innentuerenView.zeigeFehlermeldung("Fehler", "Die Anzahl der Glasausschnitte(Milchig) ist falsch");
		}
		if (InnentürenValidierung.validiereGarage(anzahlarray[2])) {
			speichernOderAendern(anzahlarray[2], kundeSonderwuensche, sonderwuensche.get(2));
		}
		else {
			innentuerenView.zeigeFehlermeldung("Garage", "Die Anzahl der Garagen ist falsch");
		}
	}
	private void speichernOderAendern(int anzahl, List<KundeSonderwunsch> kundeSonderwuensche,
			Sonderwunsch sonderwunsch) {
		if (sonderwunschExistiert(kundeSonderwuensche, sonderwunsch.getId())) {
			this.kundeSonderwunschModel.updateKundeSonderwunschByKundeAndSonderwunsch(this.kunde.getId(), sonderwunsch.getId(), anzahl);
			
		}
		else {
			try {
				this.kundeSonderwunschModel.addKundeSonderwunsch(this.kunde.getId(), sonderwunsch.getId() , anzahl);
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	private boolean sonderwunschExistiert(List<KundeSonderwunsch> kundeSonderwuensche, ObjectId sonderwunschid) {
		for (KundeSonderwunsch kundeSonderwunsch : kundeSonderwuensche) {
			if (kundeSonderwunsch.getSonderwunschId().compareTo(sonderwunschid) == 0) {
				return true;
			}
		}
		return false;
	}

	public void speichereCsv() {
		// TODO Auto-generated method stub
		
	}

	public int[] lesePreise() {
		// TODO Auto-generated method stub
		return null;
	}

}
