package WPI.CampusMap.AStar;

public class Node {
	private Point point;
	private Node parent;
	private int heuristic = 0;
	private float cumulativeDist;
	private float currentScore;
	
	public static final int stdH = 0;

	public Node(Point point, Node parent) {
		this.point = point;
		this.parent = parent;
		
		if (this.parent == null) {
			this.cumulativeDist = 0;
		}
		else {
			this.cumulativeDist = this.parent.cumulativeDist + this.parent.getPoint().distance(this.point);	
		}

		this.currentScore = this.cumulativeDist + Node.stdH;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point point) {
		this.point = point;
	}
	
	public float getCurrentScore() {
		return this.currentScore;
	}
	
	public void setCurrentScore(float currentScore) {
		this.currentScore = currentScore;
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
}
