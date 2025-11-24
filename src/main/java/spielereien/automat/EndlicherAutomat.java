package spielereien.automat;

/**
 * Das Interface um den Zustand des EndlichenAutomats zu ermitteln
 */
public interface EndlicherAutomat {


    /**
     * Automat kann gestartet werden
     */
    void starten();

    /**
     * Automat kann ein Zustandswechsel ausführen
     * @param zeichen Die Zeichenkette
     */
    void zustandsWechsel(char zeichen);

    /**
     * Automat kann einen Zustand akzeptieren oder nicht
     * @return der aktuelle Zustand
     */
    boolean istAkzeptierend();

    /**
     * Default methode zum Starten, Wechsel des Zustands und gibt Zustand wieder.
     * @param zeichenkette Die zeichenkette
     * @return der Wahrheitswert
     */
    default boolean testen(String zeichenkette) {
        // Startet den Automaten
        starten();
        // Wenn ZeichenKette null oder leer ist der Automat in nichtAkzeptierenden Zustand.
        if (zeichenkette == null || zeichenkette.isEmpty()) {
            return false;
        }
        // führt für jedes Zeichen ein Zustandswechsel durch
        for (char zeichen : zeichenkette.toCharArray()) {
            zustandsWechsel(zeichen);
        }
        // gibt den Zustand wieder
        return istAkzeptierend();
    }
}
