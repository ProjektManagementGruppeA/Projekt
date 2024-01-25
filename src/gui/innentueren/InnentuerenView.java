package gui.innentueren;

import java.util.List;

import business.kundeSonderwunsch.KundeSonderwunsch;
import gui.basis.BasisView;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.IOException;

public class InnentuerenView extends BasisView{
	
	private InnentuerenControl innentuerenControl;
	private int[] preise;
	
	private Label lblGlasausschnittKlar = new Label("Glasauschnitt (Klarglas)");
	private Spinner spinGlasausschnittKlarAnzahl = new Spinner();
	private Label lblGlasausschnittKlarPreis = new Label();
	private CheckBox chckBxGlasausschnittKlar = new CheckBox();
	
	private Label lblGlasausschnittMilch = new Label("Glasauschnitt (Milchglas)");
	private Spinner spinGlasausschnittMilchAnzahl = new Spinner();
	private Label lblGlasausschnittMilchPreis = new Label();
	private CheckBox chckBxGlasausschnittMilch = new CheckBox();
	
	private Label lblInnentuerGarage = new Label("Innentuer zur Garage als Holztür");
	private Label lblInnentuerGaragePreis = new Label();
	private CheckBox chckBxInnentuerGarage = new CheckBox();
	

	public InnentuerenView(InnentuerenControl innentuerenControl, Stage sonderwunschStage) {
		super(sonderwunschStage);
		this.innentuerenControl = innentuerenControl;
		sonderwunschStage.setTitle("Sonderwünsche zu Innentüren");
		this.preise = this.innentuerenControl.leseInnentuerenSonderwuenschePreise();
		
		this.initKomponenten();
		
	}
	
	protected void initKomponenten() {
		super.initKomponenten();
		super.getLblSonderwunsch().setText("Innentüren");
		
		super.getGridPaneSonderwunsch().add(lblGlasausschnittKlar, 0, 1);
		spinGlasausschnittKlarAnzahl.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
		super.getGridPaneSonderwunsch().add(spinGlasausschnittKlarAnzahl, 1, 1);
		super.getGridPaneSonderwunsch().add(lblGlasausschnittKlarPreis, 2, 1);
		lblGlasausschnittKlarPreis.setText(Integer.toString(this.preise[0])+ "€");
		super.getGridPaneSonderwunsch().add(chckBxGlasausschnittKlar, 3, 1);
		
		super.getGridPaneSonderwunsch().add(lblGlasausschnittMilch, 0, 2);
		spinGlasausschnittMilchAnzahl.setValueFactory( new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10));
		super.getGridPaneSonderwunsch().add(spinGlasausschnittMilchAnzahl, 1, 2);
		super.getGridPaneSonderwunsch().add(lblGlasausschnittMilchPreis, 2, 2);
		lblGlasausschnittMilchPreis.setText(Integer.toString(this.preise[1])+ "€");
		super.getGridPaneSonderwunsch().add(chckBxGlasausschnittMilch, 3, 2);
		
		super.getGridPaneSonderwunsch().add(lblInnentuerGarage, 0, 3);
		super.getGridPaneSonderwunsch().add(lblInnentuerGaragePreis, 2, 3);
		lblInnentuerGaragePreis.setText(Integer.toString(this.preise[2])+ "€");
		super.getGridPaneSonderwunsch().add(chckBxInnentuerGarage, 3, 3);
		
		
	}
	
	public void oeffneInnentuerenView(){ 
		this.leseKundenSonderwuensche();
		super.oeffneBasisView();
		
	}
	
	public void leseKundenSonderwuensche() {
		int[] anzahl = innentuerenControl.extractAnzahl();
		if (anzahl[0] > 0) {
			spinGlasausschnittKlarAnzahl.getValueFactory().setValue(anzahl[0]);
			chckBxGlasausschnittKlar.setSelected(true);
		}
		if (anzahl[1] > 0) {
			spinGlasausschnittMilchAnzahl.getValueFactory().setValue(anzahl[1]);
			chckBxGlasausschnittMilch.setSelected(true);
		}
		if (anzahl[2] > 0) {
			chckBxInnentuerGarage.setSelected(true);
		}
	}

	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		int finalPrice = 0;

	    if (chckBxGlasausschnittKlar.isSelected()) {
	        finalPrice += preise[0] * (int) spinGlasausschnittKlarAnzahl.getValue();
	    }
	    if (chckBxGlasausschnittMilch.isSelected()) {
	        finalPrice += preise[1] * (int) spinGlasausschnittMilchAnzahl.getValue();
	    }
	    if (chckBxInnentuerGarage.isSelected()) {
	        finalPrice += preise[2];
	    }

  	    // Zeige den Gesamtpreis an
  	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
  	    alert.setTitle("Gesamtpreis");
  	    alert.setHeaderText(null);
  	    alert.setContentText("Gesamtpreis der Sonderwünsche: " + finalPrice + " Euro");
  	    alert.showAndWait();
		
	}

	@Override
	protected void speichereSonderwuensche() {
		int[] checked = {chckBxGlasausschnittKlar.isSelected()? (int) spinGlasausschnittKlarAnzahl.getValue(): 0,
		                     chckBxGlasausschnittMilch.isSelected()? (int) spinGlasausschnittMilchAnzahl.getValue(): 0,
		                     chckBxInnentuerGarage.isSelected()? 1 : 0
		};
		try {
			this.innentuerenControl.speichereSonderwuensche(checked);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected void speichereCsv() throws IOException {
		int finalPrice = 0;
		int[] csvPreis = {0,0,0,0};
		if (chckBxGlasausschnittKlar.isSelected()) {
			finalPrice += preise[0];
	        csvPreis[0]= preise[0];
	    }
	    if (chckBxGlasausschnittMilch.isSelected()) {
	    	csvPreis[1]= preise[1];
	    	finalPrice += preise[1];
	    }
	    if (chckBxInnentuerGarage.isSelected()) {
	    	csvPreis[2]= preise[2];
	    	finalPrice += preise[2];
	    }
	    csvPreis[3]= finalPrice;
		 FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Speichere CSV Datei");
	        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Dateien", "*.csv"));
	        File source = fileChooser.showSaveDialog(sonderwunschStage);
	        this.innentuerenControl.speichereToCsv(source, csvPreis);
	        
		
	}
	
	private void lesePreise() {
		this.preise = this.innentuerenControl.lesePreise();
	}
	
	public void zeigeFehlermeldung(String ueberschrift, String meldung){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText(ueberschrift);
        alert.setContentText(meldung);
        alert.show();
    }


}
