package WPI.CampusMap.Frontend.Graphics.Dev;

import java.util.HashMap;

import WPI.CampusMap.Backend.Core.Pair.UnorderedPair;
import WPI.CampusMap.Backend.Core.Point.IPoint;
import WPI.CampusMap.Backend.Core.Point.RealPoint;
import WPI.CampusMap.Frontend.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.Graphics.GraphicalMap;
import WPI.CampusMap.Frontend.Graphics.RealMouseEvent;
import WPI.CampusMap.Frontend.UI.DevMode;

public class DevGraphicalMap extends GraphicalMap
{
	private HashMap<UnorderedPair<IPoint, IPoint>, DevEdgeGraphicsObject> edges = new HashMap<>();

	public DevGraphicalMap(String map, DevMode mode)
	{		
		super(map, mode);
	}

	@Override
	public void spawnMap()
	{		
		for(IPoint p : getMap().getAllPoints())
		{
			new DevPointGraphicsObject((RealPoint)p, this);
		}
	}
	
	@Override
	public boolean onMouseClick(RealMouseEvent e) 
	{
		return false;
	}
	
	/**
	 * Gets a graphical edge between two graphics points.
	 * @param a The first graphics point.
	 * @param b The second graphics point.
	 * @return The graphical edge between the two points.
	 */
	protected DevEdgeGraphicsObject getGraphicalEdge(IPoint a, IPoint b)
	{
		UnorderedPair<IPoint, IPoint> pair = new UnorderedPair<IPoint, IPoint>(a, b);
		
		DevEdgeGraphicsObject edge = edges.get(pair);
		if(edge != null)
			return edge;
		
		edge = new DevEdgeGraphicsObject(pair.getA(), pair.getB(), this);
		edges.put(pair, edge);
		
		return edge;
	}

	protected void unregisterGraphicalEdge(DevEdgeGraphicsObject devEdgeGraphicsObject) 
	{
		UnorderedPair<IPoint, IPoint> pair = devEdgeGraphicsObject.getRepresentedObject();
		
		edges.remove(pair);
		
		getMap().removeEdge(pair.getA(), pair.getB());
	}
}
