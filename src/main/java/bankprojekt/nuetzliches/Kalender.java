package bankprojekt.nuetzliches;

import java.time.LocalDate;

/**
 * Bereitstellung des heutigen Datums
 */
public class Kalender {
    /**
     * der StandartKonstruktor
     */
    public Kalender() {
        // Kein Aufruf
    }
	/**
	 * liefert das heutige Datum
	 * @return das heutige Datum
	 */
	public LocalDate getHeutigesDatum() {
		return LocalDate.now();
	}
}
