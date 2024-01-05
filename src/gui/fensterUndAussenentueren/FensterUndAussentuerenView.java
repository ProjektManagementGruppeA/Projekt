package gui.fensterUndAussenentueren;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
import gui.basis.BasisView;
import validierung.fensterUndAussentueren.FensterUndAussentuerenValidierung;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu 
 * den Grundrissvarianten bereitstellt.
 */
public class FensterUndAussentuerenView extends BasisView{
 
 	// das Control-Objekt des Grundriss-Fensters
	private FensterUndAussentuerenControl fensterUndAussentuerenControl;
	private int[] preise;
	
	
	//---Anfang Attribute der grafischen Oberflaeche---
	private Label lblSchiebetuerenEGTerrasse = new Label("Schiebetüren im EG zur Terrasse");
	private TextField txtPreisSchiebetuerenEGTerrasse = new TextField();
	private Label lblSchiebetuerenEGTerrasseEuro = new Label("Euro");
	private CheckBox chckBxSchiebetuerenEGTerrasse = new CheckBox();

	private Label lblSchiebetuerenDGDachterrasse = new Label("Schiebetüren im DG zur Dachterrasse");
	private TextField txtPreisSchiebetuerenDGDachterrasse = new TextField();
	private Label lblSchiebetuerenDGDachterrasseEuro = new Label("Euro");
	private CheckBox chckBxSchiebetuerenDGDachterrasse = new CheckBox();

	private Label lblEinbruchschutzHaustuer = new Label("Erhöhter Einbruchschutz an der Haustür");
	private TextField txtPreisEinbruchschutzHaustuer = new TextField();
	private Label lblEinbruchschutzHaustuerEuro = new Label("Euro");
	private CheckBox chckBxEinbruchschutzHaustuer = new CheckBox();

	private Label lblVorbereitungRollaedenEG = new Label("Vorbereitung für elektrische Antriebe Rolläden EG");
	private TextField txtPreisVorbereitungRollaedenEG = new TextField();
	private Label lblVorbereitungRollaedenEGEuro = new Label("Euro");
	private CheckBox chckBxVorbereitungRollaedenEG = new CheckBox();

	private Label lblVorbereitungRollaedenOG = new Label("Vorbereitung für elektrische Antriebe Rolläden OG");
	private TextField txtPreisVorbereitungRollaedenOG = new TextField();
	private Label lblVorbereitungRollaedenOGEuro = new Label("Euro");
	private CheckBox chckBxVorbereitungRollaedenOG = new CheckBox();

	private Label lblVorbereitungRollaedenDG = new Label("Vorbereitung für elektrische Antriebe Rolläden DG");
	private TextField txtPreisVorbereitungRollaedenDG = new TextField();
	private Label lblVorbereitungRollaedenDGEuro = new Label("Euro");
	private CheckBox chckBxVorbereitungRollaedenDG = new CheckBox();

	private Label lblElektrischeRollaedenEG = new Label("Elektrische Rolläden EG");
	private TextField txtPreisElektrischeRollaedenEG = new TextField();
	private Label lblElektrischeRollaedenEGEuro = new Label("Euro");
	private CheckBox chckBxElektrischeRollaedenEG = new CheckBox();

	private Label lblElektrischeRollaedenOG = new Label("Elektrische Rolläden OG");
	private TextField txtPreisElektrischeRollaedenOG = new TextField();
	private Label lblElektrischeRollaedenOGEuro = new Label("Euro");
	private CheckBox chckBxElektrischeRollaedenOG = new CheckBox();

	private Label lblElektrischeRollaedenDG = new Label("Elektrische Rolläden DG");
	private TextField txtPreisElektrischeRollaedenDG = new TextField();
	private Label lblElektrischeRollaedenDGEuro = new Label("Euro");
	private CheckBox chckBxElektrischeRollaedenDG = new CheckBox();

    //-------Ende Attribute der grafischen Oberflaeche-------
	List<CheckBox> listCheckBox = new ArrayList<>();
	private ObjectId kunde;
	DatabaseConnector connector = DatabaseConnector.getInstance();
	
	public Button button = new Button("Sonderwunsch speichern");
	
	String kundennummer;
	
    /**
     * erzeugt ein GrundrissView-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param grundrissControl GrundrissControl, enthaelt das zugehoerige Control
     * @param grundrissStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public FensterUndAussentuerenView (FensterUndAussentuerenControl fensterUndAussentuerenControl, Stage grundrissStage, Kunde kunde){ // ObjectId kunde von MainView aus übergeben
    	super(grundrissStage);
        this.fensterUndAussentuerenControl = fensterUndAussentuerenControl;
        grundrissStage.setTitle("Sonderwünsche zu Fenster und Außentüren");
        this.preise = this.fensterUndAussentuerenControl.leseFensterUndAussentuerenSonderwuenschePreise();
        this.kundennummer = kunde.getKundennummer();
	    this.initKomponenten();
	    this.kunde = KundeModel.getInstance(connector).getKundeByKundennummer(this.kundennummer).getId(); 
	  
	    
	    this.loadSonderwuensche(this.kunde);
	    
	    grundrissStage.setOnCloseRequest(event -> {
	        try {
	            this.fensterUndAussentuerenControl = null;
	            //System.out.println("Controller entfernt");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	 
    }
    
    
    public void loadSonderwuensche(ObjectId kunde) {
        List<KundeSonderwunsch> list = this.fensterUndAussentuerenControl.loadKundenSonderwunsch(kunde, "Fenster und Außentüren");

        if (list.size() >= 1) {
            for (int i = 0; i < list.size(); i++) {
                ObjectId sonderwunschid = list.get(i).getSonderwunschId();
                String beschreibung = SonderwunschModel.getInstance(connector).getSonderwunschById(sonderwunschid).getBeschreibung();
                //System.out.println(beschreibung);
                //System.out.println(list.get(i).getAnzahl());

                if (list.get(i).getAnzahl() >= 1 && "Schiebetüren im EG zur Terrasse".equals(beschreibung)) {
                    chckBxSchiebetuerenEGTerrasse.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Schiebetüren im DG zur Dachterrasse".equals(beschreibung)) {
                    chckBxSchiebetuerenDGDachterrasse.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Erhöhter Einbruchschutz an der Haustür".equals(beschreibung)) {
                    chckBxEinbruchschutzHaustuer.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Vorbereitung für elektrische Antriebe Rolläden EG".equals(beschreibung)) {
                    chckBxVorbereitungRollaedenEG.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Vorbereitung für elektrische Antriebe Rolläden OG".equals(beschreibung)) {
                    chckBxVorbereitungRollaedenOG.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Vorbereitung für elektrische Antriebe Rolläden DG".equals(beschreibung)) {
                    chckBxVorbereitungRollaedenDG.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Elektrische Rolläden EG".equals(beschreibung)) {
                    chckBxElektrischeRollaedenEG.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Elektrische Rolläden OG".equals(beschreibung)) {
                    chckBxElektrischeRollaedenOG.setSelected(true);
                }
                if (list.get(i).getAnzahl() >= 1 && "Elektrische Rolläden DG".equals(beschreibung)) {
                    chckBxElektrischeRollaedenDG.setSelected(true);
                }
            }
        }
    }

  
   
   public void saveSonderwuensche(ObjectId kunde) throws Exception {
	    
	    KundeSonderwunschModel kundeSonderwunschModel = KundeSonderwunschModel.getInstance(connector);
	    List<Sonderwunsch> listSonderwunsch = SonderwunschModel.getInstance(connector).getSonderwunschByKategorie("Fenster und Außentüren");
	    
	    for(Sonderwunsch sw : listSonderwunsch) {
	        CheckBox correspondingCheckBox = findCheckBoxForSonderwunsch(sw.getBeschreibung());
	        if(correspondingCheckBox == null) continue; // If no corresponding checkbox found, skip to next Sonderwunsch

	        boolean isSelected = correspondingCheckBox.isSelected();
	        KundeSonderwunsch existingKundeSonderwunsch = kundeSonderwunschModel.getKundeSonderwunschByKundeAndSonderwunsch(kunde, sw.getId());
	        
	        //System.out.println(sw.getBeschreibung().contains("Elektrische Rolläden"));
	        
	        if(sw.getBeschreibung().equals("Elektrische Rolläden EG") && !FensterUndAussentuerenValidierung.validElektrischeRolladenEG(findCheckBoxForSonderwunsch("Vorbereitung für elektrische Antriebe Rolläden EG").isSelected(), isSelected)){
	        	isSelected = false;
	        	chckBxElektrischeRollaedenEG.setSelected(false);
	        }
	        else if(sw.getBeschreibung().equals("Elektrische Rolläden OG") && !FensterUndAussentuerenValidierung.validElektrischeRolladenEG(findCheckBoxForSonderwunsch("Vorbereitung für elektrische Antriebe Rolläden OG").isSelected(), isSelected)){
	        	isSelected = false;
	        	chckBxElektrischeRollaedenOG.setSelected(false);
	        }
	        else if(sw.getBeschreibung().equals("Elektrische Rolläden DG") && !FensterUndAussentuerenValidierung.validElektrischeRolladenEG(findCheckBoxForSonderwunsch("Vorbereitung für elektrische Antriebe Rolläden DG").isSelected(), isSelected)){
	        	isSelected = false;
	        	chckBxElektrischeRollaedenDG.setSelected(false);
	        }else {
	        
		        if (isSelected && existingKundeSonderwunsch == null) {
		            // Sonderwunsch is selected and not yet in the database for this customer
		        	//System.out.println("Sonderwunsch is selected and not yet in the database for this customer");
		            kundeSonderwunschModel.addKundeSonderwunsch(kunde, sw.getId(), 1);
		        } else if (!isSelected && existingKundeSonderwunsch != null) {
		            // Sonderwunsch is not selected but exists in the database for this customer
		        	//System.out.println("Sonderwunsch is not selected but exists in the database for this customer");
		            kundeSonderwunschModel.updateKundeSonderwunschByKundeAndSonderwunsch(kunde, sw.getId(), 0);
		        } // If Sonderwunsch is selected and already exists, no action needed
		        
		        //System.out.println(isSelected);
	        }
	    }
	    //System.out.println("Daten gespeichert");}
	}
   
  
   
   private CheckBox findCheckBoxForSonderwunsch(String beschreibung) {
	    // Map Sonderwunsch descriptions to their corresponding checkboxes
	    switch (beschreibung) {
	        case "Schiebetüren im EG zur Terrasse":
	            return chckBxSchiebetuerenEGTerrasse;
	        case "Schiebetüren im DG zur Dachterrasse":
	            return chckBxSchiebetuerenDGDachterrasse;
	        case "Erhöhter Einbruchschutz an der Haustür":
	            return chckBxEinbruchschutzHaustuer;
	        case "Vorbereitung für elektrische Antriebe Rolläden EG":
	            return chckBxVorbereitungRollaedenEG;
	        case "Vorbereitung für elektrische Antriebe Rolläden OG":
	            return chckBxVorbereitungRollaedenOG;
	        case "Vorbereitung für elektrische Antriebe Rolläden DG":
	            return chckBxVorbereitungRollaedenDG;
	        case "Elektrische Rolläden EG":
	            return chckBxElektrischeRollaedenEG;
	        case "Elektrische Rolläden OG":
	            return chckBxElektrischeRollaedenOG;
	        case "Elektrische Rolläden DG":
	            return chckBxElektrischeRollaedenDG;
	        default:
	            return null;
	    }
	}

  
    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
    	super.initKomponenten();
    	super.getLblSonderwunsch().setText("Fenster und Außentüren");

    	super.getGridPaneSonderwunsch().add(lblSchiebetuerenEGTerrasse, 0, 1);
    	super.getGridPaneSonderwunsch().add(txtPreisSchiebetuerenEGTerrasse, 1, 1);
    	txtPreisSchiebetuerenEGTerrasse.setText(Integer.toString(this.preise[0]));
    	txtPreisSchiebetuerenEGTerrasse.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblSchiebetuerenEGTerrasseEuro, 2, 1);
    	super.getGridPaneSonderwunsch().add(chckBxSchiebetuerenEGTerrasse, 3, 1);

    	super.getGridPaneSonderwunsch().add(lblSchiebetuerenDGDachterrasse, 0, 2);
    	super.getGridPaneSonderwunsch().add(txtPreisSchiebetuerenDGDachterrasse, 1, 2);
    	txtPreisSchiebetuerenDGDachterrasse.setText(Integer.toString(this.preise[1]));
    	txtPreisSchiebetuerenDGDachterrasse.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblSchiebetuerenDGDachterrasseEuro, 2, 2);
    	super.getGridPaneSonderwunsch().add(chckBxSchiebetuerenDGDachterrasse, 3, 2);

    	super.getGridPaneSonderwunsch().add(lblEinbruchschutzHaustuer, 0, 3);
    	super.getGridPaneSonderwunsch().add(txtPreisEinbruchschutzHaustuer, 1, 3);
    	txtPreisEinbruchschutzHaustuer.setText(Integer.toString(this.preise[2]));
    	txtPreisEinbruchschutzHaustuer.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblEinbruchschutzHaustuerEuro, 2, 3);
    	super.getGridPaneSonderwunsch().add(chckBxEinbruchschutzHaustuer, 3, 3);

    	super.getGridPaneSonderwunsch().add(lblVorbereitungRollaedenEG, 0, 4);
    	super.getGridPaneSonderwunsch().add(txtPreisVorbereitungRollaedenEG, 1, 4);
    	txtPreisVorbereitungRollaedenEG.setText(Integer.toString(this.preise[3]));
    	txtPreisVorbereitungRollaedenEG.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblVorbereitungRollaedenEGEuro, 2, 4);
    	super.getGridPaneSonderwunsch().add(chckBxVorbereitungRollaedenEG, 3, 4);

    	super.getGridPaneSonderwunsch().add(lblVorbereitungRollaedenOG, 0, 5);
    	super.getGridPaneSonderwunsch().add(txtPreisVorbereitungRollaedenOG, 1, 5);
    	txtPreisVorbereitungRollaedenOG.setText(Integer.toString(this.preise[4]));
    	txtPreisVorbereitungRollaedenOG.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblVorbereitungRollaedenOGEuro, 2, 5);
    	super.getGridPaneSonderwunsch().add(chckBxVorbereitungRollaedenOG, 3, 5);

    	super.getGridPaneSonderwunsch().add(lblVorbereitungRollaedenDG, 0, 6);
    	super.getGridPaneSonderwunsch().add(txtPreisVorbereitungRollaedenDG, 1, 6);
    	txtPreisVorbereitungRollaedenDG.setText(Integer.toString(this.preise[5]));
    	txtPreisVorbereitungRollaedenDG.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblVorbereitungRollaedenDGEuro, 2, 6);
    	super.getGridPaneSonderwunsch().add(chckBxVorbereitungRollaedenDG, 3, 6);

    	super.getGridPaneSonderwunsch().add(lblElektrischeRollaedenEG, 0, 7);
    	super.getGridPaneSonderwunsch().add(txtPreisElektrischeRollaedenEG, 1, 7);
    	txtPreisElektrischeRollaedenEG.setText(Integer.toString(this.preise[6]));
    	txtPreisElektrischeRollaedenEG.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblElektrischeRollaedenEGEuro, 2, 7);
    	super.getGridPaneSonderwunsch().add(chckBxElektrischeRollaedenEG, 3, 7);

    	super.getGridPaneSonderwunsch().add(lblElektrischeRollaedenOG, 0, 8);
    	super.getGridPaneSonderwunsch().add(txtPreisElektrischeRollaedenOG, 1, 8);
    	txtPreisElektrischeRollaedenOG.setText(Integer.toString(this.preise[7]));
    	txtPreisElektrischeRollaedenOG.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblElektrischeRollaedenOGEuro, 2, 8);
    	super.getGridPaneSonderwunsch().add(chckBxElektrischeRollaedenOG, 3, 8);

    	super.getGridPaneSonderwunsch().add(lblElektrischeRollaedenDG, 0, 9);
    	super.getGridPaneSonderwunsch().add(txtPreisElektrischeRollaedenDG, 1, 9);
    	txtPreisElektrischeRollaedenDG.setText(Integer.toString(this.preise[8]));
    	txtPreisElektrischeRollaedenDG.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblElektrischeRollaedenDGEuro, 2, 9);
    	super.getGridPaneSonderwunsch().add(chckBxElektrischeRollaedenDG, 3, 9);

    	
 
    }  
    
    /**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneFensterUndAussentuerenView(){ 
		super.oeffneBasisView();
	}
    
	

    //private void leseGrundrissSonderwuensche(){
    //	this.grundrissControl.leseGrundrissSonderwuensche();
    //}
    
 	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
	protected void berechneUndZeigePreisSonderwuensche() {
	    // Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
	    // aus dem Control aufgerufen, dann der Preis berechnet.
	    int finalPrice = 0;

	    if (chckBxSchiebetuerenEGTerrasse.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisSchiebetuerenEGTerrasse.getText());
	    }
	    if (chckBxSchiebetuerenDGDachterrasse.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisSchiebetuerenDGDachterrasse.getText());
	    }
	    if (chckBxEinbruchschutzHaustuer.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisEinbruchschutzHaustuer.getText());
	    }
	    if (chckBxVorbereitungRollaedenEG.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisVorbereitungRollaedenEG.getText());
	    }
	    if (chckBxVorbereitungRollaedenOG.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisVorbereitungRollaedenOG.getText());
	    }
	    if (chckBxVorbereitungRollaedenDG.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisVorbereitungRollaedenDG.getText());
	    }
	    if (chckBxElektrischeRollaedenEG.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisElektrischeRollaedenEG.getText());
	    }
	    if (chckBxElektrischeRollaedenOG.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisElektrischeRollaedenOG.getText());
	    }
	    if (chckBxElektrischeRollaedenDG.isSelected()) {
	        finalPrice += Integer.parseInt(txtPreisElektrischeRollaedenDG.getText());
	    }

  	    // Zeige den Gesamtpreis an
  	    Alert alert = new Alert(Alert.AlertType.INFORMATION);
  	    alert.setTitle("Gesamtpreis");
  	    alert.setHeaderText(null);
  	    alert.setContentText("Gesamtpreis der Sonderwünsche: " + finalPrice + " Euro");
  	    alert.showAndWait();
  		
  	}
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected void speichereSonderwuensche(){
  		try {
			this.saveSonderwuensche(KundeModel.getInstance(connector).getKundeByKundennummer(this.kundennummer).getId());
			messageboxSpeichernErfolgreich("Fenster und Außentüren");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  		
  		
  	}

  	@Override
  	protected void speichereCsv() {
  	    try {
  	        // Datei-Auswahldialog anzeigen
  	        FileChooser fileChooser = new FileChooser();
  	        fileChooser.setTitle("CSV speichern");
  	        fileChooser.getExtensionFilters().add(new ExtensionFilter("CSV Dateien", "*.csv"));
  	        File file = fileChooser.showSaveDialog(sonderwunschStage);

  	        if (file != null) {
  	            FileWriter writer = new FileWriter(file);
  	            // Header schreiben
  	            writer.append("Beschreibung,Preis,Anzahl\n");

  	            // Sonderwünsche durchgehen und in die CSV-Datei schreiben
  	            writeSonderwunschToCsv(writer, "Schiebetüren im EG zur Terrasse:", txtPreisSchiebetuerenEGTerrasse, chckBxSchiebetuerenEGTerrasse);
  	            writeSonderwunschToCsv(writer, "Schiebetüren im DG zur Dachterrasse:", txtPreisSchiebetuerenDGDachterrasse, chckBxSchiebetuerenDGDachterrasse);
  	            writeSonderwunschToCsv(writer, "Erhöhter Einbruchschutz an der Haustür:", txtPreisEinbruchschutzHaustuer, chckBxEinbruchschutzHaustuer);
  	            writeSonderwunschToCsv(writer, "Vorbereitung für elektrische Antriebe Rolläden EG:", txtPreisVorbereitungRollaedenEG, chckBxVorbereitungRollaedenEG);
  	            writeSonderwunschToCsv(writer, "Vorbereitung für elektrische Antriebe Rolläden OG:", txtPreisVorbereitungRollaedenOG, chckBxVorbereitungRollaedenOG);
  	            writeSonderwunschToCsv(writer, "Vorbereitung für elektrische Antriebe Rolläden DG:", txtPreisVorbereitungRollaedenDG, chckBxVorbereitungRollaedenDG);
  	            writeSonderwunschToCsv(writer, "Elektrische Rolläden EG:", txtPreisElektrischeRollaedenEG, chckBxElektrischeRollaedenEG);
  	            writeSonderwunschToCsv(writer, "Elektrische Rolläden OG:", txtPreisElektrischeRollaedenOG, chckBxElektrischeRollaedenOG);
  	            writeSonderwunschToCsv(writer, "Elektrische Rolläden DG:", txtPreisElektrischeRollaedenDG, chckBxElektrischeRollaedenDG);

  	            writer.close();
  	        }
  	    } catch (IOException e) {
  	        e.printStackTrace();
  	       
  	    }
  	}

  	private void writeSonderwunschToCsv(FileWriter writer, String beschreibung, TextField preisField, CheckBox checkBox) throws IOException {
  	    if (checkBox.isSelected()) {
  	        // Wenn CheckBox ausgewählt ist, dann den Sonderwunsch in die CSV schreiben
  	        int preis = Integer.parseInt(preisField.getText());
  	        writer.append(beschreibung + "," + preis + ",1\n");
  	    }
  	}
  	
 	
 }


