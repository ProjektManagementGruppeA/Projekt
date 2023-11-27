package business.kundeSonderwunsch;

import business.DatabaseConnector;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class KundeSonderwunschModel {
    private MongoCollection<Document> collection;

    /**
     * Konstruktor, der eine Verbindung zur Kunden-Sonderwunsch-Sammlung in der MongoDB herstellt.
     *
     * @param connector Der Datenbankverbinder, der die Verbindung zur MongoDB bereitstellt.
     */
    public KundeSonderwunschModel(DatabaseConnector connector) {
        collection = connector.getDatabase().getCollection("kundeSonderwuensche");
    }

    /**
     * Fügt eine Verbindung zwischen einem Kunden und einem Sonderwunsch mit einer bestimmten Anzahl in die Datenbank ein.
     *
     * @param kundeId        Die ID des Kunden.
     * @param sonderwunschId Die ID des Sonderwunschs.
     * @param anzahl         Die Anzahl, wie oft der Sonderwunsch vom Kunden gewählt wurde.
     * @return ObjectId Die generierte ID der hinzugefügten Beziehung in der Datenbank.
     */
    public ObjectId addKundeSonderwunsch(ObjectId kundeId, ObjectId sonderwunschId, int anzahl) {
        Document doc = new Document("kundeId", kundeId)
                .append("sonderwunschId", sonderwunschId)
                .append("anzahl", anzahl);
        collection.insertOne(doc);
        return doc.getObjectId("_id");
    }

    /**
     * Ermittelt alle Sonderwunsch-Verbindungen eines Kunden.
     *
     * @param kundeId Die ID des Kunden, für den die Sonderwunsch-Verbindungen gesucht werden.
     * @return Eine Liste von Dokumenten, die die Sonderwunsch-Verbindungen des Kunden repräsentieren.
     */
    public List<KundeSonderwunsch> getKundeSonderwuensche(ObjectId kundeId) {
        List<KundeSonderwunsch> kundeSonderwuensche = new ArrayList<>();
        for (Document doc : collection.find(Filters.eq("kundeId", kundeId))) {
            kundeSonderwuensche.add(documentToKundeSonderwunsch(doc));
        }
        return kundeSonderwuensche;
    }

    /**
     * Aktualisiert die Anzahl, wie oft ein Sonderwunsch von einem Kunden gewählt wurde, basierend auf der Beziehungs-ID.
     *
     * @param id     Die ID der Beziehung, die aktualisiert werden soll.
     * @param anzahl Die neue Anzahl für den Sonderwunsch.
     * @return true, wenn die Aktualisierung erfolgreich war, andernfalls false.
     */
    public boolean updateKundeSonderwunsch(ObjectId id, int anzahl) {
        UpdateResult result = collection.updateOne(
                Filters.eq("_id", id),
                new Document("$set", new Document("anzahl", anzahl))
        );
        return result.getModifiedCount() > 0;
    }

    /**
     * Löscht eine Sonderwunsch-Verbindung basierend auf ihrer ID.
     *
     * @param id Die ID der zu löschenden Beziehung.
     * @return true, wenn das Löschen erfolgreich war, andernfalls false.
     */
    public boolean deleteKundeSonderwunsch(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    /**
     * Wandelt ein Document in ein KundeSonderwunsch-Objekt um.
     *
     * @param doc Das MongoDB Document-Objekt, das umgewandelt wird.
     * @return KundeSonderwunsch Das umgewandelte KundeSonderwunsch-Objekt.
     */
    private KundeSonderwunsch documentToKundeSonderwunsch(Document doc){
        ObjectId kundeId = doc.getObjectId("kundeId");
        ObjectId wunschId = doc.getObjectId("sonderwunschId");
        int anzahl = doc.getInteger("anzahl");

        KundeSonderwunsch ksw = new KundeSonderwunsch(kundeId, wunschId, anzahl);

        return ksw;
        
    }

}
