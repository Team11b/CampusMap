package WPI.CampusMap.Backend.Core.Map;

import java.util.ArrayList;
import java.util.Collection;

import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

public interface IMap {
	/**
	 * Get the scale from inches to feet.
	 * 
	 * @return The scale from inches to feet.
	 */
	public float getScale();
	/**
	 * Gets the building name by trimming the floor from name
	 * @return String of building name
	 */
	public String getBuilding();
	/**
	 * Set the scale from inches to feet.
	 * 
	 * @param f the inches to feet scale.
	 */
	public void setScale(float f);
	/**
	 * Gets the name of this map.
	 * 
	 * @return The name of this map.
	 */
	public String getName();
	/**
	 * Get the display name of this map
	 * @return the display name of the map
	 */
	public String getDisplayName();
	/**
	 * Gets the loaded image of the map png to display.
	 * 
	 * @return The loaded image to display for this map.
	 */
	public ImageIcon getLoadedImage();
	/**
	 * Gets a point from the map.
	 * 
	 * @param id
	 *            The id of the point to get.
	 * @return The point with the id.
	 */
	public RealPoint getPoint(String id);
	/**
	 * Gets all the points as a collection
	 * @return Collection
	 */
	public Collection<RealPoint> getAllPoints();
	/**
	 * Removes the point with the given ID from the map array, and from the
	 * neighbor arrays of all points on the map
	 * 
	 * @param id
	 *            The ID of the point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	public boolean removePoint(String id);
	/**
	 * Adds a point to the map. Does NOT connect the point to any other points.
	 * 
	 * @param point
	 *            a new Point to add
	 * @return true if the point was added, false if there already exists a
	 *         point with the same ID
	 */
	public boolean removePoint(IPoint point);
	/**
	 * 
	 * @param point
	 * @return true if point added
	 */
	public boolean addPoint(RealPoint point);
	/**
	 * creates a point wiht coordlocation
	 * @param location
	 * @return the point
	 */
	public IPoint createPoint(Coord location);
	/**
	 * Adds an edge between two Points
	 * 
	 * @param point
	 *            the first Point
	 * @param other
	 *            the second Point
	 * @return true if the edge was added, false if one Points doesn't exist or
	 *         if the edge already exists
	 */
	public boolean addEdge(IPoint point, IPoint other);
	/**
	 * Removes an edge between two Points
	 * 
	 * @param point
	 *            the first Point
	 * @param other
	 *            the second Point
	 * @return true if the edge was removed, false if one Points doesn't exist
	 *         or if the edge does not exist
	 */
	public boolean removeEdge(IPoint point, IPoint other);
	/**
	 * Removes a point and readds it under a new name	
	 * @param p the point
	 * @param newName the new name
	 */
	public void renamePoint(RealPoint p, String newName);
	/**
	 * Determines if the map is connected to campus
	 * @return true if true
	 */
	public boolean connectedToCampus();
	/**
	 * Gets a list of connecting points that are connected
	 * @return the list
	 */
	public ArrayList<IPoint> pointsConnectedToOtherMaps();
	/**
	 * Uses the serializer to save the map data.
	 */
	public void save();
	/**
	 * for keeping track if it needs to save
	 * @return true if unsaved changes
	 */
	public boolean unsavedChanged();
	/**
	 * sets unsavedChanges to true
	 */
	public void changed();
	/**
	 * gets the floor name of the map 
	 * @return string of floor name, by trimming buildingname
	 */
	String getFloorName();
}