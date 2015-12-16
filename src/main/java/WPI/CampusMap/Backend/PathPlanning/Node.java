package WPI.CampusMap.Backend.PathPlanning;

import java.util.ArrayList;
import java.util.HashSet;

import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * Represents a traveled node in the path finding.
 * @author Benny
 *
 */
public final class Node
{
	private IPoint point;
	private Node last;
	private float accumulatedDistance;
	private float heuristic;
	/**
	 * Represents a root node.
	 * @param point The point that this node represents.
	 */
	public Node(IPoint point)
	{
		this.point = point;
		this.accumulatedDistance = 0;
	}
	
	/**
	 * Creates a new node.
	 * @param point The point that this node represents.
	 * @param last The last node that we traveled from.
	 * @param distanceFromLast The cost of traveling from the last node to this node.
	 */
	public Node(IPoint point, Node last, float distanceFromLast)
	{
		this.point = point;
		this.last = last;
		if(last != null){
			this.accumulatedDistance = last.getAccumulatedDistance()+ distanceFromLast;
		} else {
			this.accumulatedDistance = 0;
		}
		
	}
	
	/**
	 * Gets the last node traveled.
	 * @return last traveled node
	 */
	public Node getLast()
	{
		return last;
	}
	
	/**
	 * Gets the valid neighbors of the contained point.
	 * @param whitelist list of allowed maps
	 * @return the valid neighbors
	 */
	public ArrayList<IPoint> getNeighbors(HashSet<String> whitelist)
	{
		return point.getValidNeighbors(whitelist);
	}
	
	/**
	 * Gets the valid neighbors of the contained point.
	 * @return the valid neighbors
	 */
	public ArrayList<IPoint> getNeighbors()
	{
		return point.getNeighborsP();
	}
	
	/**
	 * Gets distance  to the given point.
	 * @param point Point to measure distance to
	 * @return the distance
	 */
	public float getDistance(IPoint point)
	{
		return (float) this.point.distance(point);
	}
	
	/**
	 * Gets the total cost of traveling to this node (D + H).
	 * @return Distance + Heuristic
	 */
	public float getTotalCost()
	{
		return accumulatedDistance + heuristic;
	}
	
	/**
	 * The accumulated distance traveled to get to this node.
	 * @return The accumulated distance to get to this node.
	 */
	public float getAccumulatedDistance()
	{
		return accumulatedDistance;
	}
	
	/**
	 * Sets the accumulated distance traveled to get to this node.
	 * @param newDistance The new distance to replace the current one.
	 */
	public void setAccumulatedDistance(float newDistance)
	{
		this.accumulatedDistance = newDistance;
	}
	
	/**
	 * The heuristic cost of this node.
	 * @return The heuristic cost of this node.
	 */
	public float getHeuristicCost()
	{
		return heuristic;
	}
	
	/**
	 * Modifies the heuristic to change by the delta amount.
	 * @param delta The amount to change the heuristic by.
	 */
	protected void modifyHeuristicCost(float delta)
	{
		heuristic += delta;
	}
	
	/**
	 * Sets the heuristic to a new value. This should be used with caution if not used properly will break the processor chain.
	 * @param newCost The new heuristic cost of this node.
	 */
	protected void setHeuristicCost(float newCost)
	{
		this.heuristic = newCost;
	}
	
	@Override
	public boolean equals(Object other){
		if (other instanceof Node) {
			Node that = (Node) other;
			if(!that.point.exists()){
				System.out.println("Can not compute equals due to unavailible point: "+this.point+", "+ that.point);
//				return false;
			}
			boolean result = that.point.equals(point);
//			System.out.println("Result"+ result);
			return result;
		}
		return false;
		
	}
	
	@Override
	public int hashCode() 
	{
		return point.hashCode();
	}
	
	public IPoint getPoint(){
		return point;
	}

}
