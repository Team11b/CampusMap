package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar.Frontier;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;

public class RealPoint implements IPoint,java.io.Serializable {

	private static final long serialVersionUID = 1262614340821579118L;
	private Coord coord;
	private String type;
	private String id;
	private String map;
	private transient HashMap<String, IPoint> neighbors = new HashMap<String, IPoint>();
	//TODO: Make neighbors transient and make a list of strings of neighbor names that actually serializes
	
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

	public RealPoint(String map)
	{
		id = UUID.randomUUID().toString();
		neighbors = new HashMap<String, IPoint>();
	}
	@Override
	public double distance(IPoint other) {
		return this.getCoord().distance(other.getCoord());
	}

	@Override
	public Coord getCoord() {
		return coord;
	}

	@Override
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		// TODO Add check to see if id already exists?
		IMap map = AllMaps.getMap(getMap());
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

	@Override
	public ArrayList<IPoint> getNeighborsP() {
		ArrayList<IPoint> tempAL = new ArrayList<IPoint>(neighbors.values());
		return tempAL;
	}

	@Override
	public void buildFrontier(Frontier frontier, Node fromNode, IPoint goal) {
		// TODO Auto-generated method stub

	}

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

		this.neighbors.put(point.getId(), point);

		return true;
	}
	
	@Override
	public String getMap() {
		return map;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Point) {
			Point that = (Point) other;
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
	}


}
