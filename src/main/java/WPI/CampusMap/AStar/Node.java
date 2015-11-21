package WPI.CampusMap.AStar;

import WPI.CampusMap.Backend.Point;

/**
 * 
 * @author Jacob Zizmor
 * @author Max Stenke 
 *
 */
public class Node {
	private Point point;
	private Node parent;
	private int heuristic = 0;
	private double cumulativeDist;
	private double currentScore;
	
	/**
	 * Standard heuristic for all regular Nodes
	 */
	public static final int stdH = 0;

	/**
	 * Constructor. Sets cumulative distance and the most recent distance for each Node based upon the parent.
	 * @param point Point to base this Node on
	 * @param parent Parent Node
	 */
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
	
	public double getCurrentScore() {
		return this.currentScore;
	}
	
	public void setCurrentScore(double d) {
		this.currentScore = d;
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

	public double getCumulativeDist() {
		return cumulativeDist;
	}

	public void setCumulativeDist(double d) {
		this.cumulativeDist = d;
	}
}
