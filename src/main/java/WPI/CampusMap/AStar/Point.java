package WPI.CampusMap.AStar;

import java.util.ArrayList;

/**
 * 
 * @author Max Stenke
 *
 */
public class Point {
	private Coord coord;
	private String type;
	private String id;
	private Point[] neighborsP;
	private String[] neighborsID;

	public static final String WALL = "wall";
	/** Standard type of wall */
	public static final String DOOR = "door";
	/** Standard type of door */
	public static final String STAIRS = "stairs";
	/** Standard type of stairs */
	public static final String HALLWAY = "hallway";
	/** Standard type of hallway */
	public static final String ELEVATOR = "elevator";
	/** Standard type of elevator */

	/**
	 * Constructor
	 * 
	 * @param coord
	 *            Coordinate of point
	 * @param type
	 *            Point type using public constants
	 * @param id
	 *            ID of point
	 */
	public Point(Coord coord, String type, String id) {
		this.coord = coord;
		this.type = type;
		this.id = id;
		this.neighborsP = new Point[8];
		this.neighborsID = getNeighborsIDs(neighborsP);
	}
	
	public Point(){
	}

	private String[] getNeighborsIDs(Point[] object) {
		String[] temp = new String[8];
		for (int i = 0; i < object.length; i++) {
			temp[i] = object[i].getId();
		}
		return temp;
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

	public Point[] getNeighborsP() {
		return neighborsP;
	}

	public void setNeighborsP(Point[] neighborsP) {
		this.neighborsP = neighborsP;
	}

	public String[] getNeighborsID() {
		return neighborsID;
	}

	public void setNeighborsID(String[] neighborsID) {
		this.neighborsID = neighborsID;
	}

	/**
	 * returns a list of all neighbors of this point which are valid locations a
	 * valid location is any Point which does not have a type of wall
	 * 
	 * @return an array of any neighbor points which do not have a type wall
	 */
	public Point[] getValidNeighbors() {
		Point[] neigh = this.getNeighborsP();
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
