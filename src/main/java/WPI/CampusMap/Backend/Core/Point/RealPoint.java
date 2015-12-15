package WPI.CampusMap.Backend.Core.Point;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Map.ProxyMap;

/**
 * @author g4vi
 *
 */
/**
 * @author g4vi
 *
 */
public class RealPoint implements IPoint,java.io.Serializable {

	private static final long serialVersionUID = 1262614340821579118L;
	private Coord coord;
	private String type = RealPoint.HALLWAY;
	private String id;
	private String map;
	private transient HashMap<String, IPoint> neighbors = new HashMap<String, IPoint>();
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
	 * Marks the containing map as changed.
	 */
	private void changed(){
		AllMaps.getInstance().getMap(map).changed();
	}
	
	/**
	 * Creates a new RealPoint with the given map and default values
	 * @param map name of the Map this point is located in
	 */
	public RealPoint(String map)
	{
		this.map = map;
		id = UUID.randomUUID().toString();
		neighbors = new HashMap<String, IPoint>();
	}
	
	public void constructNeighbors(){
//		System.out.println(this.toString() + "'s neighbors: "+ this.neighborList.size());
		neighbors = new HashMap<String, IPoint>();
		if(neighbors == null){
			System.out.printf("Null neighbors: %s\n", this.getMap()+"/"+this.getId());
		}
		for(String name: neighborList){
			if(name.contains("/")){
				neighbors.put(name, new ProxyPoint(name));
			}else{
				neighbors.put(name, new ProxyPoint(this.getMap()+"/"+name));
			}
//			System.out.println("Adding " +name+ " to " + this.toString()+"'s neighbor list");
		}
	}
	
	public void validateNeighbors(){ 
		if(neighbors == null){
			System.out.printf("Null neighbors: %s\n", this.getMap()+"/"+this.getId());
		}
		for(IPoint neighbor: neighbors.values()){
			if(!neighbor.exists()){
				System.out.println("Neighbor does not exist, removing from list of neighbors");
//				neighbors.remove(neighbor.getId());
				neighborList.remove(neighbor.getId());
			}
		}
	}

	/**
	 * Gets the distance between two points.
	 * 
	 * @param other
	 *            The other point to get the distance too.
	 * @return The distance to the other point or -1 if on different maps.
	 */
	@Override
	public double distance(IPoint other) {
		if(other.getMap().equals(this.getMap())){
			return this.getCoord().distance(other.getCoord());
		}else{
			return -1;
		}
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
	 * @param newId the new id
	 */
	@Override
	public boolean setId(String newId) {
		// TODO Add check to see if id already exists?
		if((!newId.equals(getId())) && !newId.equals("")){
			for(IPoint n : getNeighborsP()){
				System.out.println("Removed neighbor: "+n.removeNeighbor(this));	
			}

			IMap map = AllMaps.getInstance().getMap(getMap());
			if(map != null)
			{
				if(map.getPoint(newId) != null)
					return false;
				
				map.renamePoint(this, newId);
			}
			else
			{
				return false;
			}
			
			this.id = newId;
			
			for(IPoint n : getNeighborsP())
			{
				n.addNeighbor(this);
				n.getNeighborsP().contains(this);
				System.out.println("Replaced self in "+n+"'s neighbor list");
			}
		}
		
		return true;
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

	/**
	 * Returns all the valid nieghbors of the point
	 * 
	 * @param whitelist whitelist of valid floors
	 * 
	 * @return The list of only neighbors that exists on the specifies maps
	 */
	@Override
	public ArrayList<IPoint> getValidNeighbors(HashSet<String> whitelist) {
		ArrayList<IPoint> neigh = new ArrayList<IPoint>();
		if(whitelist == null || whitelist.size() == 0){
			neigh = this.getNeighborsP();
		}else{
			for(IPoint point: this.getNeighborsP()){
				if(whitelist.contains(point.getMap())){
					neigh.add(point);
				}
			}
		}
		return neigh;
	}

	@Override
	public boolean removeNeighbor(IPoint point) {
		changed();
		boolean firstSuccess = (this.neighbors.remove(point.getId()) != null) || (this.neighbors.remove(point.toString()) != null);
		boolean secondSuccess = this.neighborList.remove(point.toString()) || this.neighborList.remove(point.getId());
		System.out.println("First Success: "+firstSuccess+" Second Success: "+secondSuccess);
		if(!firstSuccess || !secondSuccess){
			System.out.println("First Point: "+this+" Second point: "+point);
			System.out.println(this.neighbors.keySet());
			System.out.println(this.neighborList);
		}
		return firstSuccess && secondSuccess;
	}
	
	@Override
	public boolean removeNeighbor(String pointId) {
		changed();
		return this.neighbors.remove(pointId) != null;
	}

	@Override
	public boolean addNeighbor(IPoint point) {
		changed();
		if (this.neighbors.containsValue(point))
			return false;
		
		if(point.getMap().equals(this.getMap())){
			this.neighbors.put(point.getId(), point);
			this.neighborList.add(point.getId());
		}else{
			this.neighbors.put(point.getMap()+"/"+point.getId(), point);
			this.neighborList.add(point.getMap()+"/"+point.getId());
		}
		return true;
	}
	
	public double distance(RealPoint other) {
		return this.getCoord().distance(other.getCoord());
	}
	
	@Override
	public String getMap() {
		return map;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof IPoint) {
			IPoint that = (IPoint) other;
//			System.out.println(this);
//			System.out.println(that);
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
		changed();
		this.neighbors.clear();
		this.neighborList = new ArrayList<String>();
	}

	@Override
	public boolean exists() {
		return true;
	}


	@Override
	public String toString() {
		return map+"/"+id;
	}

	@Override
	public HashMap<String, ArrayList<String>> getNeighborPointsOnOtherMaps() {
		HashMap<String, ArrayList<String>> temp = new HashMap<String, ArrayList<String>>();
		for(IPoint point: this.getNeighborsP()){
			String map = point.getMap();
			if(!map.equals(this.getMap())){
				if(!temp.keySet().contains(map)) temp.put(map, new ArrayList<String>());

				temp.get(map).add(point.toString());
			}
		}
		return temp;
	}

	@Override
	public boolean connectToCampus() {
		return ((ProxyMap) AllMaps.getInstance().getMap(getMap())).connectedToCampus();
	}
	
	@Override
	public String getBuilding() {
		return this.getMap().split("-")[0];

	}

	@Override
	public String getMapDisplayName() {		
		return getMap().replace('_', ' ');
	}

	
	@Override
	public String getDisplayName() {			
		return getId().replace('_', ' ');
	}
}
