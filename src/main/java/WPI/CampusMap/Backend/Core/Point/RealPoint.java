package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar.Frontier;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;

public class RealPoint implements IPoint,java.io.Serializable {

	private static final long serialVersionUID = 1262614340821579118L;
	private Coord coord;
	private String type = RealPoint.HALLWAY;
	private String id;
	private String map;
	private transient HashMap<String, IPoint> neighbors = new HashMap<String, IPoint>();
	//TODO: Make neighbors transient and make a list of strings of neighbor names that actually serializes
	private ArrayList<String> neighborList = new ArrayList<String>();
	
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
	public RealPoint(Coord coord, String type, String id, String map) {
		this.coord = coord;
		this.type = type;
		this.id = id;
		this.map = map;
		this.neighbors = new HashMap<String, IPoint>();
	}
	
	/**
	 * Creates a new RealPoint with the given map and default values
	 * @param map
	 */
	public RealPoint(String map)
	{
		id = UUID.randomUUID().toString();
		neighbors = new HashMap<String, IPoint>();
	}
	
	public void constructNeighbors(){
		for(String name: neighborList){
			neighbors.put(name, new ProxyPoint(name));
		}
		validateNeighbors();
	}
	
	public void validateNeighbors(){
		ArrayList<String> temp = new ArrayList<String>(); 
		for(IPoint neighbor: neighbors.values()){
			if(neighbor.exists()){
				if(neighbor.getMap() == this.getMap()){
					temp.add(neighbor.getId());
				}else{
					temp.add(neighbor.getMap() + "/" + neighbor.getId());
				}
			}else{
				neighbors.remove(neighbor.getId());
			}
		}
		neighborList = temp;
	}

	/**
	 * Gets the distance between two points.
	 * 
	 * @param other
	 *            The other point to get the distance too.
	 * @return The distance to the other point.
	 */
	@Override
	public double distance(IPoint other) {
		return this.getCoord().distance(other.getCoord());
	}

	/**
	 * Gets the coordinate of this point
	 * 
	 * @return The current coordinate of this point
	 */
	@Override
	public Coord getCoord() {
		return coord;
	}

	/**
	 * Sets the coordinates location of this point
	 * 
	 * @param coord The new coordinate of this point
	 */
	@Override
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	/**
	 * Gets the type of this point
	 * 
	 * @return The type of the point
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * Sets the type of this point
	 * 
	 * @param type The type to set this point to
	 */
	@Override
	public void setType(String type) {
		this.type = type;

	}

	/**
	 * Returns the id of the point
	 * 
	 * @return The id of the point
	 */
	@Override
	public String getId() {
		return id;
	}

	/**|
	 * Sets the id of the point
	 * 
	 * @param The new id
	 */
	@Override
	public void setId(String id) {
		// TODO Add check to see if id already exists?
		IMap map = AllMaps.getInstance().getMap(getMap());
		if(map != null)
		{
			map.renamePoint(this, id);
		}
		
		for(IPoint n : getNeighborsP())
		{
			n.removeNeighbor(this);
			n.addNeighbor(this);
		}
		
		this.id = id;

	}
	
	/**
	 * Returns the list of neighboring points
	 * 
	 * @return the list of neighboring points
	 */
	@Override
	public ArrayList<IPoint> getNeighborsP() {
		ArrayList<IPoint> tempAL = new ArrayList<IPoint>(neighbors.values());
		return tempAL;
	}

	@Override
	public void buildFrontier(Frontier frontier, Node fromNode, IPoint goal) {
		// TODO Auto-generated method stub

	}

	/**
	 * Returns all the valid nieghbors of the point
	 * 
	 * @param whitelist whitelist of valid floors
	 * 
	 * @return The list of only neighbors that exists on the specifies maps
	 */
	@Override
	public ArrayList<IPoint> getValidNeighbors(ArrayList<String> whitelist) {
		//TODO check white list when returning neighbors
		ArrayList<IPoint> neigh = this.getNeighborsP();

		return neigh;
	}

	@Override
	public boolean removeNeighbor(IPoint point) {
		return this.neighbors.remove(point.getId()) != null;
	}
	
	@Override
	public boolean removeNeighbor(String pointId) {
		return this.neighbors.remove(pointId) != null;
	}

	@Override
	public boolean addNeighbor(IPoint point) {
		if (this.neighbors.containsValue(point))
			return false;
		
		if(point.getMap() == this.getMap()){
			this.neighbors.put(point.getId(), point);
		}else{
			this.neighbors.put(point.getMap()+"/"+point.getId(), point);
		}
		return true;
	}
	
	@Override
	public String getMap() {
		return map;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof IPoint) {
			IPoint that = (IPoint) other;
			boolean result = getMap().equals(that.getMap()) && getId().equals(that.getId());
			return result;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return (this.getMap() + "/" + getId()).hashCode();
	}

	@Override
	public void removeAllNeighbors() {
		this.neighbors.clear();
		this.neighborList = new ArrayList<String>();
	}

	@Override
	public boolean exists() {
		return true;
	}


}
