package WPI.CampusMap;

public class Coord {
	private float x;
	private float y;

	public Coord(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public static float distance(Coord p1, Coord p2) {
		return (float) Math.sqrt(Math.pow(p2.getY() - p1.getY(), 2) + Math.pow(p2.getX() - p1.getX(), 2));
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

}
