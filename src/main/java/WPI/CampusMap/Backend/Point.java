package WPI.CampusMap.Backend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * 
 * @author Max Stenke
 *
 */
public class Point implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1262614340821579118L;
	private Coord coord;
	private String type;
	private String id;
	private String map;
	private HashMap<String, Point> neighbors = new HashMap<String, Point>();
	
	public static final String OUT_DOOR = "out_door";
	/** Standard type of door */
	public static final String STAIRS = "stairs";
	/** Standard type of stairs */
	public static final String HALLWAY = "hallway";
	/** Standard type of hallway */
	public static final String ELEVATOR = "elevator";
	/** Standard type of elevator */

	/**
	 * Point constructor
	 * @param coord Coordinate for this Point
	 * @param type Type of point, based upon static constants
	 * @param id unique identifier
	 * @param map name of the Map this point is located in
	 */
	public Point(Coord coord, String type, String id, String map) {
		this.coord = coord;
		this.type = type;
		this.id = id;
		this.setMap(map);
		this.neighbors = new HashMap<String, Point>();
	}

	public Point(String map)
	{
		id = UUID.randomUUID().toString();
		neighbors = new HashMap<String, Point>();
	}

	/**
	 * Gets the distance between two points.
	 * 
	 * @param other
	 *            The other point to get the distance too.
	 * @return The distance to the other point.
	 */
	public double distance(Point other) {
		return this.getCoord().distance(other.getCoord());
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

	public ArrayList<Point> getNeighborsP() {
		ArrayList<Point> tempAL = new ArrayList<Point>(neighbors.values());
		return tempAL;
	}

	public ArrayList<String> getNeighborsID() {
		ArrayList<String> tempAL = new ArrayList<String>();
		tempAL.addAll(neighbors.keySet());
		return tempAL;
	}

	/**
	 * returns a list of all neighbors of this point which are valid locations a
	 * valid location is any Point which does not have a type of wall
	 * 
	 * @return an array of any neighbor points which do not have a type wall
	 */
	public ArrayList<Point> getValidNeighbors() {
		ArrayList<Point> neigh = this.getNeighborsP();
		ArrayList<Point> trim = new ArrayList<Point>();

		/*for (int i = 0; i < neigh.size(); i++) {
			if (neigh.get(i).getType() == null || !(neigh.get(i).getType().equalsIgnoreCase(Point.WALL))) {
				trim.add(neigh.get(i));
			}
		}*/

		return trim;
	}

	/**
	 * Removes Point from list of neighbors.
	 * 
	 * @param point
	 *            point to be removed
	 * @return True if successfully removed, False if not removed
	 */
	public boolean removeNeighbor(Point point) {
		return this.neighbors.remove(point.id) != null;
	}

	/**
	 * Adds a neighbor to this point
	 * 
	 * @param point
	 *            the new Point to add
	 * @return true if the neighbor was added, false if the neighbor already
	 *         exists
	 */
	public boolean addNeighbor(Point point) {
		if (this.neighbors.containsValue(point))
			return false;

		this.neighbors.put(point.getId(), point);

		return true;
	}

	/**
	 * Removes all the neighbors from this point
	 */
	public void removeAllNeighbors() {
		this.neighbors.clear();
	}
	
	/**
	 * Returns the normal point version
	 * 
	 * @return The current Point
	 */
	public Point getNormalPoint(){
		 return this;
	}
	
	/**
	 * Converts this point to a connection Point and links the new connection point to the specified map and point
	 * 
	 * @return The new connection point
	 */
	public ConnectionPoint getConnectionPoint(){
		ConnectionPoint temp = new ConnectionPoint(this.getCoord(), this.getType(), this.getId(), this.getMap(), "", "");
		for(Point point: this.getNeighborsP()){
			System.out.println(point);
			temp.addNeighbor(point);
		}
		 return temp;
	}

	@Override
	public boolean equals(Object other) {
		boolean result = false;
		if (other instanceof Point) {
			Point that = (Point) other;
			if (this.getMap() != null) {
				result = (this.getCoord().equals(that.getCoord())) && (this.getMap().equals(((Point) other).getMap()));
			}
			else {
				result = (this.getCoord().equals(that.getCoord()));
			}
		}
		return result;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return id;
	}
}
