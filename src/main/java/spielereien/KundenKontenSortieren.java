package spielereien;

import bankprojekt.basisdaten.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Die Kunden und Konten sortiert
 */
public class KundenKontenSortieren {
    // Standard Konstruktor
    public KundenKontenSortieren() {
        // Kein Aufruf
    }

    /**
     * die Main
     * @param args mit args
     */
    public static void main(String[] args) {
        // Vier angelegte Kunden
        Kunde kunde = new Kunde("Max","Mustermann", "Berndstr.12", LocalDate.of(1997,2, 18));
        Kunde kunde2 = new Kunde("Peter","Thorsten", "Da Hang So 34", LocalDate.of(1998,4, 19));
        Kunde kunde3 = new Kunde("Sascha","Trieef", "Burg Keng", LocalDate.of(1999,8, 12));
        Kunde kunde4 = new Kunde("Quinnn","scheeef", "mecd donad", LocalDate.of(2000,10, 24));

        // Arrays von 4 Kunden
        Konto[] kontenArray = new Konto[4];

        kontenArray[0] = new Girokonto(kunde, 12345678L, new Geldbetrag(400));
        kontenArray[3] = new Sparbuch(kunde2, 123456789L);
        kontenArray[2] = new Girokonto (kunde3, 1234567810L, new Geldbetrag(500));
        kontenArray[1] = new Sparbuch (kunde4, 1234567811L);

        // Vergleicht die Geburtstage von der Comparable Methode in Kunde
        Comparator<Konto> inhaberGeburtstagComparator = new Comparator<Konto>() {
            @Override
            public int compare(Konto o1, Konto o2) {
                //vergleicht die Geburtstage mit der compareTo - Methode
                return o1.getInhaber().compareTo(o2.getInhaber());
            }
        };

        System.out.println("Konteninhaber unsortiert nach Geburtstag!");
        System.out.println(Arrays.toString(kontenArray));

        // Sortiere das Array
        System.out.println("-----------------------------------------");
        Arrays.sort(kontenArray, inhaberGeburtstagComparator);
        System.out.println("Konteninhaber sortiert nach Geburtstag!");
        System.out.println(Arrays.toString(kontenArray));


    }

}
