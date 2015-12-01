package WPI.CampusMap.Backend;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;
import WPI.CampusMap.Serialization.Serializer;

/**
 * Represents a single map/area.
 * 
 * @author Jacob Zizmor
 * @author Max Stenke
 * @author Will Craft
 *
 */
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
	 * @throws XMLStreamException
	 *             Thrown if there is an error parsing the xml file.
	 */
	public Map(String name) throws XMLStreamException {
		this.scale = 100;
		this.name = name;
		this.png = "maps/" + name + ".png";
		this.xml = "XML/" + this.name + ".xml";
		this.allPoints = new HashMap<String, Point>();
		
		Map testMap = Serializer.read(name);
		if(testMap != null){
			setScale(testMap.getScale());
			setAllPoints(testMap.getAllPoints());
		}
		Map.allMaps.remove(name);
		Map.allMaps.put(name,this);
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
	 * Creates a point on the map at the mouse point.
	 * 
	 * @param e
	 *            The mouse event to trigger the method.
	 * @return The point that was created.
	 */
	public Point createPointOnMap(MouseEvent e) {
		Coord screenCoord = new Coord(e.getX(), e.getY());

		Coord mapCoord = this.screenToWorldSpace(screenCoord);

		Point newPoint = new Point(mapCoord, "", UUID.randomUUID().toString(), this.name);
		this.addPoint(newPoint);

		return newPoint;
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
	public ImageIcon getLoadedImage()
	{
		if(loadedImage == null)
		{
			try {
				loadImage();
			} catch (IOException e) {
				// TODO Auto-generated catch block
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
			Serializer.read(mapKey);
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
	 * Converts screen space coords to world space coords.
	 * 
	 * @param screenSpace
	 *            The coords in screen space
	 * @return The coords in world space.
	 */
	public Coord screenToWorldSpace(Coord screenSpace) {
		double imageX = screenSpace.getX() / 1000.0f * loadedImage.getIconWidth();
		double imageY = screenSpace.getY() / 660.0f * loadedImage.getIconHeight();

		double inchesX = imageX / 72.0f;
		double inchesY = imageY / 72.0f;

		double feetX = inchesX / scale;
		double feetY = inchesY / scale;

		return new Coord(feetX, feetY);
	}

	/**
	 * Converts world space to screen space.
	 * 
	 * @param worldSpace
	 *            The world space coords to convert.
	 * @return The screen space coords.
	 */
	public Coord worldToScreenSpace(Coord worldSpace) {
		double inchesX = worldSpace.getX() * scale;
		double inchesY = worldSpace.getY() * scale;

		double imageX = inchesX * 72.0f;
		double imageY = inchesY * 72.0f;

		double screenX = imageX / loadedImage.getIconWidth() * 1000.0f;
		double screenY = imageY / loadedImage.getIconHeight() * 660.0f;

		return new Coord(screenX, screenY);
	}

	/**
	 * Gets the index of a specific Node in a list of Nodes, based upon the
	 * Point
	 * 
	 * @param aNode
	 *            the Node to search for
	 * @param LoN
	 *            the list of Nodes to search in
	 * @return the index of the existing Node, -1 if not found
	 */
	// TODO may not be used
	private static int getIndex(Node aNode, ArrayList<Node> LoN) {
		for (int j = 0; j < LoN.size(); j++) {
			if (LoN.get(j).getPoint() == aNode.getPoint()) {
				return j;
			}
		}
		return -1;
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
		Point point = this.allPoints.get(id);
		if (point != null) {
			for (Point pointN : point.getNeighborsP()) {
				if (!pointN.removeNeighbor(point))
					return false;
			}
			point.removeAllNeighbors();
			this.allPoints.remove(point.getId());
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
		ArrayList<Point> neighbors = point.getNeighborsP();
		for (Point pointN : neighbors) {
			if (!pointN.removeNeighbor(point))
				return false;
		}
		point.removeAllNeighbors();
		this.allPoints.remove(point);
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
	
	/**
	 * Converts specified point to other type 
	 * (ie. from a normal point to connection point and vice versa)
	 * @param point Point to convert
	 */
	public void convertPoint(Point point){
		Point temp = point.switchPointConnectionType();
		removePoint(point);
		addPoint(temp);
	}
	
	public void setAllPointMaps() {
		for (Point p : this.allPoints.values()) {
			p.setMap(this.getName());
		}
	}

}
