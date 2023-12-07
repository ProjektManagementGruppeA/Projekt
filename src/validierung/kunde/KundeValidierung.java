package validierung.kunde;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KundeValidierung {
	
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile("^\\w+@\\w+\\.\\w{2,}$");
		Matcher matcher = pattern.matcher(email);
		return matcher.find();
	}
	
	public static boolean isValidName(String name) {
		Pattern pattern = Pattern.compile("^[a-zA-ZäöüÄÖÜß][a-zA-ZäöüÄÖÜß-]+$");
		Matcher matcher = pattern.matcher(name);
		return matcher.find();
	}
	
	public static boolean isValidPhoneNumber(String phonenumber) {
		Pattern pattern = Pattern.compile("^(\\+49|0049|0)?[1-9][0-9]{1,14}$");
		Matcher matcher = pattern.matcher(phonenumber);
		return matcher.find();
	}
}

