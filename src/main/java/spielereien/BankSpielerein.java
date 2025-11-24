package spielereien;

import bankprojekt.basisdaten.Geldbetrag;
import bankprojekt.basisdaten.Kunde;
import bankprojekt.exceptions.GesperrtException;
import bankprojekt.verwaltung.Bank;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Bank Spielereien Funktionstest der Bank vom
 * Initialisieren, Kontoerstellen, alleKontonummern, abheben, einzahlen usw.
 */
public class BankSpielerein {
    /**
     * Nur für Bugspots/ JavaDoc
     */
    public BankSpielerein() {
        // Kein Konstruktor Aufruf
    }

    /**
     *  Bank Spielereien Funktionstest der Bank vom
     *  Initialisieren, Kontoerstellen, alleKontonummern, abheben, einzahlen usw.
     * @param args für Main
     * @throws GesperrtException Die Exception, falls gesperrt
     */
    public static void main(String[] args) throws GesperrtException {
        // Bank und Kunden
        System.out.println("Bank wurde initialisiert");
        Bank neueBank = new Bank(12345678L);
        System.out.println("\nKunden wurden angelegt.");
        Kunde neuerKunde1 = new Kunde("Peter", "Lustig", "Das ist meine Adresse: 1", LocalDate.now());
        Kunde neuerKunde2 = new Kunde("Laura", "Unlustig", "Das ist meine Adresse: 2", LocalDate.now());

        System.out.println("\nDie getBankleitzahl: " + neueBank.getBankleitzahl());

        System.out.println("\nErstelle Girokkontos");
        neueBank.girokontoErstellen(neuerKunde1);
        neueBank.sparbuchErstellen(neuerKunde1);
        neueBank.girokontoErstellen(neuerKunde2);

        System.out.println("\nPrinte alle Kontonummern und ihre Geldbetraege ");
        System.out.println(neueBank.getAlleKonten());

        for (Long e : neueBank.getAlleKontonummern()) {
            System.out.println(e);
        }

        System.out.println("\nGeldabheben " + neueBank.geldAbheben(100200300L, new Geldbetrag(100)));
        System.out.println("\nEs wurden 200 Euro gerade abgehoben.");

        System.out.println("\nPrinte alle Kontonummern und ihre Geldbetraege ");
        System.out.println(neueBank.getAlleKonten());

        System.out.println("\nGeldeinzahlen ");
        neueBank.geldEinzahlen(100200300L, new Geldbetrag(300));
        neueBank.geldEinzahlen(100200301L, new Geldbetrag(300));
        neueBank.geldEinzahlen(100200302L, new Geldbetrag(300));



        System.out.println("\nPrinte alle Kontonummern und ihre Geldbetraege ");
        System.out.println(neueBank.getAlleKonten());

        System.out.println("\nDie Kunden und ihr Geld");

        Map<Kunde, Geldbetrag> kundeGeldbetragMap = neueBank.getGesamtkontostaende();

        for (Map.Entry<Kunde, Geldbetrag> entry : kundeGeldbetragMap.entrySet()) {
            System.out.println("\nKunde: " + entry.getKey() + ", \nGeldbetrag: " + entry.getValue());
        }
    }
}
