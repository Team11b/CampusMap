package WPI.CampusMap.AStar;

import java.util.ArrayList;

public class Point {
	private Coord coord;
	private String type;
	private int id;
	private Point[] neighbors;

	public static final String WALL = "wall";
	public static final String DOOR = "door";
	public static final String STAIRS = "stairs";
	public static final String HALLWAY = "hallway";
	public static final String ELEVATOR = "elevator";

	public Point(Coord coord, String type, int id) {
		this.coord = coord;
		this.type = type;
		this.id = id;
		this.neighbors = new Point[8];
	}

	public float distance(Point p1, Point p2) {
		return p1.coord.distance(p1.coord, p2.coord);
	}

	public Coord getCoord() {
		return coord;
	}

	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Point[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Point[] neighbors) {
		this.neighbors = neighbors;
	}

	public Point[] getValidNeighbors() {
		Point[] neigh = this.getNeighbors();
		ArrayList<Point> trim = new ArrayList<Point>();

		for (int i = 0; i < neigh.length; i++) {
			if (!(neigh[i].getType().equalsIgnoreCase(Point.WALL))) {
				trim.add(neigh[i]);
			}
		}

		return (Point[]) trim.toArray();
	}
}
