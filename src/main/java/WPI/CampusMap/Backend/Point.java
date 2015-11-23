package WPI.CampusMap.Backend;

import java.util.ArrayList;

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
	private ArrayList<Point> neighborsP;
	private ArrayList<String> neighborsID;
	private String map;

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
	public Point(Coord coord, String type, String id, String map) {
		this.coord = coord;
		this.type = type;
		this.id = id;
		this.neighborsP = new ArrayList<Point>();
//		 this.neighborsID = getNeighborsIDs(neighborsP);
		this.neighborsID = new ArrayList<String>();
		this.setMap(map);
	}

	public Point() {

	}

	// private ArrayList<String> getNeighborsIDs(Point[] object) {
	// ArrayList<String> ids = new ArrayList<String>();
	// for (int i = 0; i < object.length; i++) {
	// if(temp[i] != null){
	// temp[i] = object[i].getId();
	// }
	// }
	// return temp;
	// }

	/**
	 * Gets the distance between two points.
	 * @param other The other point to get the distance too.
	 * @return The discane to the other point.
	 */
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

	public ArrayList<Point> getNeighborsP() {
		return neighborsP;
	}

	public void setNeighborsP(ArrayList<Point> neighborsP) {
		this.neighborsP = neighborsP;
	}

	public ArrayList<String> getNeighborsID() {
		return neighborsID;
	}

	public void setNeighborsID(ArrayList<String> neighborsID) {
		this.neighborsID = neighborsID;
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

		for (int i = 0; i < neigh.size(); i++) {
			if (neigh.get(i).getType() == null || !(neigh.get(i).getType().equalsIgnoreCase(Point.WALL))) {
				trim.add(neigh.get(i));
			}
		}

		return trim;
	}

	/**
	 * Removes Point from list of neighbors.
	 * @param point point to be removed
	 * @return True if successfully removed, False if not removed
	 */
	public boolean removeNeighbor(Point point) {
		try {
			this.neighborsID.remove(point.id);
		} catch (NullPointerException e) {
			return false;
		}

		try {
			this.neighborsP.remove(point);
			return true;
		} catch (NullPointerException e) {
			return false;
		}
	}

	/**
	 * adds a neighbor to this point
	 * 
	 * @param point
	 *            the new Point to add
	 * @return true if the neighbor was added, false if the neighbor already
	 *         exists
	 */
	public boolean addNeighbor(Point point) {
		if(this.getNeighborsID().contains(point.getId())&&this.getNeighborsP().contains(point)) return false;
		
		if(!this.getNeighborsID().contains(point.getId())){
			this.neighborsID.add(point.getId());
		}
		if(!this.getNeighborsP().contains(point)){
			this.neighborsP.add(point);
		}
		return true;
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
	
	@Override
	public String toString() {
		return "" + this.getId() + "\t" + this.getCoord();
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}
}
