package WPI.CampusMap.AStar;

import java.util.ArrayList;

public class Point {
	private Coord coord;
	private String type;
	private String id;
	private Point[] neighbors;

	public static final String WALL = "wall";
	public static final String DOOR = "door";
	public static final String STAIRS = "stairs";
	public static final String HALLWAY = "hallway";
	public static final String ELEVATOR = "elevator";

	public Point(Coord coord, String type, String id) {
		this.coord = coord;
		this.type = type;
		this.id = id;
		this.neighbors = new Point[8];
	}

	public float distance(Point other) {
		return this.coord.distance(other.getCoord());
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Point[] getNeighbors() {
		return neighbors;
	}

	public void setNeighbors(Point[] neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * returns a list of all neighbors of this point which are valid locations a
	 * valid location is any Point which does not have a type of wall
	 * 
	 * @return an array of any neighbor points which do not have a type wall
	 */
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

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Point) {
			Point that = (Point) other;
			result = (this.getCoord().equals(that.getCoord()));
		}
		return result;
	}
}
