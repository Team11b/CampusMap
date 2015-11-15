package WPI.CampusMap.AStar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

	/**
	 * Map constructor
	 * @param scale Scale of this map
	 * @param png File name of the image for this map
	 * @param xml File name of the XML of points for this map
	 */
	public Map(int scale, String png, String xml) throws FileNotFoundException,XMLStreamException{
		this.scale = scale;
		this.name = png.substring(0, png.length()-4);
		this.png = png;
		this.xml = xml;
		this.map = parseXML(xml);
		
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
	 * Removes the point with the given ID from the map array, and from the neighbor arrays of
	 * all points on the map
	 * 
	 * @param id  The ID of the string to be removed
	 */
	private void removePoint(String id){
		map = removePointArray(map,id);
		for(int i = 0;i<map.length;i++){
			map[i].setNeighborsP(removePointArray(map[i].getNeighborsP(),id));
			map[i].setNeighborsID(removeStringArray(map[i].getNeighborsID(),id));
		}
	}
	
	/**
	 * Removes the point with the given id from an array of points if one is found
	 * 
	 * @param points  The array to remove the point from
	 * @param id  The ID of the point being removed
	 * @return  The modified array
	 */
	public Point[] removePointArray(Point[] points,String id){
		for(int i = 0;i<points.length;i++){
			if(points[i].getId().equals(id)){
				for(;i<points.length - 1;i++){
					points[i] = points[i+1];
				}
				i++;
				points[i] = null;
			}
		}
		return points;
	}
	
	/**
	 * Removes the string equal to id from the array if it is found
	 * 
	 * @param array The array to remove the string from
	 * @param id  The id to remove from the array
	 * @return  The modified array
	 */
	public String[] removeStringArray(String[] array,String id){
		for(int i = 0;i<array.length;i++){
			if(array[i].equals(id)){
				for(;i<array.length - 1;i++){
					array[i] = array[i+1];
				}
				i++;
				array[i] = null;
			}
		}
		return array;
	}
	
	/**
	 * Function to take an xml file as input and output an array of points.
	 * 
	 * @param filename Relative file path of XML file
	 * @return Array of all points in the file
	 * @throws XMLStreamException 
	 * @throws FileNotFoundException
	 */
	private Point[] parseXML(String filename) throws XMLStreamException, FileNotFoundException{
		Point currPoint = null;
		Coord tempCoord = null;
		String tagContent = null;
		
		ArrayList<Point> pointAList = new ArrayList<Point>();
		ArrayList<String> neighAList = new ArrayList<String>();
		
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
					neighAList = new ArrayList<String>();
					currPoint.setId(reader.getAttributeValue(0));
					tempCoord = new Coord(Float.parseFloat(reader.getAttributeValue(1)),Float.parseFloat(reader.getAttributeValue(2)));
				}
				if("Map".equals(reader.getLocalName())){
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
		return pointAList.toArray(new Point[0]);
	}

}

