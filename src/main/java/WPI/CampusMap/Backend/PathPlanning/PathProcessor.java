package WPI.CampusMap.Backend.PathPlanning;

import java.util.Comparator;

import org.jboss.resteasy.spi.NotImplementedYetException;
import org.jboss.resteasy.spi.UnauthorizedException;

import WPI.CampusMap.Backend.Core.Point.Point;

/**
 * The path processor is responsible for processing the path finding algorithm.
 * @author Benny
 *
 */
public abstract class PathProcessor
{
	public PathProcessor(Point[] keyPoints)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Gets the key points that the path finder should visit.
	 * @return The key points that the path finder should visit.
	 */
	protected final Point[] getKeyPoints()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Executes the path processors path finding.
	 * @return The found path.
	 * @throws PathNotFoundException
	 */
	protected Path execute() throws PathNotFoundException
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Processes a node.
	 * @param node The node to process.
	 */
	protected void processNode(Node node)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Expands a node to all valid neighbors.
	 * @param node The node to expand.
	 */
	protected void expandNode(Node node)
	{
		throw new UnsupportedOperationException("not implemented");
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
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Pulls the top node from the frontier.
	 * @return The top node from the frontier.
	 */
	protected final Node pullFromFrontier()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Adds a node to the expored set.
	 * @param node The node to add to the expored set.
	 * @return
	 */
	protected final Node addToExplored(Node node)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
