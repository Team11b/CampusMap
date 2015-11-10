package WPI.CampusMap.AStar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.Collections;
import java.util.Comparator;

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

	private String png;
	private String xml;
	private Point[] map;

	/**
	 * Map constructor
	 * @param png File name of the image for this map
	 * @param xml File name of the XML of points for this map
	 */
	public Map(String png, String xml) throws FileNotFoundException,XMLStreamException{
		this.png = png;
		this.xml = xml;
		parseXML(xml);
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

					if (otherIndex != -1) {
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
		while (tempNode.getPoint() != null) {
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

	public static void main(String[] args) {
	}
	
	/**
	 * Function to take an xml file as input and output an array of points.
	 * 
	 * @param filename name of XML file
	 * @return Array of all points in the file
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException
	 */
	private Point[] parseXML(String filename) throws XMLStreamException, FileNotFoundException{
		Point[] pointList= null;
		Point currPoint = null;
		Coord tempCoord = null;
		String tagContent = null;
		String[] tempNeigh = null;
		int nCount = 0;
		int pos = 0;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		File testFile = new File(filename);
		InputStream test = new FileInputStream(testFile);
		
		XMLStreamReader reader = factory.createXMLStreamReader(test);
		
		while(reader.hasNext()){
			int event = reader.next();
			switch(event){
			case XMLStreamConstants.START_ELEMENT:
				if("Node".equals(reader.getLocalName())){
					currPoint = new Point();
					tempNeigh = new String[8];
					nCount = 0;
					currPoint.setId(reader.getAttributeValue(0));
					tempCoord = new Coord(Float.parseFloat(reader.getAttributeValue(1)),Float.parseFloat(reader.getAttributeValue(2)));
				}
				if("Map".equals(reader.getLocalName())){
					pointList = new Point[100];//Temporary max value until a max is determined or we add point count to the XMl
					//Integer.parseInt(reader.getAttributeValue(0))];
					setPng(reader.getAttributeValue(0));
				}
				break;
			case XMLStreamConstants.CHARACTERS:
				tagContent = reader.getText().trim();
			break;
			case XMLStreamConstants.END_ELEMENT:
				switch(reader.getLocalName()){
				case "Node":
					currPoint.setCoord(tempCoord);
					pointList[pos] = currPoint;
					currPoint.setNeighborsID(tempNeigh);
					pos++; 
					break;
				case "type":
					currPoint.setType(tagContent);
					break;
				case "Connection":
					tempNeigh[nCount] = tagContent;
					nCount++;
					break;
				}
				break;
			}
			
		}
		return pointList;
	}

}

