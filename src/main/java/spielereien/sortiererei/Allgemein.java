package spielereien.sortiererei;

/**
 * Eine Klasse von Objekten, die sich mit allem vergleichen lassen
 */
public class Allgemein implements Comparable<Allgemein>
{
	@Override
	public int compareTo(Allgemein o) {
		return Integer.compare(this.hashCode(), o.hashCode());
	}

    @Override
    public String toString() {
        return "Allgemein " + this.hashCode();
    }
}