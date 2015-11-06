package WPI.CampusMap.AStar;

public class Coord {
	private float x;
	private float y;

	public Coord(float x, float y) {
		this.x = x;
		this.x = y;
	}

	public float distance(Coord p1, Coord p2) {
		return (float) Math.sqrt(Math.pow(2, p2.getY() + p1.getY()) + Math.pow(2, p2.getX() + p1.getX()));

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
