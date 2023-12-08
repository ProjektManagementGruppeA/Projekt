package gui.innentueren;

import java.util.Iterator;
import java.util.List;

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

	public InnentuerenControl(KundeModel kundeModel) {
		DatabaseConnector connector = DatabaseConnector.getInstance();
    	this.sonderwunschModel = SonderwunschModel.getInstance(connector);
    	this.kundeSonderwunschModel = KundeSonderwunschModel.getInstance(connector);
		Stage stageInnentueren= new Stage();
	   	stageInnentueren.initModality(Modality.APPLICATION_MODAL);
    	this.innentuerenView = new InnentuerenView(this, stageInnentueren);
    	this.kundeModel = kundeModel;
    	
    	
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

	public void speichereSonderwuensche(boolean[] checked) throws Exception {
		List<KundeSonderwunsch> kundesonderwünsche = this.kundeSonderwunschModel.getKundeSonderwuenscheByKategorie(this.kunde.getId(), "Innentüren");
		if (kundesonderwünsche != null) {
			for (int i = 0; i < checked.length; i++) {
				
			}
		}
		Sonderwunsch sonderwunsch;
		for (int i = 0; i < checked.length; i++) {
			if (checked[i]) {
				this.kundeSonderwunschModel.addKundeSonderwunsch(kunde.getId(), sonderwuensche.get(i).getId(), 1);
			}
		}
		
	
		
		
	}

	public void speichereCsv() {
		// TODO Auto-generated method stub
		
	}

	public int[] lesePreise() {
		// TODO Auto-generated method stub
		return null;
	}

}
