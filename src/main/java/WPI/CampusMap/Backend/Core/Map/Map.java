package WPI.CampusMap.Backend.Core.Map;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Recording.Serialization.OLSSerializer;

/**
 * Represents a single map/area.
 * 
 * @author Jacob Zizmor
 * @author Max Stenke
 * @author Will Craft
 *
 */
@Deprecated
public class Map implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3434772073791894710L;
	private float scale;
	private String name;
	private String png;
	private String xml;
	private HashMap<String, Point> allPoints;
	private ImageIcon loadedImage;
	private static HashMap<String, Map> allMaps = new HashMap<String, Map>();

	/**
	 * Creates a map from an xml file. Default values are used if the xml cannot
	 * be parsed.
	 * 
	 * @param name
	 *            The name of the map to be created.
	 */
	public Map(String name) {
		this.scale = 100;
		this.name = name;
		this.png = "maps/" + name + ".png";
		this.xml = "XML/" + this.name + ".xml";
		this.allPoints = new HashMap<String, Point>();

		Map testMap = OLSSerializer.read(name);
		if (testMap != null) {
			setScale(testMap.getScale());
			setAllPoints(testMap.getAllPoints());
		}
		Map.allMaps.remove(name);
		Map.allMaps.put(name, this);
	}

	/**
	 * Creates a new default map.
	 */
	public Map() {
		this.scale = 0;
		this.name = "new_map";
		this.png = this.name.concat(".png");
		this.xml = "XML/".concat(this.name).concat(".xml");

		this.allPoints = new HashMap<String, Point>();
	}

	public static void clearAllMaps() {
		Map.allMaps.clear();
	}

	public void fixMap() {
		for (Point p : allPoints.values()) {
			p.fixNeighbors();
		}
	}

	/**
	 * Get the scale from inches to feet.
	 * 
	 * @return The scale from inches to feet.
	 */
	public float getScale() {
		return this.scale;
	}

	/**
	 * Set the scale from inches to feet.
	 * 
	 * @param f
	 *            The inches to feet scale.
	 */
	public void setScale(float f) {
		float oldScale = this.scale;
		this.scale = f;

		float ratio = (float) f / (float) oldScale;

		if (this.allPoints != null) {
			String[] keys = this.allPoints.keySet().toArray(new String[this.allPoints.size()]);
			for (String p : keys) {
				Coord oldCoord = this.allPoints.get(p).getCoord();
				oldCoord.setX(oldCoord.getX() / ratio);
				oldCoord.setY(oldCoord.getY() / ratio);
			}
		}
	}

	/**
	 * Gets the name of this map.
	 * 
	 * @return The name of this map.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Sets the name of the map.
	 * 
	 * @param name
	 *            The new name of the map.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the png file that this map should use.
	 * 
	 * @return The png file name that this map should use.
	 */
	public String getPng() {
		return this.png;
	}

	/**
	 * Gets the xml file that this map should use.
	 * 
	 * @return The xml file name that this map should use.
	 */
	public String getXML() {
		return this.xml;
	}

	/**
	 * Sets the xml file that this map should use.
	 * 
	 * @param xml
	 *            The xml file name that this map should use.
	 */
	public void setXML(String xml) {
		this.xml = xml;
	}

	/**
	 * Sets the png file that this map should use.
	 * 
	 * @param png
	 *            The png file name that this map should use.
	 */
	public void setPng(String png) {
		this.png = png;
	}

	/**
	 * Gets the points that make up this map.
	 * 
	 * @return An array list of the points that make up this map.
	 */
	public HashMap<String, Point> getAllPoints() {
		return this.allPoints;
	}

	/**
	 * Sets the points that make up this map.
	 * 
	 * @param ap
	 *            The array list that will be the new points for this map.
	 */
	public void setAllPoints(HashMap<String, Point> ap) {
		this.allPoints = ap;
	}

	/**
	 * Gets the loaded image of the map png to display.
	 * 
	 * @return The loaded image to display for this map.
	 */
	public ImageIcon getLoadedImage() {
		if (loadedImage == null) {
			try {
				loadImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return loadedImage;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public static HashMap<String, Map> getAllMaps() {
		return allMaps;
	}

	public static void setAllMaps(HashMap<String, Map> allMaps) {
		Map.allMaps = allMaps;
	}

	public static Map getMap(String mapKey) {
		if (!(Map.allMaps.containsKey(mapKey))) {
			OLSSerializer.read(mapKey);
		}
		return Map.allMaps.get(mapKey);
	}

	public static boolean addMap(Map mapValue) {
		if ((Map.allMaps.containsKey(mapValue.getName()))) {
			return false;
		}

		Map.allMaps.put(mapValue.getName(), mapValue);
		return true;
	}

	/**
	 * Gets a point from the map.
	 * 
	 * @param id
	 *            The id of the point to get.
	 * @return The point with the id.
	 */
	public Point getPoint(String id) {
		return this.allPoints.get(id);
	}

	/**
	 * Removes the point with the given ID from the map array, and from the
	 * neighbor arrays of all points on the map
	 * 
	 * @param id
	 *            The ID of the point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	public boolean removePoint(String id) {
		Point point = allPoints.get(id);
		if (point != null) {
			for (Point pointN : point.getNeighborsP()) {
				if (!pointN.removeNeighbor(point))
					return false;
			}
			point.removeAllNeighbors();
			allPoints.remove(point.getId());
			return true;
		}
		return false;
	}

	/**
	 * Removes the given point from the map array, and from the neighbor arrays
	 * of all points on the map
	 * 
	 * @param point
	 *            The point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	public boolean removePoint(Point point) {
		// System.out.println("Remove: " + point.getId());
		ArrayList<Point> neighbors = point.getNeighborsP();
		for (Point pointN : neighbors) {
			if (!pointN.removeNeighbor(point))
				return false;
		}
		point.removeAllNeighbors();
		allPoints.remove(point.getId());

		return true;
	}

	private void loadImage() throws IOException {
		try {
			BufferedImage buffer = ImageIO.read(new File(png));
			loadedImage = new ImageIcon(buffer.getScaledInstance(1000, 660, Image.SCALE_SMOOTH));
		} catch (Exception e) {

		}
	}

	/**
	 * Adds a point to the map. Does NOT connect the point to any other points.
	 * 
	 * @param point
	 *            a new Point to add
	 * @return true if the point was added, false if there already exists a
	 *         point with the same ID
	 */
	public boolean addPoint(Point point) {
		if (this.allPoints.containsKey(point.getId()))
			return false;

		this.allPoints.put(point.getId(), point);
		return true;
	}

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
	public boolean addEdge(Point point, Point other) {
		if (point.equals(other)) {
			return false;
		}
		if (this.allPoints.containsKey(point.getId()) && this.allPoints.containsKey(other.getId())) {
			boolean adder = point.addNeighbor(other);
			if (!(adder)) {
				return false;
			}
			other.addNeighbor(point);
			return true;
		}
		return false;
	}

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
	public boolean removeEdge(Point point, Point other) {
		if (this.allPoints.containsKey(point.getId()) && (this.allPoints.containsKey(other.getId()))) {
			boolean remover = point.removeNeighbor(other);
			if (!(remover)) {
				return false;
			}
			other.removeNeighbor(point);
			return true;
		}
		return false;
	}

	public void setAllPointMaps() {
		for (Point p : this.allPoints.values()) {
			p.setMap(this.getName());
		}
	}

	public void renamePoint(Point p, String newName) {
		allPoints.remove(p.getId());
		allPoints.put(newName, p);
	}
}
