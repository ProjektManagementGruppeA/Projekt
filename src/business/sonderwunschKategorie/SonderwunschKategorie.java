package business.sonderwunschKategorie;
import org.bson.types.ObjectId;

public class SonderwunschKategorie {
    private ObjectId id;
    private String name;

    public SonderwunschKategorie(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
