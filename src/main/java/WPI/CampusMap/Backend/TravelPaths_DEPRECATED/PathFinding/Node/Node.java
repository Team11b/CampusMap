package WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node;

import WPI.CampusMap.Backend.AdditionalFeatures.InternetFeatures.Weather.WeatherAnalysis;
import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.ConnectionPoint;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.Core.Point.RealPoint;

/**
 * 
 * @author Jacob Zizmor
 * @author Max Stenke
 *
 */
public class Node {
	private RealPoint point;
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
	 * @param one
	 *            Point to base this Node on
	 * @param one2
	 *            The end point of the pathfinding
	 * @param parent
	 *            Parent Node
	 */
	public Node(RealPoint one, Node parent, RealPoint one2) {
		this.point = one;
		this.parent = parent;

		if (this.parent == null || !parent.getPoint().getMap().equals(one2.getMap())) {
			this.cumulativeDist = 0;
		} else {
			this.cumulativeDist = this.parent.cumulativeDist + this.parent.getPoint().distance(this.point);
		}

		this.currentScore = this.cumulativeDist + Node.stdH + (parent == null ? 0 : parent.calcHeuristic(one2));
	}

	public RealPoint getPoint() {
		return point;
	}

	public void setPoint(RealPoint point) {
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

	public double calcHeuristic(RealPoint one2) {
		double temp = stdH;
		double weather = WeatherAnalysis.getWeatherScore();
		String building = one2.getMap();
		if(building != null && building.length() > 3) building = building.substring(0, one2.getMap().length() - 2);

		if (point.getMap().equals(one2.getMap())) {

			if (WeatherAnalysis.isUsingWeather()) {
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
			
			if(point.getMap() == one2.getMap())
				temp -= 1000;

			temp += this.getPoint().distance(one2);
		}

		else if (point.getMap().equals("CampusMap")) {

			if (WeatherAnalysis.isUsingWeather()) {
				if (weather > 0) {
					temp += Math.abs(weather);
				} else {
					temp -= Math.abs(weather);
				}
			}

			RealPoint mapPoint = AllMaps.getInstance().getMap("CampusMap").getPoint(building);
			if (mapPoint != null) {
				temp += this.getPoint().distance(mapPoint);
			}
		}

		else {
			if (WeatherAnalysis.isUsingWeather()) {
				if (weather > 0) {
					temp += Math.abs(weather);
				} else {
					temp -= Math.abs(weather);
				}
			}
		}
//		if ((this.getPoint() instanceof ConnectionPoint) && ((this.getPoint().getType().equals(Point.ELEVATOR)) || (this.getPoint().getType().equals(Point.STAIRS)))) {
//			temp += ConnectionNode.travelCost;
//		}
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
