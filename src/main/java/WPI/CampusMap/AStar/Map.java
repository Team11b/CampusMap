package WPI.CampusMap.AStar;

import java.awt.print.Book;
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

		// Instantiate frontier and explored lists
		ArrayList<Node> frontier = new ArrayList<Node>();
		ArrayList<Node> explored = new ArrayList<Node>();

		// add start to frontier as a Node
		frontier.add(new Node(start, null, 0));

		while (!frontier.isEmpty()) {
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

			explored.add(frontier.get(0));
			frontier.remove(0);
		}

		return new Path(null);
	}

	public static void main(String[] args) {
		String file = "hi";
		Point[] map = new Point[1];
		map[0] = new Point(null, file, 0);
		Map test = new Map(file, map);

		test.astar(new Point(null, file, 0), new Point(null, file, 0));
	}
}
