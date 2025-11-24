package bankprojekt.basisdaten;

import bankprojekt.exceptions.GesperrtException;
import bankprojekt.exceptions.UngueltigeKontonummerException;

/**
 * Ein Girokonto, d.h. ein Konto mit einem Dispo und der Fähigkeit,
 * Überweisungen zu senden und zu empfangen.
 * Grundsätzlich sind Überweisungen und Abhebungen möglich bis
 * zu einem Kontostand von -this.dispo
 * @author Doro
 *
 */
public class Girokonto extends UeberweisungsfaehigesKonto {
    /**
     * Wert, bis zu dem das Konto überzogen werden darf
     */
    private Geldbetrag dispo;

    /**
     * Die Kontonummer, des Accounts
     */
    private long kontonummer;


    /**
     * erzeugt ein Girokonto mit den angegebenen Werten
     * @param inhaber Kontoinhaber
     * @param kontonummer Kontonummer
     * @param dispo Dispo
     * @throws IllegalArgumentException wenn der inhaber null ist
     * 									oder der angegebene dispo negativ bzw. NaN ist
     * @throws UngueltigeKontonummerException wenn kontonummer ungültig ist
     */
    public Girokonto(Kunde inhaber, long kontonummer, Geldbetrag dispo)
    {
        super(inhaber, kontonummer);
        if(dispo == null || dispo.isNegativ())
            throw new IllegalArgumentException("Der Dispo ist nicht gültig!");

        // Der Konto-Konstruktor initialisiert die Währung
        this.dispo = dispo.umrechnen(getKontostand().getWaehrung());
    }

    /**
     * erzeugt ein leeres, nicht gesperrtes Standard-Girokonto
     * von Max MUSTERMANN
     */
    public Girokonto()
    {
        // call super with safe defaults
        super(Kunde.MUSTERMANN, 99887766);

        // Initialisiere Dispo, nachdem superclass mit der erstellung von Objekt fertig ist
        Geldbetrag defaultDispo = new Geldbetrag(500);
        this.dispo = defaultDispo.umrechnen(getKontostand().getWaehrung());
    }


    /**
     * liefert den Dispo
     * @return Dispo von this
     */
    public Geldbetrag getDispo() {
        return dispo;
    }

    /**
     * setzt den Dispo neu
     * @param dispo muss größer sein als 0
     * @throws IllegalArgumentException wenn dispo negativ bzw. NaN ist
     */
    public void setDispo(Geldbetrag dispo) {
        if(dispo == null || dispo.isNegativ())
            throw new IllegalArgumentException("Der Dispo ist nicht gültig!");
        // Dispo wird immer in die aktuelle Kontowährung umgerechnet, falls nötig.
        this.dispo = dispo.umrechnen(getKontostand().getWaehrung());
    }

    /**
     * Berechnet den Geldbetrag anhand der Waehrung um
     * @param geldbetrag der übergebene Geldbetrag
     * @return der Umgerechnete Geldbetrag
     */
    public Geldbetrag kontoSpezifischeWaehrungUmrechnen(Geldbetrag geldbetrag) {
        return geldbetrag.umrechnen(getKontostand().getWaehrung());
    }

    @Override
    public void waehrungsWechsel(Waehrung neu) {
        // 1. Logik der Oberklasse aufrufen um Kontostand und Kontowährung zu aktualisieren
        this.dispo = this.dispo.umrechnen(neu);

        // 2. Dispo-Betrag durch die neue Währung umrechnen
        super.waehrungsWechsel(neu);
    }

    @Override
    public boolean ueberweisungAbsenden(Geldbetrag betrag,
            String empfaenger, long nachKontonr,
            long nachBlz, String verwendungszweck)
                    throws GesperrtException
    {
      if (this.isGesperrt())
            throw new GesperrtException(this.getKontonummer());
        if (betrag == null || betrag.isNegativ()|| empfaenger == null || verwendungszweck == null)
            throw new IllegalArgumentException("Parameter fehlerhaft");

        // Den abzusenden Betrag in Kontowährung umrechnen
        Geldbetrag betragInKontoWaehrung = kontoSpezifischeWaehrungUmrechnen(betrag);

        // Ob Betrag im Rahmen des Kontostands + Dispos liegt.
        if (!getKontostand().plus(dispo).minus(betragInKontoWaehrung).isNegativ())
        {
            setKontostand(getKontostand().minus(betragInKontoWaehrung));
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean abheben(Geldbetrag betrag) throws GesperrtException{
        if (betrag == null || betrag.isNegativ()) {
            throw new IllegalArgumentException("Betrag ungültig");
        }
        if(this.isGesperrt())
            throw new GesperrtException(this.getKontonummer());

        // Kontospezifische Währung umrechnen zum Abheben
        Geldbetrag betragInKontoWaehrung = kontoSpezifischeWaehrungUmrechnen(betrag);

        if (!getKontostand().plus(dispo).minus(betragInKontoWaehrung).isNegativ())
        {
            setKontostand(getKontostand().minus(betragInKontoWaehrung));
            return true;
        }
        else
            return false;
    }

    @Override
    public void ueberweisungEmpfangen(Geldbetrag betrag, String vonName, long vonKontonr, long vonBlz, String verwendungszweck)
    {
        if (betrag == null || betrag.isNegativ()|| vonName == null || verwendungszweck == null)
            throw new IllegalArgumentException("Parameter fehlerhaft");

        // Betrag in Konto-Währung
        Geldbetrag betragInKontoWaehrung = kontoSpezifischeWaehrungUmrechnen(betrag);

        setKontostand(getKontostand().plus(betragInKontoWaehrung));
    }

    @Override
    public String toString()
    {
        String ausgabe = "-- GIROKONTO --" + System.lineSeparator() +
        super.toString() + System.lineSeparator()
        + "Dispo: " + this.dispo;
        return ausgabe;
    }
}
