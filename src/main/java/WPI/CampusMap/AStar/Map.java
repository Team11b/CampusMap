package WPI.CampusMap.AStar;

public class Map {

	private String png;
	private Point[] map;

	public Map(String png, Point[] map) {
		this.png = png;
		this.map = map;
	}

	public Path astar(Point start, Point end) {
		Path temp = null;
		return temp; // TODO: astar();
	}

	public void expandWalls() {
		// TODO: expandWalls();
	}

	public String getPng() {
		return png;
	}

	public void setPng(String png) {
		this.png = png;
	}

	public Point[] getMap() {
		return map;
	}

	public void setMap(Point[] map) {
		this.map = map;
	}

}
