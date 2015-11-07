package WPI.CampusMap.AStar;

public class Coord {
	private float x;
	private float y;

	public Coord(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float distance(Coord other) {
		return Math.abs(
				(float) (Math.sqrt(Math.pow(2, other.getY() + this.getY()) + Math.pow(2, other.getX() + this.getX()))));

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
		Coord cTwo = new Coord(1, 1);
		Coord cThree = new Coord(2, 1);
		
		boolean test = cTwo.equals(cThree);
		System.out.println(test);
	}
}
