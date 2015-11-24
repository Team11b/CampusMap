package WPI.CampusMap.PathPlanning.AStar;

import java.util.ArrayList;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.MultiPath;
import WPI.CampusMap.PathPlanning.Node;
import WPI.CampusMap.PathPlanning.Path;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class AStar {
	public AStar() {

	}

	/**
	 * Runs A* on a single map
	 * 
	 * @param start
	 *            the starting Point on amap
	 * @param goal
	 *            the goal Point on amap
	 * @return a path between start and goal
	 */
	// TODO This function should be able to replaced by _AStar, but that is not
	// confirmed yet
	public static Path single_AStar(Point start, Point goal) {
		if (start.equals(goal)) {
			return new Path();
		}
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
		Frontier frontier = new Frontier(Frontier.stdNodeComp);
		Explored explored = new Explored();

		// Instantiate path
		Path returnPath = new Path();

		Node tempNode = new Node(start, null);
		tempNode.getHeuristic();

		// add start to frontier as a Node
		frontier.add(tempNode);

		while ((!frontier.isEmpty()) && (!(goalFound))) {
			goalFound = frontier.contains(new Node(goal, null));

			if (!(goalFound)) {
				explored.add(frontier.getNext());

				if (explored.getLast().getPoint().equals(goal)) {
					goalFound = true;
				}

				if (!(goalFound)) {
					// get the valid neighbors from the last Node on the
					// explored
					// list
					Node centerPoint = explored.getLast();
					ArrayList<Point> neigh = centerPoint.getPoint().getValidNeighbors();
					for (int j = 0; j < neigh.size(); j++) {
						tempNode = new Node(null, null);
						tempNode = new Node(neigh.get(j), explored.getLast());

						tempNode.setCumulativeDist(explored.getLast().getCumulativeDist());
						tempNode.setCurrentScore(
								tempNode.getCumulativeDist() + tempNode.setHeuristic(tempNode.calcHeuristic(goal)));

						// check if Node is in Explored
						if (!(explored.containsSamePoint(tempNode))) {

							frontier.isBetter(tempNode);
						}
					}
				}
			}
		}
		tempNode = new Node(null, null);
		tempNode = frontier.find(new Node(goal, null));
		while ((tempNode != null) && (!(tempNode.getPoint().equals(start)))) {
			returnPath.addNode(tempNode);
			tempNode = tempNode.getParent();
		}
		returnPath.addNode(tempNode);

		returnPath.reverse();
		return returnPath;
	}

	/**
	 * Runs A* across multiple maps
	 * 
	 * @param start
	 *            the starting Point located on startMap
	 * @param goal
	 *            the goal Point located on goalMap
	 * @return a Path which spans multiple maps
	 */
	public MultiPath multi_AStar(Point start, Point goal) {
		if ()
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
		Frontier frontier = new Frontier(Frontier.stdNodeComp);
		Explored explored = new Explored();

		// Instantiate path
		Path returnPath = new Path();

		Node tempNode = new Node(start, null);
		ConnectionPoint tempConn = new ConnectionPoint(null, null, null, null, null, null);

		// add start to frontier as a Node
		frontier.add(tempNode);

		while ((!frontier.isEmpty()) && (!(goalFound))) {

			explored.add(frontier.getNext());

			if (explored.getLast().getPoint().equals(goal)) {
				goalFound = true;
			}

			if (!(goalFound)) {
				tempNode = new Node(null, null);
				tempConn = new ConnectionPoint(null, null, null, null, null, null);

				if (explored.getLast().getPoint() instanceof ConnectionPoint) {
					tempConn = (ConnectionPoint) (explored.getLast().getPoint());
					tempConn.setConnMap(Map.getMap(tempConn.getLinkedMap()));
					Map.addMap(tempConn.getConnMap());
					tempConn.setConnPoint((ConnectionPoint) tempConn.getConnMap().getPoint(tempConn.getLinkedPoint()));

					tempNode = new Node(tempConn, explored.getLast());
					tempNode.setCumulativeDist(explored.getLast().getCumulativeDist());
					tempNode.setCurrentScore(tempNode.getCumulativeDist() + ConnectionPoint.getConnectioncost()
							+ tempNode.calcHeuristic(tempNode.getPoint()));

					if (!(explored.containsSamePoint(tempNode))) {

						if (!(frontier.isBetter(tempNode))) {
							frontier.add(tempNode);
						}
					}
				}

				else {
					// get the valid neighbors from the last Node on the
					// explored
					// list
					ArrayList<Point> neigh = explored.getLast().getPoint().getValidNeighbors();
					for (int j = 0; j < neigh.size(); j++) {
						tempNode = new Node(neigh.get(j), explored.getLast());
						tempNode.setCumulativeDist(explored.getLast().getCumulativeDist());
						tempNode.setCurrentScore(
								tempNode.getCumulativeDist() + tempNode.setHeuristic(tempNode.calcHeuristic(goal)));

						// check if Node is in Explored
						if (!(explored.containsSamePoint(tempNode))) {

							if (!(frontier.isBetter(tempNode))) {
								frontier.add(tempNode);
							}
						}
					}
				}
			}
		}

		// form the path
		tempNode = new Node(null, null);
		tempNode = explored.getLast();
		while ((tempNode != null) && (!(tempNode.getPoint().equals(start)))) {
			returnPath.addNode(tempNode);
			tempNode = tempNode.getParent();
		}
		returnPath.addNode(tempNode);

		returnPath.reverse();
		return new MultiPath(returnPath);
	}

}