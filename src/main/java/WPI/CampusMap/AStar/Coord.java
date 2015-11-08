package WPI.CampusMap.AStar;

/**
 * 
 * @author Max
 *
 */
public class Coord {
	private float x;
	private float y;

	/**
	 * Constructor
	 * @param x X-Coordinate
	 * @param y Y-Coordinate
	 */
	public Coord(float x, float y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Determines the Cartesian distance between two points
	 * @param other the Coord to get the distance to
	 * @return the distance
	 */
	public float distance(Coord other) {
		return (float)(Math.abs(Math.sqrt(Math.pow((other.getX() - this.getX()),2) + Math.pow((other.getY() - this.getY()),2))));
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
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

	public static void main(String[] args) {
	}
}
