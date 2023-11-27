package business.kunde;

import business.DatabaseConnector;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class KundeModel {
    private static KundeModel instance; // Die Singleton-Instanz
    private MongoCollection<Document> collection;

    // Privater Konstruktor verhindert Instanziierung von außen
    private KundeModel(DatabaseConnector connector) {
        MongoDatabase database = connector.getDatabase();
        collection = database.getCollection("kunden");
    }

    /**
     * Statische Methode, um die einzige Instanz der Klasse zu erhalten.
     * @param connector Eine Instanz des DatabaseConnector für die Datenbankverbindung.
     * @return KundeDAO Die einzige Instanz dieser Klasse.
     */
    public static KundeModel getInstance(DatabaseConnector connector) {
        if (instance == null) {
            instance = new KundeModel(connector);
        }
        return instance;
    }

    /**
     * Speichert ein Kunde-Objekt in der Datenbank.
     * 
     * @param kunde Das zu speichernde Kunde-Objekt.
     * @return ObjectId Die Datenbank-ID des gespeicherten Kunden.
     * @throws IllegalArgumentException, wenn das Kunde-Objekt null ist.
     */
    public ObjectId addKunde(Kunde kunde) {
        Document doc = new Document("kundennummer", kunde.getKundennummer())
                .append("haustypId", kunde.getHaustypId())
                .append("vorname", kunde.getVorname())
                .append("nachname", kunde.getNachname())
                .append("telefonnummer", kunde.getTelefonnummer())
                .append("email", kunde.getEmail());

        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Ermittelt einen Kunden anhand seiner Kundennummer.
     *
     * @param kundennummer Die Kundennummer des zu suchenden Kunden.
     * @return Kunde Das Kundenobjekt, das der angegebenen Kundennummer entspricht.
     */
    public Kunde getKundeByKundennummer(String kundennummer) {
        Document doc = collection.find(Filters.eq("kundennummer", kundennummer)).first();
        return documentToKunde(doc);
    }

    /**
     * Ermittelt einen Kunden anhand seiner Datenbank-ID.
     *
     * @param id Die ID des Kunden.
     * @return Kunde Das Kundenobjekt mit der angegebenen ID.
     */
    public Kunde getKundeById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        return documentToKunde(doc);
    }

    /**
     * Ermittelt einen Kunden anhand seiner E-Mail-Adresse.
     *
     * @param email Die E-Mail-Adresse des Kunden.
     * @return Kunde Das Kundenobjekt mit der angegebenen E-Mail-Adresse.
     */
    public Kunde getKundeByEmail(String email) {
        Document doc = collection.find(Filters.eq("email", email)).first();
        return documentToKunde(doc);
    }

    /**
     * Ruft alle Kunden aus der Datenbank ab.
     *
     * @return List<Kunde> Eine Liste aller Kunden.
     */
    public List<Kunde> getAllKunden() {
        List<Kunde> kunden = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();
        
        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                kunden.add(documentToKunde(doc));
            }
        } finally {
            cursor.close();
        }
        
        return kunden;
    }

    /**
     * Aktualisiert die Informationen eines Kunden in der Datenbank.
     *
     * @param id Der ID des zu aktualisierenden Kunden.
     * @param kunde Die neuen Informationen des Kunden.
     * @return boolean Wahr, wenn das Update erfolgreich war, falsch andernfalls.
     */
    public boolean updateKunde(ObjectId id, Kunde kunde) {
        Document doc = new Document("kundennummer", kunde.getKundennummer())
                .append("haustypId", kunde.getHaustypId())
                .append("vorname", kunde.getVorname())
                .append("nachname", kunde.getNachname())
                .append("telefonnummer", kunde.getTelefonnummer())
                .append("email", kunde.getEmail());

        UpdateResult result = collection.updateOne(Filters.eq("_id", id), new Document("$set", doc));
        return result.getModifiedCount() > 0;
    }

    /**
     * Löscht einen Kunden aus der Datenbank unter Verwendung seiner ID.
     *
     * @param id Die ID des zu löschenden Kunden.
     * @return boolean Wahr, wenn der Kunde gelöscht wurde, falsch andernfalls.
     */
    public boolean deleteKunde(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    /**
     * Wandelt ein Document-Objekt in ein Kunde-Objekt um.
     *
     * @param doc Das Document-Objekt aus der MongoDB, das umgewandelt wird.
     * @return kunde Ein Kunde-Objekt, das aus dem Document erstellt wurde.
     */
    private Kunde documentToKunde(Document doc) {
        if (doc == null) {
            return null;
        }

        ObjectId haustypId = doc.getObjectId("haustypId");
        String kundennummer = doc.getString("kundennummer");
        // int hausnummer = doc.getInteger("hausnummer");
        String vorname = doc.getString("vorname");
        String nachname = doc.getString("nachname");
        String telefonnummer = doc.getString("telefonnummer");
        String email = doc.getString("email");
        ObjectId id = doc.getObjectId("_id");

        Kunde kunde = new Kunde(kundennummer, haustypId, vorname, nachname, telefonnummer, email);
        kunde.setId(id);

        return kunde;
    }

    /**
	 * gibt die Ueberschrift zum Grundfenster mit den Kundendaten heraus
	 * @return String, Ueberschrift zum Grundfenster mit den Kundendaten 
	 */
	public String getUeberschrift(){
		return "Verwaltung der Sonderwunschlisten";
	}
}
