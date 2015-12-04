package WPI.CampusMap.PathPlanning;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Set;

import WPI.CampusMap.Backend.ConnectionPoint;
import WPI.CampusMap.Backend.Map;
import WPI.CampusMap.Backend.Point;

/**
 * 
 * @author Jacob Zizmor
 *
 */
public class MultiPath
{
	@SuppressWarnings("unchecked")
	public static MultiPath join(MultiPath a, MultiPath b)
	{
		MultiPath newMultiPath = new MultiPath();
		
		for(Path path : a.mp)
		{
			Path newPath = new Path(path.getPathScale());
			
			newPath.setPath((ArrayList<Node>)path.getPath().clone());
			
			String mapName = newPath.getPath().get(0).getPoint().getMap();
			newMultiPath.mp.add(newPath);
			LinkedList<Path> parts = newMultiPath.pathLookup.get(mapName);
			
			if(parts == null)
			{
				parts = new LinkedList<Path>();
			}
			
			parts.add(newPath);
			newMultiPath.pathLookup.put(mapName, parts);
		}
		
		for(Path path : b.mp)
		{
			Path newPath = new Path(path.getPathScale());
			
			newPath.setPath((ArrayList<Node>)path.getPath().clone());
			
			String mapName = newPath.getPath().get(0).getPoint().getMap();
			newMultiPath.mp.add(newPath);
//			System.out.println(newMultiPath.pathLookup);
			LinkedList<Path> parts = newMultiPath.pathLookup.get(mapName);
			
			if(parts == null)
			{
				parts = new LinkedList<Path>();
			}
			
			parts.add(newPath);
			newMultiPath.pathLookup.put(mapName, parts);
		}
		
		return newMultiPath;
	}
	
	private LinkedList<Path> mp;
	
	private Hashtable<String, LinkedList<Path>> pathLookup = new Hashtable<String, LinkedList<Path>>();

	public MultiPath() {
		mp = new LinkedList<Path>();
	}

	public MultiPath(Path start) {
		mp = new LinkedList<Path>();
		pathLookup = new Hashtable<>();
		parse(start);
	}

	public LinkedList<Path> getMp() {
		return mp;
	}

	public void setMp(LinkedList<Path> mp) {
		this.mp = mp;
	}
	
	public LinkedList<Path> getMapPath(Map map)
	{
		return getMapPath(map.getName());
	}
	
	public LinkedList<Path> getMapPath(String mapName)
	{
		return pathLookup.get(mapName);
	}
	
	public Set<String> getReferencedMaps()
	{
		return pathLookup.keySet();
	}

	/**
	 * Adds a path to the MultiPath
	 * 
	 * @param p
	 *            a Path to add
	 */
	public void add(Path p) {
		this.mp.add(p);
		LinkedList<Path> parts = pathLookup.get(p.getMapName());
		
		if(parts == null)
		{
			parts = new LinkedList<Path>();
		}
		
		parts.add(p);
		pathLookup.put(p.getMapName(), parts);
	}
	
	public void add(Node n)
	{
		Point p = n.getPoint();
		
		Path path;
		if(mp.size() == 0 || !mp.getLast().getMapName().equals(p.getMap()))
		{
			path = new Path(Map.getMap(p.getMap()).getScale());
			path.setMapName(p.getMap());
			add(path);
		}
		else 
		{
			path = mp.getLast();
		}
		
		path.addNode(n);
	}

	public Path get(int i)
	{
		return this.mp.get(i);
	}

	/**
	 * Parses an input path and splits it into multiple paths Each sub-path is
	 * added to this.mp A path is separated between two adjacent Nodes with
	 * ConnectionPoints
	 * 
	 * @param start
	 *            a path to be split
	 */
	@Deprecated
	public void parse(Path start) {
		/*ArrayList<Node> bigPath = start.getPath();
		Path part = new Path(start.getPathScale());
		Node node = new Node(null, null);
		int count = 0;
		int index = 0;

//		System.out.println("Size" + bigPath.size());
//		System.out.println(start);
//		System.out.println(start.getPath());
//		System.out.println("---" + bigPath.get(0));
//		System.out.println(bigPath.get(0).getPoint());
		if(!(bigPath.get(0).getPoint() instanceof ConnectionPoint)){
			count++;
		}
		
		while (index < bigPath.size()) {
			part = new Path(start.getPathScale());
			node = new Node(null, null);//?

			while ((index < bigPath.size())) {
				node = new Node(null, null);//???
				node = bigPath.get(index);

				if ((node.getPoint() instanceof ConnectionPoint)) {
					part.addNode(node);
					index++;
					count++;
					if(count == 2){
						count = 0;
						break;
					}
					
				} else {
					part.addNode(node);
					index++;
				}
			}
			this.mp.add(part);
			
			LinkedList<Path> parts = this.pathLookup.get(node.getPoint().getMap());
			if(parts == null)
			{
				parts = new LinkedList<Path>();
			}
			
			parts.add(part);
			this.pathLookup.put(node.getPoint().getMap(), parts);
		}*/
	}

	public int size() {
		return this.mp.size();
	}

}
