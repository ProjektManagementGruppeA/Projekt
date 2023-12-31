package gui.basis;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

/**
 * Klasse, welche die Basis fuer die Fenster zu den Sonderwuenschen bereitstellt.
 */
public abstract class BasisView {
 
    //---Anfang Attribute der grafischen Oberflaeche---
	protected Stage sonderwunschStage;
	private BorderPane borderPane 		= new BorderPane();
	private GridPane gridPane 		 	= new GridPane();
	private GridPane gridPaneButtons 	= new GridPane();
   	private Label lblSonderwunsch   	= new Label("Sonderwunsch");
    private Button btnBerechnen 	 	= new Button("Preis berechnen");
    private Button btnSpeichern 	 	= new Button("Speichern");
    private Button btnExport	 		= new Button("Export as csv");
   
    //-------Ende Attribute der grafischen Oberflaeche-------
  
   /**
    * erzeugt ein BasisView-Objekt
    */
    public BasisView(Stage sonderwunschStage){
    	this.sonderwunschStage = sonderwunschStage;
	    Scene scene = new Scene(borderPane, 600, 500);
	    sonderwunschStage.setScene(scene);
	
	    this.initListener();
    }

    /* initialisiert die Steuerelemente auf der Maske */
    protected void initKomponenten(){
      	borderPane.setCenter(gridPane);
      	borderPane.setBottom(gridPaneButtons);
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    gridPane.setPadding(new Insets(25, 25, 25, 25));
	    gridPane.setStyle("-fx-background-color: #FED005;");
        gridPaneButtons.setHgap(10);
	    gridPaneButtons.setPadding(new Insets(25, 25, 25, 50));
	    
    	gridPane.add(lblSonderwunsch, 0, 0);
    	lblSonderwunsch.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	    // Buttons
    	gridPaneButtons.add(btnExport, 0, 0);
    	gridPaneButtons.add(btnBerechnen, 1, 0);
	    btnBerechnen.setMinSize(150,  25);
    	gridPaneButtons.add(btnSpeichern, 2, 0);
	    btnSpeichern.setMinSize(150,  25);
	    
	    btnExport.setMinSize(150,  25);
    }  
    
    /* Es muessen die Listener implementiert werden. */
    protected void initListener(){
       	btnBerechnen.setOnAction(aEvent -> {
    		berechneUndZeigePreisSonderwuensche();
     	});
        btnSpeichern.setOnAction(aEvent -> {
    		speichereSonderwuensche();
    	});
        btnExport.setOnAction(aEvent -> {
    		try {
				speichereCsv();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	});
    }
    
    protected GridPane getGridPaneSonderwunsch() {
  		return this.gridPane;
  	}

  	protected Label getLblSonderwunsch() {
  		return lblSonderwunsch;
  	}
  	
  	/*
  	 * macht das BasisView-Objekt sichtbar.
  	 */
  	protected void oeffneBasisView(){ 
	    sonderwunschStage.show();
  	}
  	     	
  	/* berechnet den Preis der ausgesuchten Sonderwuensche und zeigt diesen an */
  	protected abstract void berechneUndZeigePreisSonderwuensche();
  	
   	/* speichert die ausgesuchten Sonderwuensche in der Datenbank ab */
  	protected abstract void speichereSonderwuensche();
  	
  	protected abstract void speichereCsv() throws IOException;
  	
  	protected void messageboxSpeichernErfolgreich(String sonderwunsch) {
  	  Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    alert.setTitle(sonderwunsch);
	    alert.setHeaderText(null);
	    alert.setContentText("speichern erfolgreich");
	    alert.show();
  	};
  	
 	
}


