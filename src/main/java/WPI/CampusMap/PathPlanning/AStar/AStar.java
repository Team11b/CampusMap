package WPI.CampusMap.PathPlanning.AStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
				ArrayList<Point> neigh = explored.get(explored.size() - 1).getPoint().getValidNeighbors();
				for (int j = 0; j < neigh.size(); j++) {
					tempNode = new Node(neigh.get(j), explored.get(explored.size() - 1));
					// check if Node is in Explored
					if (explored.contains(tempNode)) {
						if (explored.contains(tempNode)) {
							frontier.add(new Node(neigh.get(j), explored.get(explored.size() - 1)));
							frontier.get(frontier.size() - 1)
									.setCumulativeDist(explored.get(explored.size() - 1).getCumulativeDist()
											+ frontier.get(frontier.size() - 1).getPoint()
													.distance(explored.get(explored.size() - 1).getPoint()));
							frontier.get(frontier.size() - 1)
									.setCurrentScore(frontier.get(frontier.size() - 1).getCumulativeDist()
											+ frontier.get(frontier.size() - 1).getHeuristic());
						} else {
							if (tempNode.getCurrentScore() < frontier.get(otherIndex).getCurrentScore()) {
								frontier.set(otherIndex, new Node(neigh.get(j), explored.get(explored.size() - 1)));
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
		returnPath.setScale(8);
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
		return null;
	}

}
