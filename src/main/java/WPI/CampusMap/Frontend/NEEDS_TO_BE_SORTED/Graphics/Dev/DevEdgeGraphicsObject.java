package WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.Dev;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Hashtable;

import WPI.CampusMap.Backend.Core.Coordinate.Coord;
import WPI.CampusMap.Backend.Core.Pair.UnorderedPair;
import WPI.CampusMap.Backend.Core.Point.Point;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Dev.EditorToolMode;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.GraphicsObject;
import WPI.CampusMap.Frontend.NEEDS_TO_BE_SORTED.Graphics.RealMouseEvent;

/**
 * A visual representation of an edge between two s.
 * @author Benny
 *
 */
public class DevEdgeGraphicsObject extends GraphicsObject<UnorderedPair<Point, Point>, DevGraphicalMap>
{
	private static Hashtable<UnorderedPair<Point, Point>, DevEdgeGraphicsObject> edgeLookupTable = new Hashtable<>();
	
	public static void cleanupEdges()
	{
		edgeLookupTable.clear();
	}
	
	/**
	 * Creates a unique edge on the current map.
	 * @param p1 The first  to create an edge between.
	 * @param p2 The second  to create an edge between.
	 * @param owner The graphical map owner.
	 * @return The graphical edge that was either created or already exist.
	 */
	public static DevEdgeGraphicsObject createGraphicsEdge(Point p1, Point p2, DevGraphicalMap owner)
	{
		UnorderedPair<Point, Point> pair = new UnorderedPair<Point, Point>(p1, p2);
		DevEdgeGraphicsObject go = edgeLookupTable.get(pair);
		
		if(go != null)
			return go;
		
		go = new DevEdgeGraphicsObject(p1, p2, owner);
		
		return go;
	}
	
	public static DevEdgeGraphicsObject getGraphicsEdge(Point p1, Point p2, DevGraphicalMap owner)
	{
		UnorderedPair<Point, Point> pair = new UnorderedPair<Point, Point>(p1, p2);
		DevEdgeGraphicsObject go = edgeLookupTable.get(pair);
		
		if(go != null)
			return go;
		
		return null;
	}
	
	private UnorderedPair<Point, Point> edge;
	
	private boolean isOver;
	
	private DevEdgeGraphicsObject(Point p1, Point p2, DevGraphicalMap owner) 
	{
		super(new UnorderedPair<Point, Point>(p1, p2), owner);
		// TODO Auto-generated constructor stub
		edge = getRepresentedObject();
		edgeLookupTable.put(edge, this);
	}

	@Override
	public int getDrawBatch() {
		// TODO Auto-generated method stub
		return -1;
	}

	@Override
	public void onDraw(Graphics2D graphics)
	{
		Coord screenCoord1 = getOwner().getScreenCoord(edge.getA().getCoord());
		Coord screenCoord2 = getOwner().getScreenCoord(edge.getB().getCoord());
		
		graphics.setStroke(new BasicStroke(3));
		graphics.drawLine((int)screenCoord1.getX(), (int)screenCoord1.getY(), (int)screenCoord2.getX(), (int)screenCoord2.getY());
		graphics.setStroke(new BasicStroke(1));
	}
	
	@Override
	public void onDeleted()
	{
		getOwner().getMap().removeEdge(edge.getA(), edge.getB());
		edgeLookupTable.remove(edge);
	}
	
	@Override
	public Color getColor()
	{
		return isOver ? Color.yellow : Color.gray;
	}

	@Override
	public void onMouseOver(RealMouseEvent e)
	{
		isOver = true;
	}

	@Override
	public void onMouseLeave(RealMouseEvent e) 
	{
		isOver = false;
	}

	@Override
	public void onMouseMove(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onMouseClick(RealMouseEvent e) 
	{
		if(getOwner().getMode() != EditorToolMode.DeleteEdge)
			return;
		
		delete();
	}

	@Override
	public void onMouseDrag(RealMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isMouseOver(RealMouseEvent e)
	{
		if(getOwner().getMode() != EditorToolMode.DeleteEdge)
			return false;
		
		Coord screenCoord1 = getOwner().getScreenCoord(edge.getA().getCoord());
		Coord screenCoord2 = getOwner().getScreenCoord(edge.getB().getCoord());
		
		Line2D.Float line = new Line2D.Float(screenCoord1.getX(), screenCoord1.getY(), screenCoord2.getX(), screenCoord2.getY());
		
		return line.ptSegDist(e.getX(), e.getY()) <= 5.0f;
	}
}