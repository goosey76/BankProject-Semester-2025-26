package spielereien.generisch;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import bankprojekt.basisdaten.Geldbetrag;
import bankprojekt.basisdaten.Girokonto;
import bankprojekt.basisdaten.Konto;
import bankprojekt.basisdaten.Kunde;
import bankprojekt.basisdaten.Sparbuch;

/**
* eine sehr primitive Liste
* @author Doro
* @param <T> Datentyp der Elemente in der Liste
*/
public class SortierteListe<T extends Comparable<? super T>> {
/**
 * ein Element der Liste
 * @author Doro
 *
 */
private class Element
{
    private T wert;
    private Element naechster;

    /**
     * erstellt ein Element der Liste mit Inhalt w und
     * Nachfolger n
     * @param w Inhalt des neuen Elementes
     * @param n sein Nachfolger
     */
    public Element(T w, Element n)
    {
        wert = w;
        naechster = n;
    }
}

/**
 * "Zeiger" auf das erste Element der Liste
 */
private Element anfang = null;

/**
 * Die von allen Elementen zu erfüllende Bedingung
 */
private Probe<? super T> bedingung;

/**
 * erstellt eine leere Liste, deren Elemente die bedingung erfüllen müssen
 * @param bedingung zu überprüfende bedingung für alle Elemente in der Liste
 * @throws IllegalArgumentException wenn bedingung null ist
 */
public SortierteListe(Probe<? super T> bedingung)
{
    if(bedingung == null)
        throw new IllegalArgumentException();
    this.bedingung = bedingung;
}

/**
 * fügt den angegeben Wert in die Liste ein, wenn es die Bedingung erfüllt
 * @param w einzufügender Wert
 */
public void add(T w)
{
    if(bedingung.isOk(w))
    {
        Element neu = new Element(w, null);
        Element zeiger = anfang;
        if(zeiger == null)
        {
                anfang = neu;
            }
            else if(zeiger.wert.compareTo(w) > 0)
            {
                    neu.naechster = anfang;
                    anfang = neu;
            }
            else
            {
                while(zeiger.naechster != null
                        && zeiger.naechster.wert.compareTo(w) < 0)
                {
                    zeiger = zeiger.naechster;
                }
                neu.naechster = zeiger.naechster;
                zeiger.naechster = neu;
            }
    }
}

/**
 * die Methode soll alle Elemente aus neueMitglieder zu this hinzufügen – sofern sie
 * denn die Probe von this erfüllen.
 * @param neueMitglieder die Collection
 */
public void addAll(Collection<? extends T> neueMitglieder) {
    if (neueMitglieder == null) {
        throw new LeerException();
    }
    for (T element : neueMitglieder) {
        if (bedingung.isOk(element)) {
            this.add(element);
        }
    }
}


/**
 * die Methode soll alle Elemente aus this entfernen, die die bedingung erfüllen.
 * @param bedingung die Bedingung
 */
public void remove(Probe<? super T> bedingung) {
    if (anfang == null) {
        throw new LeerException();
    }
    Element aktuell = anfang;
    Element vorgaenger = null;

    while (aktuell != null) {
        // Prüfe Bedingung
        if (bedingung.isOk(aktuell.wert)) {
            // 1. Fall -- Entfernen --
            if (vorgaenger == null) {
                // Der neue Anfang ist der Nachfolger des aktuellen Elements.
                anfang = aktuell.naechster;
                } else {
                    // Der Vorgänger überspringt das aktuelle Element und zeigt auf dessen Nachfolger.
                    vorgaenger.naechster = aktuell.naechster;
                }
                // 'aktuell' wird auf den nächsten zu prüfenden Nachfolger gesetzt.
                aktuell = aktuell.naechster;
        }
        // 2. Fall, das zu entfernende Element ist nicht am Anfang der liste
        else {
            // 2. Fall -- Behalten --
            // Das Element soll behalten werden, also muss es neuer Vorgänger werden
            vorgaenger = aktuell;

            // Wir gehen zum nächsten Element, über um es zu Überprüfen
            aktuell = aktuell.naechster;
        }
    }
}

/**
 * die Methode entfernt alle Elemente aus this, die größer sind als element.
 * @param element Das Element
 * @return Ob die Liste verändert wurde
 */
public boolean removeAllBigger(T element) {
    if (anfang == null) {
        throw new LeerException();
    }

    // 1. Starte am Anfang der Liste
    Element aktuell = anfang;
    Element vorgaenger = null;


    // 2. Durchlaufe Liste, solange es ein Element gibt
    while (aktuell != null) {
        // Prüfe Bedingung, ob aktueller Wert > als Vergleichswert
        // 3. Prüfung: Ist der Wert größer als unser Grenzwert?
        if (aktuell.wert.compareTo(element) > 0) {

            // 1. Fall -- Entfernen --
            if (vorgaenger == null) {
                // Die ganze Liste muss gelöscht werden
                anfang = null;
            } else {
                // Der Vorgänger existiert und nächster wird auf null gesetzt.
                vorgaenger.naechster = null;
            }
            return true;
        }
        // Das Element ist in Ordnung (kleiner oder gleich dem Grenzwert).
        // Schiebe Zeiger einen weiter
        vorgaenger = aktuell;
        aktuell = aktuell.naechster;
    }
    return false;
}


    /**
     * Die to String Methode
     * @return den String mit den Elementen im der Liste
     */
    @Override
public String toString()
{
    String ausgabe = "[";
    Element zeiger = anfang;
    while(zeiger != null)
    {
        ausgabe += zeiger.wert + ", ";
        zeiger = zeiger.naechster;
    }
    ausgabe += "]";
    return ausgabe;
}

/**
 * liefert das erste Element dieser Liste zurück und entfernt es gleichzeitig
 * @return das erste Element aus dieser Liste
 * @throws LeerException wenn this keine Elemente enthält.
 */
public T getSpitze()
{
    if(anfang == null)
        throw new LeerException();
    T erstes = anfang.wert;
    anfang = anfang.naechster;
    return erstes;
}

/**
 * EigeneListe ausprobieren
 * @param args wird nicht verwendet
 */
public static void main(String args[])
{
    SortierteListe<Integer> l =
            new SortierteListe<Integer>(new LaesstAllesZu());
            //EigeneListe(Probe<Integer> bedingung)
    l.add(1);
    l.add(2);
    System.out.println(l);
    l.add(5);
    System.out.println(l);


    SortierteListe<String> woerter =
            new SortierteListe<>(new FaengtAnMitAProbe());
    woerter.add("Adalbert");
    woerter.add("Bertha");
    woerter.add("Anna");
    woerter.add("Anton");
    woerter.add("Zacharias");
    System.out.println(woerter);


/*		SortierteListe<Exception> dasGingAllesSchief =
            new SortierteListe<>(new FehlerNiedrigsterEbene());
    dasGingAllesSchief.add(new IllegalArgumentException());
    dasGingAllesSchief.add(new ArithmeticException());
    System.out.println(dasGingAllesSchief);
*/

    Kunde ich = new Kunde("Dorothea", "Hubrich", "zuhause", LocalDate.of(1976, 7, 13));
    Girokonto g1 = new Girokonto(ich, 1234, new Geldbetrag(0));
    Girokonto g2 = new Girokonto(ich, 999, new Geldbetrag(1000));
    Girokonto g3 = new Girokonto(ich, 34567, new Geldbetrag(70));

    SortierteListe<Konto> kontoliste =
            new SortierteListe<>(new LaesstAllesZu());
    kontoliste.add(g1);
    kontoliste.add(g2);
    kontoliste.add(g3);
    System.out.println(kontoliste);


    /**
     * Zweiter Aufgabenteil
     * Ergänzen Sie die main-Methode:
     * • Legen Sie eine SortierteListe mit dem Typparameter java.util.Date an und fügen Sie eine Collection von java.sql.Time hinzu.
     * • Legen Sie einen Wert vom Datentyp java.sql.Timestamp an und entfernen Sie alle
     * Elemente aus der SortierteListe, die größer sind.
     * • Löschen Sie alle Elemente aus dieser SortierteListe mit Hilfe einer LaesstAllesZu-Probe.
     */

    SortierteListe<Date> dateSortierteListe =
            new SortierteListe<>(new LaesstAllesZu());

    // Zeitstempel
    Timestamp vergleichsZeitstempel = new Timestamp(System.currentTimeMillis());

    // Collection of Times
    Collection<Time> times = new LinkedList<>();
    times.add(new Time(vergleichsZeitstempel.getTime()));
    times.add(new Time(vergleichsZeitstempel.getTime() - 3600000)); // Eine Stunde
    times.add(new Time(vergleichsZeitstempel.getTime() - 7200000)); // Zwei Stunden


    times.add(new Time(vergleichsZeitstempel.getTime() + 3600000)); // Eine Stunde
    times.add(new Time(vergleichsZeitstempel.getTime() + 7200000));

    dateSortierteListe.addAll(times);

    System.out.println("Der vergleichZeitstempel " + vergleichsZeitstempel);

    System.out.println("Liste vor dem Entfernen: " + dateSortierteListe);

    dateSortierteListe.removeAllBigger(vergleichsZeitstempel);

    System.out.println("Liste nach dem Entfernen: " + dateSortierteListe);

}
}
