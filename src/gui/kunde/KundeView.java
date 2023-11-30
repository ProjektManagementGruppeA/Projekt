package gui.kunde;

import org.bson.types.ObjectId;

import business.haustyp.Haustyp;
import business.haustyp.HaustypModel;
import business.kunde.*;

import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Klasse, welche das Grundfenster mit den Kundendaten bereitstellt.
 */
public class KundeView{
 
	// das Control-Objekt des Grundfensters mit den Kundendaten
	private KundeControl kundeControl;
	// das Model-Objekt des Grundfensters mit den Kundendaten
	private KundeModel kundeModel;
	private HaustypModel haustypModel;

    //---Anfang Attribute der grafischen Oberflaeche---
	private BorderPane borderPane 		= new BorderPane();
	private GridPane gridPane 			= new GridPane();
	private Label lblKunde    	      	= new Label("Kunde");
    private Label lblNummerHaus     	= new Label("Plannummer des Hauses");
    private ComboBox<Integer> 
        cmbBxNummerHaus                 = new ComboBox<Integer>();
    private CheckBox cbxDachgeschoss 	= new CheckBox("Hat ein Dachgeschoss");
    private Label lblKundennummer      	= new Label("Kundennummer");
    private TextField txtKundennummer 	= new TextField();
    private Label lblVorname         	= new Label("Vorname");
    private TextField txtVorname     	= new TextField();
    private Label lblNachname 			= new Label("Nachname");
    private TextField txtNachname 		= new TextField();
    private Label lblTelefonnummer      = new Label("Telefonnummer");
    private TextField txtTelefonnummer  = new TextField();
    private Label lblEmail      		= new Label("E-Mail");
    private TextField txtEmail			= new TextField();
    private Button btnAnlegen	 	  	= new Button("Anlegen");
    private Button btnAendern 	      	= new Button("�ndern");
    private Button btnLoeschen 	 		= new Button("L�schen");
    private MenuBar mnBar 			  	= new MenuBar();
    private Menu mnSonderwuensche    	= new Menu("Sonderw�nsche");
    private MenuItem mnItmGrundriss  	= new MenuItem("Grundrissvarianten");
    private MenuItem mnItmCsvExport 	= new MenuItem("Csv Export");
    //-------Ende Attribute der grafischen Oberflaeche-------
  
    /**
     * erzeugt ein KundeView-Objekt und initialisiert die Steuerelemente der Maske
     * @param kundeControl KundeControl, enthaelt das zugehoerige Control
     * @param primaryStage Stage, enthaelt das Stage-Objekt fuer diese View
     * @param kundeModel KundeModel, enthaelt das zugehoerige Model
    */
    public KundeView (KundeControl kundeControl, Stage primaryStage, 
    	KundeModel kundeModel,HaustypModel haustypModel){
        this.kundeControl = kundeControl;
        this.kundeModel = kundeModel;
        this.haustypModel = haustypModel;
        
        primaryStage.setTitle(this.kundeModel.getUeberschrift());	
	    Scene scene = new Scene(borderPane, 550, 400);
	    primaryStage.setScene(scene);
        primaryStage.show();

	    this.initKomponenten();
	    this.initListener();
    }

 
    /* initialisiert die Steuerelemente auf der Maske */
    private void initKomponenten(){
    	borderPane.setCenter(gridPane);
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(25, 25, 25, 25));
	    
	    
	    gridPane.add(lblNummerHaus, 0, 2);
	    gridPane.add(cmbBxNummerHaus, 1, 2);
	    cmbBxNummerHaus.setMinSize(150,  25);
	    cmbBxNummerHaus.setItems(this.haustypModel.getPlannummern());
	    
	    gridPane.add(cbxDachgeschoss, 2, 2);
       	
	    gridPane.add(lblKunde, 0, 1);
       	lblKunde.setMinSize(150, 40);
	    lblKunde.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	    gridPane.add(lblKundennummer, 0, 3);
	    gridPane.add(txtKundennummer, 1, 3);
	    gridPane.add(lblVorname, 0, 4);
	    gridPane.add(txtVorname, 1, 4);
	    gridPane.add(lblNachname, 0, 5);
	    gridPane.add(txtNachname, 1, 5);
	    gridPane.add(lblTelefonnummer, 0, 6);
	    gridPane.add(txtTelefonnummer, 1, 6);
	    gridPane.add(lblEmail, 0, 7);
	    gridPane.add(txtEmail, 1, 7);
	    // Buttons
	    gridPane.add(btnAnlegen, 0, 8);
	    btnAnlegen.setMinSize(150,  25);
	    gridPane.add(btnAendern, 1, 8);
	    btnAendern.setMinSize(150,  25);
	    gridPane.add(btnLoeschen, 2, 8);
	    btnLoeschen.setMinSize(150,  25);
	    // MenuBar und Menu
	    borderPane.setTop(mnBar);
	    mnBar.getMenus().add(mnSonderwuensche);
	    mnSonderwuensche.getItems().add(mnItmGrundriss);
	    mnSonderwuensche.getItems().add(mnItmCsvExport);
    }

    /* initialisiert die Listener zu den Steuerelementen auf de Maske */
    private void initListener(){
    	cmbBxNummerHaus.setOnAction(aEvent-> {
    		 holeInfoDachgeschoss();  
    		 leseKunden();
     	});
    	cbxDachgeschoss.setOnAction(aEvent-> {
    		aendereHaustyp();
    	});
       	btnAnlegen.setOnAction(aEvent-> {
 	        legeKundenAn();
	    });
    	btnAendern.setOnAction(aEvent-> {
           	aendereKunden();
	    });
       	btnLoeschen.setOnAction(aEvent-> { 
           	loescheKunden();
	    });
      	mnItmGrundriss.setOnAction(aEvent-> {
 	        kundeControl.oeffneGrundrissControl(); 
	    });
      	mnItmCsvExport.setOnAction(aEvent-> {
       		exportAsCsv();
       	});
    }
    
    private void holeInfoDachgeschoss(){
    	int hausnummer = cmbBxNummerHaus.getValue();
    	Haustyp haustyp = haustypModel.getHaustypByHausnummer(hausnummer);
    	if (haustyp != null) {
    		cbxDachgeschoss.setSelected(haustyp.isHatDachgeschoss());
    	}
    	else {
    		haustyp = new Haustyp(hausnummer, false);
    		this.haustypModel.addHaustyp(haustyp);
    	}
    }
    
    
    private void leseKunden(){
    	int hausnummer = cmbBxNummerHaus.getValue();
    	Kunde kunde = kundeControl.leseKunde(hausnummer);
    	if (kunde != null) {
	    	txtKundennummer.setText(kunde.getKundennummer());
	    	txtEmail.setText(kunde.getEmail());
	    	txtVorname.setText(kunde.getVorname());
	    	txtNachname.setText(kunde.getNachname());
	    	txtTelefonnummer.setText(kunde.getTelefonnummer());
    	} else {
    		leereFelder();
    	}
    }
    
    private void legeKundenAn(){
    	ObjectId hausNummer = haustypModel.getHaustypByHausnummer(cmbBxNummerHaus.getValue()).getId();
    	String kundenNummer = txtKundennummer.getText();
    	String vorname = txtVorname.getText();
    	String nachname = txtNachname.getText();
    	String telefonnummer = txtTelefonnummer.getText();
    	String email = txtEmail.getText();
        Kunde kunde = new Kunde(kundenNummer, hausNummer, vorname, nachname, telefonnummer, email);
        
        kundeControl.speichereKunden(kunde);
	}
    
    private void aendereKunden(){
  		int hausnummer = cmbBxNummerHaus.getValue();
    	Kunde kunde = kundeControl.leseKunde(hausnummer);
    	kunde.setKundennummer(txtKundennummer.getText());
    	kunde.setVorname(txtVorname.getText());
    	kunde.setNachname(txtNachname.getText());
    	kunde.setTelefonnummer(txtTelefonnummer.getText());
    	kunde.setEmail(txtEmail.getText());
    	kundeControl.aendereKunden(kunde);
   	}
    
    private void aendereHaustyp() {
    	int hausnummer = cmbBxNummerHaus.getValue();
    	boolean dachgeschossvorhanden = cbxDachgeschoss.isSelected();
    	Haustyp haustyp = new Haustyp(hausnummer, dachgeschossvorhanden);
    	ObjectId id = haustypModel.getHaustypByHausnummer(hausnummer).getId();
    	haustypModel.updateHaustyp(id, haustyp);
    }
   	
  	
   	private void loescheKunden(){
   		int hausnummer = cmbBxNummerHaus.getValue();
    	Kunde kunde = kundeControl.leseKunde(hausnummer);
    	boolean erfolgreich = kundeControl.loescheKunde(kunde);
    	if (erfolgreich) {
    		leereFelder();
    	}
   	}
   	
   	private void leereFelder() {
   		txtKundennummer.setText("");
    	txtEmail.setText("");
    	txtVorname.setText("");
    	txtNachname.setText("");
    	txtTelefonnummer.setText("");
   	}
   	
   	private void exportAsCsv() {
	}
   	
   /** zeigt ein Fehlermeldungsfenster an
    * @param ueberschrift, Ueberschrift fuer das Fehlermeldungsfenster
    * @param meldung, String, welcher die Fehlermeldung enthaelt
    */
    public void zeigeFehlermeldung(String ueberschrift, String meldung){
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Fehlermeldung");
        alert.setHeaderText(ueberschrift);
        alert.setContentText(meldung);
        alert.show();
    }
    
    public void zeigeErfolg(String ueberschrift, String meldung){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Erfolgreich");
        alert.setHeaderText(ueberschrift);
        alert.setContentText(meldung);
        alert.show();
    }

}


