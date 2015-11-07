package WPI.CampusMap.AStar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Map {

	private String png;
	private Point[] map;

	public Map(String png, Point[] map) {
		this.png = png;
		this.map = map;
	}

	public String getPng() {
		return png;
	}

	public void setPng(String png) {
		this.png = png;
	}

	public Point[] getMap() {
		return map;
	}

	public void setMap(Point[] map) {
		this.map = map;
	}

	/**
	 * Creates a Path of points using the A* algorithm. Uses this map as a map.
	 * Will return null if either the start or the goal is an invalid location,
	 * a wall.
	 * 
	 * @param start
	 *            the starting Point
	 * @param goal
	 *            the goal Point
	 * @return a Path of points or null if either the start or goal is invalid
	 */
	public Path astar(Point start, Point goal) {		
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

		// add start to frontier as a Node
		frontier.add(new Node(start, null, 0));

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
			
			if (explored.get(explored.size() - 1).getPoint().equals(goal))) {
				goalFound = true;
			}

			if (!(goalFound)) {
				// get the valid neighbors from the last Node on the explored list
				Point[] neigh = explored.get(explored.size() - 1).getPoint().getValidNeighbors();

				for (int j = 0; j < neigh.length; j++) {
					frontier.add(new Node(neigh[j], explored.get(explored.size() - 1),
							explored.get(explored.size() - 1).getCumulativeDist()
									+ explored.get(explored.size() - 1).getPoint().distance(neigh[j])));
				}
			}
		}
		
		// form the path
		Node tempNode = explored.get(explored.size() - 1);
		while (!(tempNode.getPoint().equals(start))) {
			returnPath.addNode(tempNode);
			tempNode = tempNode.getParent();
		}

		return returnPath;
	}

	public static void main(String[] args) {
		String file = "hi";
		Point[] map = new Point[1];
		map[0] = new Point(null, file, 0);
		Map test = new Map(file, map);

		test.astar(new Point(null, file, 0), new Point(null, file, 0));
	}
}
