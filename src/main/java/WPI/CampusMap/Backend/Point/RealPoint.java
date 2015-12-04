package WPI.CampusMap.Backend.Point;

import java.util.ArrayList;
import java.util.HashMap;

import WPI.CampusMap.Backend.Coord;
import WPI.CampusMap.Backend.Map.AllMaps;
import WPI.CampusMap.Backend.Map.IMap;
import WPI.CampusMap.Backend.Map.Map;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.AStar.Frontier;

public class RealPoint implements IPoint,java.io.Serializable {

	private static final long serialVersionUID = 1262614340821579118L;
	private Coord coord;
	private String type;
	private String id;
	private String map;
	private HashMap<String, IPoint> neighbors = new HashMap<String, IPoint>();
	//TODO: Make neighbors transient and make a list of strings of neighbor names that actually serializes
	
	public static final String OUT_DOOR = "out_door";
	/** Standard type of door */
	public static final String STAIRS = "stairs";
	/** Standard type of stairs */
	public static final String HALLWAY = "hallway";
	/** Standard type of hallway */
	public static final String ELEVATOR = "elevator";
	/** Standard type of elevator */
	
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
