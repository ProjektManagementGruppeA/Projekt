package business.sonderwunsch;

import business.DatabaseConnector;
import business.sonderwunschKategorie.SonderwunschKategorie;
import business.sonderwunschKategorie.SonderwunschKategorieModel;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class SonderwunschModel {
    private static SonderwunschModel instance;
    private MongoCollection<Document> collection;
    private DatabaseConnector dbconnector;

    /**
     * Konstruktor, der eine Verbindung zur Sonderwunsch-Sammlung in der MongoDB herstellt.
     *
     * @param connector Der Datenbankverbinder, der die Verbindung zur MongoDB bereitstellt.
     */
    private SonderwunschModel(DatabaseConnector connector) {
        dbconnector = connector;
        MongoDatabase database = connector.getDatabase();
        collection = database.getCollection("sonderwuensche");
    }

    public static SonderwunschModel getInstance(DatabaseConnector connector) {
        if (instance == null) {
            instance = new SonderwunschModel(connector);
        }
        return instance;
    }

    /**
     * Fügt einen neuen Sonderwunsch in die Datenbank ein.
     *
     * @param sonderwunsch Das Sonderwunsch-Objekt, das hinzugefügt werden soll.
     * @return ObjectId Die generierte ID des hinzugefügten Sonderwunschs in der Datenbank.
     */
    public ObjectId addSonderwunsch(Sonderwunsch sonderwunsch) {
        Document doc = new Document("beschreibung", sonderwunsch.getBeschreibung())
                .append("preis", sonderwunsch.getPreis());
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Ermittelt einen Sonderwunsch anhand seiner ID.
     *
     * @param id Die ObjectId des Sonderwunschs.
     * @return Sonderwunsch Das Objekt des ermittelten Sonderwunschs, falls vorhanden, sonst null.
     */
    public Sonderwunsch getSonderwunschById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        if (doc != null) {
            SonderwunschKategorieModel kategorieModel = SonderwunschKategorieModel.getInstance(dbconnector); // Instanz des Kategorie-Modells
            SonderwunschKategorie kategorie = kategorieModel.getSonderwunschKategorieById(doc.getObjectId("kategorieId"));
            Sonderwunsch sw = documentToSonderwunsch(doc);
            sw.setKategorieId(kategorie.getId());
        }
        return documentToSonderwunsch(doc);
    }

    public List<Sonderwunsch> getSonderwunschByKategorie(String kategorieName) {
        List<Sonderwunsch> sonderwuensche = new ArrayList<>();
        SonderwunschKategorieModel kategorieModel = SonderwunschKategorieModel.getInstance(dbconnector);
        SonderwunschKategorie kategorie = kategorieModel.getSonderwunschKategorieByName(kategorieName);
    
        if (kategorie == null) {
            return sonderwuensche; // Return empty list if category not found
        }
    
        ObjectId kategorieId = kategorie.getId();
        for (Document doc : collection.find(Filters.eq("kategorieId", kategorieId))) {
            sonderwuensche.add(documentToSonderwunsch(doc));
        }
    
        return sonderwuensche;
    }

    public List<ObjectId> getSonderwunschIdsByKategorieId(ObjectId kategorieId) {
        List<ObjectId> sonderwunschIds = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("kategorieId", kategorieId))) {
            ObjectId sonderwunschId = doc.getObjectId("_id");
            sonderwunschIds.add(sonderwunschId);
        }
        return sonderwunschIds;
    }
    

    /**
     * Ermittelt alle Sonderwünsche in der Datenbank.
     *
     * @return List<Sonderwunsch> Eine Liste von Sonderwunsch-Objekten.
     */
    public List<Sonderwunsch> getAllSonderwunsch() {
        List<Sonderwunsch> sonderwuensche = new ArrayList<>();
        for (Document doc : collection.find()) {
            sonderwuensche.add(documentToSonderwunsch(doc));
        }
        return sonderwuensche;
    }

    /**
     * Aktualisiert die Beschreibung eines Sonderwunschs anhand seiner ID.
     *
     * @param id Der ObjectId des zu aktualisierenden Sonderwunschs.
     * @param sonderwunsch Das aktualisierte Sonderwunsch-Objekt.
     * @return boolean Gibt true zurück, wenn die Aktualisierung erfolgreich war, sonst false.
     */
    public boolean updateSonderwunsch(ObjectId id, Sonderwunsch sonderwunsch) {
        Document doc = new Document("beschreibung", sonderwunsch.getBeschreibung())
                .append("preis", sonderwunsch.getPreis());
        UpdateResult result = collection.updateOne(Filters.eq("_id", id), new Document("$set", doc));
        return result.getModifiedCount() > 0;
    }

    /**
     * Löscht einen Sonderwunsch aus der Datenbank anhand seiner ID.
     *
     * @param id Die ObjectId des zu löschenden Sonderwunschs.
     * @return boolean Gibt true zurück, wenn der Sonderwunsch erfolgreich gelöscht wurde, sonst false.
     */
    public boolean deleteSonderwunsch(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    /**
     * Wandelt ein Document in ein Sonderwunsch-Objekt um.
     *
     * @param doc Das MongoDB Document-Objekt, das umgewandelt wird.
     * @return Sonderwunsch Das umgewandelte Sonderwunsch-Objekt.
     */
    private Sonderwunsch documentToSonderwunsch(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        ObjectId kategorieId = doc.getObjectId("kategorieId");
        String beschreibung = doc.getString("beschreibung");
        Integer preis = doc.getInteger("preis");

        Sonderwunsch sw = new Sonderwunsch(kategorieId, beschreibung, preis);
        sw.setId(id);

        return sw;
    }
}
