package WPI.CampusMap.Backend.PathPlanning;

import WPI.CampusMap.Backend.Core.Point.IPoint;

public class PathFinder
{
	/**
	 * Gets a path between all destinations.
	 * @param destinations An array of destinations.
	 * @param processor The path processor to use for processing the path finding.
	 * @return The path between all destinations
	 * @throws PathNotFoundException Thrown when no path can be found.
	 * @throws NotEnoughPointsException 
	 */
	public static Path getPath(IPoint[] destinations, PathProcessor processor) throws PathNotFoundException, NotEnoughPointsException
	{
		return processor.execute(destinations);
	}
	
	/**
	 * Gets a path between all destinations using a default path processor
	 * @param destinations An array of destinations.
	 * @return The path between all destinations
	 * @throws PathNotFoundException Thrown when no path can be found.
	 */
	public static Path getPath(IPoint[] destinations) throws PathNotFoundException
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Gets a path between two points.
	 * @param a The start point.
	 * @param b The end point.
	 * @param processor The path processor to use for processing the path finding.
	 * @return A path between the start and end point.
	 * @throws PathNotFoundException Thrown when no path can be found.
	 * @throws NotEnoughPointsException 
	 */
	public static Path getPath(IPoint a, IPoint b, PathProcessor processor) throws PathNotFoundException, NotEnoughPointsException
	{
		IPoint[] destinations = new IPoint[2];
		destinations[0] = a;
		destinations[1] = b;
		
		return getPath(destinations, processor);
	}
	
	/**
	 * Gets a path between two points using a default path processor.
	 * @param a The start point.
	 * @param b The end point.
	 * @return A path between the start and end point.
	 * @throws PathNotFoundException Thrown when no path can be found.
	 */
	public static Path getPath(IPoint a, IPoint b) throws PathNotFoundException
	{
		IPoint[] destinations = new IPoint[2];
		destinations[0] = a;
		destinations[1] = b;
		
		return getPath(destinations);
	}
}
