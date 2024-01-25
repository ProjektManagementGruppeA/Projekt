package business.kundeSonderwunsch;

import business.DatabaseConnector;
import business.sonderwunschKategorie.*;
import business.sonderwunsch.*;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class KundeSonderwunschModel {
    private static KundeSonderwunschModel instance;
    private MongoCollection<Document> collection;
    private DatabaseConnector dbconnector;

    /**
     * Konstruktor, der eine Verbindung zur Kunden-Sonderwunsch-Sammlung in der MongoDB herstellt.
     *
     * @param connector Der Datenbankverbinder, der die Verbindung zur MongoDB bereitstellt.
     */
    private KundeSonderwunschModel(DatabaseConnector connector) {
        dbconnector = connector;
        MongoDatabase database = connector.getDatabase();
        collection = database.getCollection("kundeSonderwuensche");
    }

    public static KundeSonderwunschModel getInstance(DatabaseConnector connector) {
        if (instance == null) {
            instance = new KundeSonderwunschModel(connector);
        }
        return instance;
    }

    /**
     * Fügt eine Verbindung zwischen einem Kunden und einem Sonderwunsch mit einer bestimmten Anzahl in die Datenbank ein.
     *
     * @param kundeId        Die ID des Kunden.
     * @param sonderwunschId Die ID des Sonderwunschs.
     * @param anzahl         Die Anzahl, wie oft der Sonderwunsch vom Kunden gewählt wurde.
     * @return ObjectId Die generierte ID der hinzugefügten Beziehung in der Datenbank.
     */
    public ObjectId addKundeSonderwunsch(ObjectId kundeId, ObjectId sonderwunschId, int anzahl) throws Exception{
        if(anzahl == 0) {
            throw new Exception("Anzahl darf nicht 0 sein");
        }
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

        //Ausgewählen sonderwünsche zuu speicher
    }

    public List<KundeSonderwunsch> getKundeSonderwuenscheByKategorie(ObjectId kundeId, String kategorieName) {
        List<KundeSonderwunsch> kundeSonderwuenscheByKategorie = new ArrayList<>();
        // Assuming you have a method in SonderwunschKategorieModel to get category ID by name
        SonderwunschKategorieModel swkm = SonderwunschKategorieModel.getInstance(dbconnector);
        SonderwunschModel swm = SonderwunschModel.getInstance(dbconnector);
        ObjectId kategorieId = swkm.getSonderwunschKategorieByName(kategorieName).getId();
        if (kategorieId == null) {
            return kundeSonderwuenscheByKategorie; // Return empty list if category not found
        }

        // Get the IDs of all Sonderwunsch objects in this category
        List<ObjectId> sonderwunschIds = swm.getSonderwunschIdsByKategorieId(kategorieId);

        for (ObjectId sonderwunschId : sonderwunschIds) {
            for (Document doc : collection.find(Filters.and(Filters.eq("kundeId", kundeId), Filters.eq("sonderwunschId", sonderwunschId)))) {
                kundeSonderwuenscheByKategorie.add(documentToKundeSonderwunsch(doc));
            }
        }

        return kundeSonderwuenscheByKategorie;
    }


    /**
     * Aktualisiert die Anzahl, wie oft ein Sonderwunsch von einem Kunden gewählt wurde, basierend auf der Beziehungs-ID.
     *
     * @param id     Die ID der Beziehung, die aktualisiert werden soll.
     * @param anzahl Die neue Anzahl für den Sonderwunsch.
     * @return true, wenn die Aktualisierung erfolgreich war, andernfalls false.
     */
    public boolean updateKundeSonderwunsch(ObjectId id, int anzahl) {
        if (anzahl == 0) {
            return deleteKundeSonderwunsch(id);
        }
        else{
            UpdateResult result = collection.updateOne(
                Filters.eq("_id", id),
                new Document("$set", new Document("anzahl", anzahl))
            );
            return result.getModifiedCount() > 0;
        }
    }
    
    
    
    

    public KundeSonderwunsch getKundeSonderwunschByKundeAndSonderwunsch(ObjectId kundeId, ObjectId sonderwunschId) {
        Document doc = collection.find(Filters.and(Filters.eq("kundeId", kundeId), Filters.eq("sonderwunschId", sonderwunschId))).first();
        if (doc == null) {
            return null; // No matching record found
        }
        return documentToKundeSonderwunsch(doc); // Convert the found document to a KundeSonderwunsch object
    }
    
    public boolean updateKundeSonderwunschByKundeAndSonderwunsch(ObjectId kundeId, ObjectId sonderwunschId, int anzahl) {
        if (anzahl == 0) {
            return deleteKundeSonderwunschByKundeAndSonderwunsch(kundeId, sonderwunschId);
        }
        else{
            UpdateResult result = collection.updateOne(
                Filters.and(Filters.eq("kundeId", kundeId), Filters.eq("sonderwunschId", sonderwunschId)),
                new Document("$set", new Document("anzahl", anzahl))
            );
            return result.getModifiedCount() > 0;
        }
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

    public boolean deleteKundeSonderwunschByKundeAndSonderwunsch(ObjectId kundeId, ObjectId sonderwunschId) {
        DeleteResult result = collection.deleteOne(Filters.and(Filters.eq("kundeId", kundeId), Filters.eq("sonderwunschId", sonderwunschId)));
        return result.getDeletedCount() > 0;
    }
    
    public boolean deleteKundeSonderwunschByKundeId(ObjectId kundeId) {
        DeleteResult result = collection.deleteMany(Filters.eq("kundeId", kundeId));
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
