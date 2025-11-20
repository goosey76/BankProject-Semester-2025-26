package spielereien.generisch;

/**
 * eine Probe, die alle Elemente erlaubt
 */
public class LaesstAllesZu implements Probe<Object> {

	@Override
	public boolean isOk(Object pruefobjekt) {
		return true;
	}

}
