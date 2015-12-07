package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * Represents a traveled node in the path finding.
 * @author Benny
 *
 */
public final class Node
{
	/**
	 * Represents a root node.
	 * @param point The point that this node represents.
	 */
	public Node(IPoint point)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Creates a new node.
	 * @param point The point that this node represents.
	 * @param last The last node that we traveled from.
	 * @param distanceFromLast The cost of traveling from the last node to this node.
	 */
	public Node(IPoint point, Node last, float distanceFromLast)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Gets the last node traveled.
	 * @return
	 */
	public Node getLast()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Gets the total cost of traveling to this node (D + H).
	 * @return Distance + Heuristic
	 */
	public float getTotalCost()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * The accumulated distance traveled to get to this node.
	 * @return The accumulated distance to get to this node.
	 */
	public float getAccumulatedDistance()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * The heuristic cost of this node.
	 * @return The heuristic cost of this node.
	 */
	public float getHeuristicCost()
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Modifies the heuristic to change by the delta amount.
	 * @param delta The amount to change the heuristic by.
	 */
	protected void modifyHeuristicCost(float delta)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Sets the heuristic to a new value. This should be used with caution if not used properly will break the processor chain.
	 * @param newCost The new heuristic cost of this node.
	 */
	protected void setHeuristicCost(float newCost)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}