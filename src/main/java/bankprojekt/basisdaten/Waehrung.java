package bankprojekt.basisdaten;

/**
 * Waehrungsenum für die Kursumrechnung
 */
public enum Waehrung {
    /**
     * Euro (EUR) mit einem Umrechnungskurs von 1 zum Euro.
     */
    EUR(1, "EUR"),
    /**
     * Bulgarischer Lew (BGN) mit einem Umrechnungskurs von 1.95583 zum Euro.
     */
    LEW(1.95583, "BGN"),
    /**
     * Komorischer Franc (KMF) mit einem Umrechnungskurs von 495.1 zum Euro.
     */
    FRANC(495.1, "KMF"),
    /**
     * Dänische Krone (DKK) mit einem Umrechnungskurs von 7.46038 zum Euro.
     */
    KRONE(7.46038, "DKK");

    // --- Attribute ---
    private double umrechnungsKursZuEuro;
    private String kuerzel;

    // --- Konstruktor ---
    // Privater Konstruktor initialisiert das Attribut
    Waehrung(double umrechnungZuEuro, String kuerzel) {
        this.umrechnungsKursZuEuro = umrechnungZuEuro;
        this.kuerzel = kuerzel;
    }

    // --- Getter ---

    /**
     * Der Umrechnungskurs
     * @return den umrechnungskurs als Double
     */
    public double getUmrechnungsKursZuEuro(){
        return this.umrechnungsKursZuEuro;
    }

    /**
     * Gibt, den Kürzel zurück zur Waehrung
     * @return Kürzel als String
     */
    public String getKuerzel() {
        return this.kuerzel;
    }

    /**
     * Die Währung und der Umrechnungskurs der Währung
     * @return Gibt das Kuerzel zurück}‘‘
     */
    @Override
    public String toString() {
        return this.kuerzel;
    }
}
