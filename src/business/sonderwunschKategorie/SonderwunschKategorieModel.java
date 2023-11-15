package business.sonderwunschKategorie;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.types.ObjectId;

import business.DatabaseConnector;

public class SonderwunschKategorieModel {
    private static SonderwunschKategorieModel instance;
    private MongoCollection<Document> collection;

    private SonderwunschKategorieModel(DatabaseConnector connector) {
        MongoDatabase database = connector.getDatabase();
        collection = database.getCollection("sonderwunschKategorie");
    }

    public static SonderwunschKategorieModel getInstance(DatabaseConnector connector) {
        if (instance == null) {
            instance = new SonderwunschKategorieModel(connector);
        }
        return instance;
    }
    

    public SonderwunschKategorie getSonderwunschKategorieById(ObjectId id) {
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc == null) {
            return null;
        }
        return documentToSonderwunschKategorie(doc);
    }

    public List<SonderwunschKategorie> getAllSonderwunschKategorie(){
        List<SonderwunschKategorie> kategorien = new ArrayList<>();
        for (Document doc : collection.find()) {
            kategorien.add(documentToSonderwunschKategorie(doc));
        }
        return kategorien;
    }

    public boolean updateSonderwunschKategorie(ObjectId id, SonderwunschKategorie sonderwunschKategorie) {
        Document doc = new Document("name", sonderwunschKategorie.getName());
        UpdateResult result = collection.updateOne(Filters.eq("_id", id), new Document("$set", doc));
        return result.getModifiedCount() > 0;
    }

    public boolean deleteSonderwunschKategorie(ObjectId id ) {
        DeleteResult result = collection.deleteOne(Filters.eq("_id", id));
        return result.getDeletedCount() > 0;
    }

    private SonderwunschKategorie documentToSonderwunschKategorie(Document doc) {
        ObjectId id = doc.getObjectId("_id");
        String name = doc.getString("name");

        SonderwunschKategorie sk = new SonderwunschKategorie(name);
        sk.setId(id);

        return sk;

    }


    
}
