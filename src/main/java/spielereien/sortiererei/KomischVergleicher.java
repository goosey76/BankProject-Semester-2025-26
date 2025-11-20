package spielereien.sortiererei;

public class KomischVergleicher implements Vergleicher<Komisch>{

    @Override
    public int vergleichen(Komisch a, Komisch b) {
        Number wertA = a.getWert();
        Number wertB = b.getWert();

        return Double.compare(wertA.doubleValue(), wertB.doubleValue());
    }
}
