package WPI.CampusMap.AStar;

public class Coord {
	private float x;
	private float y;

	public Coord(float x, float y) {
		this.x = x;
		this.x = y;
	}

	public float distance(Coord other) {
		return Math.abs( (float) (Math.sqrt(Math.pow(2, other.getY() + this.getY()) + Math.pow(2, other.getX() + this.getX()))));

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

	public boolean equals(Coord other) {
		return ((this.getX() == other.getX()) && (this.getY() == other.getY()));
	}
}
