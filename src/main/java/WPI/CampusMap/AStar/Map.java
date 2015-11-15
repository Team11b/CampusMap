package WPI.CampusMap.AStar;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
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
	private Point[] map;
	private ImageIcon loadedImage;
	
	public Map(String xml) throws XMLStreamException{
		this.scale = 100;
		this.name = xml.substring(0, xml.length()-4);
		this.png = name + ".png";
		this.xml = xml;
		parseXML(xml);
		try {
			loadImage();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getScale() {
		return this.scale;
	}
	
	public void setScale(int scale) {
		this.scale = scale;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPng() {
		return this.png;
	}
	
	public String getXML() {
		return this.xml;
	}
	
	public void setXML(String xml) {
		this.xml = xml;
	}

	public void setPng(String png) {
		this.png = png;
	}

	public Point[] getMap() {
		return this.map;
	}

	public void setMap(Point[] map) {
		this.map = map;
	}
	
	public ImageIcon getLoadedImage() {
		return loadedImage;
	}

	public void setLoadedImage(ImageIcon loadedImage) {
		this.loadedImage = loadedImage;
	}

	public Point getPoint(String id)
	{
		for(Point p : map)
		{
			if(p.getId() == id)
				return p;
		}
		
		return null;
	}
	
	/**
	 * Converst screen space coords to world space coords.
	 * @param screenSpace The coords in screen space
	 * @return The coords in world space.
	 */
	public Coord screenToWorldSpace(Coord screenSpace)
	{
		float x = screenSpace.getX() / (float)loadedImage.getIconWidth() * (float)scale;
		float y = screenSpace.getY() / (float)loadedImage.getIconHeight() * (float)scale;
		
		return new Coord(x, y);
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
				Point[] neigh = explored.get(explored.size() - 1).getPoint().getValidNeighbors();
				for (int j = 0; j < neigh.length; j++) {
					tempNode = new Node(neigh[j], explored.get(explored.size() - 1));
					// check if Node is in Explored
					otherIndex = Map.getIndex(tempNode, explored);
//					if (otherIndex != -1) {
					if (otherIndex == -1){
						otherIndex = Map.getIndex(tempNode, frontier);
						if (otherIndex == -1) {
							frontier.add(new Node(neigh[j], explored.get(explored.size() - 1)));
						} else {
							if (tempNode.getCurrentScore() < frontier.get(otherIndex).getCurrentScore()) {
								frontier.set(otherIndex, new Node(neigh[j], explored.get(explored.size() - 1)));
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
	 * Function to take an xml file as input and output an array of points.
	 * 
	 * @param filename Relative file path of XML file
	 * @return Array of all points in the file
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException
	 */
	private void parseXML(String filename) throws XMLStreamException{
		Point currPoint = null;
		Coord tempCoord = null;
		String tagContent = null;
		
		ArrayList<Point> pointAList = new ArrayList<Point>();
		ArrayList<String> neighAList = new ArrayList<String>();
		
		XMLInputFactory factory = XMLInputFactory.newInstance();
		File testFile = new File(filename);
		
		
		InputStream test = null;
		try 
		{
			test = new FileInputStream(testFile);
		} 
		catch (FileNotFoundException e) 
		{
			map = new Point[0];
			return;
		}
		
		XMLStreamReader reader = factory.createXMLStreamReader(test);
		
		while(reader.hasNext()){
			int event = reader.next();
			switch(event){
			case XMLStreamConstants.START_ELEMENT:
				if("Node".equals(reader.getLocalName()))
				{
					currPoint = new Point();
					neighAList = new ArrayList<String>();
					currPoint.setId(reader.getAttributeValue(0));
					tempCoord = new Coord(Float.parseFloat(reader.getAttributeValue(1)),Float.parseFloat(reader.getAttributeValue(2)));
				}
				if("Map".equals(reader.getLocalName()))
				{
					setPng(reader.getAttributeValue(0));
					setScale(Integer.parseInt(reader.getAttributeValue(1)));
					setName(reader.getAttributeValue(2));
				}
				break;
			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
			break;
			case XMLStreamConstants.END_ELEMENT:
				switch(reader.getLocalName()){
				case "Node":
					currPoint.setCoord(tempCoord);
					pointAList.add(currPoint);
					currPoint.setNeighborsID(neighAList.toArray(new String[0]));
//					pos++; 
					break;
				case "type":
					currPoint.setType(tagContent);
					break;
				case "Connection":
					neighAList.add(tagContent);
					break;
				}
				break;
			}
			
		}
		
		//goes through the points and gets the point objects associated with the neighbor ids and assigns them as neighbors
		for(Point point: pointAList){
			List<String> neighborIDs = Arrays.asList(point.getNeighborsID());
			Point[] neighbors = new Point[neighborIDs.size()]; 
			int i=0;
			for(Point searchPoint: pointAList){
				if(neighborIDs.contains(searchPoint.getId())){
					neighbors[i] = searchPoint;
					i++;
				}
				if(i>neighbors.length) break;
			}
			point.setNeighborsP(neighbors);
		}
		map = pointAList.toArray(new Point[0]);
	}
	
	private void loadImage() throws IOException
	{
		BufferedImage buffer = ImageIO.read(new File(png));
		loadedImage = new ImageIcon(buffer.getScaledInstance(1000, 660, Image.SCALE_SMOOTH));//TODO: do not scale, but rather have graphics draw
	}

}

