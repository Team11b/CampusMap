package WPI.CampusMap.PathPlanning;

import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.AStar.Heuristic;

/**
 * 
 * @author Jacob Zizmor
 * @author Max Stenke
 *
 */
public class Node {
	private Point point;
	private Node parent;
	private double heuristic = 0;
	private double cumulativeDist;
	private double currentScore;

	/**
	 * Standard heuristic for all regular Nodes
	 */
	public static final int stdH = 0;

	/**
	 * Constructor. Sets cumulative distance and the most recent distance for
	 * each Node based upon the parent.
	 * 
	 * @param point
	 *            Point to base this Node on
	 * @param parent
	 *            Parent Node
	 */
	public Node(Point point, Node parent, Point goal) {
		this.point = point;
		this.parent = parent;

		if (this.parent == null) {
			this.cumulativeDist = 0;
		} else {
			this.cumulativeDist = this.parent.cumulativeDist + this.parent.getPoint().distance(this.point);
		}

		this.currentScore = this.cumulativeDist + Node.stdH + (parent == null ? 0 : parent.calcHeuristic(goal));
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

	public double getHeuristic() {
		return this.heuristic;
	}

	public double setHeuristic(double heuristic) {
		this.heuristic = heuristic;
		return this.heuristic;
	}

	public double calcHeuristic(Point goal) {
		double temp = stdH;
		double weather = Heuristic.getWeatherScore();
		String building = goal.getMap().substring(0, goal.getMap().length() - 3);
		building = this.getPoint().getMap();

		if (point.getMap().equals(goal.getMap())) {

			if (Heuristic.isUsingWeather()) {
				if (weather > 0) {

					if (point.getMap().equalsIgnoreCase("CampusMap")) {
						temp += Math.abs(weather);
					} else {
						temp -= Math.abs(weather);
					}
				} else {
					if (point.getMap().equalsIgnoreCase("CampusMap")) {
						temp -= Math.abs(weather);
					} else {
						temp += Math.abs(weather);
					}
				}
			}

			temp += this.getPoint().distance(goal);
		}

		else if (point.getMap().equals("CampusMap")) {

			if (Heuristic.isUsingWeather()) {
				if (weather > 0) {
					temp += Math.abs(weather);
				} else {
					temp -= Math.abs(weather);
				}
			}

//			temp += this.getPoint().distance(Map.getMap(goal.getMap()).getPoint(building));
		}

		else {
			if (Heuristic.isUsingWeather()) {
				if (weather > 0) {
					temp += Math.abs(weather);
				} else {
					temp -= Math.abs(weather);
				}
			}
		}
		return temp;
	}

	public double getCumulativeDist() {
		return cumulativeDist;
	}

	public void setCumulativeDist(double d) {
		this.cumulativeDist = d;
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Node) {
			return this.getPoint().equals(((Node) other).getPoint());
		}
		return false;
	}

	@Override
	public String toString() {
		return this.getPoint().toString() + "\t" + this.getCurrentScore();
	}

}
