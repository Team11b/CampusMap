package WPI.CampusMap.AStar;

public class Node {
	private Point point;
	private Node parent;
	private int heuristic = 0;
	private float cumulativeDist;
	private float immediateCost;
	
	public static final int stdH = 0;

	public Node(Point point, Node parent, float cumulativeDist, float immediateCost) {
		this.point = point;
		this.parent = parent;
		this.cumulativeDist = cumulativeDist;
		this.immediateCost = immediateCost;
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

	public float getCumulativeDist() {
		return cumulativeDist;
	}

	public void setCumulativeDist(float cumulativeDist) {
		this.cumulativeDist = cumulativeDist;
	}

	public float getImmediateCost() {
		return this.immediateCost;
	}
	
	public void setImmediateCost(float immediateCost) {
		this.immediateCost = immediateCost;
	}
}
