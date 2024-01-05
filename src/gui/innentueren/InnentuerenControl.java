package gui.innentueren;

import business.DatabaseConnector;
import business.export.CsvFile;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;
import business.sonderwunsch.Sonderwunsch;
import business.sonderwunsch.SonderwunschModel;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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

	public void speichereToCsv(File file,int[]input) throws IOException {
		
		//int input[] = leseInnentuerenSonderwuenschePreise();
		 String[][] inputString = new String[2][input.length];
		
		 for (int i = 0; i < input.length; i++) {
	            inputString[1][i] = String.valueOf(input[i]);
	            System.out.print(i);
	        }
		 	inputString[0][0] = "Glasauschnitt (Klarglas)";
		 	inputString[0][1] = "Glasauschnitt (Milchglas)";
		 	inputString[0][2] = "Innentuer zur Garage als Holztuer";
		 	inputString[0][3] = "Gesamtpreis";
		 	
			 CsvFile idFile = new CsvFile(file,inputString);
			 idFile.export();
	}

	public int[] lesePreise() {
		// TODO Auto-generated method stub
		return null;
	}


}
