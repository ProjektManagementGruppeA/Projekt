package validierung.fensterUndAussentueren;

public class FensterUndAussentuerenValidierung {

	public static boolean hasDachgeschoss(boolean hasDachgeschoss) {
		return hasDachgeschoss == true;	
	}
	
	public static boolean hasVorbereitungElektrischeAntriebeEG(boolean hasVorbereitungElektrischeAntriebeEG) {
		return hasVorbereitungElektrischeAntriebeEG == true;
	}
	
	public static boolean hasVorbereitungElektrischeAntriebeOG(boolean hasVorbereitungElektrischeAntriebeOG) {
		return hasVorbereitungElektrischeAntriebeOG == true;
	}
	
	public static boolean hasVorbereitungElektrischeAntriebeDG(boolean hasVorbereitungElektrischeAntriebeDG) {
		return hasVorbereitungElektrischeAntriebeDG ==true;
	}
	
	public static boolean validElektrischeRolladenEG(boolean hasVorbereitungElektrischeRolladenEG, boolean hasElektrischeRolladenEG) {
		return (hasVorbereitungElektrischeRolladenEG && hasElektrischeRolladenEG) == true;
	}
	
	public static boolean validElektrischeRolladenOG(boolean hasVorbereitungElektrischeRolladenOG, boolean hasElektrischeRolladenOG) {
		return (hasVorbereitungElektrischeRolladenOG && hasElektrischeRolladenOG) == true;
	}
	
	public static boolean validElektrischeRolladenDG(boolean hasVorbereitungElektrischeRolladenDG, boolean hasElektrischeRolladenDG) {
		return (hasVorbereitungElektrischeRolladenDG && hasElektrischeRolladenDG) == true;
	}

}
