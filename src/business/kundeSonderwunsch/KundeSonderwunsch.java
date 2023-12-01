package business.kundeSonderwunsch;

import org.bson.types.ObjectId;

public class KundeSonderwunsch {
    private ObjectId kundeId;
    private ObjectId sonderwunschId;
    private int anzahl;

    public KundeSonderwunsch(ObjectId kundeId, ObjectId sonderwunschId, int anzahl) {
        this.kundeId = kundeId;
        this.sonderwunschId = sonderwunschId;
        this.anzahl = anzahl;
    }

    public ObjectId getKundeId() {
        return kundeId;
    }

    public void setKundeId(ObjectId kundeId) {
        this.kundeId = kundeId;
    }

    public ObjectId getSonderwunschId() {
        return sonderwunschId;
    }

    public void setSonderwunschId(ObjectId sonderwunschId) {
        this.sonderwunschId = sonderwunschId;
    }

    public int getAnzahl() {
        return anzahl;
    }

    public void setAnzahl(int anzahl) {
        this.anzahl = anzahl;
    }
}
