package WPI.CampusMap.AStar;

import java.util.ArrayList;

public class Path {

	private ArrayList<Node> path;

	public Path(ArrayList<Node> path) {
		this.path = path;
	}
	
	public Path() {
		this.path = new ArrayList<Node>();
	}
	
	public boolean addNode(Node node){
		return path.add(node);
	}

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
