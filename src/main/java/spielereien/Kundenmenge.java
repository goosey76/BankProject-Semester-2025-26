package spielereien;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Predicate;

import bankprojekt.basisdaten.Kunde;

/**
* verwaltet eine Menge von Kunden
* @author Doro
*/
public class Kundenmenge {
    /**
     * Standard Konstruktor
     */
    public Kundenmenge() {
        // Kein Aufruf
    }
/**
* erstellt eine Menge von Kunden mittels Treeset und behandelt Methoden zu HasMaps und Lists.
* @param args die Args
*/
    public static void main(String[] args) {
        Kunde anna = new Kunde("Anna", "Müller", "hier", LocalDate.parse("1979-05-14"));
        Kunde berta = new Kunde("Berta", "Beerenbaum", "hier", LocalDate.parse("1980-03-15"));
        Kunde chris = new Kunde("Chris", "Tall", "hier", LocalDate.parse("1979-01-07"));
        Kunde anton = new Kunde("Anton", "Meier", "hier", LocalDate.parse("1982-10-23"));
        Kunde bert = new Kunde("Bert", "Chokowski", "hier", LocalDate.parse("1970-12-24"));
        Kunde doro = new Kunde("Doro", "Hubrich", "hier", LocalDate.parse("1976-07-13"));

        Set<Kunde> menge = new TreeSet<Kunde>();
        menge.add(anna);
        menge.add(berta);
        menge.add(chris);
        menge.add(anton);
        menge.add(bert);
        menge.add(doro);

        System.out.println("Gebe Alle Namen der Kunden aus\n");
        for (Kunde kunde : menge) {
            System.out.println("Der Kunde wurde gefunden " + kunde.getName());
        }

        int mengeDerKunden = menge.size();
        System.out.println("\nDie Menge der Kunden " + mengeDerKunden);

//      Kunden spezifischer Name ausgeben auf der  Console
        Scanner tastatur = new Scanner(System.in);
        System.out.println("Nach welchem Namen wollen Sie suchen? ");
        String gesucht = tastatur.nextLine();

        boolean gefunden = false;
        for (Kunde kunde : menge) {
            if (Objects.equals(kunde.getNachname(), gesucht)) {
                System.out.println("Der Kunde wurde gefunden " + kunde.getName());
                gefunden = true;
                break;
            }
        }

        if (!gefunden) {
            System.out.println("Der Kunde mit dem Nachnamen " + gesucht + " wurde nicht gefunden");
        }

        System.out.println("\nVornamen mit A werden gelöscht? ");
        // Lösche Alle Kunden beginnend mit A aus der Liste
        menge.removeIf(new Predicate<Kunde>() {
            @Override
            public boolean test(Kunde kunde) {
                return kunde.getVorname().startsWith("A");
            }
        });

        System.out.println("Gebe Alle Namen der Kunden aus\n");
        for (Kunde kunde : menge) {
            System.out.println("Der Kunde wurde gefunden " + kunde.getName());
        }

        // Lege Neues Treeset an Kunden an
        Kunde anna1 = new Kunde("Anna die Erste", "Müller", "hier", LocalDate.parse("1979-05-14"));
        Kunde berta2 = new Kunde("Berta die Dritte", "Beerenbaum", "hier", LocalDate.parse("1980-03-15"));
        Kunde chris3 = new Kunde("Chris der Zweite", "Tall", "hier", LocalDate.parse("1979-01-07"));
        Kunde anton4 = new Kunde("Anton der Vierte", "Meier", "hier", LocalDate.parse("1982-10-23"));

        // neues set an Kunden und fuege sie alle ein
        Set<Kunde> neueKundenMengeNachGeburtstag = new TreeSet<>(Comparator.comparing(Kunde::getGeburtstag));
        List<Kunde> alleKundenNachGeburtstag = Arrays.asList(anna1, berta2, chris3, anton4);
        neueKundenMengeNachGeburtstag.addAll(alleKundenNachGeburtstag); // Fuege zum Tree set hinzue und sortiere nach Geburtstag

        System.out.println("\nGebe Kund:innen aus sortiert nach Geburtstag");
        for (Kunde kunde : neueKundenMengeNachGeburtstag) {
            System.out.println("Der/Die Kunden:innen: " + kunde.getName() + " nach Geburtstag: " + kunde.getGeburtstag());
        }

        menge.addAll(neueKundenMengeNachGeburtstag);
        System.out.println("\nGebe Alle Namen der Kunden:innen aus\n");
        for (Kunde kunde : menge) {
            System.out.println("Die Kund:innen wurde gefunden " + kunde.getName());
        }
    }
}
