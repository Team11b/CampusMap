package WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.AStar;

import java.util.ArrayList;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Map.Map;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.MultiPath;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.Path.Path;
import WPI.CampusMap.Backend.TravelPaths_DEPRECATED.PathFinding.Node.Node;

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
	@Deprecated
	public static Path single_AStar(Point start, Point goal) {
		if (start.equals(goal)) {
			Path returnPath = new Path(Map.getMap(start.getMap()).getScale());
			Node tempNode = new Node(start, null, goal);
			returnPath.addNode(tempNode);
			returnPath.addNode(new Node(goal, tempNode, goal));
			return returnPath;
		}

		boolean goalFound = false;

		// Instantiate frontier and explored lists
		Frontier frontier = new Frontier();
		Explored explored = new Explored();

		// Instantiate path
		Path returnPath = new Path(Map.getMap(start.getMap()).getScale());

		Node tempNode = new Node(start, null, goal);
		tempNode.setHeuristic(tempNode.calcHeuristic(goal));

		// add start to frontier as a Node
		frontier.addToFrontier(tempNode);

		while ((!frontier.isEmpty()) && (!(goalFound))) {
			goalFound = frontier.contains(new Node(goal, null, goal));

			if (!(goalFound)) {
				explored.add(frontier.getNext());

				if (!(goalFound)) {
					// get the valid neighbors from the last Node on the
					// explored
					// list
					Node centerPoint = explored.getLast();
					ArrayList<Point> neigh = centerPoint.getPoint().getValidNeighbors(null);
					for (int j = 0; j < neigh.size(); j++) {
						tempNode = new Node(null, null, goal);
						tempNode = new Node(neigh.get(j), explored.getLast(), goal);

						tempNode.setCumulativeDist(explored.getLast().getCumulativeDist());
						tempNode.setCurrentScore(
								tempNode.getCumulativeDist() + tempNode.setHeuristic(tempNode.calcHeuristic(goal)));

						// check if Node is in Explored
						if (!(explored.containsSamePoint(tempNode))) {

//							frontier.isBetter(tempNode);
						}
					}
				}
			}
		}
//		tempNode = frontier.find(new Node(goal, null, goal));
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
	public static MultiPath multi_AStar(Point start, Point goal) 
	{
		Frontier frontier = new Frontier();
		
		Node startNode = new Node(start, null, goal);
		frontier.addToVisited(startNode);
		start.buildFrontier(frontier, startNode, goal);
		
		Node front = frontier.visitFront();
		while(front != null && !front.getPoint().equals(goal))
		{
			
			front.getPoint().buildFrontier(frontier, front, goal);
			front = frontier.visitFront();
			
			if(front.getPoint().getId().equals(goal.getId()) && front.getPoint().getMap().equals(goal.getMap()))
				System.out.println("D");
		}
		
		if(front == null)
			return null;
		
		LinkedList<Node> pathList = new LinkedList<Node>();
		for(Node node = front; node != null; node = node.getParent())
		{
			pathList.addFirst(node);
		}
		
		MultiPath path = new MultiPath();
		for(Node node : pathList)
		{
			path.add(node);
		}
		
		return path;
	}
}