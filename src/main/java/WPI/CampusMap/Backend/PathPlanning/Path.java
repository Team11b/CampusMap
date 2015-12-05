package WPI.CampusMap.Backend.PathPlanning;

import java.util.Iterator;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * Represents a path across maps.
 * @author Benny
 *
 */
public class Path implements Iterable<Path.Section>
{
	/**
	 * A section of a path.
	 * @author Benny
	 *
	 */
	public class Section implements Iterable<IPoint>
	{
		/**
		 * Creates a path section.
		 * @param map What map this path section belongs to.
		 */
		public Section(IMap map)
		{
			throw new UnsupportedOperationException("not implemented");
		}

		@Override
		public Iterator<IPoint> iterator()
		{
			throw new UnsupportedOperationException("not implemented");
		}
	}
	
	/**
	 * Creates a path from a node chain.
	 * @param nodeChain The node chain to create the path from.
	 */
	public Path(Node nodeChain)
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Creates a path from a list of points.
	 * @param points The points to create the path from.
	 */
	public Path(IPoint[] points)
	{
		
	}

	@Override
	public Iterator<Section> iterator() 
	{
		throw new UnsupportedOperationException("not implemented");
	}
	
	/**
	 * Gets a list of path sections for the specified map.
	 * @param map The map to get the path sections for.
	 * @return A linked list of path sections.
	 */
	public LinkedList<Section> getSections(IMap map)
	{
		throw new UnsupportedOperationException("not implemented");
	}
}
