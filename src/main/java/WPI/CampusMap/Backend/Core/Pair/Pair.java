package WPI.CampusMap.Backend.Core.Pair;

public final class Pair<T1, T2> {
	private T1 a;
	private T2 b;

	/**
	 * Constructor 
	 * @param a
	 * @param b
	 */
	public Pair(T1 a, T2 b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public int hashCode() {
		return (a.hashCode() + "/" + b.hashCode()).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Pair<?, ?>) {
			Pair<?, ?> pair = (Pair<?, ?>) obj;

			return a.equals(pair.a) && b.equals(b);
		}

		return super.equals(obj);
	}

	public T1 getA() {
		return a;
	}

	public T2 getB() {
		return b;
	}
}
