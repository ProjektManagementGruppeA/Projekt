package business.kunde;
import org.bson.types.ObjectId;

public class Kunde {
	
	private ObjectId id;
	private ObjectId haustypId;
	private String kundennummer;
	private String vorname;
	private String nachname;
	private String telefonnummer;
	private String email;

	public Kunde(String kundennummer, ObjectId haustypId, String vorname, String nachname,
				String telefonnummer, String email){
		this.kundennummer = kundennummer;
		this.haustypId = haustypId;
		this.vorname = vorname;
		this.nachname = nachname;
		this.telefonnummer = telefonnummer;
		this.email = email;
	}

	public ObjectId getId(){
		return id;
	}

	public void setId(ObjectId id){
		this.id = id;
	}
	

	public String getKundennummer(){
		return kundennummer;
	}

	public void setKundennummer(String kundennummer){
		this.kundennummer = kundennummer;
	}

	public ObjectId getHaustypId() {
		return haustypId;
	}

	public void setHaustypId(ObjectId haustypId) {
		this.haustypId = haustypId;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}
	
	public String getNachname() {
		return nachname;
	}
	
	public void setNachname(String nachname) {
		this.nachname = nachname;
	}
	
	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
