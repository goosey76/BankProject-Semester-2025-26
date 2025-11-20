package spielereien.sortiererei;

/**
 * Eine eher unsinnige Klasse, deren Objekte nur mit anderen
 * Number-Objekten vergleichbar sind, aber nicht untereinander. Man kann 
 * sie also nicht sortieren.
 */
public class Komisch implements Comparable<Komisch> {
	/**
	 * im Objekt gespeicherter Wert
	 */
	private Number wert;

	/**
	 * erstellt ein Objekt der Klasse mit dem angegeben Wert
	 * @param wert Wert des Objekts
	 */
	public Komisch(Number wert) {
		this.wert = wert;
	}

    /**
     * gibt den neuen gespeicherten Wert der Klasse zurück
     * @return this.Wert
     */

    public Number getWert() {
        return this.wert;
    }

    /**
     * Gibt den Wert für Komisch und Hashcode aus
     * @return
     */
	@Override
	public String toString() {
		return "Komisch - " + this.getWert() + " Hash: " + this.hashCode();
	}

    /**
     * vergleicht beide Komisch Klassen anhand ihren DoubleWert
     * und gibt den verglichen
     * @param o dem VergleichsObjekt
     * @return  Int Wert.
     */
    @Override
    public int compareTo(Komisch o) {
        return Double.compare(this.wert.doubleValue(), o.getWert().doubleValue());
    }
}
