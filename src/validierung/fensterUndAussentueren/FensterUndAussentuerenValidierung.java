package validierung.fensterUndAussentueren;

import javafx.scene.control.CheckBox;

public class FensterUndAussentuerenValidierung {
	
	public static boolean hasDachgeschoss(boolean hasDachgeschoss) {
		return hasDachgeschoss == true;	
	}
	
	public static void pruefeVorbereitungEG(CheckBox vorbereitungEG, CheckBox ElektroEG) {
		if(vorbereitungEG.isSelected()) {
			ElektroEG.setDisable(false);
		}else {
			ElektroEG.setDisable(true);
			ElektroEG.setSelected(false);
		}
	}
	
	public static void pruefeVorbereitungOG(CheckBox vorbereitungOG, CheckBox ElektroOG) {
		if(vorbereitungOG.isSelected()) {
			ElektroOG.setDisable(false);
		}else {
			ElektroOG.setDisable(true);
			ElektroOG.setSelected(false);
		}
	}
	
	public static void pruefeVorbereitungDG(CheckBox vorbereitungDG, CheckBox ElektroDG) {
		if(vorbereitungDG.isSelected()) {
			ElektroDG.setDisable(false);
		}else {
			ElektroDG.setDisable(true);
			ElektroDG.setSelected(false);
		}
	}

}
