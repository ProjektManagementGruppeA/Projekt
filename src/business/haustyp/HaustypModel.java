package business.haustyp;
import business.DatabaseConnector;
import business.kunde.KundeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class HaustypModel {
    private MongoCollection<Document> collection;
    private static HaustypModel instance;

    /**
     * Konstruktor, der eine Verbindung zur Haustypen-Sammlung in der MongoDB herstellt.
     *
     * @param connector Der Datenbankverbinder, der die Verbindung zur MongoDB bereitstellt.
     */
    public HaustypModel(DatabaseConnector connector) {
        MongoDatabase database = connector.getDatabase();
        collection = database.getCollection("haustypen");
    }

    public static HaustypModel getInstance(DatabaseConnector connector) {
        if (instance == null) {
            instance = new HaustypModel(connector);
        }
        return instance;
    }

    /**
     * Fügt einen neuen Haustyp zur Datenbank hinzu.
     *
     * @param haustyp Das Haustyp-Objekt, das hinzugefügt werden soll.
     * @return ObjectId Die generierte ID des eingefügten Haustyps in der Datenbank.
     */
    public ObjectId addHaustyp(Haustyp haustyp) {
        Document doc = new Document("plannummer", haustyp.getPlannummer())
                .append("hatKeller", haustyp.isHatKeller())
                .append("hatDachgeschoss", haustyp.isHatDachgeschoss())
                .append("hatGarage", haustyp.isHatGarage())
                .append("hatCarPort", haustyp.isHatCarPort())
                .append("hatTerrasse", haustyp.isHatTerrasse())
                .append("hatDachterrasse", haustyp.isHatDachterrasse());

        collection.insertOne(doc);

        return doc.getObjectId("_id");
    }

    /**
     * Holt einen Haustyp anhand seiner ID aus der Datenbank.
     *
     * @param id Die ObjectId des Haustyps in der Datenbank.
     * @return Haustyp Das Objekt des Haustyps, wenn gefunden, sonst null.
     */
    public Haustyp getHaustypById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();

        if (doc == null) {
            return null;
        }

        return documentToHaustyp(doc);
    }

    public Haustyp getHaustypByHausnummer(int hausnummer) {
        Document doc = collection.find(Filters.eq("plannummer", hausnummer)).first();

        if (doc == null) {
            return null;
        }

        return documentToHaustyp(doc);
    }

    /**
     * Gibt eine Liste aller Haustypen in der Datenbank zurück.
     *
     * @return List<Haustyp> Eine Liste von Haustyp-Objekten.
     */
    public List<Haustyp> getAllHaustypen() {
        List<Haustyp> haustypen = new ArrayList<>();
        MongoCursor<Document> cursor = collection.find().iterator();

        try {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                haustypen.add(documentToHaustyp(doc));
            }
        } finally {
            cursor.close();
        }

        return haustypen;
    }

    /**
     * Aktualisiert die Informationen eines vorhandenen Haustyps in der Datenbank.
     *
     * @param id Der ObjectId des zu aktualisierenden Haustyps.
     * @param haustyp Das Haustyp-Objekt mit den aktualisierten Informationen.
     * @return boolean True, wenn die Aktualisierung erfolgreich war, sonst False.
     */
    public boolean updateHaustyp(ObjectId id, Haustyp haustyp) {
        Document doc = new Document("plannummer", haustyp.getPlannummer())
                .append("hatKeller", haustyp.isHatKeller())
                .append("hatDachgeschoss", haustyp.isHatDachgeschoss())
                .append("hatGarage", haustyp.isHatGarage())
                .append("hatCarPort", haustyp.isHatCarPort())
                .append("hatTerrasse", haustyp.isHatTerrasse())
                .append("hatDachterrasse", haustyp.isHatDachterrasse());

        UpdateResult result = collection.updateOne(Filters.eq("_id", id), new Document("$set", doc));
        return result.getModifiedCount() > 0;
    }

    /**
     * Löscht einen Haustyp aus der Datenbank anhand seiner ID.
     *
     * @param id Die ObjectId des zu löschenden Haustyps.
     * @return boolean True, wenn der Haustyp gelöscht wurde, sonst False.
     */
    public boolean deleteHaustyp(ObjectId id) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    /**
     * Wandelt ein Document-Objekt in ein Haustyp-Objekt um.
     *
     * @param doc Das Document-Objekt aus der MongoDB, das umgewandelt wird.
     * @return haustyp Ein Haustyp-Objekt, das aus dem Document erstellt wurde.
     */
    private Haustyp documentToHaustyp(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        int plannummer = doc.getInteger("plannummer");
        boolean hatDachgeschoss = doc.getBoolean("hatDachgeschoss");

        Haustyp haustyp = new Haustyp(plannummer, hatDachgeschoss);
        haustyp.setId(id);

        return haustyp;
    }
    
    public Haustyp getHaustypByHausnummer(int hausnummer) {
        Document doc = collection.find(Filters.eq("plannummer", hausnummer)).first();

        if (doc == null) {
            return null;
        }

        return documentToHaustyp(doc);
    }

    /* enthaelt die Plannummern der Haeuser, diese muessen vielleicht noch
	   in eine andere Klasse verschoben werden */
	ObservableList<Integer> plannummern = 
	    FXCollections.observableArrayList(
		0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24);
	
    /**
	 * gibt saemtliche Plannummern der Haeuser des Baugebiets heraus.
	 * @return ObservableList<Integer> , enthaelt saemtliche Plannummern der Haeuser
	 */
	public ObservableList<Integer> getPlannummern(){
		return this.plannummern; 
	}
}
