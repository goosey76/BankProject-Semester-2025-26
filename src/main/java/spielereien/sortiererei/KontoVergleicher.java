package spielereien.sortiererei;

import bankprojekt.basisdaten.Geldbetrag;
import bankprojekt.basisdaten.Konto;

/**
* ein Vergleicher für zwei Konten
*/
public class KontoVergleicher implements Vergleicher<Konto> {

	@Override
	public int vergleichen(Konto a, Konto b) {
        Geldbetrag standA = a.getKontostand();
        Geldbetrag standB = b.getKontostand();
        return standA.compareTo(standB);

	}
}
