package validierung.sonderwunsch;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum SonderwuenscheInnenTuer {
	Klarglas, Milchglas, Holztuer
}

public class InnentureValidierung {

	public static boolean isValidKategorieName(String Innentüren) {
		Pattern pattern = Pattern.compile("Innentüren");
		Matcher matcher = pattern.matcher(Innentüren);
		return matcher.find();
	}

	public static boolean isValidPreis(SonderwuenscheInnenTuer name, double preis) {

		switch (name) {
		case Klarglas:
			if (preis != 460)
				return false;
			break;
		case Milchglas:
			if (preis != 560)
				return false;
			break;
		case Holztuer:
			if (preis != 660)
				return false;
			break;
		default:
			return false;
		}
		return true;
	}

	/*
	 * Mehrpreis für die Ausführung eines Glasausschnitts (Klarglas) in einer
	 * Innentür: 460,- Euro je Tür 4.2) Mehrpreis für die Ausführung eines
	 * Glasausschnitts (Milchglas) in einer Innentür: 560,- Euro je Tür 4.3)
	 * Innentür zur Garage als Holztür:
	 */

}
