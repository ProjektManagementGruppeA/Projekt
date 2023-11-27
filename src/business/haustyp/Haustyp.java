package business.haustyp;
import org.bson.types.ObjectId;

public class Haustyp {
    private ObjectId id;
    private int plannummer;
    private boolean hatDachgeschoss;
    private boolean hatKeller;
    private boolean hatGarage;
    private boolean hatCarPort;
    private boolean hatTerrasse;
    private boolean hatDachterrasse;

    public Haustyp(int plannummer, boolean hatDachgeschoss) { // Dieser Konstruktor soll benutzt werden
        this.plannummer = plannummer; // Plannummer/Hausnummer wird eindeuting vom Bauträger vergeben
        this.hatKeller = true; // Es gibt immer einen Keller
        this.hatDachgeschoss = hatDachgeschoss;
        this.hatGarage = !hatDachgeschoss; // Wenn es ein DG gibt, keine Garage im Keller
        this.hatCarPort = !hatGarage; // Wenn es eine Garage gibt, kein Car-Port und umgekehrt
        this.hatTerrasse = true; // Es gibt immer eine Terrasse
        this.hatDachterrasse = hatDachgeschoss; // Wenn es ein DG gibt, gibt es auch eine Dachterrasse
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public boolean isHatKeller() {
        return hatKeller;
    }

    public void setHatKeller(boolean hatKeller) {
        this.hatKeller = hatKeller;
    }

    public boolean isHatDachgeschoss() {
        return hatDachgeschoss;
    }

    public void setHatDachgeschoss(boolean hatDachgeschoss) {
        this.hatDachgeschoss = hatDachgeschoss;
    }

    public int getPlannummer() {
        return plannummer;
    }

    public void setPlannummer(int plannummer) {
        this.plannummer = plannummer;
    }

    public boolean isHatGarage() {
        return hatGarage;
    }

    public void setHatGarage(boolean hatGarage) {
        this.hatGarage = hatGarage;
    }

    public boolean isHatCarPort() {
        return hatCarPort;
    }

    public void setHatCarPort(boolean hatCarPort) {
        this.hatCarPort = hatCarPort;
    }

    public boolean isHatTerrasse() {
        return hatTerrasse;
    }

    public void setHatTerrasse(boolean hatTerrasse) {
        this.hatTerrasse = hatTerrasse;
    }

    public boolean isHatDachterrasse() {
        return hatDachterrasse;
    }

    public void setHatDachterrasse(boolean hatDachterrasse) {
        this.hatDachterrasse = hatDachterrasse;
    }

}
