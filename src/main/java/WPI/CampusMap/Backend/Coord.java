package WPI.CampusMap.Backend;

/**
 * 
 * @author Max Stenke
 */

public class Coord implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -940085833607276260L;
	private double x;
	private double y;

	/**
	 * Constructor
	 * 
	 * @param d
	 *            X-Coordinate
	 * @param e
	 *            Y-Coordinate
	 */
	public Coord(float d, float e) {
		this.x = d;
		this.y = e;
	}

	public Coord(double d, double e) 
	{
		this.x = (float)d;
		this.y = (float)e;
	}

	/**
	 * Determines the Cartesian distance between two points
	 * 
	 * @param other
	 *            the Coord to get the distance to
	 * @return the distance
	 */
	public float distance(Coord other) {
		return (float) (Math
				.abs(Math.sqrt(Math.pow((other.getX() - this.getX()), 2) + Math.pow((other.getY() - this.getY()), 2))));
	}

	public float getX() {
		return (float)x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return (float)y;
	}

	public void setY(float y) {
		this.y = y;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Coord) {
			Coord that = (Coord) other;
			boolean X = this.getX() == that.getX();
			boolean Y = this.getY() == that.getY();
			result = (X && Y);
		}
		return result;
	}

	@Override
	public String toString() {
		return "{X: " + x + ", Y:" + y + "}";
	}
}
