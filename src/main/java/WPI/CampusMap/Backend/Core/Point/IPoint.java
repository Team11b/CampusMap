package WPI.CampusMap.Backend.Core.Point;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;

public interface IPoint extends Serializable {
	
	/**
	 * Gets the distance between two points.
	 * 
	 * @param other
	 *            The other point to get the distance too.
	 * @return The distance to the other point or -1 if on different maps.
	 */
	public double distance(IPoint other);
	/**
	 * Gets the coordinate of this point
	 * 
	 * @return The current coordinate of this point
	 */
	public Coord getCoord();
	/**
	 * Sets the coordinates location of this point
	 * 
	 * @param coord The new coordinate of this point
	 */
	public void setCoord(Coord coord);
	/**
	 * Gets the type of this point
	 * 
	 * @return The type of the point
	 */
	public String getType();
	/**
	 * Sets the type of this point
	 * 
	 * @param type The type to set this point to
	 */
	public void setType(String type);
	/**
	 * Returns the id of the point
	 * 
	 * @return The id of the point
	 */
	public String getId();
	/**|
	 * Sets the id of the point
	 * 
	 * @param newId the new id
	 */
	public boolean setId(String id);
	/**
	 * Get the display name of a point
	 * @return String display_name
	 */
	public String getDisplayName();
	/**
	 * Returns the list of neighboring points
	 * 
	 * @return the list of neighboring points
	 */
	public ArrayList<IPoint> getNeighborsP();
	/**
	 * Returns all the valid neighbors of the point
	 * 
	 * @param whitelist whitelist of valid floors
	 * 
	 * @return The list of only neighbors that exists on the specifies maps
	 */
	public ArrayList<IPoint> getValidNeighbors(HashSet<String> whitelist);
	/**
	 * Removes a neighbor by point
	 * @param point
	 * @return true if success from removing it from both places
	 */
	public boolean removeNeighbor(IPoint point);
	/**
	 * Removes a neighbor by name (String)
	 * @param pointId
	 * @return true if success from removing it from both places
	 */
	public boolean removeNeighbor(String pointId);
	/**
	 * Removes all neighbors of a point provided by name
	 */
	public void removeAllNeighbors();
	/**
	 * adds a point as a neighbor
	 * @param point
	 * @return true if success, false if already a neighbor
	 */
	public boolean addNeighbor(IPoint point);
	/**
	 * gets internal map names
	 * @return String map_name
	 */
	public String getMap();
	/**
	 * gets display name of maps
	 * @return String map name
	 */
	public String getMapDisplayName();
	/**
	 * For displaying destinations 
	 * @return map name/id
	 */
	public String toString();
	/**
	 * Test if its a realPoint 
	 * @return true if real
	 */
	public boolean exists();
	/**
	 * Gets a collection of neighbor points on other maps
	 * @return HashMap<String, ArrayList<String>>
	 */
	public HashMap<String, ArrayList<String>> getNeighborPointsOnOtherMaps();
	/**
	 * Determines whether the point is on a map that connects with Campus Map
	 * @return
	 */
	public boolean connectToCampus();
	/**
	 * Get a String of a building name a point is on.
	 * @return String
	 */
	public String getBuilding();
}
