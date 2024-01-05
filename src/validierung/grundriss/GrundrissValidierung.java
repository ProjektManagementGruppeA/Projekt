package validierung.grundriss;

public class GrundrissValidierung {
	
	public static boolean hasDachgeschoss(boolean hasDachgeschoss) {
		return hasDachgeschoss == true;	
	}
	
	public static boolean hasWandZwischenKuecheUndEssbereich(boolean hasWandZwischenKuecheUndEssbereich) {
		return hasWandZwischenKuecheUndEssbereich == true;	
	}
	
	public static boolean hasTuerZwischenKuecheUndEssbereich(boolean hasTuerZwischenKuecheUndEssbereich, boolean hasWandZwischenKuecheUndEssbereich) {
		return (hasTuerZwischenKuecheUndEssbereich && hasWandZwischenKuecheUndEssbereich) == true;
	}

	public static boolean hasGroßesZimmerInOG(boolean hasGroßesZimmerInOG) {
		return hasGroßesZimmerInOG == true;
	}
	
	public static boolean isAbgetrennterTreppenRaumDG(boolean hasAbgetrennterTreppenRaumDG) {
		return hasAbgetrennterTreppenRaumDG == true;
	}
	
	public static boolean isVorrichtungBadDG(boolean isVorrichtungBadDG) {
		return isVorrichtungBadDG == true;
	}
	
	public static boolean isAusfuehrungBadDG(boolean isAusfuehrungBadDG, boolean isVorrichtungBadDG ) {
		return (isVorrichtungBadDG && isAusfuehrungBadDG) == true;
	}
	
}
