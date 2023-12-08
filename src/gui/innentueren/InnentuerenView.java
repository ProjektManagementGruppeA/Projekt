package gui.innentueren;

import gui.basis.BasisView;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class InnentuerenView extends BasisView{
	
	private InnentuerenControl innentuerenControl;
	private int[] preise;
	
	private Label lblGlasausschnittKlar = new Label("Glasauschnitt (Klarglas)");
	private Label lblGlasausschnittKlarPreis = new Label();
	private CheckBox chckBxGlasausschnittKlar = new CheckBox();
	
	private Label lblGlasausschnittMilch = new Label("Glasauschnitt (Milchglas)");
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
		super.getGridPaneSonderwunsch().add(lblGlasausschnittKlarPreis, 1, 1);
		lblGlasausschnittKlarPreis.setText(Integer.toString(this.preise[0])+ "€");
		super.getGridPaneSonderwunsch().add(chckBxGlasausschnittKlar, 2, 1);
		
		super.getGridPaneSonderwunsch().add(lblGlasausschnittMilch, 0, 2);
		super.getGridPaneSonderwunsch().add(lblGlasausschnittMilchPreis, 1, 2);
		lblGlasausschnittMilchPreis.setText(Integer.toString(this.preise[1])+ "€");
		super.getGridPaneSonderwunsch().add(chckBxGlasausschnittMilch, 2, 2);
		
		super.getGridPaneSonderwunsch().add(lblInnentuerGarage, 0, 3);
		super.getGridPaneSonderwunsch().add(lblInnentuerGaragePreis, 1, 3);
		lblInnentuerGaragePreis.setText(Integer.toString(this.preise[2])+ "€");
		super.getGridPaneSonderwunsch().add(chckBxInnentuerGarage, 2, 3);
		
		
	}
	
	public void oeffneInnentuerenView(){ 
		super.oeffneBasisView();
	}

	@Override
	protected void berechneUndZeigePreisSonderwuensche() {
		int finalPrice = 0;

	    if (chckBxGlasausschnittKlar.isSelected()) {
	        finalPrice += preise[0];
	    }
	    if (chckBxGlasausschnittMilch.isSelected()) {
	        finalPrice += preise[1];
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
		boolean[] checked = {chckBxGlasausschnittKlar.isSelected(),
		                     chckBxGlasausschnittMilch.isSelected(),
		                     chckBxInnentuerGarage.isSelected()
		};
		this.innentuerenControl.speichereSonderwuensche(checked);
		
	}

	@Override
	protected void speichereCsv() {
		this.innentuerenControl.speichereCsv();
		
	}
	
	private void lesePreise() {
		this.preise = this.innentuerenControl.lesePreise();
	}


}
