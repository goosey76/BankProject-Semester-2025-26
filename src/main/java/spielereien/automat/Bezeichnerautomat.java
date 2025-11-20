package spielereien.automat;

/**
 * Der Bezeichner Automat für den Zustandswechsel.
 */
public class Bezeichnerautomat implements EndlicherAutomat {
    /**
     * Die Zustände von dem Automat
     */
    private enum Zustand {
        Q0_START,               // Anfangszustand
        Q1_AKZEPTIEREND,       // Akzeptierender Zustand
        QF_FEHLER               // Nicht-Akzeptierender Zustand
    }

    /**
     * Der aktuelle Zustand des Automaten
     */
    private Zustand aktuellerZustand;

    /**
     * Der StandardKonstruktor
     */
    public Bezeichnerautomat() {
        // Kein Aufruf
    }

    // Zeichenprüfung von Q0 nach Q1
    private static boolean istStartZeichen(char c) {
        // Buchstabe oder _
        return Character.isLetter(c) || c == '_';
    }

    // folgende Zeichenprüfung von Buchstaben
    private static boolean istFolgeZeichen(char c) {
        // Buchstabe, Ziffer oder _
        return Character.isLetter(c) || Character.isDigit(c) || c == '_';
    }

    // Interface Methoden
    @Override
    public void starten() {
        this.aktuellerZustand = Zustand.Q0_START;
    }

    @Override
    public void zustandsWechsel(char zeichen) {
        switch (aktuellerZustand) {
            case Q0_START:          // Q0 Zustand
                if (istStartZeichen(zeichen)) {
                                    // von Q0 zu Q1 -> Akzeptierend
                    this.aktuellerZustand = Zustand.Q1_AKZEPTIEREND;
                } else {
                                    // Von Q0 zu Q2 -> Ungültig
                    this.aktuellerZustand = Zustand.QF_FEHLER;
                }
                break;
            case Q1_AKZEPTIEREND:   // Q1 Zustand
                if (istFolgeZeichen(zeichen)) {
                    // Loop übergang - Zustand bleibt gleich Akzeptierend
                } else {
                    this.aktuellerZustand = Zustand.QF_FEHLER;
                }
                break;
            case QF_FEHLER:
                // Im Q2 Feld, bleibt der Automat dort, Ende des Automaten
                break;
        }
    }

    /**
     * Der Akzeptierende zustand.
     * @return Wahrheitswert.
     */
    @Override
    public boolean istAkzeptierend() {
        return this.aktuellerZustand == Zustand.Q1_AKZEPTIEREND;
    }

}
