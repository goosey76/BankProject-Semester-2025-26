package bankprojekt.basisdaten;

import org.decimal4j.util.DoubleRounder;

import java.util.Objects;

/**
 * Ein Geldbetrag mit Währung
 */
public class Geldbetrag implements Comparable<Geldbetrag> {
    /**
     * 0 €
     */
    public static final Geldbetrag NULL_EURO = new Geldbetrag(0, Waehrung.EUR);

    /**
     * Betrag in der in waehrung angegebenen Währung
     */
    private final double betrag;

    /**
     * Währung des Geldbetrags
     */
    private Waehrung waehrung;

    /**
     * erstellt einen Geldbetrag in der Währung Euro
     *
     * @param betrag Betrag in €
     * @throws IllegalArgumentException wenn betrag unendlich oder NaN ist
     */
    public Geldbetrag(double betrag) {
        if (!Double.isFinite(betrag))
            throw new IllegalArgumentException();
        this.betrag = DoubleRounder.round(betrag, 2);
        this.waehrung = Waehrung.EUR;
    }

    /**
     * erstellt einen Geldbetrag in der Währung Euro mit
     *
     * @param betrag Betrag in €
     * @param waehrung Die Waehrung
     * @throws IllegalArgumentException wenn betrag unendlich oder NaN ist
     * @throws NullPointerException     wenn die Waehrung null ist
     */
    public Geldbetrag(double betrag, Waehrung waehrung) {
        if (!Double.isFinite(betrag))
            throw new IllegalArgumentException();
        if (waehrung == null) {
            throw new NullPointerException();
        }
        this.betrag = DoubleRounder.round(betrag, 2);
        this.waehrung = waehrung;
    }

    /**
     * Betrag von this
     *
     * @return Betrag in der Währung von this
     */
    public double getBetrag() {
        return this.betrag;
    }

    /**
     * Die Waehrung
     * @return die Waehrungs
     */
    public Waehrung getWaehrung() {
        return this.waehrung;
    }

    /**
     * Berechnet den Wert dieses Geldbetrags umgerechnet in Euro
     * @return der Wert in Euro
     */
    private double getBetragInEuro() {
        return DoubleRounder.round(betrag / this.waehrung.getUmrechnungsKursZuEuro(), 2);
    }


    /**
     * prüft, ob this einen negativen Betrag darstellt
     *
     * @return true, wenn this negativ ist
     */
    public boolean isNegativ() {
        return this.betrag < 0;
    }


    /**
     * --- Rechne den Geldbetrag um ---
     * this in die angegebene Zielwährung
     * @param zielwaehrung die umzurechnende Zielwährung
     * @return der umgerechnete Geldbetrag/
     */
    public Geldbetrag umrechnen(Waehrung zielwaehrung) {
        if (zielwaehrung == null) {
            throw new IllegalArgumentException();
        }

        // 1. Umrechnung in Euro
        double betragInEuro = this.getBetragInEuro();

        // 2. Umrechnen von Euro zur Zielwährung
        double zielBetrag = betragInEuro * zielwaehrung.getUmrechnungsKursZuEuro();

        return new Geldbetrag(DoubleRounder.round(zielBetrag, 2), zielwaehrung);
    }

    /**
     * rechnet this + summand
     * @param summand zu addierender Betrag
     * @return this + summand in der Währung von this
     * @throws IllegalArgumentException wenn summand null ist
     */
    public Geldbetrag plus(Geldbetrag summand)
    {
        if(summand == null)
            throw new IllegalArgumentException("summand darf nicht null sein");

        // Konvertiere den Summanden in die gleiche Währung wie this
        Geldbetrag summandInDieseWaehrung = summand.umrechnen(this.waehrung);
        return new Geldbetrag(this.betrag + summandInDieseWaehrung.betrag, this.waehrung);
    }

    /**
     * rechnet this - subtrahend
     * @param subtrahend abzuziehender Betrag
     * @return this - subtrahend in der Währung von this
     * @throws IllegalArgumentException wenn subtrahend null ist
     */
    public Geldbetrag minus(Geldbetrag subtrahend)
    {
        if(subtrahend == null)
            throw new IllegalArgumentException("Subtrahend darf nicht null sein.");
        // Konvertiere Subtrahenden in die gleiche Währung von this
        Geldbetrag subtrahendInDieserWaehrung = subtrahend.umrechnen(this.waehrung);
        return new Geldbetrag(this.betrag - subtrahendInDieserWaehrung.betrag, this.waehrung);
    }

    /**
     * multipliziert this mit faktor
     * @param faktor Faktor der Multiplikation
     * @return das faktor-Fache von this
     * @throws IllegalArgumentException wenn faktor nicht finit ist
     */
    public Geldbetrag mal(double faktor)
    {
        if(!Double.isFinite(faktor))
            throw new IllegalArgumentException();
        return new Geldbetrag(this.betrag * faktor, this.waehrung);
    }

    @Override
    public int compareTo(Geldbetrag o) {
        return Double.compare(this.betrag, o.betrag);
    }

    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof Geldbetrag)) return false;
        if(o == this) return true;
        return this.compareTo((Geldbetrag) o) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getBetrag(), this.waehrung);
    }

    @Override
    public String toString()
    {
        return String.format("%,.2f €", this.betrag);
    }

}
