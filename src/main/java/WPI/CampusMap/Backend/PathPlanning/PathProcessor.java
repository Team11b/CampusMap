package WPI.CampusMap.Backend.PathPlanning;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * The path processor is responsible for processing the path finding algorithm.
 * @author Benny
 *
 */
public abstract class PathProcessor
{
	private ArrayList<Node> explored;
	private PriorityQueue<Node> frontier;
	private IPoint[] keyPoints;
	static protected Node goal;
	
	public PathProcessor()
	{
		
	}
	
	/**
	 * Gets the key points that the path finder should visit.
	 * @return The key points that the path finder should visit.
	 */
	protected final IPoint[] getKeyPoints()
	{
		return keyPoints;
	}
	
	/**
	 * Executes the path processors path finding.
	 * @return The found path.
	 * @throws PathNotFoundException
	 */
	protected Path execute(IPoint[] keyPoints) throws PathNotFoundException
	{
		this.keyPoints = keyPoints;
		
		//no previous end for the first node
		Node previousEnd = null;
		for(int i = 1; i < keyPoints.length; i++){
			explored = new ArrayList<Node>();
			frontier = new PriorityQueue<Node>(getNodeCompartor());
			
			goal = new Node(keyPoints[i], null, 0);
			Node currentNode = new Node(keyPoints[i - 1], previousEnd , 0);
			
//			System.out.println(currentNode.point);
			if(previousEnd != null){
//				System.out.println(previousEnd.point);
			}
//			System.out.println();
			
			while(!currentNode.equals(goal) ){
				addToExplored(currentNode);
				expandNode(currentNode);
				currentNode = pullFromFrontier();
				if(currentNode == null){
					throw new PathNotFoundException(keyPoints[i-1], keyPoints[i]);
				}
			}
			previousEnd = currentNode;
			
		}
		goal = null;
		Path path = new Path(previousEnd);
		return path;
	}
	
	/**
	 * Processes a node.
	 * @param node The node to process.
	 */
	protected void processNode(Node node)
	{
		NodeProcessor nProcessor = getProcessorChain();
		if(nProcessor != null) nProcessor.execute(node,goal);
	}
	
	/**
	 * Expands a node to all valid neighbors.
	 * @param node The node to expand.
	 */
	protected void expandNode(Node node)
	{
		ArrayList<IPoint> neighbors= node.getNeighbors(new ArrayList<String>());
		for(IPoint point: neighbors){
			Node newNode = new Node(point, node, node.getDistance(point));
			processNode(newNode);
			if(!alreadyExplored(newNode))pushToFrontier(newNode);
		}
	}
	
	/**
	 * Gets the comparator to use to determine which node is "best".
	 * @return The comparator to use while determining which node is best.
	 */
	protected abstract Comparator<Node> getNodeCompartor();
	
	/**
	 * Gets the node processors to use while processing a path.
	 * @return The node processor chain to use while processing a path.
	 */
	protected abstract NodeProcessor getProcessorChain();
	
	/**
	 * Pushes a node onto the frontier.
	 * @param node The node to push onto the frontier.
	 * @return True if the node could be pushed onto the frontier, otherwise false.
	 */
	protected final boolean pushToFrontier(Node node)
	{
		for(Node fNode: frontier.toArray(new Node[0])){
			if(fNode.equals(node)){
//				System.out.println("Frontier already contains " + node.point + ", replacing with shorter value");
				frontier.remove(fNode);
				if(node.getAccumulatedDistance() < fNode.getAccumulatedDistance()){
					node = fNode;
				}
				break;
			}
		}
		
		return frontier.add(node);
	}
	
	/**
	 * Pulls the top node from the frontier.
	 * @return The top node from the frontier.
	 */
	protected final Node pullFromFrontier()
	{
		return frontier.poll();
	}
	
	/**
	 * Adds a node to the explored set.
	 * @param node The node to add to the explored set.
	 */
	protected final void addToExplored(Node node)
	{
		explored.add(node);
	}
	
	/**
	 * Checks if a node has already been explored.
	 * @param node The node to check.
	 * @return If the node has been checked
	 */
	protected final boolean alreadyExplored(Node node)
	{
		return explored.contains(node);
	}
}
