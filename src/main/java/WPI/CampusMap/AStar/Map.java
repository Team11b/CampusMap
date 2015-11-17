package WPI.CampusMap.AStar;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.stream.XMLStreamException;

import WPI.CampusMap.XML.XML;

/**
 * Represents a single map/area.
 * 
 * @author Jacob Zizmor
 * @author Max Stenke
 * @author Will Craft
 *
 */
public class Map {

	private int scale;
	private String name;
	private String png;
	private String xml;
	private ArrayList<Point> map;
	private ImageIcon loadedImage;
	
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
//		System.out.println("Constructor: " + this.name);
//		System.out.println("name:" + this.name);
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
			map = XML.parseXML(this);
			System.out.println("map scale" + this.scale);

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
		this.map = new ArrayList<Point>();
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
		this.scale = scale;
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
	public ArrayList<Point> getMap() {
		return this.map;
	}

	/**
	 * Sets the points that make up this map.
	 * 
	 * @param map
	 *            The array list that will be the new points for this map.
	 */
	public void setMap(ArrayList<Point> map) {
		this.map = map;
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

	/**
	 * Gets a point from the map.
	 * 
	 * @param id
	 *            The id of the point to get.
	 * @return The point with the id.
	 */
	public Point getPoint(String id) {
		for (Point p : map) {
			if (p.getId().equals(id))
				return p;
		}

		return null;
	}

	/**
	 * Converts screen space coords to world space coords.
	 * 
	 * @param screenSpace The coords in screen space
	 * @return The coords in world space.
	 */
	public Coord screenToWorldSpace(Coord screenSpace) {
		float imageX = screenSpace.getX() / 1000.0f * (float)loadedImage.getIconWidth();
		float imageY = screenSpace.getY() / 660.0f * (float)loadedImage.getIconHeight();
		
		float inchesX = imageX / 72.0f;
		float inchesY = imageY / 72.0f;
		
		float feetX = inchesX / scale;
		float feetY = inchesY / scale;

		return new Coord(feetX, feetY);
	}
	
	/**
	 * Converts world space to screen space.
	 * @param worldSpace The world space coords to convert.
	 * @return The screen space coords.
	 */
	public Coord worldToScreenSpace(Coord worldSpace)
	{
		float inchesX = worldSpace.getX() * scale;
		float inchesY = worldSpace.getY() * scale;
		
		float imageX = inchesX * 72.0f;
		float imageY = inchesY * 72.0f;
		
		float screenX = imageX / (float)loadedImage.getIconWidth() * 1000.0f;
		float screenY = imageY / (float)loadedImage.getIconHeight() * 660.0f;
		
		return new Coord(screenX, screenY);
	}

	/**
	 * Creates a Path of points using the A* algorithm. Uses this map as a map.
	 * Will return null if either the start or the goal is an invalid location,
	 * a wall.
	 * 
	 * @param start
	 *            the starting Point
	 * @param goal
	 *            the goal Point
	 * @return a Path of points or null if either the start or goal is invalid
	 */
	public Path astar(Point start, Point goal) {
		// checks to see if either the start or goal is a wall
		if (start.getType() == Point.WALL) {
			System.out.println("Invalid start point.");
			return null;
		} else if (goal.getType() == Point.WALL) {
			System.out.println("Invalid goal point.");
			return null;
		}

		boolean goalFound = false;

		// Instantiate frontier and explored lists
		ArrayList<Node> frontier = new ArrayList<Node>();
		ArrayList<Node> explored = new ArrayList<Node>();

		// Instantiate path
		Path returnPath = new Path();

		Node tempNode = new Node(start, null);
		int otherIndex = -1;

		// add start to frontier as a Node
		frontier.add(new Node(start, null));

		while ((!frontier.isEmpty()) && (!(goalFound))) {

			// sort frontier list based upon cumulative distance
			// sorting by attribute algorithm used from:
			// http://stackoverflow.com/questions/12449766/java-sorting-sort-an-array-of-objects-by-property-object-not-allowed-to-use-co
			Collections.sort(frontier, new Comparator<Node>() {
				public int compare(Node n1, Node n2) {
					if (n1.getCumulativeDist() < n2.getCumulativeDist()) {
						return -1;
					} else if (n1.getCumulativeDist() > n2.getCumulativeDist()) {
						return 1;
					} else {
						return 0;
					}
				}
			});

			// add the Node at the top of the frontier and add it to the
			// explored list
			// remove that Node from the frontier
			explored.add(frontier.get(0));
			frontier.remove(0);

			if (explored.get(explored.size() - 1).getPoint().equals(goal)) {
				goalFound = true;
			}

			if (!(goalFound)) {
				// get the valid neighbors from the last Node on the explored
				// list
				ArrayList<Point> neigh = explored.get(explored.size() - 1).getPoint().getValidNeighbors();
				for (int j = 0; j < neigh.size(); j++) {
					tempNode = new Node(neigh.get(j), explored.get(explored.size() - 1));
					// check if Node is in Explored
					otherIndex = Map.getIndex(tempNode, explored);
					if (otherIndex == -1) {
						otherIndex = Map.getIndex(tempNode, frontier);
						if (otherIndex == -1) {
							frontier.add(new Node(neigh.get(j), explored.get(explored.size() - 1)));
							frontier.get(frontier.size()-1).setCumulativeDist(explored.get(explored.size()-1).getCumulativeDist() + frontier.get(frontier.size() - 1).getPoint().distance(explored.get(explored.size() - 1).getPoint()));
							frontier.get(frontier.size() - 1).setCurrentScore(frontier.get(frontier.size() - 1).getCumulativeDist() + frontier.get(frontier.size() - 1).getHeuristic());
						} else {
							if (tempNode.getCurrentScore() < frontier.get(otherIndex).getCurrentScore()) {
								frontier.set(otherIndex, new Node(neigh.get(j), explored.get(explored.size() - 1)));
							}
						}
					}
				}
			}
		}

		// form the path
		tempNode = explored.get(explored.size() - 1);
		while (tempNode != null && tempNode.getPoint() != null) {
			returnPath.addNode(tempNode);
			tempNode = tempNode.getParent();
		}

		returnPath.reverse();
		return returnPath;
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
		for (Point point : map) {
			if (point.getId().equals(id)) {
				ArrayList<Point> neighbors = point.getNeighborsP();
				for (Point pointN : neighbors) {
					if(!pointN.removeNeighbor(point)) return false;
				}
				point.setNeighborsID(new ArrayList<String>());
				point.setNeighborsP(new ArrayList<Point>());
				map.remove(point);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Removes the given point from the map array, and from the
	 * neighbor arrays of all points on the map
	 * 
	 * @param point
	 *            The point to be removed
	 * @return True if point is successfully removed, False if specified point
	 *         does note exist
	 */
	public boolean removePoint(Point point) {
		ArrayList<Point> neighbors = point.getNeighborsP();
		for (Point pointN : neighbors) {
			if(!pointN.removeNeighbor(point)) return false;
		}
		point.setNeighborsID(new ArrayList<String>());
		point.setNeighborsP(new ArrayList<Point>());
		map.remove(point);
		return true;
	}

	private void loadImage() throws IOException {
//		System.out.println(png);
		BufferedImage buffer = ImageIO.read(new File(png));
		loadedImage = new ImageIcon(buffer.getScaledInstance(1000, 660, Image.SCALE_SMOOTH));// TODO:																					// draw
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
		for (Point p : this.map) {
			if (p.getId().equals(point.getId()))
				return false;
		}
		this.map.add(point);
		Collections.sort(this.map, new Comparator<Point>() {
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
		if (this.map.contains(point) && this.map.contains(other)) {
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
		if (this.map.contains(point) && (this.map.contains(other))) {
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
