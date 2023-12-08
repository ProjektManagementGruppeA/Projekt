package gui.grundriss;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;

import business.DatabaseConnector;
import business.haustyp.Haustyp;
import business.kunde.Kunde;
import business.kunde.KundeModel;
import business.kundeSonderwunsch.KundeSonderwunsch;
import business.kundeSonderwunsch.KundeSonderwunschModel;
import business.sonderwunsch.Sonderwunsch;
import business.sonderwunsch.SonderwunschModel;
import gui.basis.BasisView;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import validierung.grundriss.GrundrissValidierung;
import javafx.stage.Stage;

/**
 * Klasse, welche das Fenster mit den Sonderwuenschen zu 
 * den Grundrissvarianten bereitstellt.
 */
public class GrundrissView extends BasisView{
 
 	// das Control-Objekt des Grundriss-Fensters
	private GrundrissControl grundrissControl;
	private int[] preise;
	
	
   
    //---Anfang Attribute der grafischen Oberflaeche---
    private Label lblWandKueche    	     
        = new Label("Wand zur Abtrennung der Küche von dem Essbereich");
    private TextField txtPreisWandKueche 	= new TextField();
    private Label lblWandKuecheEuro 		= new Label("Euro");
    private CheckBox chckBxWandKueche 		= new CheckBox();
    
    
    private Label lblTuerKueche  	     
	    = new Label("Tür in der Wand zwischen Küche und Essbereich");
	private TextField txtPreisTuerKueche	= new TextField();
	private Label lblTuerKuecheEuro  	 		= new Label("Euro");
	private CheckBox chckBxTuerKueche  	 	= new CheckBox();
	
	
	private Label lblOg   	     
	= new Label("Großes Zimmer im OG statt zwei kleinen Zimmern");
	private TextField txtPreisOg  	= new TextField();
	private Label lblOgEuro 		= new Label("Euro");
	private CheckBox chckBxOg 		= new CheckBox();
	
	
	private Label lblTreppenraumDg  	     
	= new Label("Abgetrennter Treppenraum im DG");
	private TextField txtPreisTreppenraumDg  	= new TextField();
	private Label lblTreppenraumDgEuro 		= new Label("Euro");
	private CheckBox chckBxTreppenraumDg  	= new CheckBox();
	
	
	private Label lblVBad  	     
	= new Label("Vorrichtung eines Bades im DG");
	private TextField txtPreisVBad  	  	= new TextField();
	private Label lblVBadEuro  	 	= new Label("Euro");
	private CheckBox chckBxVBad		= new CheckBox();
	
	private Label lblABad  	     
	= new Label("Ausführung eines Bades im DG");
	private TextField txtPreisABad  	  	= new TextField();
	private Label lblABadEuro  	 	= new Label("Euro");
	private CheckBox chckBxABad		= new CheckBox();
    //-------Ende Attribute der grafischen Oberflaeche-------
	List<CheckBox> listCheckBox = new ArrayList<>();
	private ObjectId kunde;
	DatabaseConnector connector = DatabaseConnector.getInstance();
	
	public Button button = new Button("Sonderwunsch speichern");
	
    /**
     * erzeugt ein GrundrissView-Objekt, belegt das zugehoerige Control
     * mit dem vorgegebenen Objekt und initialisiert die Steuerelemente der Maske
     * @param grundrissControl GrundrissControl, enthaelt das zugehoerige Control
     * @param grundrissStage Stage, enthaelt das Stage-Objekt fuer diese View
     */
    public GrundrissView (GrundrissControl grundrissControl, Stage grundrissStage){ // ObjectId kunde von MainView aus übergeben
    	super(grundrissStage);
        this.grundrissControl = grundrissControl;
        grundrissStage.setTitle("Sonderwünsche zu Grundriss-Varianten");
        this.preise = this.grundrissControl.leseGrundrissSonderwuenschePreise();
        
	    this.initKomponenten();
	    this.kunde = KundeModel.getInstance(connector).getKundeByKundennummer("12345678").getId(); // replace with constructor parameter
	  
	    
	    this.loadSonderwuensche(this.kunde);
	    
	    
	 
    }
    
    
   
   
    
    
   public void loadSonderwuensche(ObjectId kunde)
   {
	   
	   List<KundeSonderwunsch> list = this.grundrissControl.loadKundenSonderwunsch(kunde, "Grundriss");
	   if(list.size()>=1) {
		   for(int i = 0; i < list.size(); i++) {
			   ObjectId sonderwunschid = list.get(i).getSonderwunschId();
			   String beschreibung = SonderwunschModel.getInstance(connector).getSonderwunschById(sonderwunschid).getBeschreibung();
			   System.out.println(beschreibung);
			   System.out.println(list.get(i).getAnzahl());
			   
			   
			   if (list.get(i).getAnzahl() >= 1 && "Wand zur Abtrennung der Küche von dem Essbereich".equals(beschreibung)) {
				    chckBxWandKueche.setSelected(true);
				}
				if (list.get(i).getAnzahl() >= 1 && "Tür in der Wand zwischen Küche und Essbereich".equals(beschreibung)) {
				    chckBxTuerKueche.setSelected(true);
				}
				if (list.get(i).getAnzahl() >= 1 && "Großes Zimmer im OG statt zwei kleinen Zimmern".equals(beschreibung)) {
				    chckBxOg.setSelected(true);
				}
				if (list.get(i).getAnzahl() >= 1 && "Abgetrennter Treppenraum im DG".equals(beschreibung)) {
				    chckBxTreppenraumDg.setSelected(true);
				}
				if (list.get(i).getAnzahl() >= 1 && "Vorrichtung eines Bades im DG".equals(beschreibung)) {
				    chckBxVBad.setSelected(true);
				}
				if (list.get(i).getAnzahl() >= 1 && "Ausführung eines Bades im DG".equals(beschreibung)) {
				    chckBxABad.setSelected(true);
				}
		   }
	      
	   }
   }
  
   
   public void saveSonderwuensche(ObjectId kunde) throws Exception {
	    
	    KundeSonderwunschModel kundeSonderwunschModel = KundeSonderwunschModel.getInstance(connector);
	    List<Sonderwunsch> listSonderwunsch = SonderwunschModel.getInstance(connector).getSonderwunschByKategorie("Grundriss");

	    for(Sonderwunsch sw : listSonderwunsch) {
	        CheckBox correspondingCheckBox = findCheckBoxForSonderwunsch(sw.getBeschreibung());
	        if(correspondingCheckBox == null) continue; // If no corresponding checkbox found, skip to next Sonderwunsch

	        boolean isSelected = correspondingCheckBox.isSelected();
	        KundeSonderwunsch existingKundeSonderwunsch = kundeSonderwunschModel.getKundeSonderwunschByKundeAndSonderwunsch(kunde, sw.getId());

	        if(sw.getBeschreibung().equals("Tür in der Wand zwischen Küche und Essbereich") && !GrundrissValidierung.hasTuerZwischenKuecheUndEssbereich(findCheckBoxForSonderwunsch("Wand zur Abtrennung der Küche von dem Essbereich").isSelected(), isSelected)){
	        	isSelected = false;
	        	chckBxWandKueche.setSelected(false);
	        }
	        else if(sw.getBeschreibung().equals("Großes Zimmer im OG statt zwei kleinen Zimmern")){
	        	isSelected = false;
	        	chckBxOg.setSelected(false);
	        }
	        else if(sw.getBeschreibung().equals("Abgetrennter Treppenraum im DG")){
	        	isSelected = false;
	        	chckBxTreppenraumDg.setSelected(false);
	        }
	        else if(sw.getBeschreibung().equals("Vorrichtung eines Bades im DG")){
	        	isSelected = false;
	        	chckBxVBad.setSelected(false);
	        }
	        else if(sw.getBeschreibung().equals("Ausführung eines Bades im DG") && !GrundrissValidierung.isAusfuehrungBadDG(findCheckBoxForSonderwunsch("Vorrichtung eines Bades im DG").isSelected(), isSelected)){
	        	isSelected = false;
	        	chckBxABad.setSelected(false);
	        }else {
	        
		        if (isSelected && existingKundeSonderwunsch == null) {
		            // Sonderwunsch is selected and not yet in the database for this customer
		        	System.out.println("Sonderwunsch is selected and not yet in the database for this customer");
		            kundeSonderwunschModel.addKundeSonderwunsch(kunde, sw.getId(), 1);
		        } else if (!isSelected && existingKundeSonderwunsch != null) {
		            // Sonderwunsch is not selected but exists in the database for this customer
		        	System.out.println("Sonderwunsch is not selected but exists in the database for this customer");
		            kundeSonderwunschModel.updateKundeSonderwunschByKundeAndSonderwunsch(kunde, sw.getId(), 0);
		        } // If Sonderwunsch is selected and already exists, no action needed
		        
		        System.out.println(isSelected);
	    }
	     
	    }
	}
   
  
   
	private CheckBox findCheckBoxForSonderwunsch(String beschreibung) {
	    // Map Sonderwunsch descriptions to their corresponding checkboxes
	    switch(beschreibung) {
	        case "Wand zur Abtrennung der Küche von dem Essbereich":
	            return chckBxWandKueche;
	        case "Tür in der Wand zwischen Küche und Essbereich":
	            return chckBxTuerKueche;
	        case "Großes Zimmer im OG statt zwei kleinen Zimmern":
	            return chckBxOg;
	        case "Abgetrennter Treppenraum im DG":
	            return chckBxTreppenraumDg;
	        case "Vorrichtung eines Bades im DG":
	            return chckBxVBad;
	        case "Ausführung eines Bades im DG":
	            return chckBxABad;
	        default:
	            return null;
	    }
	}
  
    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
    	super.initKomponenten(); 
       	super.getLblSonderwunsch().setText("Grundriss-Varianten");
       	
       	super.getGridPaneSonderwunsch().add(lblWandKueche, 0, 1);
    	super.getGridPaneSonderwunsch().add(txtPreisWandKueche, 1, 1);
    	txtPreisWandKueche.setText(Integer.toString(this.preise[0]));
    	txtPreisWandKueche.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblWandKuecheEuro, 2, 1);
    	super.getGridPaneSonderwunsch().add(chckBxWandKueche, 3, 1);
    	
     	super.getGridPaneSonderwunsch().add(lblTuerKueche  , 0, 2);
    	super.getGridPaneSonderwunsch().add(txtPreisTuerKueche  , 1, 2);
    	txtPreisTuerKueche.setText(Integer.toString(this.preise[1]));
    	txtPreisTuerKueche.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblTuerKuecheEuro, 2, 2);
    	super.getGridPaneSonderwunsch().add(chckBxTuerKueche  , 3, 2);
    	
     	super.getGridPaneSonderwunsch().add(lblOg , 0, 3);
    	super.getGridPaneSonderwunsch().add(txtPreisOg , 1, 3);
    	txtPreisOg.setText(Integer.toString(this.preise[2]));
    	txtPreisOg.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblOgEuro, 2, 3);
    	super.getGridPaneSonderwunsch().add(chckBxOg, 3, 3);
    	
     	super.getGridPaneSonderwunsch().add(lblTreppenraumDg, 0, 4);
    	super.getGridPaneSonderwunsch().add(txtPreisTreppenraumDg, 1, 4);
    	txtPreisTreppenraumDg.setText(Integer.toString(this.preise[3]));
    	txtPreisTreppenraumDg.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblTreppenraumDgEuro, 2, 4);
    	super.getGridPaneSonderwunsch().add(chckBxTreppenraumDg, 3, 4);
    	
     	super.getGridPaneSonderwunsch().add(lblVBad, 0, 5);
    	super.getGridPaneSonderwunsch().add(txtPreisVBad, 1, 5);
    	txtPreisVBad.setText(Integer.toString(this.preise[4]));
    	txtPreisVBad.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblVBadEuro, 2, 5);
    	super.getGridPaneSonderwunsch().add(chckBxVBad, 3, 5);
    	
    	super.getGridPaneSonderwunsch().add(lblABad, 0, 6);
    	super.getGridPaneSonderwunsch().add(txtPreisABad, 1, 6);
    	txtPreisABad.setText(Integer.toString(this.preise[5]));
    	txtPreisABad.setEditable(false);
    	super.getGridPaneSonderwunsch().add(lblABadEuro, 2, 6);
    	super.getGridPaneSonderwunsch().add(chckBxABad, 3, 6);
    	
    	
    	//menubar
    }  
    
    /**
	 * macht das GrundrissView-Objekt sichtbar.
	 */
	public void oeffneGrundrissView(){ 
		super.oeffneBasisView();
	}
    
	

    //private void leseGrundrissSonderwuensche(){
    //	this.grundrissControl.leseGrundrissSonderwuensche();
    //}
    
 	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	protected void berechneUndZeigePreisSonderwuensche(){
  		// Es wird erst die Methode pruefeKonstellationSonderwuensche(int[] ausgewaehlteSw)
  		// aus dem Control aufgerufen, dann der Preis berechnet.
  		int finalPrice = 0;

  	    if (chckBxWandKueche.isSelected()) {
  	        finalPrice += Integer.parseInt(txtPreisWandKueche.getText());
  	    }
  	    if (chckBxTuerKueche.isSelected()) {
  	        finalPrice += Integer.parseInt(txtPreisTuerKueche.getText());
  	    }
  	    if (chckBxOg.isSelected()) {
  	        finalPrice += Integer.parseInt(txtPreisOg.getText());
  	    }
  	    if (chckBxTreppenraumDg.isSelected()) {
  	        finalPrice += Integer.parseInt(txtPreisTreppenraumDg.getText());
  	    }
  	    if (chckBxVBad.isSelected()) {
  	        finalPrice += Integer.parseInt(txtPreisVBad.getText());
  	    }
  	    if (chckBxABad.isSelected()) {
  	        finalPrice += Integer.parseInt(txtPreisABad.getText());
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
			this.saveSonderwuensche(KundeModel.getInstance(connector).getKundeByKundennummer("12345678").getId());
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
  	            writeSonderwunschToCsv(writer, "Wand zur Abtrennung der Küche von dem Essbereich", txtPreisWandKueche, chckBxWandKueche);
  	            writeSonderwunschToCsv(writer, "Tür in der Wand zwischen Küche und Essbereich", txtPreisTuerKueche, chckBxTuerKueche);
  	            writeSonderwunschToCsv(writer, "Großes Zimmer im OG statt zwei kleinen Zimmern", txtPreisOg, chckBxOg);
  	            writeSonderwunschToCsv(writer, "Abgetrennter Treppenraum im DG", txtPreisTreppenraumDg, chckBxTreppenraumDg);
  	            writeSonderwunschToCsv(writer, "Vorrichtung eines Bades im DG", txtPreisVBad, chckBxVBad);
  	            writeSonderwunschToCsv(writer, "Ausführung eines Bades im DG", txtPreisABad, chckBxABad);

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


