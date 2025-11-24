package bankprojekt.nuetzliches;

import bankprojekt.basisdaten.Konto;

import java.util.Comparator;

/**
 * Vergleiche beide IBAN's von der KontoKlasse
 */
public class KontenIbanComparator implements Comparator<Konto> {
    /**
     * Obligatorischer Kosntruktor
     */
    public KontenIbanComparator() {
        // kein Aufruf
    }
    // Die festgelegte IBAN
    private static final int BLZ = 10010000;

    @Override
    public int compare(Konto o1, Konto o2) {
        // Get Beide IBAN's zum Vergleich
        String iban1 = o1.getIban(BLZ);
        String iban2 = o2.getIban(BLZ);

        // Vergleiche die IBAN's & sortiert alphabetisch
        return iban1.compareTo(iban2);
    }
}
