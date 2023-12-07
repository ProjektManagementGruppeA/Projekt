package business.sonderwunsch;
import org.bson.types.ObjectId;

public class Sonderwunsch {
    private ObjectId id;
    private ObjectId kategorieId;
    private String beschreibung;
    private Integer preis;

    public Sonderwunsch(ObjectId kategorieId, String beschreibung, Integer preis) {
        this.kategorieId = kategorieId;
        this.beschreibung = beschreibung;
        this.preis = preis;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getKategorieId() {
        return kategorieId;
    }

    public void setKategorieId(ObjectId kategorieId) {
        this.kategorieId = kategorieId;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public Integer getPreis() {
        return preis;
    }

    public void setPreis(Integer preis) {
        this.preis = preis;
    }

}
