package WPI.CampusMap.Backend.PathPlanning;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import WPI.CampusMap.Backend.Core.Map.AllMaps;
import WPI.CampusMap.Backend.Core.Map.IMap;
import WPI.CampusMap.Backend.Core.Point.IPoint;

/**
 * Represents a path across maps.
 * @author Benny
 *
 */
public class Path implements Iterable<Path.Section>
{	
	LinkedList<Section> sections = new LinkedList<Section>();
	/**
	 * A section of a path.
	 * @author Benny
	 *
	 */
	public class Section implements Iterable<IPoint>
	{
		IMap map;
		LinkedList<IPoint> points;
		/**
		 * Creates a path section.
		 * @param map What map this path section belongs to.
		 * @param points What points in this section.
		 */
		public Section(IMap map, LinkedList<IPoint> points)
		{
//			System.out.println(points.size());
			this.map = map;
			this.points = points;
		}

		@Override
		public Iterator<IPoint> iterator()
		{
			return points.iterator();
		}
		
		public int size()
		{
//			System.out.println(points.size());
			return points.size();
		}
	}
	
	/**
	 * Creates a path from a node chain.
	 * @param nodeChain The node chain to create the path from.
	 */
	public Path(Node nodeChain)
	{
		String currentMap = nodeChain.point.getMap();
		LinkedList<IPoint> currentSection = new LinkedList<IPoint>();
		currentSection.add(nodeChain.point);
		Node temp = nodeChain;
		nodeChain = nodeChain.getLast();
		
		while(nodeChain != null){
//			System.out.println(temp.point);
			if(!nodeChain.point.getMap().equals(currentMap) || temp.point.equals(nodeChain.point)){
				IMap map = AllMaps.getInstance().getMap(currentMap);
				Collections.reverse(currentSection);
				sections.add(new Section(map, currentSection));
				currentMap = nodeChain.point.getMap();
				currentSection = new LinkedList<IPoint>();
			}
			currentSection.add(nodeChain.point);
			temp = nodeChain;
			nodeChain = nodeChain.getLast();
		}
		
		IMap map = AllMaps.getInstance().getMap(currentMap);
		Collections.reverse(currentSection);
		sections.add(new Section(map, currentSection));
		Collections.reverse(sections);
	}
	
	/**
	 * Creates a path from a list of points.
	 * @param points The points to create the path from.
	 */
	public Path(IPoint[] points)
	{
		String currentMap = points[0].getMap();
		LinkedList<IPoint> currentSection = new LinkedList<IPoint>();
		currentSection.add(points[0]);
		for(int i = 1; i < points.length; i++){
			if(!points[0].getMap().equals(currentMap)){
				IMap map = AllMaps.getInstance().getMap(currentMap);
				sections.add(new Section(map, currentSection));
				currentSection = new LinkedList<IPoint>();
				currentMap = points[0].getMap();
			}
			currentSection.add(points[i]);
		}
		IMap map = AllMaps.getInstance().getMap(currentMap);
		sections.add(new Section(map, currentSection));
	}

	@Override
	public Iterator<Section> iterator() 
	{
		return sections.iterator();
	}
	
	/**
	 * Gets a list of path sections for the specified map.
	 * @param map The map to get the path sections for.
	 * @return A linked list of path sections.
	 */
	public LinkedList<Section> getSections(IMap map)
	{
		return sections;	
	}
	
	/**
	 * Gets the size of the path.
	 * @return The size.
	 * @return
	 */
	public int size(){
		int size = 0;
		for(Section section: this){
			size += section.size();
		}
		return size;
	}
}
