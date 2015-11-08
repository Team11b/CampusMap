package WPI.CampusMap.AStar;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author Max
 *
 */
public class Path {

	private ArrayList<Node> path;

	/**
	 * Constructor with pre-defined ArrayList of Nodes
	 * @param path pre-defined ArrayList of Nodes
	 */
	public Path(ArrayList<Node> path) {
		this.path = path;
	}
	
	/**
	 * Constructor with no previous Nodes
	 */
	public Path() {
		this.path = new ArrayList<Node>();
	}
	
	public boolean addNode(Node node){
		return path.add(node);
	}
	
	/**
	 * Reverses the ArrayList of this Path
	 */
	public void reverse() {
		Collections.reverse(this.path);
	}

	/**
	 * Parses the current path of Nodes and returns a new Path with only the start, goal, and any Nodes
	 * that result in a change of direction
	 * @return abridged list of Nodes
	 */
	public Path getTurns() {
		ArrayList<Node> temp = new ArrayList<Node>();
		Node first = path.get(0);
		Node last = path.get(path.size());

		temp.add(first);
		for (int i = 1; i < path.size(); i++) {
			
		}
		
		return null;
	}
	
	public Coord getNodePointCoord(Node node){
		return node.getPoint().getCoord();
	}

	public ArrayList<Node> getPath() {
		return path;
	}

	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}

}
