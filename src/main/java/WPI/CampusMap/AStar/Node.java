package WPI.CampusMap.AStar;

public class Node {
	private Point point;
	private Node parent;
	private int heuristic = 0;

	public Node(Point point, Node parent) {
		this.point = point;
		this.parent = parent;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(int heuristic) {
		this.heuristic = heuristic;
	}

}
