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
import WPI.CampusMap.XML.XML;

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
	private int scale;
	private String name;
	private String png;
	private String xml;
	private ArrayList<Point> allPoints;
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
		// System.out.println("Constructor: " + this.name);
		// System.out.println("name:" + this.name);
		this.png = "maps/" + name + ".png";
		this.xml = "XML/" + this.name + ".xml";
		// XML.parseXML(this);

		if (this.name.equals("Select a map")) {
			this.scale = -1; // it is THE fake map, we could do cool xml parsing
								// for the fake map if needed
		} else {
			try {
				loadImage();
			} catch (IOException e) {
				e.printStackTrace();
			}
			allPoints = XML.parseXML(this);
		}
	}

	/**
	 * Creates a new default map.
	 */
	public Map() {
		this.scale = 0;
		this.name = "new_map";
		this.png = this.name.concat(".png");
		this.xml = "XML/".concat(this.name).concat(".xml");
		this.allPoints = new ArrayList<Point>();
	}

	/**
	 * Get the scale from inches to feet.
	 * 
	 * @return The scale from inches to feet.
	 */
	public int getScale() {
		return this.scale;
	}

	/**
	 * Set the scale from inches to feet.
	 * 
	 * @param scale
	 *            The inches to feet scale.
	 */
	public void setScale(int scale) {
		int oldScale = this.scale;
		this.scale = scale;

		float ratio = (float) scale / (float) oldScale;

		if (allPoints != null) {
			for (Point p : allPoints) {
				Coord oldCoord = p.getCoord();
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
	public ArrayList<Point> getAllPoints() {
		return this.allPoints;
	}

	/**
	 * Sets the points that make up this map.
	 * 
	 * @param allPoints
	 *            The array list that will be the new points for this map.
	 */
	public void setAllPoints(ArrayList<Point> ap) {
		this.allPoints = ap;
	}

	/**
	 * Gets the loaded image of the map png to display.
	 * 
	 * @return The loaded image to display for this map.
	 */
	public ImageIcon getLoadedImage() {
		return loadedImage;
	}

	// Benny: I don't think we need a setter for this, only the map should be
	// changing the loaded image.
	public void setLoadedImage(ImageIcon loadedImage) {
		this.loadedImage = loadedImage;
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
		return Map.allMaps.get(mapKey);
	}
	
	public static boolean addMap(Map mapValue) {
		if (!(Map.allMaps.containsKey(mapValue.getName()))) {
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
		for (Point p : allPoints) {
			if (p.getId().equals(id))
				return p;
		}

		return null;
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
	 * Gets the index of a specific Node in a list of Nodes, based upon the
	 * Point
	 * 
	 * @param aNode
	 *            the Node to search for
	 * @param LoN
	 *            the list of Nodes to search in
	 * @return the index of the existing Node, -1 if not found
	 */
	//TODO may not be used
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
		for (Point point : allPoints) {
			if (point.getId().equals(id)) {
				ArrayList<Point> neighbors = point.getNeighborsP();
				for (Point pointN : neighbors) {
					if (!pointN.removeNeighbor(point))
						return false;
				}
				point.setNeighborsID(new ArrayList<String>());
				point.setNeighborsP(new ArrayList<Point>());
				allPoints.remove(point);
				return true;
			}
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
		point.setNeighborsID(new ArrayList<String>());
		point.setNeighborsP(new ArrayList<Point>());
		allPoints.remove(point);
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
		for (Point p : this.allPoints) {
			if (p.getId().equals(point.getId()))
				return false;
		}
		this.allPoints.add(point);
		Collections.sort(this.allPoints, new Comparator<Point>() {
			public int compare(Point p1, Point p2) {
				return p1.getId().compareTo(p2.getId());
			}
		});
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
		if (this.allPoints.contains(point) && this.allPoints.contains(other)) {
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
		if (this.allPoints.contains(point) && (this.allPoints.contains(other))) {
			boolean remover = point.removeNeighbor(other);
			if (!(remover)) {
				return false;
			}
			other.removeNeighbor(point);
			return true;
		}
		return false;
	}
}
