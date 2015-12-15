package WPI.CampusMap.Backend.PathPlanning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * The path processor is responsible for processing the path finding algorithm.
 * 
 * @author Benny
 *
 */
public abstract class PathProcessor {
	private HashSet<Node> explored;
	private PriorityQueue<Node> frontier;
	private IPoint[] keyPoints;
	static protected Node goal;

	public PathProcessor() {

	}

	/**
	 * Gets the key points that the path finder should visit.
	 * 
	 * @return The key points that the path finder should visit.
	 */
	protected final IPoint[] getKeyPoints() {
		return keyPoints;
	}

	/**
	 * Executes the path processors path finding.
	 * 
	 * @param keyPoints
	 *            the destinations to pathfind to
	 * @return The found path.
	 * @throws PathNotFoundException
	 *             thrown when no path is found
	 * @throws NotEnoughPointsException
	 *             thrown when not enough points are present to create a path
	 *             {0,1}
	 */
	protected Path execute(IPoint[] keyPoints) throws PathNotFoundException, NotEnoughPointsException {
		if (keyPoints.length == 0) {
			throw new NotEnoughPointsException("Only " + keyPoints.length + " points provided. Minimum of 2 required.");
		}
		if (keyPoints.length == 1) {
			throw new NotEnoughPointsException("Only " + keyPoints.length + " point provided. Minimum of 2 required.");
		}
		this.keyPoints = keyPoints;

		// no previous end for the first node
		Node previousEnd = null;
		for (int i = 1; i < keyPoints.length; i++) {
			explored = new HashSet<Node>();
			frontier = new PriorityQueue<Node>(getNodeCompartor());

			HashSet<String> whiteList = AllMaps.getInstance().generateWhitelist(keyPoints[i - 1].getMap(),
					keyPoints[i].getMap());
			System.out.println("WhiteList:" + whiteList);

			goal = new Node(keyPoints[i], null, 0);
			Node currentNode = new Node(keyPoints[i - 1], previousEnd, 0);

			// System.out.println(currentNode.point);
			if (previousEnd != null) {
				// System.out.println(previousEnd.point);
			}
			while (!currentNode.equals(goal)) {
				addToExplored(currentNode);
				expandNode(currentNode, whiteList);
				currentNode = pullFromFrontier();
				if (currentNode == null) {
					throw new PathNotFoundException(keyPoints[i - 1], keyPoints[i]);
				}
			}
			previousEnd = currentNode;

		}
		goal = null;
		if (previousEnd == null)
			throw new PathNotFoundException(keyPoints[0], keyPoints[keyPoints.length - 1]);
		Path path = new Path(previousEnd);
		return path;
	}

	/**
	 * Processes a node.
	 * 
	 * @param node
	 *            The node to process.
	 */
	protected void processNode(Node node) {
		NodeProcessor nProcessor = getProcessorChain();
		if (nProcessor != null)
			nProcessor.execute(node, goal);
	}

	/**
	 * Expands a node to all valid neighbors.
	 * 
	 * @param node
	 *            The node to expand.
	 */
	protected void expandNode(Node node) {
		expandNode(node, new HashSet<String>());
	}

	/**
	 * Expands a node to all valid neighbors.
	 * 
	 * @param node
	 *            The node to expand.
	 * @param whiteList
	 *            list of maps allowed to load
	 */
	private void expandNode(Node node, HashSet<String> whiteList) {
		ArrayList<IPoint> neighbors = node.getNeighbors(whiteList);
		for (IPoint point : neighbors) {
//			if (point.getId().equals("SL2BDST")) {
//				System.out.println("SL2BDST's neighbor:" + node.getPoint());
//			}
			if (point.exists()) {
				Node newNode = new Node(point, node, node.getDistance(point));
				processNode(newNode);
				if (!alreadyExplored(newNode)) {
					pushToFrontier(newNode);
				}
			} else {
				System.out.println(
						node.getPoint() + "'s neighbor: " + point + " does not exist, not adding to front end");
			}
		}

	}

	/**
	 * Gets the comparator to use to determine which node is "best".
	 * 
	 * @return The comparator to use while determining which node is best.
	 */
	protected abstract Comparator<Node> getNodeCompartor();

	/**
	 * Gets the node processors to use while processing a path.
	 * 
	 * @return The node processor chain to use while processing a path.
	 */
	protected abstract NodeProcessor getProcessorChain();

	/**
	 * Pushes a node onto the frontier.
	 * 
	 * @param node
	 *            The node to push onto the frontier.
	 * @return True if the node could be pushed onto the frontier, otherwise
	 *         false.
	 */
	protected final boolean pushToFrontier(Node node)
	{
		if(explored.contains(node)){
			System.out.println("Found node in explored");
		}
		for(Node fNode: frontier.toArray(new Node[0])){
			if(fNode.equals(node)){
//				System.out.println("Frontier already contains " + node.getPoint() + ", replacing with shorter value");
//				System.out.println("Current: "+ fNode.getAccumulatedDistance() +" New: " + node.getAccumulatedDistance() );
				frontier.remove(fNode);
				if(fNode.getAccumulatedDistance() < node.getAccumulatedDistance()){
					node = fNode;
				}
//				System.out.println("Adding node with value: "+ node.getAccumulatedDistance());
//				System.out.println();
				break;
			}
		}
		return frontier.add(node);
	}

	/**
	 * Pulls the top node from the frontier.
	 * 
	 * @return The top node from the frontier.
	 */
	protected final Node pullFromFrontier() {
		return frontier.poll();
	}

	/**
	 * Adds a node to the explored set.
	 * 
	 * @param node
	 *            The node to add to the explored set.
	 */
	protected final void addToExplored(Node node) {
		explored.add(node);
	}

	/**
	 * Checks if a node has already been explored.
	 * 
	 * @param node
	 *            The node to check.
	 * @return If the node has been checked
	 */
	protected final boolean alreadyExplored(Node node) {
		return explored.contains(node);
	}
}
