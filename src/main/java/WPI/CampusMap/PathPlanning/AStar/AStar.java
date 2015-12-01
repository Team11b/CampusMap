package WPI.CampusMap.PathPlanning.AStar;

import java.util.ArrayList;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;
import WPI.CampusMap.PathPlanning.ConnectionNode;
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
			Path returnPath = new Path();
			Node tempNode = new Node(start, null);
			returnPath.addNode(tempNode);
			returnPath.addNode(new Node(goal, tempNode));
			return returnPath;
		}
	

		boolean goalFound = false;

		// Instantiate frontier and explored lists
		Frontier frontier = new Frontier(Frontier.stdNodeComp);
		Explored explored = new Explored();

		// Instantiate path
		Path returnPath = new Path();

		Node tempNode = new Node(start, null);
		tempNode.setHeuristic(tempNode.calcHeuristic(goal));

		// add start to frontier as a Node
		frontier.add(tempNode);

		while ((!frontier.isEmpty()) && (!(goalFound))) {
			goalFound = frontier.contains(new Node(goal, null));

			if (!(goalFound)) {
				explored.add(frontier.getNext());

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
	public static MultiPath multi_AStar(Point start, Point goal) {
		if (start.equals(goal)) {
			Path returnPath = new Path();
			Node tempNode = new Node(start, null);
			returnPath.addNode(tempNode);
			returnPath.addNode(new Node(goal, tempNode));
			return new MultiPath(returnPath);
		}		

		boolean goalFound = false;

		// Instantiate frontier and explored lists
		Frontier frontier = new Frontier(Frontier.stdNodeComp);
		Explored explored = new Explored();

		// Instantiate path
		Path returnPath = new Path();

		System.out.println("Start == null " + (start == null));
		Node tempNode = new Node(start, null);
//		tempNode.setHeuristic(tempNode.calcHeuristic(goal));
		ConnectionPoint tempConn = new ConnectionPoint(null, null, null, null, null, null);
		ConnectionNode tempConNode = new ConnectionNode(null, null, false);

		// add start to frontier as a Node
		System.out.println("Temp node == null " + (tempNode == null));
		System.out.println("tempNode.getPoint == null " + (tempNode.getPoint() == null));
		frontier.add(tempNode);

		while ((!frontier.isEmpty()) && (!(goalFound))) {
			goalFound = frontier.contains(new Node(goal, null));

			if (!(goalFound)) {
				explored.add(frontier.getNext());

				if (!(goalFound)) {
					tempNode = new Node(null, null);
					tempConNode = new ConnectionNode(null, null, false);
					tempConn = new ConnectionPoint(null, null, null, null, null, null);
					Node centerPoint = explored.getLast();

					if ((centerPoint.getPoint() instanceof ConnectionPoint)
							&& (((ConnectionNode) centerPoint).getEntryPoint())) {
						tempConn = (ConnectionPoint) (explored.getLast().getPoint());

						Map tempMap = Map.getMap(tempConn.getLinkedMap());
						tempConn.setConnMap(tempMap);
						tempConn.setConnPoint((ConnectionPoint) tempMap.getPoint(tempConn.getLinkedPoint()));

						tempConNode = new ConnectionNode(tempConn.getConnPoint(), explored.getLast(), false);
						tempConNode.setCumulativeDist(explored.getLast().getCumulativeDist() + explored.getLast().getPoint().distance(tempConn));
						tempConNode.setCurrentScore(
								tempConNode.getCumulativeDist() + tempConNode.calcHeuristic(goal));

						if (!(explored.containsSamePoint(tempConNode))) {

							// frontier.isBetter(tempNode);
							frontier.add(tempConNode);
						}
					}

					else {
						// get the valid neighbors from the last Node on the
						// explored
						// list
						ArrayList<Point> neigh = centerPoint.getPoint().getValidNeighbors();
						System.out.println(centerPoint+ "'s neighbors: " +neigh);
						for (int j = 0; j < neigh.size(); j++) {
							tempNode = new Node(null, null);
							tempConNode = new ConnectionNode(null, null, false);

							if (neigh.get(j) instanceof ConnectionPoint) {
								tempConNode = new ConnectionNode(neigh.get(j), explored.getLast(), true);

								tempConNode.setCumulativeDist(explored.getLast().getCumulativeDist() + explored.getLast().getPoint().distance(tempConNode.getPoint()));
								tempConNode.setCurrentScore(tempConNode.getCumulativeDist()
										+ tempConNode.setHeuristic(tempConNode.calcHeuristic(goal)));

								// check if Node is in Explored
								if (!(explored.containsSamePoint(tempConNode))) {

									frontier.isBetter(tempConNode);
								}
							}

							else {
								tempNode = new Node(neigh.get(j), explored.getLast());

								tempNode.setCumulativeDist(explored.getLast().getCumulativeDist() + explored.getLast().getPoint().distance(tempNode.getPoint()));
								tempNode.setCurrentScore(tempNode.getCumulativeDist()
										+ tempNode.setHeuristic(tempNode.calcHeuristic(goal)));

								// check if Node is in Explored
								if (!(explored.containsSamePoint(tempNode))) {

									frontier.isBetter(tempNode);
								}
							}
						}
					}
				}
			}else{System.out.println("Goal Found");}
		}

		// form the path
		tempNode = new Node(null, null);
		tempNode = frontier.find(new Node(goal, null));
		System.out.println("HERE");
		System.out.println("Frontier itself null " + (frontier == null));
		System.out.println("Frontier .find null " + (frontier.find(new Node(goal, null)) == null));
		System.out.println("Temp node == null " + (tempNode == null));
		System.out.println("tempNode.getPoint == null " + (tempNode.getPoint() == null));
		System.out.println("start== null" + (start == null));
		System.out.println("tempNode.getPoint().equals(start)" + (tempNode.getPoint().equals(start)));
		while ((tempNode != null) && (!(tempNode.getPoint().equals(start)))) {
			System.out.println("Looping");
			returnPath.addNode(tempNode);
			tempNode = tempNode.getParent();
		}

		returnPath.addNode(tempNode);


		returnPath.reverse();
		System.out.println(returnPath.getPath().size());
		return new MultiPath(returnPath);
	}
}