package bankprojekt.basisdaten;

import java.time.LocalDate;

import bankprojekt.exceptions.GesperrtException;
import bankprojekt.exceptions.UngueltigeKontonummerException;
import bankprojekt.nuetzliches.Kalender;

/**
 * ein Sparbuch, d.h. ein Konto, das nur recht eingeschränkt genutzt
 * werden kann. Insbesondere darf man monatlich nur höchstens 2000€
 * abheben, wobei der Kontostand nie unter 0,50€ fallen darf. Zur Abfrage
 * des heutigen Datums (was die Zuordnung zweier Abhebungen zu einem gemeinsamen
 * Monat bestimmt) kann ein Kalender-Objekt angegeben werden.
 * @author Doro
 */
public class Sparbuch extends Konto {
    /**
     * Monatlich erlaubter Gesamtbetrag für Abhebungen
     */
    public static final Geldbetrag ABHEBESUMME = new Geldbetrag(2000);

    /**
     * Betrag, der nach einer Abhebung mindestens auf dem Konto bleiben muss
     */
    public static final Geldbetrag MINIMUM = new Geldbetrag(0.5);

    /**
     * Zinssatz, mit dem das Sparbuch verzinst wird. 0,03 entspricht 3%
     */
    private double zinssatz;

    /**
     * Betrag, der im aktuellen Monat bereits abgehoben wurde
     */
    private Geldbetrag bereitsAbgehoben;

    /**
     * Kalender zur Abfrage des heutigen Datums
     */
    private Kalender kalender;

    /**
     * Monat und Jahr der letzten Abhebung
     */
    private LocalDate zeitpunkt;

    /**
    * ein Standard-Sparbuch
    */
    public Sparbuch() {
        this.zinssatz = 0.03;
        this.kalender = new Kalender();
        zeitpunkt = kalender.getHeutigesDatum();
    }

    /**
    * ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
    * Das heutige Datum wird an allen Stellen, wo es benötigt wird, vom Betriebssystem
    * abgefragt.
    * @param inhaber der Kontoinhaber
    * @param kontonummer die Wunsch-Kontonummer
    * @throws IllegalArgumentException wenn inhaber null ist
    * @throws UngueltigeKontonummerException wenn kontonummer ungültig ist
    */
    public Sparbuch(Kunde inhaber, long kontonummer) {
        this(inhaber, kontonummer, new Kalender());
    }

    /**
    * ein Standard-Sparbuch, das inhaber gehört und die angegebene Kontonummer hat
    * @param inhaber der Kontoinhaber
    * @param kontonummer die Wunsch-Kontonummer
    * @param kalender der Kalender, der für die Abfrage des heutigen Datums benutzt wird
    * @throws IllegalArgumentException wenn inhaber oder kalender null ist
    * @throws UngueltigeKontonummerException wenn kontonummer ungültig ist
    */
    public Sparbuch(Kunde inhaber, long kontonummer, Kalender kalender) {
        super(inhaber, kontonummer);

        this.zinssatz = 0.03;
        this.kalender = kalender;
        zeitpunkt = kalender.getHeutigesDatum();
        this.bereitsAbgehoben = new Geldbetrag(0, this.getKontostand().getWaehrung());
    }


    @Override
    public void waehrungsWechsel(Waehrung neu) {
        // 1. Das Sparbuch-spezifische Attribut um die Währung umzurechnen
        this.bereitsAbgehoben = this.bereitsAbgehoben.umrechnen(neu);

        // 2. Logik der Oberklasse aufrufen um Kontostand und Kontowährung zu aktualisieren
        super.waehrungsWechsel(neu);
    }

    /**
     * Hebt Geld ab und prüft, ob die AbhebeSumme des Monats noch im Rahmen liegt
     * @param betrag abzuhebender Betrag
     * @return Wahrheitswert
     * @throws GesperrtException eine Exception
     */
    @Override
    public boolean abheben (Geldbetrag betrag) throws GesperrtException {
        if (betrag == null || betrag.isNegativ()) {
            throw new IllegalArgumentException("Betrag ungültig");
        }
        if(this.isGesperrt()) {
            GesperrtException e = new GesperrtException(this.getKontonummer());
            throw e;
        }

        // Währungsangleichung
        Waehrung kontoWaehrung = this.getKontostand().getWaehrung();
        Geldbetrag betragInKontoWaehrung = betrag.umrechnen(kontoWaehrung);
        Geldbetrag minimumInKontoWaehrung = MINIMUM.umrechnen(kontoWaehrung);
        Geldbetrag maxAhebInKontoWaehrung = ABHEBESUMME.umrechnen(kontoWaehrung);

        // Monatswechsel prüfen
        LocalDate heute = kalender.getHeutigesDatum();
        if(heute.getMonth() != zeitpunkt.getMonth() || heute.getYear() != zeitpunkt.getYear()) {
            this.bereitsAbgehoben = new Geldbetrag(0, kontoWaehrung);
            this.zeitpunkt = heute;
        }

        // Prüfung ob Abhebung möglich
        Geldbetrag neuerKontostand = getKontostand().minus(betragInKontoWaehrung);

        if (neuerKontostand.compareTo(minimumInKontoWaehrung) >= 0 &&
                bereitsAbgehoben.plus(betragInKontoWaehrung).compareTo(maxAhebInKontoWaehrung)<= 0) {

            setKontostand(neuerKontostand);
            bereitsAbgehoben = bereitsAbgehoben.plus(betragInKontoWaehrung);
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public String toString()
    {
        String ausgabe = "-- SPARBUCH --" + System.lineSeparator() +
        super.toString()+ System.lineSeparator()
        + "Zinssatz: " + this.zinssatz * 100 +"%";
        return ausgabe;
    }
}
