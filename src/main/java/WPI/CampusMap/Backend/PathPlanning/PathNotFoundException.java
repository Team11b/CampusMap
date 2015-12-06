package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * Exception thrown when a path cannot be resolved between a start and an end point.
 * @author Benny
 *
 */
public class PathNotFoundException extends Exception
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5966857820837092319L;
	
	private IPoint start, goal;
	
	public PathNotFoundException(IPoint start, IPoint goal)
	{
		this.start = start;
		this.goal = goal;
	}
	
	/**
	 * Get the start point of the failed path.
	 * @return The start point.
	 */
	public IPoint getStart()
	{
		return start;
	}
	
	/**
	 * Get the goal point of the failed path.
	 * @return The goal point.
	 */
	public IPoint getGoal()
	{
		return goal;
	}
	
	@Override
	public String getMessage()
	{
		return String.format("Could not create path between {0} and {1}.", start, goal);
	}
}
