package bankprojekt.verwaltung;

import bankprojekt.basisdaten.*;
import bankprojekt.exceptions.GesperrtException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Die Bank mit festgelegter Bankleitzahl und internen Abwicklungen für interne
 * Konten, Sparkonto, Girokonto usw.
 */
public class Bank {
    /**
     * Die Bankleitzahl
     */
    private final long bankleitzahl;

    /**
     * Die Konten Map
     */
    private final Map<Long, Konto> kontenMap;

    /**
     * Internet NextKontonummerZähler
     */
    private long nextKontonummer = 100200300L;

    // Standard-Dispo, da der Girokonto-Konstruktor ihn erwartet
    private static final Geldbetrag STANDARD_DISPO = new Geldbetrag(500, Waehrung.EUR);
    /**
     * Erstellt eine Bank mit einer angegebenen Bankleitzahl und eine KontenListe
     * @param bankleitzahl die übergebene Bankleitzahl
     */
    public Bank(long bankleitzahl) {
        if (bankleitzahl <= 1) {
            throw new IllegalArgumentException("Die Bankleitzahl muss größer als 1 sein");
        }
        this.bankleitzahl = bankleitzahl;
        kontenMap = new HashMap<>();
    }

    /**
     * Gibt die Bankleitzahl zurück
     * @return die Bankleitzahl
     */
    public long getBankleitzahl() {
        return bankleitzahl;
    }

    /**
     * Erstellt ein GiroKonto und übergibt ihn in die KontenMap der Bank
     * erhöht den Wert der Kontonummer
     * @param inhaber der übergebene Inhaber des GiroKontos
     * @return die Kontonummer als Long wert
     * @throws GesperrtException falls das Konto gesperrt ist
     */
    public long girokontoErstellen(Kunde inhaber) throws GesperrtException {
        if (inhaber == null) {
            throw new NullPointerException("Der Inhaber ist null");
        }
        kontenMap.put(nextKontonummer, new Girokonto(inhaber, nextKontonummer, STANDARD_DISPO));
        return nextKontonummer++;
    }

    /**
     * Erstellt ein sparbuch und übergibt ihn an die KontenMap der Bank
     * @param inhaber der Inhaber
     * @return die Kontonummer
     */
    public long sparbuchErstellen(Kunde inhaber){
        if (inhaber == null) {
            throw new NullPointerException("Der Inhaber ist null");
        }
        kontenMap.put(nextKontonummer, new Sparbuch(inhaber, nextKontonummer));
        return nextKontonummer++;
    }


    /**
     * liefert einen Single string mit Kontonummer und Kontostand wieder
     * @return Ein String mit Kontonummer + Kontostand der Bank
     */
    public String getAlleKonten() {
        StringJoiner joiner = new StringJoiner(System.lineSeparator());
        for (Map.Entry<Long, Konto> entry : kontenMap.entrySet()) {
            String line = "Die Kontonummer: " + entry.getKey() +
                    ", Der Kontostand: " + entry.getValue().getKontostand();
            joiner.add(line);
        }
        return joiner.toString();
    }

    /**
     * Liefert eine Liste von den Kontonummern
     * @return eine neue Liste, der Kontonummern, der Bank
     */
    public List<Long> getAlleKontonummern() {
        return new ArrayList<Long>(kontenMap.keySet());
    }

    /**
     * Hebt Geld von VON + Wahrheitswert
      * @param von Von der Kontonummer
     * @param betrag der Geldbetrag
     * @return ob es geklappt hat oder nicht
     * @throws GesperrtException falls das Konto gesperrt ist
     */
    public boolean geldAbheben(long von, Geldbetrag betrag) throws GesperrtException {
        if (betrag.isNegativ()) {
            throw new ArithmeticException("Der Geldbetrag darf nicht negativ sein.");
        }
        // überprüfe, ob das Konto existiert
        if (kontenMap.containsKey(von)) {
            kontenMap.get(von).abheben(betrag);
            return true;
        }
        return false;
    }

    /**
     * Zahle Geld auf das Konto, der Bank
     * @param auf das Konto
     * @param betrag der Betrag
     */
    public void geldEinzahlen(long auf, Geldbetrag betrag) {
        if (betrag.isNegativ()) {
            throw new ArithmeticException("Der Geldbetrag darf nicht negativ sein.");
        }
        if (kontenMap.containsKey(auf)) {
            kontenMap.get(auf).einzahlen(betrag);
        }
    }

    /**
     * Überweist, die gewünschte Summe von Quellkonto auf das Zielkonto
     * @param vonKontoNr das QuellKonto
     * @param nachKontoNr das ZielKonto
     * @param betrag der Betrag
     * @param verwendungszweck der Verwendungszweck
     * @return ob, die überweisung geklappt hat
     * @throws GesperrtException falls das Konto gesperrt ist
     */
    public boolean geldUeberweisen(long vonKontoNr, long nachKontoNr, Geldbetrag betrag, String verwendungszweck) throws GesperrtException {

        // Fals vonKontonummer oder nachKontonummer nicht in der Bank aufgeführt sind
        if (!kontenMap.containsKey(vonKontoNr )|| !kontenMap.containsKey(nachKontoNr)) {
            return false;
        }
        // Falls der Betrag oder Verwendungszweck negativ/ungültig ist/sind.
        if (betrag.isNegativ()) throw new ArithmeticException("Der Überweisungsbetrag darf nicht negativ sein");
        if (verwendungszweck == null) throw new IllegalArgumentException("Der Verwendungszweck ist nicht gültig.");

        Konto vonKonto = kontenMap.get(vonKontoNr);
        Konto nachKonto = kontenMap.get(nachKontoNr);

        // überprüfe, ob die jeweiligen Konten (Quell und Ziel) eine Instanz von Überweisungsfähigen Konten ist
        if (!(vonKonto instanceof UeberweisungsfaehigesKonto) ||
                !(nachKonto instanceof UeberweisungsfaehigesKonto)) {
            return false;
        }

        // Geld vom QuellKonto abheben
        boolean abhebenErfolgreich = geldAbheben(vonKontoNr, betrag);

        if (abhebenErfolgreich) {
            // Wenn das Abheben erfolgreich ist, zahle auf ZielKonto ein
            geldEinzahlen(nachKontoNr, betrag);
            return true;
        }
        return false;
    }

    /**
     * Gibt eine Map zurück mit den Kunden und ihren gesamten Vermögen bei der Bank
     * @return Gibt die eine Map der gesamten Konten
     */
    public Map<Kunde, Geldbetrag> getGesamtkontostaende() {
        Map<Kunde, Geldbetrag> gesamtkontostaende = new HashMap<>();
        kontenMap.values().stream()
                .forEach(konto ->
                        gesamtkontostaende.merge(
                                konto.getInhaber(),
                                konto.getKontostand(),
                                Geldbetrag::plus
                        )
                );
        return gesamtkontostaende;
    }
}
