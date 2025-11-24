package spielereien.sortiererei;

/**
 * vergleicht Objekte anhand ihrer String-Darstellung
 */
public class AllesVergleicher implements Vergleicher<Object>{

    /**
     * Gibt den Unterschied als String zurück
     * @param a Zugriff auf a
     * @param b Zugriff auf b
     * @return gibt den Wert, für den Vergleich an, falls -1, 0 oder 1 ist
     */
	@Override
	public int vergleichen(Object a, Object b) {
		return a.toString().compareToIgnoreCase(b.toString()); // unabhängig von sein Case klein und groß in 1.
	}
}
